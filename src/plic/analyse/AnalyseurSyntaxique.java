package plic.analyse;

import plic.repint.Boolean;
import plic.repint.*;
import plic.repint.operateur.OperateurDeComparaison.*;
import plic.repint.operateur.OperateurEntier.Multiplication;
import plic.repint.operateur.OperateurEntier.Somme;
import plic.repint.operateur.OperateurEntier.Soustraction;
import plic.repint.operateur.OperateurLogique.Et;
import plic.repint.operateur.OperateurLogique.Non;
import plic.repint.operateur.OperateurLogique.Ou;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

import static java.lang.Integer.parseInt;

public class AnalyseurSyntaxique {
    private AnalyseurLexical analex;
    private String uniteCourante;
    public final boolean logger = false;

    public AnalyseurSyntaxique(File file) {
        try {
            this.analex = new AnalyseurLexical(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        }
    }

    public Bloc analyse() throws ErreurSyntaxique, DoubleDeclaration, ErreurSemantique {
        // Demander la construction de la première unité lexicale
        this.uniteCourante = this.analex.next();
        Bloc blocCourant = new Bloc();
        this.analyseProg(blocCourant);
        if (!this.uniteCourante.equals("EOF"))
            throw new ErreurSyntaxique("Le programme ne se termine pas par EOF");
        return blocCourant;
    }

    /**
     * Programme => programme idf
     *
     * @throws ErreurSyntaxique Si non conforme
     */
    private void analyseProg(Bloc blocCourant) throws ErreurSyntaxique, DoubleDeclaration, ErreurSemantique {
        if (!this.uniteCourante.equals("programme"))
            throw new ErreurSyntaxique("programme attendu");
        this.uniteCourante = this.analex.next();
        if (!this.estIdf())
            throw new ErreurSyntaxique("Le nom du programme n'est pas correct");
        this.uniteCourante = this.analex.next();
        this.analyseBloc(blocCourant);
        this.uniteCourante = this.analex.next();
    }

    /**
     * Bloc => { DECLARATION* INSTRUCTION* }
     *
     * @throws ErreurSyntaxique Si non conforme
     */
    private void analyseBloc(Bloc blocCourant) throws ErreurSyntaxique, DoubleDeclaration, ErreurSemantique {
        if (logger) System.out.println("\tAnalyse du bloc");
        this.analyseTerminal("{");
        // Itérer sur analyseDeclaration tant qu’il y a des déclarations
        if (logger) System.out.println("\tBoucle d'analyse des déclarations");
        while (this.uniteCourante.equals("entier") || this.uniteCourante.equals("tableau") || this.uniteCourante.equals("boolean")) {
            this.analyseDeclaration();
        }
        if (logger) System.out.println("\nFin de l'analyse des déclarations");
        // Itérer sur analyseInstruction tant qu’il y a des instructions
        if (logger) System.out.println("Analyse des instructions");
        try {
            this.analyseInstruction(blocCourant);
        } catch (ErreurSyntaxique e) {
            if (logger) System.out.println("Erreur syntaxique sur la seule instruction: " + e.getMessage());
            throw new ErreurSyntaxique("Il faut au moins une instruction, ou première instruction incorrect : " + e.getMessage());
        }
        if (logger)
            System.out.println("\t|Fin de la première inscruction, caractère courant : " + this.uniteCourante);
        while (this.uniteCourante.equals("ecrire") || this.estIdf() || this.uniteCourante.equals("lire") || this.uniteCourante.equals("si") || this.uniteCourante.equals("pour") || this.uniteCourante.equals("tantque")) {
            this.analyseInstruction(blocCourant);
        }
        if (logger)
            System.out.println("Fin de l'analyse des instructions");
        this.analyseTerminal("}");
    }

    /**
     * Déclaration => TYPE idf
     * Add the variable to the TDS
     *
     * @throws ErreurSyntaxique
     */
    private void analyseDeclaration() throws ErreurSyntaxique, DoubleDeclaration {
        if (logger) System.out.println("\t\tAnalyse de la déclaration");
        String type = this.analyseType();
        switch (type) {
            case "entier":
                Symbole symbole = new SymboleEntier("entier");
                Entree entree = new Entree(this.uniteCourante);
                TDS.getInstance().ajouter(entree, symbole);

                //Two nexts to skip the idf and the ;
                this.uniteCourante = this.analex.next();
                analyseTerminal(";");
                break;
            case "tableau":
                // maybe it's an array like [ nombre ] idf
                this.analyseTerminal("[");
                if (!this.estCsteEntiere()) {
                    throw new ErreurSyntaxique("constante entière attendue");
                }
                int taille = parseInt(this.uniteCourante);
                // skip the number
                this.uniteCourante = this.analex.next();
                this.analyseTerminal("]");
                if (!this.estIdf()) {
                    throw new ErreurSyntaxique("idf attendu après la déclaration d'un tableau");
                }
                String idf = this.uniteCourante;
                this.uniteCourante = this.analex.next();
                this.analyseTerminal(";");
                if (taille <= 0) throw new ErreurSyntaxique("La taille d'un tableau doit être positive");
                Symbole symboleTableau = new SymboleTableau("tableau", taille);
                Entree entreeTableau = new Entree(idf);
                TDS.getInstance().ajouter(entreeTableau, symboleTableau);
                break;
            case "boolean":
                Symbole symboleBoolean = new SymboleBoolean("boolean");
                Entree entreeBoolean = new Entree(this.uniteCourante);
                TDS.getInstance().ajouter(entreeBoolean, symboleBoolean);

                //Two nexts to skip the idf and the ;
                this.uniteCourante = this.analex.next();
                analyseTerminal(";");
                break;
            default:
                throw new ErreurSyntaxique("Type non reconnu");
        }

    }

    /**
     * Caractère courant doit être un entier ou un tableau
     *
     * @throws ErreurSyntaxique Si non conforme
     */
    private String analyseType() throws ErreurSyntaxique {
        if (logger)
            System.out.println("\t\t\t-Analyse du type: " + this.uniteCourante);
        String type = this.uniteCourante;
        this.uniteCourante = this.analex.next();
        switch (type) {
            case "entier":
                return "entier";
            case "tableau":
                return "tableau";
            case "boolean":
                return "boolean";
            default:
                throw new ErreurSyntaxique("type \"entier\" ou \"tableau\" attendu");
        }
    }


    /**
     * Vérifie si l'unité courante est un terminal
     *
     * @param terminal Terminal attendu
     * @throws ErreurSyntaxique Si non conforme
     */
    private void analyseTerminal(String terminal) throws ErreurSyntaxique {
        if (!this.uniteCourante.equals(terminal))
            throw new ErreurSyntaxique("Analyse terminal: \"" + terminal + "\" attendu alors que la ligne courante est " + this.uniteCourante);
        this.uniteCourante = this.analex.next();
    }

    /**
     * Instruction => ES | AFFECTATION
     *
     * @throws ErreurSyntaxique Si non conforme
     */
    private void analyseInstruction(Bloc blocCourant) throws ErreurSyntaxique, ErreurSemantique, DoubleDeclaration {
        if (logger) System.out.println("\tAnalyse d'une instruction");
        switch (this.uniteCourante) {
            case "ecrire":
                if (logger) System.out.println("\t\tAnalyse d'une ES");
                var instruction = this.analyseES();
                blocCourant.ajouter(instruction);
                break;
            case "lire":
                analyseTerminal("lire");
                Acces idf = this.analyseAcces();
                this.analyseTerminal(";");
                blocCourant.ajouter(new Lire(new Idf(idf.toString())));
                break;
            case "si":
                if (logger) System.out.println("\t\tAnalyse d'un si");
                //si ( EXPRESSION ) alors BLOC sinon BLOC
                this.uniteCourante = this.analex.next();
                this.analyseTerminal("(");
                var expression = this.analyseExpression();
                this.analyseTerminal(")");
                this.analyseTerminal("alors");
                var blocAlors = new Bloc();
                this.analyseBloc(blocAlors);
                if (this.uniteCourante.equals("sinon")) {
                    this.analyseTerminal("sinon");
                    var blocSinon = new Bloc();
                    this.analyseBloc(blocSinon);
                    blocCourant.ajouter(new Si(expression, blocAlors, blocSinon));
                } else {
                    blocCourant.ajouter(new Si(expression, blocAlors));
                }

                break;
            case "pour":
                if (logger) System.out.println("\t\tAnalyse d'un pour");
                this.analyseTerminal("pour");
                if (!this.estIdf()) {
                    throw new ErreurSyntaxique("idf attendu après pour");
                }
                String idfPour = this.uniteCourante;
                this.uniteCourante = this.analex.next();
                this.analyseTerminal("dans");
                Expression debut = this.analyseExpression();
                this.analyseTerminal("..");
                Expression fin = this.analyseExpression();
                this.analyseTerminal("repeter");
                Bloc blocPour = new Bloc();
                this.analyseBloc(blocPour);
                blocCourant.ajouter(new Pour(idfPour, debut, fin, blocPour));
                break;
            case "tantque":
                analyseTerminal("tantque");
                analyseTerminal("(");
                Expression expressionTantQue = analyseExpression();
                analyseTerminal(")");
                analyseTerminal("repeter");
                Bloc blocTantQue = new Bloc();
                analyseBloc(blocTantQue);
                blocCourant.ajouter(new TantQue(expressionTantQue, blocTantQue));
                break;
            default:
                if (logger) System.out.println("\t\tAnalyse d'une affectation");
                var affectation = this.analyseAffectation();
                blocCourant.ajouter(affectation);
                break;
        }
        if (logger) System.out.println("\tFin de l'analyse d'une instruction");
    }

    /**
     * ES => ecrire EXPRESSION
     *
     * @throws ErreurSyntaxique Si non conforme
     */
    private Instruction analyseES() throws ErreurSyntaxique, ErreurSemantique {
        this.analyseTerminal("ecrire");
        if (logger) System.out.println("\t\tAnalyse ES");
        var expression = this.analyseExpression();
        this.analyseTerminal(";");
        if (logger) System.out.println("\t\tFin analyse ES");
        return new Ecrire(expression);
    }


    private static final Set<String> OPERATORS = Set.of("+", "-", "*", "et", "ou", "<", ">", "=", "#", "<=", ">=");

    /**
     * OPERANDE →
     * csteEntiere
     * ACCES
     * - ( EXPRESSION )
     * non EXPRESSION
     * ( EXPRESSION )
     *
     * @return
     */
    private Expression analyseOperande() throws ErreurSyntaxique, ErreurSemantique {
        if (estCsteEntiere()) {
            var nombre = new Nombre(parseInt(this.uniteCourante));
            this.uniteCourante = this.analex.next();
            return nombre;
        }
        if (estCsteBoolean()) {
            var bool = new Boolean(this.uniteCourante);
            this.uniteCourante = this.analex.next();
            return bool;
        }
        //! non can be an idf
        if (this.uniteCourante.equals("non")) {
            this.uniteCourante = this.analex.next();
            var expression = this.analyseExpression();
            return new Non(expression);
        }
        if (this.uniteCourante.equals("(")) {
            this.uniteCourante = this.analex.next();
            var expression = this.analyseExpression();
            this.analyseTerminal(")");
            return expression;
        }
        if (this.uniteCourante.equals("-")) {
            this.uniteCourante = this.analex.next();
            this.analyseTerminal("(");
            var expression = this.analyseExpression();
            this.analyseTerminal(")");
            return new Soustraction(expression);
        }
        if (estIdf()) {
            return this.analyseAcces();
        }

        throw new ErreurSyntaxique("constante entière ou idf attendu");
    }


    /**
     * AFFECTATION → ACCES := EXPRESSION
     *
     * @throws ErreurSyntaxique Si non conforme
     */
    private Affectation analyseAffectation() throws ErreurSyntaxique, ErreurSemantique {
        Acces idf = this.analyseAcces();
        this.analyseTerminal(":=");
        var expression = analyseExpression();
        this.analyseTerminal(";");
        return new Affectation(expression, idf);
    }

    /**
     * ACCES → idf
     *
     * @throws ErreurSyntaxique Si non conforme
     */
    private Acces analyseAcces() throws ErreurSyntaxique, ErreurSemantique {
        if (!this.estIdf()) {
            throw new ErreurSyntaxique("idf attendu");
        }
        var idf = new Idf(this.uniteCourante);
        this.uniteCourante = this.analex.next();
        if (this.uniteCourante.equals("[")) {
            this.uniteCourante = this.analex.next();
            Expression expression = this.analyseOperande();
            this.analyseTerminal("]");
            return new AccesTableau(idf, expression);
        }
        return idf;
    }

    /**
     * Vérifie si l'unité courante est un identificateur
     *
     * @return true si c'est un identificateur
     */
    private boolean estIdf() {
        return estIdf(this.uniteCourante);
    }

    private boolean estIdf(String idf) {
        if (idf.equals("programme") || idf.equals("entier") || idf.equals("ecrire")) return false;
        return idf.matches("\\b(?!EOF)[a-zA-Z]\\w*\\b"); // si on mets un numero en premier caractère, ça ne marche pas
    }

    /**
     * idf[expression]
     *
     * @return true si c'est un identificateur
     */
    private boolean estIdfAcces() {
        String[] split = this.uniteCourante.split("\\[");
        if (split.length == 1) return false;
        if (estIdf(split[0])) return false;
        split = split[1].split("\\]");
        if (split.length == 1) return false;
        return this.uniteCourante.matches("\\b(?!EOF)[a-zA-Z]\\w*\\b(\\[\\d+\\])"); // si tu mets un numero en premier caractère, ça ne marche pas
    }

    /**
     * Vérifie si l'unité courante est une constante entière
     *
     * @return true si c'est une constante entière
     */
    private boolean estCsteEntiere() {
        return estCsteEntiere(this.uniteCourante);
    }

    private boolean estCsteEntiere(String uniteCourante) {
        try {
            int type = parseInt(uniteCourante);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Vérifie si l'unité courante est une constante booléenne
     *
     * @return true si c'est une constante booléenne
     */
    private boolean estCsteBoolean() {
        return this.uniteCourante.equals("vrai") || this.uniteCourante.equals("faux");
    }

    /**
     * EXPRESSION → OPERANDE
     *
     * @throws ErreurSyntaxique Si non conforme
     *                          Ne fait pas de vérification sur la fin de l'expression (comme ";")
     */
    private Expression analyseExpression() throws ErreurSyntaxique, ErreurSemantique {
        if (logger) System.out.println("Analyse expression");
        Expression operandGauche = this.analyseOperande();
        if (!estOperateur()) {
            return operandGauche;
        }

        String operateur = this.uniteCourante;
        this.uniteCourante = this.analex.next();
        Expression operandDroite = this.analyseOperande();
        return switch (operateur) {
            case "+" -> new Somme(operandGauche, operandDroite);
            case "-" -> new Soustraction(operandGauche, operandDroite);
            case "*" -> new Multiplication(operandGauche, operandDroite);
            case "et" -> new Et(operandGauche, operandDroite);
            case "ou" -> new Ou(operandGauche, operandDroite);
            case "<" -> new Inferieur(operandGauche, operandDroite);
            case ">" -> new Superieur(operandGauche, operandDroite);
            case "=" -> new Equals(operandGauche, operandDroite);
            case "#" -> new NotEquals(operandGauche, operandDroite);
            case "<=" -> new InferieurEgal(operandGauche, operandDroite);
            case ">=" -> new SuperieurEgal(operandGauche, operandDroite);
            default -> throw new ErreurSyntaxique("Opérateur non reconnu");
        };
    }

    private boolean estOperateur() {
        return OPERATORS.contains(this.uniteCourante);
    }
}

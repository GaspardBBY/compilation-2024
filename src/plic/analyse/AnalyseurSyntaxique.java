package plic.analyse;

import plic.repint.*;
import plic.repint.Expression;
import plic.repint.Idf;
import plic.repint.Nombre;
import plic.repint.Affectation;
import plic.repint.Ecrire;
import plic.repint.Instruction;

import java.io.File;
import java.io.FileNotFoundException;

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

    public Bloc analyse() throws ErreurSyntaxique, DoubleDeclaration {
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
    private void analyseProg(Bloc blocCourant) throws ErreurSyntaxique, DoubleDeclaration {
        if (!this.uniteCourante.equals("programme"))
            throw new ErreurSyntaxique("programme attendu");
        this.uniteCourante = this.analex.next();
        if (!this.estIdf())
            throw new ErreurSyntaxique("idf attendu");
        this.uniteCourante = this.analex.next();
        this.analyseBloc(blocCourant);
        this.uniteCourante = this.analex.next();
    }

    /**
     * Bloc => { DECLARATION* INSTRUCTION* }
     *
     * @throws ErreurSyntaxique Si non conforme
     */
    private void analyseBloc(Bloc blocCourant) throws ErreurSyntaxique, DoubleDeclaration {
        if (logger) System.out.println("\tAnalyse du bloc");
        this.analyseTerminal("{");
        // Itérer sur analyseDeclaration tant qu’il y a des déclarations
        if (logger) System.out.println("\tBoucle d'analyse des déclarations");
        while (this.uniteCourante.equals("entier")) {
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
        while (this.uniteCourante.equals("ecrire") || this.estIdf()) {
            this.analyseInstruction(blocCourant);
        }
        if (logger)
            System.out.println("Fin de l'analyse des instructions");
        this.analyseTerminal("}");
    }

    /**
     * Déclaration => TYPE idf
     *
     * @throws ErreurSyntaxique
     */
    private void analyseDeclaration() throws ErreurSyntaxique, DoubleDeclaration {
        if (logger) System.out.println("\t\tAnalyse de la déclaration");
        this.analyseType();
        if (!this.estIdf()) {
            throw new ErreurSyntaxique("idf attendu");
        }

        Symbole symbole = new Symbole("entier");
        Entree entree = new Entree(this.uniteCourante);
        TDS.getInstance().ajouter(entree, symbole);

        //Two nexts to skip the idf and the ;
        this.uniteCourante = this.analex.next();
        analyseTerminal(";");
    }

    /**
     * Caractère courant doit être un entier
     *
     * @throws ErreurSyntaxique Si non conforme
     */
    private void analyseType() throws ErreurSyntaxique {
        if (logger)
            System.out.println("\t\t\t-Analyse du type: " + this.uniteCourante + "=>" + this.uniteCourante.equals("entier"));
        if (!this.uniteCourante.equals("entier"))
            throw new ErreurSyntaxique("entier attendu");
        this.uniteCourante = this.analex.next();
    }


    /**
     * Vérifie si l'unité courante est un terminal
     *
     * @param terminal Terminal attendu
     * @throws ErreurSyntaxique Si non conforme
     */
    private void analyseTerminal(String terminal) throws ErreurSyntaxique {
        if (!this.uniteCourante.equals(terminal))
            throw new ErreurSyntaxique("Analyse terminal: \"" + terminal + "\" attendu");
        this.uniteCourante = this.analex.next();
    }

    /**
     * Instruction => ES | AFFECTATION
     *
     * @throws ErreurSyntaxique Si non conforme
     */
    private void analyseInstruction(Bloc blocCourant) throws ErreurSyntaxique {
        if (logger) System.out.println("\tAnalyse d'une instruction");
        if (this.uniteCourante.equals("ecrire")) {
            if (logger) System.out.println("\t\tAnalyse d'une ES");
            var instruction = this.analyseES();
            blocCourant.ajouter(instruction);
        } else {
            if (logger) System.out.println("\t\tAnalyse d'une affectation");
            var instruction = this.analyseAffectation();
            blocCourant.ajouter(instruction);
            if (logger) System.out.println("\t\tAffectation ajoutée");
        }
        if (logger) System.out.println("\tFin de l'analyse d'une instruction");
    }

    /**
     * ES => ecrire EXPRESSION
     *
     * @throws ErreurSyntaxique Si non conforme
     */
    private Instruction analyseES() throws ErreurSyntaxique {
        this.analyseTerminal("ecrire");
        if (logger) System.out.println("\t\tAnalyse ES");
        var expression = this.analyseExpression();
        if (logger) System.out.println("\t\tFin analyse ES");
        return new Ecrire(expression);
    }

    /**
     * EXPRESSION → OPERANDE
     *
     * @throws ErreurSyntaxique Si non conforme
     */
    private Expression analyseExpression() throws ErreurSyntaxique {
        if (logger) System.out.println("Analyse expression");
        Expression operand = this.analyseOperande();
        this.uniteCourante = this.analex.next();
        this.analyseTerminal(";");
        return operand;
    }

    /**
     * OPERANDE → entier
     *
     * @return
     */
    private Expression analyseOperande() throws ErreurSyntaxique {
        if (estCsteEntiere()) return new Nombre(parseInt(this.uniteCourante));
        if (estIdf()) return new Idf(this.uniteCourante);
        throw new ErreurSyntaxique("constante entière ou idf attendu");
    }

    /**
     * AFFECTATION → ACCES := EXPRESSION
     *
     * @throws ErreurSyntaxique Si non conforme
     */
    private Affectation analyseAffectation() throws ErreurSyntaxique {
        var idf = this.analyseAcces();
        this.analyseTerminal(":=");
        var expression = analyseExpression();
        return new Affectation(expression, idf);
    }

    /**
     * ACCES → idf
     *
     * @throws ErreurSyntaxique Si non conforme
     */
    private Idf analyseAcces() throws ErreurSyntaxique {
        if (!this.estIdf())
            throw new ErreurSyntaxique("idf attendu");
        var idf = new Idf(this.uniteCourante);
        this.uniteCourante = this.analex.next();
        return idf;
    }

    /**
     * Vérifie si l'unité courante est un identificateur
     *
     * @return true si c'est un identificateur
     */
    private boolean estIdf() {
        if (this.uniteCourante.equals("programme") || this.uniteCourante.equals("entier") || this.uniteCourante.equals("ecrire"))
            return false;
        return this.uniteCourante.matches("\\b(?!EOF)[a-zA-Z]\\w*\\b"); // si tu mets un numero en premier caractère, ça ne marche pas
    }

    /**
     * Vérifie si l'unité courante est une constante entière
     *
     * @return true si c'est une constante entière
     */
    private boolean estCsteEntiere() {
        try {
            int type = parseInt(this.uniteCourante);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

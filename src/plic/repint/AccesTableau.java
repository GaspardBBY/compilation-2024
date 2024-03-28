package plic.repint;

public class AccesTableau extends Acces {
    private final Idf idf;
    private final Expression expression;


    public AccesTableau(Idf nom, Expression expression) {
        this.idf = nom;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return idf + " " + "[ " + expression.toString() + " ]";
    }

    @Override
    public String getTypes() {
        return "entier";
    }

    @Override
    public String getIdf() {
        return idf.getIdf();
    }

    /**
     * Vérifie que l'identifiant est bien déclaré et que l'indice est bien dans les bornes du tableau
     *
     * @throws ErreurSemantique si l'identifiant n'est pas déclaré ou si l'indice est négatif ou hors limite
     */
    @Override
    public void verifier() throws ErreurSemantique {
        if (!TDS.getInstance().contain(new Entree(idf.getIdf()))) {
            throw new ErreurSemantique("Erreur : identifiant " + idf + " non déclaré");
        }

        if (!expression.getTypes().equals("entier")) {
            throw new ErreurSemantique("l'indice du tableau doit être un entier");
        }
    }

    @Override
    public String toMips() {
        StringBuilder sb = new StringBuilder();
        sb.append("# toMips de acces tableau\n");
        sb.append(this.getAdresse());
        sb.append("lw $v0, 0($a0)\n");
        return sb.toString();
    }

    @Override
    public String getAdresse() {
        var sb = new StringBuilder();
        var symbole = TDS.getSymbole(idf.getIdf());
        int deplacement = symbole.getDeplacement();
        sb.append("# On met l'adresse de l'expression dans $a0\n");
        sb.append(expression.toMips());
        sb.append("# On multiplie la valeur dans $v0 par 4\n");
        sb.append("addiu $a0, $sp, ").append(deplacement).append("\n");
        sb.append("mul $v0, $v0, -4\n");
        sb.append("# On additionne le déplacement initial de l'idf\n");
        sb.append("addu $a0, $a0, $v0\n");
        return sb.toString();
    }

    /**
     * Debug function to print the content of a var
     *
     * @param var the var to print
     * @return the mips code to print the var
     */
    public static String ecrire(String var) {
        var sb = new StringBuilder();
        sb.append("# Ecrire ").append(var).append("\n");
        sb.append("move $a0, ").append(var).append("\n");
        sb.append("li $v0, 1\n");
        sb.append("syscall\n");
        sb.append("la $a0, linebreak\n");
        sb.append("li $v0, 4\n");
        sb.append("syscall\n");
        return sb.toString();
    }


}

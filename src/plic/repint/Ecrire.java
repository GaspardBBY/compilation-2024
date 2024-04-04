package plic.repint;

public class Ecrire extends Instruction {
    Expression exp;

    public Ecrire(Expression exp) {
        this.exp = exp;
    }

    public String toString() {
        return "ecrire " + exp.toString() + ";";
    }

    /**
     * Vérifie que l'expression est bien déclarée, si c'est une variable on vérifie qu'elle est bien déclarée
     *
     * @throws ErreurSemantique si l'expression n'est pas déclarée
     */
    @Override
    public void verifier() throws ErreurSemantique {
        if (exp.getTypes().equals("entier") || exp.getTypes().equals("boolean")) return;
        if (TDS.getSymbole(((Acces) exp).getIdf()) == null)
            throw new ErreurSemantique("Variable " + exp.toString() + " non déclarée, impossible de l'afficher !");

    }

    @Override
    public String toMips() {
        StringBuilder sb = new StringBuilder();

        sb.append("\t# Ecrire ").append(exp.toString()).append("\n");

        sb.append(exp.toMips());
        // Instructions pour afficher la valeur de $v0
        sb.append("\tmove $a0, $v0\n");
        sb.append("\tli $v0, 1\n");
        sb.append("\tsyscall\n");

        // saut de ligne
        sb.append("\tla $a0, linebreak\n");
        sb.append("\tli $v0, 4\n");
        sb.append("\tsyscall\n");
        return sb.toString();
    }
}

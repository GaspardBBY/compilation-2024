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
     * @throws ErreurSementique si l'expression n'est pas déclarée
     */
    @Override
    public void verifier() throws ErreurSementique {
        System.out.println("verification dd'une expression de type  " + exp.getTypes());
        if (exp.getTypes().equals("nombre")) return;

        if (!(exp instanceof Acces)) {
            // dev error, if exp is not an Number, it should be an Acces
            throw new Error("A type is not handled");
        }
        if (TDS.getSymbole(((Acces) exp).getNom()) == null)
            throw new ErreurSementique("Variable " + exp.toString() + " non déclarée, impossible de l'afficher !");


    }

    @Override
    public String toMips() {
        StringBuilder sb = new StringBuilder();
        sb.append(exp.toMips());

        // Instructions pour afficher la valeur de $t1
        sb.append("move $a0, $t1\n");
        sb.append("li $v0, 1\n");
        sb.append("syscall\n");

        // saut de ligne
        sb.append("la $a0, linebreak\n");
        sb.append("li $v0, 4\n");
        sb.append("syscall\n");
        return sb.toString();
    }
}

package plic.repint;

/**
 * Affectation
 * de la forme : idf := exp;
 */
public class Affectation extends Instruction {

    Expression exp;
    Acces acces;

    public Affectation(Expression exp, Acces acces) {
        this.exp = exp;
        this.acces = acces;
    }

    public String toString() {
        return acces.toString() + " := " + exp.toString() + ";";
    }

    /**
     * Vérification que si l'expression est une variable, elle est déclarée
     *
     * @throws ErreurSementique si on tente d'affecter une valeur à une variable non déclarée
     */
    @Override
    public void verifier() throws ErreurSementique {
        boolean contained = TDS.getSymbole(acces.getIdf()) != null;
        if (!contained) {
            throw new ErreurSementique("Variable " + acces.toString() + " non déclarée");
        }

    }

    @Override
    public String toMips() {
        StringBuilder sb = new StringBuilder();
        sb.append("# Affectation\n");
        sb.append(exp.toMips());
        sb.append("# Utilisation d'une variable temporaire\n");
        sb.append("move $t0, $v0\n");
        sb.append(acces.getAdresse());
        sb.append("move $v0, $t0\n");
        sb.append("sw $v0, 0($a0)\n");
        return sb.toString();
    }


}

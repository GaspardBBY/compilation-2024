package plic.repint;

public class Si extends Instruction {
    public Expression expression;
    public Bloc blocAlors;
    public Bloc blocSinon;

    public Si(Expression expression, Bloc blocAlors, Bloc blocSinon) {
        super();
        this.expression = expression;
        this.blocAlors = blocAlors;
        this.blocSinon = blocSinon;
    }

    public Si(Expression expression, Bloc blocAlors) {
        this(expression, blocAlors, null);
    }

    @Override
    public String toString() {
        return "Si(" + expression + ") \n\t\t" + blocAlors.toStringImbriquee() + ", \n\t- Sinon\n\t\t" + blocSinon.toStringImbriquee() + "\n\t- FinSi";
    }

    @Override
    public void verifier() throws ErreurSemantique {

    }

    @Override
    public String toMips() {
        return toMipsSi(0);
    }

    /**
     * Génère le code mips pour le if, puis le blocAlors.toMips et pas blocSinon.toMips
     *
     * @return
     */
    public String toMipsSi(int indiceFin) {
        var sb = new StringBuilder();
        sb.append(expression.toMips());
        sb.append("\t\tbeq $v0, $zero, else").append(indiceFin).append("\n");
        sb.append(blocAlors.toMips());
        sb.append("\tj fin" + indiceFin + "\n");
        return sb.toString();
    }

    public String toMipsElse(int number) {
        var sb = new StringBuilder();
        sb.append("else").append(number).append(":\n");
        if (blocSinon != null) {
            sb.append(blocSinon.toMips());
        }
        return sb.toString();
    }
}

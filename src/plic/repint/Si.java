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
        var sb = new StringBuilder();
        sb.append(expression.toMips());
        sb.append("\tbeq $v0, $zero, else").append(this.hashCode()).append("\n");
        sb.append(blocAlors.toMips());
        sb.append("\tj fin").append(this.hashCode()).append("\n");
        sb.append("else").append(this.hashCode()).append(":\n");
        sb.append(blocSinon.toMips());
        sb.append("fin").append(this.hashCode()).append(":\n");
        return sb.toString();
    }


}

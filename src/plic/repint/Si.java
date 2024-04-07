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
        var sb = new StringBuilder();
        sb.append("Si(").append(expression).append(") \n\t\t").append(blocAlors.toStringImbriquee());
        if (blocSinon != null) {
            sb.append(", \n\t- Sinon\n\t\t").append(blocSinon.toStringImbriquee());
        }
        sb.append("\n\t- FinSi");
        return sb.toString();
    }

    @Override
    public void verifier() throws ErreurSemantique {
        // verification de l'expression
        if (!expression.getTypes().equals("boolean")) {
            throw new ErreurSemantique("l'expression du si n'est pas un booleen");
        }

    }

    @Override
    public String toMips() {
        var sb = new StringBuilder();
        sb.append(expression.toMips());
        sb.append("\tbeq $v0, $zero, else").append(this.hashCode()).append("\n");
        sb.append(blocAlors.toMips());
        sb.append("\tj suite").append(this.hashCode()).append("\n");
        sb.append("else").append(this.hashCode()).append(":\n");
        if (blocSinon != null) {
            sb.append(blocSinon.toMips());
        }
        sb.append("suite").append(this.hashCode()).append(":\n");
        return sb.toString();
    }


}

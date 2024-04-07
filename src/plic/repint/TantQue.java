package plic.repint;

public class TantQue extends Instruction{
    public Expression expression;
    public Bloc bloc;

    public TantQue(Expression expression, Bloc bloc) {
        this.expression = expression;
        this.bloc = bloc;
    }

    @Override
    public String toString() {
        return "tantQue(" + expression + ") \n\t\t" + bloc.toStringImbriquee() + "\n\t- FinTantQue";
    }

    @Override
    public void verifier() throws ErreurSemantique {
        // expression doit être un boolean
        if (!expression.getTypes().equals("boolean")) {
            throw new ErreurSemantique("l'expression du tant que n'est pas un booleen");
        }
    }

    @Override
    public String toMips() {
        var sb = new StringBuilder();
        sb.append("tantQue").append(this.hashCode()).append(":\n");
        sb.append(expression.toMips());
        sb.append("\tbeq $v0, $zero, finTantQue").append(this.hashCode()).append("\n");
        sb.append(bloc.toMips());
        sb.append("\tj tantQue").append(this.hashCode()).append("\n");
        sb.append("finTantQue").append(this.hashCode()).append(":\n");
        return sb.toString();
    }
}

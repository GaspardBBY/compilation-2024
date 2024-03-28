package plic.repint.operateur.OperateurLogique;

import plic.repint.ErreurSementique;
import plic.repint.Expression;

public class Non extends OperateurLogique {
    public Non(Expression uniqueOperand) throws ErreurSementique {
        super(uniqueOperand);
    }

    @Override
    public String toMipsOperation() {
        var sb = new StringBuilder();
        sb.append("nor $v0, $v0, $v0\n");
        sb.append("xori $v0, $v0, -2\n");
        return sb.toString();
    }
}

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
        //Load -1 into another register, then XOR the registers.
        sb.append("li $v1, -1\n");
        sb.append("xor $v0, $v0, $v1\n");
        return sb.toString();
//        return "nor $v0, $v0\n";
    }
}

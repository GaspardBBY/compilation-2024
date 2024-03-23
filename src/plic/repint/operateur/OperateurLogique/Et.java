package plic.repint.operateur.OperateurLogique;

import plic.repint.ErreurSementique;
import plic.repint.Expression;

public class Et extends OperateurLogique {
    public Et(Expression operandGauche, Expression operandDroite) throws ErreurSementique {
        super(operandGauche, operandDroite);
    }

    @Override
    public String toMipsOperation() {
        return "and $v0, $v0, $v1\n";
    }
}

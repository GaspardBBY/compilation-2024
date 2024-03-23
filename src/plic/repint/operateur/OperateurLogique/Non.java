package plic.repint.operateur.OperateurLogique;

import plic.repint.ErreurSementique;
import plic.repint.Expression;

public class Non extends OperateurLogique {
    public Non(Expression operandGauche, Expression operandDroite) throws ErreurSementique {
        super(operandGauche, operandDroite);
    }

    @Override
    public String toMipsOperation() {
        return "not $v0, $v0\n";
    }
}

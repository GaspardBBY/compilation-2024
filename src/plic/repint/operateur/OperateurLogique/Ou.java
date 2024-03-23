package plic.repint.operateur.OperateurLogique;

import plic.repint.ErreurSementique;
import plic.repint.Expression;

public class Ou extends OperateurLogique {
    public Ou(Expression operandGauche, Expression operandDroite) throws ErreurSementique {
        super(operandGauche, operandDroite);
    }

    @Override
    public String toMipsOperation() {
        return "or $v0, $v0, $v1\n";
    }
}

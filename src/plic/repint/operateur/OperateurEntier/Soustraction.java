package plic.repint.operateur.OperateurEntier;

import plic.repint.ErreurSementique;
import plic.repint.Expression;

public class Soustraction extends OperateurArithmetique {
    public Soustraction(Expression operandGauche, Expression operandDroite) throws ErreurSementique {
        super(operandGauche, operandDroite);
    }

    @Override
    public String toMipsOperation() {
        return "sub $v0, $v0, $v1";
    }
}

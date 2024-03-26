package plic.repint.operateur.OperateurEntier;

import plic.repint.ErreurSementique;
import plic.repint.Expression;

public class Soustraction extends OperateurArithmetique {
    public Soustraction(Expression operandGauche, Expression operandDroite) throws ErreurSementique {
        super(operandGauche, operandDroite);
    }

    public Soustraction(Expression uniqueOperand) throws ErreurSementique {
        super(uniqueOperand);
    }

    @Override
    public String toMipsOperation() {
        // if - ( expression )
        if (super.droite == null) {
            return "sub $v0, $zero, $v0";
        }
        return "sub $v0, $v0, $v1\n";
    }
}

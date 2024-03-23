package plic.repint.operateur.OperateurBoolean;

import plic.repint.Expression;
import plic.repint.operateur.Operateur;

public abstract class OperateurBoolean extends Operateur {
    public OperateurBoolean(Expression operandGauche, Expression operandDroite) {
        super(operandGauche, operandDroite);
    }

    @Override
    public String getTypes() {
        return "boolean";
    }

}

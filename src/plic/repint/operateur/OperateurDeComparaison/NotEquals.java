package plic.repint.operateur.OperateurDeComparaison;

import plic.repint.ErreurSementique;
import plic.repint.Expression;


/**
 * This class represents the greater than operator (>)
 */
public class NotEquals extends OperateurDeComparaison {
    public NotEquals(Expression operandGauche, Expression operandDroite) throws ErreurSementique {
        super(operandGauche, operandDroite);
    }

    @Override
    public String toMipsOperation() {
        return "sne $v0, $v0, $v1\n";
    }
}

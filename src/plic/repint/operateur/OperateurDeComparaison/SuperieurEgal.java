package plic.repint.operateur.OperateurDeComparaison;

import plic.repint.ErreurSementique;
import plic.repint.Expression;


/**
 * This class represents the greater than operator (>)
 */
public class SuperieurEgal extends OperateurDeComparaison {
    public SuperieurEgal(Expression operandGauche, Expression operandDroite) throws ErreurSementique {
        super(operandGauche, operandDroite);
    }

    @Override
    public String toMipsOperation() {
        return "sge $v0, $v1, $v0\n";
    }
}

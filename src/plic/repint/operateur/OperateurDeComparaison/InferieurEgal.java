package plic.repint.operateur.OperateurDeComparaison;

import plic.repint.ErreurSementique;
import plic.repint.Expression;


/**
 * This class represents the greater than operator (>)
 */
public class InferieurEgal extends OperateurDeComparaison {
    public InferieurEgal(Expression operandGauche, Expression operandDroite) throws ErreurSementique {
        super(operandGauche, operandDroite);
    }

    @Override
    public String toMipsOperation() {
        return "sle $v0, $v1, $v0\n";
    }
}

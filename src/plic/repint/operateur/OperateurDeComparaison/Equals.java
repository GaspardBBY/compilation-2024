package plic.repint.operateur.OperateurDeComparaison;

import plic.repint.ErreurSemantique;
import plic.repint.Expression;


/**
 * This class represents the greater than operator (>)
 */
public class Equals extends OperateurDeComparaison {
    public Equals(Expression operandGauche, Expression operandDroite) throws ErreurSemantique {
        super(operandGauche, operandDroite);
    }

    @Override
    public String toMipsOperation() {
        return "seq $v0, $v0, $v1\n";
    }
}

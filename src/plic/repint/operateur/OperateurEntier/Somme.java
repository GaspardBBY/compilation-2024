package plic.repint.operateur.OperateurEntier;

import plic.repint.ErreurSementique;
import plic.repint.Expression;

/**
 * Operand à résultat entier
 */
public class Somme extends OperateurArithmetique {

    public Somme(Expression operandGauche, Expression operandDroite) throws ErreurSementique {
        super(operandGauche, operandDroite);
    }

    @Override
    public String toMipsOperation() {
        return "add $v0, $v0, $v1";
    }

}

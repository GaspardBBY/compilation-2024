package plic.repint.operateur.OperateurEntier;

import plic.repint.ErreurSemantique;
import plic.repint.Expression;

public class Multiplication extends OperateurArithmetique {


    public Multiplication(Expression operandGauche, Expression operandDroite) throws ErreurSemantique {
        super(operandGauche, operandDroite);
    }

    @Override
    public String toMipsOperation() {
        return "mul $v0, $v0, $v1\n";
    }

}

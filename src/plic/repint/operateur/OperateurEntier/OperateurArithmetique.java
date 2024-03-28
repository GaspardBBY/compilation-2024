package plic.repint.operateur.OperateurEntier;

import plic.repint.Acces;
import plic.repint.ErreurSemantique;
import plic.repint.Expression;
import plic.repint.operateur.Operateur;

/**
 * opérandes et résultat entier
 */
public abstract class OperateurArithmetique extends Operateur {
    public OperateurArithmetique(Expression operandGauche, Expression operandDroite) throws ErreurSemantique {
        super(operandGauche, operandDroite);
    }

    public OperateurArithmetique(Expression uniqueOperand) throws ErreurSemantique {
        super(uniqueOperand);
    }

    @Override
    public String getTypes() {
        return "entier";
    }

    public void verifierOperande(Expression operand) throws ErreurSemantique {
        if (operand instanceof Acces) {
            System.out.println("types " + operand.getTypes());
            if (operand.getTypes().equals("entier")) {
                return;
            }
            if (!((Acces) operand).getTypeSymbole().equals("entier")) {
                throw new ErreurSemantique("impossible d'effectuer une opération arithmétique sur un accès qui n'est pas un entier");
            }
            return;
        }
        if (!operand.getTypes().equals("entier")) {
            throw new ErreurSemantique("impossible d'effectuer une opération arithmétique sur une expression qui n'est pas un entier");
        }
    }

}

package plic.repint.operateur.OperateurEntier;

import plic.repint.Acces;
import plic.repint.ErreurSementique;
import plic.repint.Expression;
import plic.repint.operateur.Operateur;

/**
 * opérandes et résultat entier
 */
public abstract class OperateurArithmetique extends Operateur {
    public OperateurArithmetique(Expression operandGauche, Expression operandDroite) throws ErreurSementique {
        super(operandGauche, operandDroite);
    }

    @Override
    public String getTypes() {
        return "entier";
    }

    public void verifierOperande(Expression operand) throws ErreurSementique {
        if (operand instanceof Acces) {
            if (!((Acces) operand).getTypeSymbole().equals("entier")) {
                throw new ErreurSementique("impossible d'effectuer une opération arithmétique sur un accès qui n'est pas un entier");
            }
            return;
        }
        if (!operand.getTypes().equals("entier")) {
            throw new ErreurSementique("impossible d'effectuer une opération arithmétique sur une expression qui n'est pas un entier");
        }
    }

}

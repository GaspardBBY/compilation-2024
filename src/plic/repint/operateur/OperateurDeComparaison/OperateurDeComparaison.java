package plic.repint.operateur.OperateurDeComparaison;

import plic.repint.Acces;
import plic.repint.ErreurSementique;
import plic.repint.Expression;
import plic.repint.operateur.Operateur;

/**
 * opérandes entier et résultat boolean
 */
public abstract class OperateurDeComparaison extends Operateur {
    public OperateurDeComparaison(Expression operandGauche, Expression operandDroite) throws ErreurSementique {
        super(operandGauche, operandDroite);
    }

    @Override
    public String getTypes() {
        return "boolean";
    }

    public void verifierOperande(Expression operand) throws ErreurSementique {
        if (operand instanceof Acces) {
            if (operand.getTypes().equals("entier")) {
                return;
            }
            if (!((Acces) operand).getTypeSymbole().equals("entier")) {
                throw new ErreurSementique("l'opérande qui est un acces n'est pas un entier");
            }
            return;
        }
        if (!operand.getTypes().equals("entier")) {
            throw new ErreurSementique("l'opérande n'est pas un acces n'est pas un entier");
        }
    }

}

package plic.repint.operateur.OperateurLogique;

import plic.repint.Acces;
import plic.repint.ErreurSementique;
import plic.repint.Expression;
import plic.repint.operateur.Operateur;

/**
 * Opérandes et résultat booléen
 */
public abstract class OperateurLogique extends Operateur {
    public OperateurLogique(Expression operandGauche, Expression operandDroite) throws ErreurSementique {
        super(operandGauche, operandDroite);
    }

    public OperateurLogique(Expression uniqueOperand) throws ErreurSementique {
        super(uniqueOperand);
    }

    @Override
    public String getTypes() {
        return "boolean";
    }


    /**
     * Verification 2 opérandes booléens
     *
     * @param operand Expression
     * @throws ErreurSementique Erreur sémantique si l'opérande n'est pas un booléen
     */
    @Override
    public void verifierOperande(Expression operand) throws ErreurSementique {
        if (operand instanceof Acces) {
            if (!((Acces) operand).getTypeSymbole().equals("boolean")) {
                throw new ErreurSementique("l'opérande qui est un acces n'est pas un boolean");
            }
            return;
        }
        if (!operand.getTypes().equals("boolean")) {
            throw new ErreurSementique("l'opérande n'est pas un acces n'est pas un boolean");
        }

    }
}

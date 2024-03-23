package plic.repint.operateur.OperateurEntier;

import plic.repint.Acces;
import plic.repint.ErreurSementique;
import plic.repint.Expression;
import plic.repint.operateur.Operateur;

/**
 * opérandes et résultat entier
 */
public abstract class OperateurEntier extends Operateur {
    public OperateurEntier(Expression operandGauche, Expression operandDroite) throws ErreurSementique {
        super(operandGauche, operandDroite);
        verifierOperande(operandGauche);
        verifierOperande(operandDroite);
    }

    @Override
    public String getTypes() {
        return "entier";
    }

    public void verifierOperande(Expression operand) throws ErreurSementique {
        if (operand instanceof Acces) {
            if (!((Acces) operand).getTypes().equals("entier")) {
                throw new ErreurSementique("Erreur : l'opérande qui est un acces n'est pas un entier");
            }
        }
        if (!operand.getTypes().equals("entier")) {
            throw new ErreurSementique("Erreur : l'opérande n'est pas un acces n'est pas un entier");
        }
    }

}

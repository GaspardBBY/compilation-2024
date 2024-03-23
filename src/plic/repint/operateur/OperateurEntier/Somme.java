package plic.repint.operateur.OperateurEntier;

import plic.repint.ErreurSementique;
import plic.repint.Expression;

/**
 * Operand à résultat entier
 */
public class Somme extends OperateurEntier {

    public Somme(Expression operandGauche, Expression operandDroite) throws ErreurSementique {
        super(operandGauche, operandDroite);
    }

    @Override
    public String toMips() {
        var sb = new StringBuilder();
        sb.append("# Calcul de droite dans $v0\n");
        sb.append(droite.toMips());
        sb.append("# Sauvegarde de $v0 dans $v1\n");
        sb.append("move $v1, $v0\n");
        sb.append("# Calcul de gauche dans $v0\n");
        sb.append(gauche.toMips());
        sb.append("# Addition de $v0 et $v1\n");
        sb.append("add $v0, $v0, $v1\n");
        return sb.toString();
    }
}

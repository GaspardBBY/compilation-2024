package plic.repint.operateur;

import plic.repint.ErreurSementique;
import plic.repint.Expression;

public abstract class Operateur extends Expression {
    public Expression gauche;
    public Expression droite;

    public Operateur(Expression operandGauche, Expression operandDroite) throws ErreurSementique {
        verifierOperande(operandGauche);
        this.gauche = operandGauche;
        verifierOperande(operandDroite);
        this.droite = operandDroite;
    }

    public abstract void verifierOperande(Expression operand) throws ErreurSementique;

    @Override
    public String toString() {
        return this.getTypes() +
                "{ gauche=" + gauche +
                ", droite=" + droite +
                '}';
    }

    public abstract String toMipsOperation();

    @Override
    public String toMips() {
        var sb = new StringBuilder();
        sb.append("# Calcul de droite dans $v0\n");
        sb.append(droite.toMips());
        sb.append("# Sauvegarde de $v0 dans $v1\n");
        sb.append("move $v1, $v0\n");
        sb.append("# Calcul de gauche dans $v0\n");
        sb.append(gauche.toMips());
        sb.append("# Operation de $v0 et $v1\n");
        sb.append(toMipsOperation());
        return sb.toString();
    }
}

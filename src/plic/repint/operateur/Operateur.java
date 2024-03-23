package plic.repint.operateur;

import plic.repint.Expression;

public abstract class Operateur extends Expression {
    public Expression gauche;
    public Expression droite;

    public Operateur(Expression operandGauche, Expression operandDroite) {
        this.gauche = operandGauche;
        this.droite = operandDroite;
    }

    @Override
    public String toString() {
        return this.getTypes() +
                "{ gauche=" + gauche +
                ", droite=" + droite +
                '}';
    }
}

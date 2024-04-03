package plic.repint.grammaire;

import plic.repint.Expression;

/**
 * FACTEUR → OPERANDE OPREL OPERANDE | OPERANDE
 */
public class Facteur extends Expression {
    public Expression operandeG;
    public Oprel oprel;
    public Expression operandeD;

    public Facteur(Expression operandeG, Oprel oprel, Expression operandeD) {
        this.operandeG = operandeG;
        this.oprel = oprel;
        this.operandeD = operandeD;
    }

    public Facteur(Expression operandeG) {
        this.operandeG = operandeG;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public String getTypes() {
        return null;
    }

    @Override
    public String toMips() {
        return null;
    }
}

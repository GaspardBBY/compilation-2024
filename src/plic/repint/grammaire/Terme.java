package plic.repint.grammaire;

import plic.repint.Expression;

/**
 * TERME → FACTEUR SUITERME
 */
public class Terme extends Expression {
    public Facteur f;
    public SuiteTerme st;

    public Terme(Facteur f, SuiteTerme st) {
        this.f = f;
        this.st = st;
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

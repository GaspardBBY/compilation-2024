package plic.repint.grammaire;

/**
 * SUITERME → ^ | OPMUL FACTEUR SUITERME
 */
public class SuiteTerme {
    public String op;
    public Facteur f;
    public SuiteTerme st;

    public SuiteTerme(String op, Facteur facteur, SuiteTerme suiteTerme) {
        this.op = op;
        this.f = facteur;
        this.st = suiteTerme;
    }
}

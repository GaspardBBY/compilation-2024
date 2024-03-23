package plic.repint;

public abstract class Acces extends Expression {
    @Override
    public String toString() {
        return null;
    }

    public abstract void verifier() throws ErreurSementique;

    public abstract String getTypes();

    public abstract String getIdf();

    public String getTypeSymbole() {
        Symbole symbole = TDS.getInstance().getSymbole(getIdf());
        return symbole.getType();
    }

    public abstract String toMips();

    /**
     * renvoie le code qui calcule l'adresse de la notation d'accès dans $a0
     * @return le code MIPS
     */
    public abstract String getAdresse();
}

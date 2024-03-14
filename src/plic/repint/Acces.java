package plic.repint;

public abstract class Acces extends Expression {
    @Override
    public String toString() {
        return null;
    }

    public abstract void verifier() throws ErreurSementique;

    public abstract String getTypes();

    public abstract String getNom();

    /**
     * Calcule l'adresse de l'élément dans la pile puis appel la méthode toMips de l'élément
     * @return
     */
    public String toMips(){
        var symbole = TDS.getSymbole(getNom());
        var deplacement = symbole.getDeplacement();
        return toMips(deplacement);
    }

    public abstract String toMips(int deplacement);
}

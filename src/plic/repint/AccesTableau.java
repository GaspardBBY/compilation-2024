package plic.repint;

public class AccesTableau extends Acces {
    private final String nom;
    private final int indice;


    public AccesTableau(String nom, int indice) {
        this.nom = nom;
        this.indice = indice;
    }

    @Override
    public String toString() {
        return nom + " " + "[ " + indice + " ]";
    }

    @Override
    public String getTypes() {
        return "tableau";
    }

    @Override
    public String getNom() {
        return nom;
    }

    /**
     * Vérifie que l'identifiant est bien déclaré et que l'indice est bien dans les bornes du tableau
     *
     * @throws ErreurSementique si l'identifiant n'est pas déclaré ou si l'indice est négatif ou hors limite
     */
    @Override
    public void verifier() throws ErreurSementique {
        if (!TDS.getInstance().contain(new Entree(nom))) {
            throw new ErreurSementique("Erreur : identifiant " + nom + " non déclaré");
        }
        var symbole = TDS.getSymbole(nom);
        if (!(symbole instanceof SymboleTableau)) {
            throw new ErreurSementique("Erreur : " + nom + " n'est pas un tableau");
        }
        if (indice < 0) {
            throw new ErreurSementique("Erreur : indice " + indice + " négatif");
        }
        if (indice >= ((SymboleTableau) symbole).getTaille()) {
            throw new ErreurSementique("Erreur : indice " + indice + " hors limite");
        }
    }

    @Override
    public String toMips(int deplacement) {
        int deplacementElement = deplacement + indice * -4;
        return "lw $t1, " + deplacementElement + "($sp)\n";
    }

    public int getIndice() {
        return indice;
    }
}

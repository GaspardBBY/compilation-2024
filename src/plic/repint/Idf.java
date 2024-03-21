package plic.repint;

public class Idf extends Acces {
    private final String nom;

    public Idf(String nom) {
        this.nom = nom;
    }

    public String getIdf() {
        return nom;
    }

    public String toString() {
        return nom;
    }

    @Override
    public String getTypes() {
        return "idf";
    }

    @Override
    public void verifier() throws ErreurSementique {
        if (!TDS.getInstance().contain(new Entree(nom))) {
            throw new ErreurSementique("Erreur : identFifiant " + nom + " non déclaré");
        }
    }

    public String toMips() {
        // load the value of the variable into $v0
        Symbole symbole = TDS.getSymbole(this.nom);
        return "lw $v0, " + symbole.getDeplacement() + "($sp)\n";
    }

    /**
     * renvoie le code qui calcule l'adresse de la notation d'accès dans $a0
     * @return le code MIPS
     */
    @Override
    public String getAdresse() {
        var deplacement = TDS.getSymbole(nom).getDeplacement();
        return "la $a0, " + deplacement + "($sp)\n";
    }
}

package plic.repint;

public class Idf extends Acces {
    private String nom;

    public Idf(String nom) {
        this.nom = nom;
    }

    public String getNom() {
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

    public String toMips(int deplacement) {
        // load the value of the variable into $t1
        return "lw $t1, " + deplacement + "($sp)\n";
    }
}

package plic.repint;

public class SymboleTableau extends Symbole {
    int taille;

    public SymboleTableau(String type, int taille) {
        super(type);
        this.taille = taille;
    }

    @Override
    public int getSize() {
        return taille;
    }

    @Override
    public String toString() {
        return "SymboleTableau{" +
                "type='" + type + '\'' +
                ", deplacement=" + deplacement +
                ", taille=" + taille +
                '}';
    }

    @Override
    public String getType() {
        return "entier";
    }

    public int getTaille() {
        return taille;
    }
}

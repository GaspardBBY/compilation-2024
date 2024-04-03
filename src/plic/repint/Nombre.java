package plic.repint;

public class Nombre extends Expression {
    private final int valeur;

    public Nombre(int valeur) {
        this.valeur = valeur;
    }

    public int getValeur() {
        return valeur;
    }

    public String toString() {
        return "" + valeur;
    }

    @Override
    public String getTypes() {
        return "entier";
    }

    public String toMips() {
        return "li $v0, " + valeur + "\n";
    }
}

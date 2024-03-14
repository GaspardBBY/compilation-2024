package plic.repint;

public class Nombre extends Expression {
    private int valeur;

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
        return "nombre";
    }

    public String toMips() {
        return "li $t1, " + valeur + "\n";
    }
}

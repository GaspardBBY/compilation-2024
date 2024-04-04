package plic.repint;

public class Boolean extends Expression {
    private final boolean valeur;

    public Boolean(String valeur) {
        if (valeur.equals("vrai")) {
            this.valeur = true;
        } else if (valeur.equals("faux")) {
            this.valeur = false;
        } else {
            throw new IllegalArgumentException("La valeur doit être vrai ou faux");
        }
    }

    @Override
    public String toString() {
        return "" + valeur;
    }

    @Override
    public String getTypes() {
        return "boolean";
    }

    @Override
    public String toMips() {
        var sb = new StringBuilder();
        sb.append("\t# Chargement de la valeur booléenne dans $v0\n");
        sb.append("\tli $v0, " + (valeur ? 1 : 0) + "\n");
        return sb.toString();
    }
}

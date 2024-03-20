package plic.repint;

public abstract class Expression {
    public abstract String toString();
    public abstract String getTypes();

    /**
     * calcul la valeur de l'expression dans v0
     * @return
     */
    public abstract String toMips();

}

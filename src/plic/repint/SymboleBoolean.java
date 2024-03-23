package plic.repint;

public class SymboleBoolean extends Symbole{
    public SymboleBoolean(String type) {
        super(type);
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public String toString() {
        return "SymboleBoolean{" +
                "type='" + type + '\'' +
                ", deplacement=" + deplacement +
                '}';
    }

    @Override
    public String getType() {
        return "boolean";
    }
}

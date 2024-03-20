package plic.repint;

public class SymboleEntier  extends Symbole{
    public SymboleEntier(String type) {
        super(type);
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public String toString() {
        return "SymboleEntier{" +
                "type='" + type + '\'' +
                ", deplacement=" + deplacement +
                '}';
    }
}

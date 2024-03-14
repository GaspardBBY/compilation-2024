package plic.repint;

public class SymboleEntier  extends Symbole{
    public SymboleEntier(String type) {
        super(type);
    }

    @Override
    public String toString() {
        return "SymboleEntier{" +
                "type='" + type + '\'' +
                ", deplacement=" + deplacement +
                '}';
    }
}

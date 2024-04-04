package plic.repint;

public class Lire extends Instruction {
    public Idf idf;

    public Lire(Idf idf) {
        this.idf = idf;
    }


    @Override
    public String toString() {
        return "Lire dans l'idf " + idf;
    }

    @Override
    public void verifier() throws ErreurSemantique {

    }

    @Override
    public String toMips() {
        var sb = new StringBuilder();
        sb.append("\tli $v0, 5\n");
        sb.append("\tsyscall\n");
        sb.append("\tsw $v0, ").append(idf.getSymbole().getDeplacement()).append("($sp)\n");
        return sb.toString();
    }

}

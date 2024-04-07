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
        // idf doit exister
        if (idf.getSymbole() == null) {
            throw new ErreurSemantique("L'idf " + idf + " n'existe pas");
        }
        // idf doit être un entier
        if (!idf.getSymbole().getType().equals("entier")) {
            throw new ErreurSemantique("L'idf " + idf + " n'est pas un entier");
        }
    }

    @Override
    public String toMips() {
        var sb = new StringBuilder();
        sb.append("\t# Lire dans ").append(idf).append("\n");
        sb.append("\tli $v0, 5\n");
        sb.append("\tsyscall\n");
        sb.append("\tsw $v0, ").append(idf.getSymbole().getDeplacement()).append("($sp)\n");
        return sb.toString();
    }

}

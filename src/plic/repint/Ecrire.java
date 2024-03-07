package plic.repint;

public class Ecrire extends Instruction {
    Expression exp;

    public Ecrire(Expression exp) {
        this.exp = exp;
    }

    public String toString() {
        return "ecrire " + exp.toString() + ";";
    }

    /**
     * Vérifie que l'expression est bien déclarée, si c'est une variable on vérifie qu'elle est bien déclarée
     * @throws ErreurSementique si l'expression n'est pas déclarée
     */
    @Override
    public void verifier() throws ErreurSementique {
        var tds = TDS.getInstance();
        if (exp instanceof Idf) {
            if (tds.contain(new Entree(exp.toString()))) return;
            throw new ErreurSementique("Variable " + exp.toString() + " non déclarée, impossible de l'afficher !");
        }
    }

    @Override
    public String toMips() {
        var map = TDS.getInstance().getMap();
        var symbole = map.get(new Entree(exp.toString()));
        StringBuilder sb = new StringBuilder();

        if (exp instanceof Idf) {
            var deplacementA = symbole.getDeplacement();
            //addi $sp, $sp, -4 # Décalage de l'élément sur la pile
            //lw $t1, 0($sp)
//            sb.append("addi $sp, $sp, ").append(deplacementA).append("\n");
//            sb.append("lw $t1, 0($sp)\n");
            // pour remettre la pile à sa place
//            sb.append("addi $sp, $sp, ").append(-deplacementA).append("\n");
            // TEST
//            sb.append("addi $sp, $sp, ").append(deplacementA).append("\n");
            sb.append("lw $t1, ").append(deplacementA).append("($sp)\n");
        } else if (exp instanceof Nombre) {
            sb.append("li $t1, ").append(((Nombre) exp).getValeur()).append("\n");
        }
        sb.append("move $a0, $t1\n");
        sb.append("li $v0, 1\n");
        sb.append("syscall\n");

        // saut de ligne
        sb.append("la $a0, linebreak\n");
        sb.append("li $v0, 4\n");
        sb.append("syscall\n");
        return sb.toString();
    }
}

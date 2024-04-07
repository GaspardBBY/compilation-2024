package plic.repint;

/**
 * ITERATION → pour idf dans EXPRESSION .. EXPRESSION
 */
public class Pour extends Instruction {
    private String idf;
    private Expression debut;
    private Expression fin;
    private Bloc bloc;

    public Pour(String idf, Expression debut, Expression fin, Bloc bloc) {
        this.idf = idf;
        this.debut = debut;
        this.fin = fin;
        this.bloc = bloc;
    }

    @Override
    public String toString() {
        return "Pour " + idf + " dans " + debut + " .. " + fin + "\n" + bloc.toStringImbriquee() + "\nFinPour";
    }

    @Override
    public void verifier() throws ErreurSemantique {
        // verification a n'est pas déclaré
        Symbole symbole = TDS.getSymbole(idf);
        if (symbole == null) {
            throw new ErreurSemantique("Erreur : identifiant " + idf + " non déclaré, il faut le déclarer avant de l'utiliser dans une boucle pour");
        }
        // verification a est un entier
        if (!symbole.getType().equals("entier")) {
            throw new ErreurSemantique("Erreur : identifiant " + idf + " n'est pas un entier, il faut un entier pour une boucle pour");
        }
        // verification debut est un entier
        if (!debut.getTypes().equals("entier")) {
            throw new ErreurSemantique("Erreur : la borne de début de la boucle pour n'est pas un entier");
        }
        // verification fin est un entier
        if (!fin.getTypes().equals("entier")) {
            throw new ErreurSemantique("Erreur : la borne de fin de la boucle pour n'est pas un entier");
        }
    }

    @Override
    public String toMips() {
        var sb = new StringBuilder();
        // on récupère le déplacement de l'idf
        var symbole = TDS.getSymbole(idf);
        var deplacement = symbole.getDeplacement();
        // on met la valeur de debut dans l'idf
        sb.append(debut.toMips());
        sb.append("\tsw $v0, " + deplacement + "($sp)\n");
        // on met la valeur de fin dans $t0
        sb.append(fin.toMips());
        sb.append("\tmove $t0, $v0\n");
        // on compare la valeur de l'idf avec la valeur de fin
        sb.append("pour" + this.hashCode() + ":\n");
        sb.append("\tlw $t1, " + deplacement + "($sp)\n");
        sb.append("\tbeq $t1, $t0, finPour" + this.hashCode() + "\n");
        // on exécute le bloc
        sb.append(bloc.toMips());
        // on incrémente la valeur de l'idf
        sb.append("\taddi $t1, $t1, 1\n");
        sb.append("\tsw $t1, " + deplacement + "($sp)\n");
        // on retourne au début de la boucle
        sb.append("\tj pour" + this.hashCode() + "\n");
        sb.append("finPour" + this.hashCode() + ":\n");

        return sb.toString();
    }
}

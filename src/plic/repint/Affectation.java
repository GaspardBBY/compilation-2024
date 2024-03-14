package plic.repint;

/**
 * Affectation
 * de la forme : idf := exp;
 */
public class Affectation extends Instruction {

    Expression exp;
    Acces acces;

    public Affectation(Expression exp, Acces acces) {
        this.exp = exp;
        this.acces = acces;
    }

    public String toString() {
        return acces.toString() + " := " + exp.toString() + ";";
    }

    /**
     * Vérification que si l'expression est une variable, elle est déclarée
     *
     * @throws ErreurSementique si on tente d'affecter une valeur à une variable non déclarée
     */
    @Override
    public void verifier() throws ErreurSementique {
        boolean contained = TDS.getSymbole(acces.getNom()) != null;
        if (!contained) {
            throw new ErreurSementique("Variable " + acces.toString() + " non déclarée");
        }

    }

    @Override
    public String toMips() {
        StringBuilder sb = new StringBuilder();
        var map = TDS.getInstance().getMap();
        var symbole = map.get(new Entree(acces.getNom()));
        //1. Déterminer l'emplacement de la variable idf dans la pile:
        var deplacement = symbole.getDeplacement();
        if (acces instanceof AccesTableau) {
            // B[i] := A;
            var index = ((AccesTableau) acces).getIndice();
            deplacement += index * -4;
        }

        if (exp instanceof Acces) {

            var variableA = map.get(new Entree(((Idf) exp).getNom()));
            var deplacementA = variableA.getDeplacement();
            if (exp instanceof Idf) {
                // B := A;
                // Charger la valeur de A dans $t0
                sb.append("lw $t0, ").append(deplacementA).append("($sp)\n");
            }
            if (exp instanceof AccesTableau) {
                System.out.println("A[I]");
                // B := A[i];
                var index = ((AccesTableau) exp).getIndice();
                // Charger la valeur de A[i] dans $t0
                sb.append("lw $t0, ").append(deplacementA + index * -4).append("($sp)\n");
            }
            sb.append(((Acces) exp).toMips());
            // Stocker la valeur de $t0 dans B
            sb.append("sw $t0, ").append(deplacement).append("($sp)\n");
        } else if (exp instanceof Nombre) {
            // i := 10;
            //Utilisez l'instruction li pour charger la valeur 10 dans le registre $t0.
            sb.append("li $t0, ").append(((Nombre) exp).getValeur()).append("\n");
            //Utilisez l'instruction sw pour stocker la valeur de $t0 à l'adresse -4($sp) dans la pile.
            sb.append("addi, $t1, $sp,").append(deplacement).append("\n");
            sb.append("sw $t0, 0($t1)\n");
        }
        return sb.toString();
    }


}

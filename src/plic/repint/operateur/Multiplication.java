package plic.repint.operateur;

import plic.repint.Expression;

public class Multiplication extends Operateur {
    public Multiplication(Expression operandGauche, Expression operandDroite) {
        super(operandGauche, operandDroite);
    }

    @Override
    public String getTypes() {
        return "multiplication";
    }

    @Override
    public String toMips() {
        var sb = new StringBuilder();
        sb.append("# Calcul de droite dans $v0\n");
        sb.append(droite.toMips());
        sb.append("# Sauvegarde de $v0 dans $v1\n");
        sb.append("move $v1, $v0\n");
        sb.append("# Calcul de gauche dans $v0\n");
        sb.append(gauche.toMips());
        sb.append("# Multiplication de $v0 et $v1\n");
        sb.append("mul $v0, $v0, $v1\n");
        return sb.toString();
    }
}

package plic.repint;

import java.util.ArrayList;

public class Bloc {
    ArrayList<Instruction> instructions;

    public Bloc() {
        this.instructions = new ArrayList<>();
    }

    public void ajouter(Instruction i) {
        this.instructions.add(i);
    }

    public String toString() {
        return "Bloc\n\t- " + this.instructions.stream().map(Instruction::toString).reduce((a, b) -> a + "\n\t- " + b).orElse("") + "\nFinBloc\n";
    }

    /**
     * Vérifie que toutes les instructions du bloc sont correctes
     *
     * @throws ErreurSementique si une erreur est soulevée, on la propage
     */
    public void verifier() throws ErreurSementique {
        for (Instruction i : this.instructions) {
            i.verifier();
        }
    }

    /**
     * Génère le code MIPS pour le bloc, avec la déclaration d'un saut de ligne qui permettra
     * d'afficher les sauts de ligne après chaque instruction écrire
     *
     * @return le code MIPS pour le bloc
     */
    public String toMips() {
        StringBuilder sb = new StringBuilder();
        sb.append(declareLineBreak());
        sb.append(".text\n");
        for (Instruction i : this.instructions) {
            sb.append(i.toMips());
        }
        return sb.toString();
    }

    /**
     * Déclare un saut de ligne
     *
     * @return le code MIPS pour déclarer un saut de ligne
     */
    private String declareLineBreak() {
        StringBuilder sb = new StringBuilder();
        sb.append(".data\n");
        sb.append("linebreak: .asciiz \"\\n\"\n");
        return sb.toString();
    }
}

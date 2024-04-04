package plic.repint;

import java.util.ArrayList;
import java.util.List;

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

    public String toStringImbriquee() {
        return this.instructions.stream().map(Instruction::toString).reduce((a, b) -> a + "\n\t\t" + b).orElse("");
    }

    /**
     * Vérifie que toutes les instructions du bloc sont correctes
     *
     * @throws ErreurSemantique si une erreur est soulevée, on la propage
     */
    public void verifier() throws ErreurSemantique {
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
//        StringBuilder sb = new StringBuilder();
//        sb.append(declareLineBreak());
//        sb.append("\t.text\n");
//        for (Instruction i : this.instructions) {
//            sb.append(i.toMips());
//        }
//        return sb.toString();
        StringBuilder sb = new StringBuilder();

        for (Instruction i : instructions) {
            sb.append(i.toMips());
        }
        return sb.toString();
    }

    /**
     * Déclare un saut de ligne
     *
     * @return le code MIPS pour déclarer un saut de ligne
     */
    public String declareLineBreak() {
        StringBuilder sb = new StringBuilder();
        sb.append(".data\n");
        sb.append("\tlinebreak: .asciiz \"\\n\"\n");
        return sb.toString();
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }
}

package plic.repint;

/**
 * Classe abstraite représentant une instruction
 */
public abstract class Instruction {
    public abstract String toString();

    public abstract void verifier() throws ErreurSemantique;

    public abstract String toMips();
}

package plic.repint;

/**
 * Classe abstraite représentant une instruction
 */
public abstract class Instruction {
    public abstract String toString();

    public abstract void verifier() throws ErreurSementique;

    public abstract String toMips();
}

package plic.repint;

public class ErreurSemantique extends Throwable {
    public ErreurSemantique(String s) {
        super("ERREUR: erreur sementique " + s);
    }
}

package plic.repint;

public class ErreurSementique extends Throwable {
    public ErreurSementique(String s) {
        super("ERREUR: erreur sementique " + s);
    }
}

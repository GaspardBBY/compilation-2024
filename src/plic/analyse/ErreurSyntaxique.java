package plic.analyse;

public class ErreurSyntaxique extends Throwable {
    public ErreurSyntaxique(String message) {
        super("ERREUR: " + message);
    }
}

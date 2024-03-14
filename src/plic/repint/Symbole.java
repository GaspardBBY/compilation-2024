package plic.repint;

/**
 * Symbole contient les informations relatives à une déclaration
 * ○ Type de chaque variable
 * ○ Déplacement par rapport au début de la zone des variables
 */
public abstract class Symbole {
    String type;
    int deplacement;

    public Symbole(String type) {
        this.type = type;
    }

    public void setDeplacement(int deplacement) {
        this.deplacement = deplacement;
    }

    public int getDeplacement() {
        return deplacement;
    }

    @Override
    public abstract String toString();
}

package plic.repint;

/**
 * Entree est un identifiant (unique) : idf
 */
public class Entree implements Comparable<Entree> {
    String idf;

    public Entree(String idf) {
        this.idf = idf;
    }

    /**
     * Permet de trier la map dans l'ordre alphabétique
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than
     * the specified object.
     */
    @Override
    public int compareTo(Entree o) {
        return this.idf.compareTo(o.idf);
    }

    @Override
    public String toString() {
        return "Entree identificateur: " + idf;
    }
}

package plic.repint;

import java.util.TreeMap;

/**
 * TDS = Table des Symboles
 */
public class TDS {
    int cptDepl; //compteurDéplacement
    TreeMap<Entree, Symbole> map;
    private static TDS instance;

    private TDS() {
        this.cptDepl = 0;
        this.map = new TreeMap<>();
    }

    public static TDS getInstance() {
        if (instance == null) {
            instance = new TDS();
        }
        return instance;
    }

    public void ajouter(Entree e, Symbole s) throws DoubleDeclaration {
        if (this.map.containsKey(e)) {
            throw new DoubleDeclaration("Double déclaration de " + e.idf);
        }
        s.setDeplacement(cptDepl * -4);
        if (s instanceof SymboleTableau) {
            cptDepl += ((SymboleTableau) s).getTaille();
        } else {
            cptDepl++;
        }
        map.put(e, s);
    }

    public TreeMap<Entree, Symbole> getMap() {
        return this.map;
    }

    @Override
    public String toString() {
        var affichageDeLamap = map.entrySet().stream()
                .map(e -> e.getKey() + " => " + e.getValue())
                .reduce((a, b) -> a + "\n\t" + b).orElse("");
        return "TDS{\n" +
                "Compteur déplacement=" + cptDepl +
                ", \nAffichage de la map: \n\t" + affichageDeLamap +
                "\n}";
    }

    public boolean contain(Entree e) {
        return map.containsKey(e);
    }

    public static Symbole getSymbole(String idf) {
        return instance.map.get(new Entree(idf));
    }
}

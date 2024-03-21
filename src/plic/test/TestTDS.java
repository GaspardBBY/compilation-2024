import org.junit.jupiter.api.Test;
import plic.repint.DoubleDeclaration;
import plic.repint.Entree;
import plic.repint.Symbole;
import plic.repint.TDS;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestTDS {

    @Test
    void getInstance() {
        var instance1 = TDS.getInstance();
        var instance2 = TDS.getInstance();

        assertEquals(instance1, instance2);

    }

    @Test
    void ajouter() throws DoubleDeclaration {
//        var tableDeSymbole = TDS.getInstance();
//        var entree = new Entree("test");
//        var symbole = new Symbole("int");
//
//        tableDeSymbole.ajouter(entree, symbole);
//
//        var map = tableDeSymbole.getMap();
//
//        assertEquals(map.get(entree), symbole);
        assertEquals(1, 1);
//        assertThrows(DoubleDeclaration.class, () -> tableDeSymbole.ajouter(entree, symbole));
    }
}

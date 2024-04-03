import org.junit.jupiter.api.Test;
import plic.Plic;
import plic.analyse.ErreurSyntaxique;
import plic.repint.ErreurSemantique;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class TestAutomatise {
    @Test
    public void testAnalyseSementique() {
        File repertoire = new File("src\\plic\\sources\\testAuto\\sementique");
        File[] files = repertoire.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                File[] subDirectoryFile = file.listFiles();
                if (subDirectoryFile == null) continue;
                for (File f : subDirectoryFile) {
                    assertThrows(ErreurSemantique.class, () -> new Plic(f.getAbsolutePath()), "Erreur sémantiques non soulevée sur le fichier \"" + file.getName() + "/" + f.getName() + "\"");
                }
            }
        }
    }

    @Test
    public void testAnalyseSyntaxique() {
        File repertoire = new File("src\\plic\\sources\\testAuto\\syntaxique");
        File[] files = repertoire.listFiles();
        assert files != null;
        for (File file : files) {
            assertThrows(ErreurSyntaxique.class, () -> new Plic(file.getAbsolutePath()), "Erreur sémantiques non soulevée sur le fichier \"" + file.getName() + "\"");
        }
    }

}

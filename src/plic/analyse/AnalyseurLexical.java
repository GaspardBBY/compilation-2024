package plic.analyse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.LineNumberReader;
import java.util.Scanner;

public class AnalyseurLexical {
    private Scanner scanner;
    private LineNumberReader lineNumberReader;

    public AnalyseurLexical(File file) throws FileNotFoundException {
        this.scanner = new Scanner(file);
    }

    /**
     * Renvoie la prochaine unité lexicale
     * @return
     */
    public String next() {
        if (!this.scanner.hasNext()) {
            return "EOF";
        }
        String s = scanner.next();
        if (s.contains("//")) {
            this.scanner.nextLine();
            if (!this.scanner.hasNext()) {
                return "EOF";
            }
            return next();
        }
        return s;
    }

    public boolean hasNext() {
        return this.scanner.hasNext();
    }


}

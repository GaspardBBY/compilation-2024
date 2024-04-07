package plic;

import plic.analyse.AnalyseurSyntaxique;
import plic.analyse.ErreurSyntaxique;
import plic.repint.Bloc;
import plic.repint.DoubleDeclaration;
import plic.repint.ErreurSemantique;
import plic.repint.TDS;

import java.io.File;

public class Plic {
    public static void main(String[] args) throws DoubleDeclaration, ErreurSemantique, ErreurSyntaxique {
        try {
            if (args.length < 1) {
                throw new Error("ERREUR: Fichier source absent");
            }
            String fichier = args[0];
            //verification de l'extension
            if (!fichier.endsWith(".plic")) {
                throw new Error("ERREUR: Suffixe incorrect");
            }
            new Plic(fichier);
        } catch (ErreurSyntaxique | DoubleDeclaration | ErreurSemantique e) {
            System.out.println(e.getMessage());
        } catch (Exception | Error e ) {
            // catch unknown exception
            System.out.println("ERREUR: " + e.getMessage());
        }
    }

    public Plic(String nomFichier) throws ErreurSyntaxique, DoubleDeclaration, ErreurSemantique {
        TDS.reset();
        File file = new File(nomFichier);
        // Créer l’analyseur syntaxique
        AnalyseurSyntaxique as = new AnalyseurSyntaxique(file);
        // Analyse syntaxique du texte source
        Bloc bloc = as.analyse();
        bloc.verifier();
        var sb = new StringBuilder();
        sb.append(bloc.declareLineBreak());
        sb.append(".text\n");
        sb.append("main:\n");

        sb.append(bloc.toMips());
        sb.append("\tli $v0, 10\n");
        sb.append("\tsyscall\n");
        System.out.println(sb);
    }
}

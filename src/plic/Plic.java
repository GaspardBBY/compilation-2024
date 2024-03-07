package plic;

import plic.analyse.AnalyseurSyntaxique;
import plic.analyse.ErreurSyntaxique;
import plic.repint.Bloc;
import plic.repint.DoubleDeclaration;
import plic.repint.ErreurSementique;

import java.io.File;

public class Plic {
    public static void main(String[] args) {
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
        } catch (ErreurSyntaxique | DoubleDeclaration | ErreurSementique e ){
            System.out.println(e.getMessage());
        }
    }

    public Plic(String nomFichier) throws ErreurSyntaxique, DoubleDeclaration, ErreurSementique {
        File file = new File(nomFichier);
        // Créer l’analyseur syntaxique
        AnalyseurSyntaxique as = new AnalyseurSyntaxique(file);
        // Analyse syntaxique du texte source
        Bloc bloc = as.analyse();
//        System.out.println(bloc);
//        System.out.println(TDS.getInstance());
        bloc.verifier();
        System.out.println(bloc.toMips());
    }
}

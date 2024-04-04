package plic;

import plic.analyse.AnalyseurSyntaxique;
import plic.analyse.ErreurSyntaxique;
import plic.repint.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        } catch (ErreurSyntaxique | DoubleDeclaration | ErreurSemantique e) {
            System.out.println(e.getMessage());
        }
    }

    public Plic(String nomFichier) throws ErreurSyntaxique, DoubleDeclaration, ErreurSemantique {
        TDS.reset();
        File file = new File(nomFichier);
        // Créer l’analyseur syntaxique
        AnalyseurSyntaxique as = new AnalyseurSyntaxique(file);
        // Analyse syntaxique du texte source
        Bloc bloc = as.analyse();
        System.out.println(bloc);
//        bloc.verifier();
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

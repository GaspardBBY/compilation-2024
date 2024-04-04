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

        List<List<Instruction>> listFin = new ArrayList<>();
        int cpt = 0;
        List<Instruction> instructions = bloc.getInstructions();
        for (Instruction i : instructions) {
            if (i instanceof Si) {
                sb.append(((Si) i).toMipsSi(cpt));
                listFin.add(new ArrayList<>());
                cpt++;
            } else {
                if (listFin.isEmpty()) {
                    sb.append(i.toMips());
                } else {
                    listFin.getLast().add(i);
                }
            }
        }

        // ajout des blocs supp
        List<Instruction> instructionsIf = instructions.stream()
                .filter(i -> i instanceof Si).toList();

        for (int i = 0; i < instructionsIf.size(); i++) {
            Si si = (Si) instructionsIf.get(i);
            sb.append(si.toMipsElse(i));
        }

        // ajout des fins
        for (int i = 0; i < listFin.size(); i++) {
            sb.append("fin").append(i).append(":\n");
            for (Instruction instruction : listFin.get(i)) {
                sb.append(instruction.toMips());
            }
        }
        System.out.println(sb);
    }
}

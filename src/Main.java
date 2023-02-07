package src;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String defaultFilePath ="vat-eu.csv";

    private static String createStringForConsoleFromFirstTask(StateDPHInformation n){
        return n.getState()+" ("+n.getShortCutOfState()+"): "+ String.valueOf(n.getFullDPH()).replace(".0","")+" %";
    }

    private static String createStringForConsoleFromFiveTask(StateDPHInformation n){
        return n.getState()+" ("+n.getShortCutOfState()+"): "+ String.valueOf(n.getFullDPH()).replace(".0","")+" % ("+String.valueOf(n.getLowerDPH()).replace(".0","")+" %)";
    }
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in,"Windows-1250");
        System.out.println("Zadejte vyhledávanou hodnotu základu DPH:");
        String hodnotaDphString = "";
        float hodnotaDphFloat=0;
        boolean isValueWritedRight=false;
        for (int i = 10; i >= 0; i--) {
            hodnotaDphString = sc.nextLine();
            if (hodnotaDphString.isEmpty()) {
                hodnotaDphFloat = 20;
                isValueWritedRight=true;
            } else {
                try {
                    hodnotaDphFloat = Float.parseFloat(hodnotaDphString.replace(",","."));
                    isValueWritedRight=true;
                }catch (Exception e){
                    if (i==0){
                        sc.close();
                        throw e;
                    }else {
                        System.err.println("Zadal jste hodnotu, která obsahuje znaky a ne číšla. Nebylo tedy možné přeparsovat do objectu Float. Prosím zkuste to znovu. Máte ještě "+i+" pokusů na správné zadání.");
                    }
                }
                if (isValueWritedRight){
                    break;
                }
            }
        }
        sc.close();
        System.out.println("Zadaná hodnota="+hodnotaDphString);
        DphListWorker dphListWorker = new DphListWorker();
        try {
            dphListWorker.transformListWithDPHToDPHObjects(defaultFilePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<StateDPHInformation> allStatesBiggerThenTwentyAndNoSCTax=new ArrayList<>();
        List<StateDPHInformation> allStatesSmallerThenTwentyAndWithSCTax=new ArrayList<>();
//        List<StateDPHInformation> allStates=new ArrayList<>();
        //snaha o vložení do jednoho fori
//        for (int i = 0; i < dphListWorker.getStateDPHInformationList().size(); i++) {
            //výpis celého seznamu
        float finalHodnotaDphFloat = hodnotaDphFloat;
        dphListWorker.getStateDPHInformationList().forEach(n ->{
                System.out.println(createStringForConsoleFromFirstTask(n));
//                allStates.add(n);
                //přidání všech vyšších než 20% a nepoužívají speciální sazbu daně
                if ((!n.isSpecialDPH())&&(n.getFullDPH()> finalHodnotaDphFloat)){
                    allStatesBiggerThenTwentyAndNoSCTax.add(n);
                }else {
                    allStatesSmallerThenTwentyAndWithSCTax.add(n);
                }
            });


        //Vypiš ve stejném formátu pouze státy, které mají základní sazbu daně z přidané hodnoty vyšší než 20 % a přitom nepoužívají speciální sazbu daně.
        allStatesBiggerThenTwentyAndNoSCTax.forEach(n-> System.out.println(createStringForConsoleFromFirstTask(n)));
        System.out.println("\n\n\n");
        //Stejný výpis jako nad tímto, akorát se seřazením
        List<StateDPHInformation> allStatesBiggerThenTwentyAndNoSCTaxButWithComperate=new ArrayList<>(allStatesBiggerThenTwentyAndNoSCTax);
        allStatesBiggerThenTwentyAndNoSCTaxButWithComperate.sort(new StateFullDPHComparator().reversed());
        allStatesBiggerThenTwentyAndNoSCTaxButWithComperate.forEach(n-> System.out.println(createStringForConsoleFromFirstTask(n)));
        //výpis všech státu dle se seřazením + zobrazním státu, které nespadají pod podmínku 20% daň + a nemají speciální daň
        System.out.println("\n\n\n");
        //zde jsem přemýšlel nad dvěmi řešení, kdy bych neukládal proměnné do listu a naopak bych znovu iteroval list všech objectu, který bych předtím vysortoval.
        //Nakonec jsem se rozhodl pro řešení, kdy jsem se snažil všechny ty listy rozdělit jíž první iteraci.

        //prvně jsem jen vypisoval přes sout ven a při plnění posledního úkolu kdy to mám uložit do souboru mě napadlo, že spíš jednodušší bude si vytvořit ten celý string toho výpisu, jelikož jej budu schopen pak použít i na zápis do souboru.
        StringBuilder wholeMessageFromTaskFive= new StringBuilder();
        allStatesBiggerThenTwentyAndNoSCTaxButWithComperate.forEach(n-> wholeMessageFromTaskFive.append(createStringForConsoleFromFiveTask(n)).append("\n"));
        wholeMessageFromTaskFive.append("====================\n");
        StringBuilder toSend= new StringBuilder();
        for (int i = 0; i < allStatesSmallerThenTwentyAndWithSCTax.size(); i++) {
            if (i==allStatesSmallerThenTwentyAndWithSCTax.size()-1) {
                toSend.append(allStatesSmallerThenTwentyAndWithSCTax.get(i).getShortCutOfState());
            }else {
                toSend.append(allStatesSmallerThenTwentyAndWithSCTax.get(i).getShortCutOfState()).append(",");
            }
        }
        wholeMessageFromTaskFive.append("Sazba VAT 20 % nebo nižší nebo používají speciální sazbu: ").append(toSend);
        System.out.println(wholeMessageFromTaskFive);

        File newFile= new File("vat-over-"+String.valueOf(finalHodnotaDphFloat).replace(".0","").replace(".",",")+".txt");
        try {
            Files.deleteIfExists(newFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (PrintWriter outputWriter =
                     new PrintWriter(new FileWriter(newFile))) {
                outputWriter.print(wholeMessageFromTaskFive);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package dictionary;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

@SuppressWarnings("ALL")
class Translation {
    private Scanner scIn = new Scanner (System.in);
    private Scanner scLine = new Scanner (System.in);
    private Map<String, String> engWords = new HashMap<> ( );
    private Map<String, String> ruWords = new HashMap<> ( );
    private File engFile;
    private File ruFile;

    Translation() {
        File dir = new File ("./files");
        dir.mkdir ( );
        engFile = new File (dir, "eng.txt");
        ruFile = new File (dir, "ru.txt");
        try {
            ruFile.createNewFile ( );
            engFile.createNewFile ( );
        } catch (
                IOException e) {
            e.printStackTrace ( );
        }
    }

    private void showMainMenu() {
        System.out.println ("Main menu.");
        System.out.println ("Press the number of the option you want: ");
        System.out.println (
                "1. English - Russian translation \n" +
                "2. Russian - English translation \n" +
                "3. Add a new English - Russian translation \n" +
                "4. Add a new Russian - English translation \n" +
                "5. Get out from the dictionary");
    }

    private void addRuEngTranslation() {
        try (PrintWriter printWriter = new PrintWriter (
                new FileOutputStream (ruFile, true), true)) {
            System.out.println (" Добавьте русское слово ");
            String ruInput = scLine.nextLine ( ).toLowerCase();
            System.out.println (" Введите перевод на английском языке ");
            String engInput = scLine.nextLine ( ).toLowerCase();
            printWriter.println (ruInput + " " + engInput);
            ruWords.put (ruInput, engInput);
        } catch (
                FileNotFoundException e) {
            e.printStackTrace ( );
        }
    }

    private void addEngRuTranslation() {
        try (PrintWriter printWriter = new PrintWriter (
                new FileOutputStream (engFile, true), true)) {
            System.out.println ("Type in an English word ");
            String engInput = scLine.nextLine ( ).toLowerCase();
            System.out.println ("Type in a Russian word ");
            String ruInput = scLine.nextLine ( ).toLowerCase();
            printWriter.println (engInput + " " + ruInput);
            engWords.put (engInput, ruInput);
        } catch (
                FileNotFoundException e) {
            e.printStackTrace ( );
        }
    }

    private void translateRuEng() {
        try (Scanner scanner = new Scanner (new FileInputStream (ruFile))) {
            while (scanner.hasNextLine ( )) {
                String line = scanner.nextLine ( );
                String[] words = line.split (" ");
                for (String word1 : words) {
                    for (String word2 : words) {
                        ruWords.put (word1.toLowerCase (), word2.toLowerCase ());
                    }
                }
            }
            System.out.println ("Введите русское слово для получения перевода ");
            String ruInput = ruWords.get (scLine.nextLine ( ));
            System.out.println (Objects.requireNonNullElse (ruInput, " Такого слова ещё нет в словаре "));
        } catch (FileNotFoundException e) {
            e.printStackTrace ( );
        }
    }

    private void translateEngRu() {
        try (Scanner scanner = new Scanner (new FileInputStream (engFile))) {
            while (scanner.hasNextLine ( )) {
                String line = scanner.nextLine ( );
                String[] words = line.split (" ");
                for (String word1 : words) {
                    for (String word2 : words) {
                        engWords.put (word1.toLowerCase (), word2.toLowerCase ());
                    }
                }
            }
            System.out.println ("Type in an English word to get a Russian translation ");
            String engInput = engWords.get (scLine.nextLine ( ));
            System.out.println (Objects.requireNonNullElse (engInput, " There is no such a word in the dictionary yet. "));
        } catch (FileNotFoundException e) {
            e.printStackTrace ( );
        }
    }

    void start() {
        int userInput;
        do {
            showMainMenu ( );
            userInput = scIn.nextInt ( );
            switch (userInput) {
                case 1:
                    translateEngRu ( );
                    break;
                case 2:
                    translateRuEng ( );
                    break;
                case 3:
                    addEngRuTranslation ( );
                    break;
                case 4:
                    addRuEngTranslation ( );
                    break;
                case 5:
                    break;
                default:
                    System.out.println ("Incorrect input");
                    break;
            }
        } while (userInput != 5);
    }
}

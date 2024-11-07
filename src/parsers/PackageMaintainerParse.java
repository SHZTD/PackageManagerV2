package parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PackageMaintainerParse {
    private String[] packages;

    private ArrayList<String> mantainer = new ArrayList<>();
    private ArrayList<String> mantainerEmail = new ArrayList<>();

    private final String path = PackageMaintainerParse.class.getResource("packageMaintainer.txt").getFile();
    private final File file = new File(path);

    public PackageMaintainerParse(String[] packageData) {
        this.packages = packageData;
    }

    public ArrayList<String> getMantainer() {
        return mantainer;
    }

    public ArrayList<String> getMantainerEmail() {
        return mantainerEmail;
    }

    public void findPackageMantainer() {
        ArrayList<String> fileLines = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            // salta la cabecera
            if (sc.hasNextLine()) {
                sc.nextLine();
            }

            // savage stuff but couldnt figure much :P
            while (sc.hasNextLine()) {
                fileLines.add(sc.nextLine());
            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        try {
            for (String pkg : packages) {
                for (String line : fileLines) {
                    String[] parts = line.split(";");
                    if (parts.length == 3) {
                        String packageName = parts[0];
                        String maintainerName = parts[1];
                        String maintainerEmail = parts[2];

                        if (pkg.equals(packageName)) {
                            mantainer.add(maintainerName);
                            mantainerEmail.add(maintainerEmail);
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("No se encontro el bug, no hay packages que procesar");
        }
    }
}

package parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BugPackageParse {
    private int bugID;
    private String[] packageNames;

    public String[] getPackageNames() {
        return packageNames;
    }

    public BugPackageParse(int bid) {
        this.bugID = bid;
    }

    private final String path = BugPackageParse.class.getResource("rcBugPackage.txt").getFile();

    File file = new File(path);

    public void readAndFindLine() {
        try (Scanner sc = new Scanner(file)) {
            // salta la primera linea
            if (sc.hasNextLine()) {
                sc.nextLine();
            }

            // BUG_ID;PACKAGE_NAME
            boolean foundBug = false;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(";");
                if (parts.length > 1) {
                    int theBugId = Integer.parseInt(parts[0].trim());
                    if (theBugId == bugID) {
                        // debug
                        //System.out.println("Se han populado los datos del array");
                        String[] packages = parts[1].split(",");
                        packageNames = new String[packages.length];
                        for (int i = 0; i < packages.length; i++) {
                            // popula el array de strings
                            packageNames[i] = packages[i].trim();
                            //System.out.println(packageNames[i]);
                        }
                        sc.close();
                        foundBug = true;
                        break;
                    }
                }
            }
            if (!foundBug) {
                EmailComposer c = new EmailComposer();
                c.composeEmail("Error: El bugID no existeix");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: El archivo no existe o no se encuentra");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error: El format del numero es incorrecto");
            e.printStackTrace();
        }
    }
}
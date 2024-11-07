import parsers.BugPackageParse;
import parsers.EmailComposer;
import parsers.PackageMaintainerParse;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        int bugID = 0;
        if (args.length != 1) {
            System.out.println(
                    "Argumento invalido.\n" +
                    "Uso del programa: PackageManajer.java [numero_bugID]"
            );
            System.exit(2);
        }

        try {
            bugID = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("No es un numero, o el formato no es el correcto");
            e.printStackTrace();
        }

        // instancia la clase con el valor del bug
        BugPackageParse bpp = new BugPackageParse(bugID);
        bpp.readAndFindLine(); // busca el valor

        // una vez buscado, se toman los datos de el array
        String[] packageList = bpp.getPackageNames(); // ahora packageList tiene todos los packages

        // encontrar a los responsables
        PackageMaintainerParse pkgMantainerP = new PackageMaintainerParse(packageList); // instancia con los paquetes
        pkgMantainerP.findPackageMantainer(); // empieza a buscar los responsables

        // almacenar los emails y el gestor
        ArrayList<String> maintainers = pkgMantainerP.getMantainer();
        ArrayList<String> email = pkgMantainerP.getMantainerEmail();

        // escribimos el email
        EmailComposer emailComposer = new EmailComposer();
        if (packageList.length > 1) {
            for (int i = 0; i < packageList.length; i++) {
                String e = "From: owner@bugs.debian.org\n" +
                        "To: " + email.get(i) +
                        "\nDear " + maintainers.get(i) +
                        "\nYou have a new bug: " +
                        packageList[i] + "- RC bug number #" + bugID +
                        "\nPlease, fix it asap.\nCheers.";
                emailComposer.composeEmail(e);
                System.out.println(e);
            }
        } else {
            String e = "From: owner@bugs.debian.org\n" +
                    "To: " + email.get(1) +
                    "\nDear " + maintainers.get(1) +
                    "\nYou have a new bug: " +
                    packageList[0] + "- RC bug number #" + bugID +
                    "\nPlease, fix it asap.\nCheers.";
            System.out.println(e);
            emailComposer.composeEmail(e);
        }
    }
}
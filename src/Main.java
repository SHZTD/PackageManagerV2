import parsers.BugPackageParse;
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
        // si es que hay mas de un package, encontrar estos responsables
        PackageMaintainerParse pkgMantainerP = new PackageMaintainerParse(packageList);
        pkgMantainerP.findPackageMantainer();
        ArrayList<String> maintainers = pkgMantainerP.getMantainer();
        ArrayList<String> email = pkgMantainerP.getMantainerEmail();
        // TODO: MOSTRAR AQUI LOS EMAILS I LOS NOMBRES DE MANTENIMIENTO
        System.out.println("Emails y Mantenedores:");
        for (int i = 0; i < maintainers.size(); i++) {
            System.out.println("Maintainer: " + maintainers.get(i) + ", Email: " + email.get(i));
        }
    }
}

/*
Debugar el array de strings

    for (int i = 0; i < packageList.length; i++) {
        System.out.println(packageList[i]);
    }
*/
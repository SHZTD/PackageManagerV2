package parsers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EmailComposer {
    public void composeEmail(String email) {
        String nombreArchivo = "emailMainteiner.txt";

        try {
            File archivo = new File(nombreArchivo);

            if (archivo.exists()) {
                System.out.println("El archivo existe, se sobreescribira.");
            } else {
                boolean creado = archivo.createNewFile();
                if (creado) {
                    System.out.println("Archivo creado.");
                } else {
                    System.out.println("No se pudo crear el archivo.");
                    return;
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                writer.write(email);
                System.out.println("Texto escrito correctamente en el archivo.");
            } catch (IOException e) {
                System.out.println("Error al escribir en el archivo: " + e.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Error al trabajar con el archivo: " + e.getMessage());
        }
    }
}
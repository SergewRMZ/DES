
package com.mycompany.cifrador_bloques.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
public class ArchivoUtils {
  public static File seleccionarArchivoImagen() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Selecciona una imagen BMP");
    FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagenes BMP", "bmp");
    fileChooser.setFileFilter(filtro);
    return fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION 
      ? fileChooser.getSelectedFile()
      : null;
  }
  
  public static byte[] leerArchivo (File archivo) throws IOException {
    return Files.readAllBytes(archivo.toPath());
  }
  
  public static void guardarArchivo(File archivo, byte[] contenido) throws IOException {
    try (FileOutputStream fos = new FileOutputStream(archivo)) {
        fos.write(contenido);
    }
  }

  public static void guardarArchivoResultado(File archivoOriginal, byte[] contenido, int modoOperacion, int accion) throws IOException {
    String sufijo = (accion == 1 ? "_e" : "_d") + obtenerSufijoModo(modoOperacion);
    String nombreArchivo = archivoOriginal.getName();
    nombreArchivo = nombreArchivo.substring(0, nombreArchivo.lastIndexOf(".")) + sufijo + ".bmp";

    File archivoResultado = new File(archivoOriginal.getParent(), nombreArchivo);
    guardarArchivo(archivoResultado, contenido);
    System.out.println("Archivo guardado como: " + archivoResultado.getAbsolutePath());
  }
  
  public static String obtenerSufijoModo(int modo) {
    return switch (modo) {
        case 1 -> "ECB";
        case 2 -> "CBC";
        case 3 -> "CFB";
        case 4 -> "OFB";
        default -> "";
    };
  }
}

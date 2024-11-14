package com.mycompany.cifrador_bloques;

import com.mycompany.cifrador_bloques.utils.ArchivoUtils;
import com.mycompany.cifrador_bloques.utils.CifradoUtils;
import com.mycompany.cifrador_bloques.utils.ClaveUtils;
import java.io.File;
import javax.crypto.spec.SecretKeySpec;
public class Main {
  public static void main(String args[]) {
    File imagen = ArchivoUtils.seleccionarArchivoImagen();
    if (imagen == null) {
        System.out.println("No se seleccionó ningún archivo.");
        return;
    }

    System.out.println("Archivo seleccionado: " + imagen.getAbsolutePath());
    byte[] key = ClaveUtils.obtenerClave();
    if (key == null) return;

    SecretKeySpec secretKey = new SecretKeySpec(key, "DES");
    byte[] iv = ClaveUtils.obtenerIV();
    int modoOperacion = InterfazUsuario.seleccionarModoOperacion();
    int accion = InterfazUsuario.seleccionarAccion();

    try {
        byte[] resultado = CifradoUtils.procesarImagen(imagen, secretKey, modoOperacion, iv, accion == 1);
        ArchivoUtils.guardarArchivoResultado(imagen, resultado, modoOperacion, accion);
    } catch (Exception e) {
        System.out.println("Error al realizar la operación: " + e.getMessage());
    }
  }
}

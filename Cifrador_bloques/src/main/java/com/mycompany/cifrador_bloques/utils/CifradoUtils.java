package com.mycompany.cifrador_bloques.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.util.Arrays;

public class CifradoUtils {

    public static byte[] procesarImagen(File imagen, SecretKeySpec secretKey, int modo, byte[] iv, boolean esCifrado) throws Exception {
        byte[] archivoBytes = ArchivoUtils.leerArchivo(imagen);
        byte[] cabecera = Arrays.copyOfRange(archivoBytes, 0, 54);
        byte[] datosImagen = Arrays.copyOfRange(archivoBytes, 54, archivoBytes.length);
        Cipher cipher;
        if (esCifrado)
          cipher = configurarCipher(secretKey, modo, iv, esCifrado, "PKCS5Padding");
        else
          cipher = configurarCipher(secretKey, modo, iv, esCifrado, "NoPadding");

        byte[] datosProcesados = cipher.doFinal(datosImagen);

        byte[] archivoResultado = new byte[cabecera.length + datosProcesados.length];
        System.arraycopy(cabecera, 0, archivoResultado, 0, cabecera.length);
        System.arraycopy(datosProcesados, 0, archivoResultado, cabecera.length, datosProcesados.length);

        return archivoResultado;
    }

    private static Cipher configurarCipher(SecretKeySpec secretKey, int modo, byte[] iv, boolean esCifrado, String padding) throws Exception {
    // Determina el esquema de cifrado y el relleno
    String modoCifrado;
    switch (modo) {
        case 1: // ECB
            modoCifrado = "DES/ECB/" + padding;
            break;
        case 2: // CBC
            modoCifrado = "DES/CBC/" + padding;
            break;
        case 3: // CFB
            modoCifrado = "DES/CFB/" + padding;
            break;
        case 4: // OFB
            modoCifrado = "DES/OFB/" + padding;
            break;
        default:
            throw new IllegalArgumentException("Modo de cifrado no válido.");
    }

    // Crea la instancia de Cipher
    Cipher cipher = Cipher.getInstance(modoCifrado);
    int operacion = esCifrado ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;

    if (modo == 1) { 
        cipher.init(operacion, secretKey);
    } else {
        cipher.init(operacion, secretKey, new IvParameterSpec(iv));
    }
    
    return cipher;
  }

}

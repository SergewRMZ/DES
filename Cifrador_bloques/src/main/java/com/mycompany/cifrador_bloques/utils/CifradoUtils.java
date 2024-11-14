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
        
        Cipher cipher = configurarCipher(secretKey, modo, iv, esCifrado);
        byte[] datosProcesados = cipher.doFinal(datosImagen);

        byte[] archivoResultado = new byte[cabecera.length + datosProcesados.length];
        System.arraycopy(cabecera, 0, archivoResultado, 0, cabecera.length);
        System.arraycopy(datosProcesados, 0, archivoResultado, cabecera.length, datosProcesados.length);

        return archivoResultado;
    }

    private static Cipher configurarCipher(SecretKeySpec secretKey, int modo, byte[] iv, boolean esCifrado) throws Exception {
        String modoCifrado = switch (modo) {
            case 1 -> "DES/ECB/PKCS5Padding";
            case 2 -> "DES/CBC/PKCS5Padding";
            case 3 -> "DES/CFB/PKCS5Padding";
            case 4 -> "DES/OFB/PKCS5Padding";
            default -> throw new IllegalArgumentException("Modo de cifrado no válido.");
        };

        Cipher cipher = Cipher.getInstance(modoCifrado);
        int operacion = esCifrado ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;

        if (modo == 1) {
            cipher.init(operacion, secretKey); // ECB no necesita IV
        } else {
            cipher.init(operacion, secretKey, new IvParameterSpec(iv));
        }
        
        return cipher;
    }
}

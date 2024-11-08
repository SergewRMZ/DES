package com.mycompany.cifrador_bloques;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

public class Cifrador_bloques {
    
    public static void main(String[] args) {
        File imagen = seleccionarArchivoImagen();
        if (imagen != null) {
            System.out.println("Archivo seleccionado: " + imagen.getAbsolutePath());
            
            byte[] key = setKey();
            
            if(key != null) {
                SecretKeySpec secretKey = new SecretKeySpec(key, "DES");
                byte[] iv = setIV();
                int opcionCifrado = setOperation();
                
                Scanner scanner = new Scanner(System.in);
                System.out.println("1. Cifrar\n 2. Descifrar");
                int modo = scanner.nextInt();
                
                while(modo < 1 || modo > 2) {
                    System.out.println("Opción invalida. Vuelva a intentarlo");
                    modo = scanner.nextInt();
                }
                
                try {
                    if(modo == 1) { // cifrado
                        try {
                            byte[] imagenCifrada = cifrarImagen(imagen, secretKey, opcionCifrado, iv);
                            System.out.println("Imagen cifrada con exito");
                            
                            String sufijo = getModoCifrado(opcionCifrado);
                            String nombreImagenI = imagen.getName();
                            int indicePunto = nombreImagenI.lastIndexOf(".");
                            
                            if(indicePunto != -1) {
                                nombreImagenI = nombreImagenI.substring(0, indicePunto);
                            }
                            
                            String nombreImagenOriginal = nombreImagenI + "_e" + sufijo + ".bmp";
                            File archivoCifrado = new File(imagen.getParent(), nombreImagenOriginal);
                            guardarArchivo(archivoCifrado, imagenCifrada);
                            System.out.println("Archivo descifrado guardado como: " + archivoCifrado.getAbsolutePath());
                            
                        } catch (Exception e) {
                            System.out.println("Error al cifrar: " + e.getMessage());
                        }
                        
                    }else { // descifrado
                        try {
                            byte[] imagenDescifrada = descifrarImagen(imagen, secretKey, opcionCifrado, iv);
                            System.out.println("Imagen descifrada con exito.");

                            String sufijo = getModoCifrado(opcionCifrado);
                            String nombreImagenI = imagen.getName();
                            int indicePunto = nombreImagenI.lastIndexOf(".");

                            if (indicePunto != -1) {
                                nombreImagenI = nombreImagenI.substring(0, indicePunto);
                            }

                            String nombreImagenOriginal = nombreImagenI + "_d" + sufijo + ".bmp";
                            File archivoDescifrado = new File(imagen.getParent(), nombreImagenOriginal);
                            guardarArchivo(archivoDescifrado, imagenDescifrada);
                            System.out.println("Archivo descifrado guardado como: " + archivoDescifrado.getAbsolutePath());

                        } catch (Exception e) {
                            System.out.println("Error al descifrar: " + e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error al realizar la operacion: " + e.getMessage());
                }
            }
        } else {
            System.out.println("No se selecciono ningun archivo.");
        }
    }
    
    public static File seleccionarArchivoImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona una imagen BMP");
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagenes BMP", "bmp");
        fileChooser.setFileFilter(filtro);
        

        int resultado = fileChooser.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }
    
    public static int setOperation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Selecciona el modo de cifrado:\n1.ECB\n2.CBC\n3.CFB\n4.OFB");
        
        int modo = scanner.nextInt();
        while(modo < 1 || modo > 4) {
            System.out.println("Opcion invalida. Vuelva a intentarlo");
            modo = scanner.nextInt();
        }
       
        return modo;
    }
    
    public static byte[] setKey() {
        Scanner scanner = new Scanner(System.in);
        String key;
        
        while(true) {
            System.out.print("Introduce una llave de 8 caracteres: ");
            key = scanner.nextLine();
            
            if(key.length() == 8) {
                
                try {
                    byte[] keyBytes = key.getBytes("UTF-8");
                    if(keyBytes.length == 8) {
                        return keyBytes;
                    }else {
                        System.out.println("Error, intenta con otros caracteres");
                    }
                } catch (UnsupportedEncodingException e) {
                    System.out.println("Error " + e.getMessage());
                }
            } else {
                System.out.println("La llave debe de ser de 8 caracteres");
            }
        }
    }
    
    // vector de inicialización
    public static byte[] setIV() {
        Scanner scanner = new Scanner(System.in);
        byte[] iv = new byte[8];

        while (true) {
            System.out.print("Introduce un vector de inicializacion de 8 valores numericos (entre 0 y 255), separados por espacio: ");
            String input = scanner.nextLine();
            String[] tokens = input.split(" "); 

            if (tokens.length == 8) {
                try {
                    for (int i = 0; i < 8; i++) {
                        int valor = Integer.parseInt(tokens[i]);
                        if (valor >= 0 && valor <= 255) {
                            iv[i] = (byte) valor; 
                        } else {
                            System.out.println("El valor " + valor + " esta fuera del rango permitido (0-255)");
                            iv = null;
                            break;
                        }
                    }
                    if (iv != null) {
                        return iv; 
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error al procesar los valores");
                }
            } else {
                System.out.println("El vector de inicializacion debe tener exactamente 8 valores numericos");
            }
        }
    }
    
    public static String getModoCifrado(int opcionCifrado) {
        return switch (opcionCifrado) {
            case 1 -> "ECB";
            case 2 -> "CBC";
            case 3 -> "CFB";
            case 4 -> "OFB";
            default -> "";
        };
    }
    
    public static byte[] cifrarImagen(File archivo, SecretKeySpec secretKey, int opcionCifrado, byte[] iv) throws Exception {
        byte[] archivoBytes = leerArchivo(archivo);
        //byte[] imagenBytes = Files.readAllBytes(archivo.toPath());
        // separamos la cabecera
        byte[] cabecera = Arrays.copyOfRange(archivoBytes, 0, 54);
        byte[] datosImagen = Arrays.copyOfRange(archivoBytes, 54, archivoBytes.length);
        
        // Cipher cipher = cifrado(secretKey, opcionCifrado, iv);
        byte[] datosImagenCifrado = cifrado(datosImagen, secretKey, opcionCifrado, iv);
        // byte[] datosImagenCifrado = cipher.doFinal(datosImagen);
        
        byte[] archivoCifrado = new byte[cabecera.length + datosImagenCifrado.length];
        System.arraycopy(cabecera, 0, archivoCifrado, 0, cabecera.length);
        System.arraycopy(datosImagenCifrado, 0, archivoCifrado, cabecera.length, datosImagenCifrado.length);
        
        return archivoCifrado;
    }
    
    public static byte[] descifrarImagen(File archivo, SecretKeySpec secretKey, int opcionCifrado, byte[] iv) throws Exception {
        byte[] archivoBytes = leerArchivo(archivo);
        
        byte[] cabecera = Arrays.copyOfRange(archivoBytes, 0, 54);
        byte[] datosImagen = Arrays.copyOfRange(archivoBytes, 54, archivoBytes.length);
        
        Cipher cipher = descifrado(secretKey, opcionCifrado, iv);
        
        byte[] datosImagenDescifrado = cipher.doFinal(datosImagen);
        
        byte[] archivoDescifrado = new byte[cabecera.length + datosImagenDescifrado.length];
        System.arraycopy(cabecera, 0, archivoDescifrado, 0, cabecera.length);
        System.arraycopy(datosImagenDescifrado, 0, archivoDescifrado, cabecera.length, datosImagenDescifrado.length);
        
        return archivoDescifrado;
    }

    
    public static byte[] cifrado(byte[] datosImagen, SecretKeySpec secretKey, int opcionCifrado, byte[] iv) throws Exception {
        Cipher cipher;
        IvParameterSpec vectorInicializacion = new IvParameterSpec(iv);
        
        switch(opcionCifrado) {
            case 1:
                cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                return cipher.doFinal(datosImagen);
            case 2:
                //AlgorithmParameterSpec algorithmParameterSpec = new IvParameterSpec(vectorIivECBnicializacion);
                cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, vectorInicializacion);
                return cipher.doFinal(datosImagen);
            case 3:
                cipher = Cipher.getInstance("DES/CFB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, vectorInicializacion);
                return cipher.doFinal(datosImagen);
            case 4:
                cipher = Cipher.getInstance("DES/OFB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, vectorInicializacion);
                return cipher.doFinal(datosImagen);
        }
        
        //return cipher;
        return null;
    }
    
    public static Cipher descifrado(SecretKeySpec secretKey, int opcionCifrado, byte[] iv) throws Exception {
        Cipher cipher;
        IvParameterSpec vectorInicializacion = new IvParameterSpec(iv);
        
        switch(opcionCifrado) {
            case 1:
                // con PKCS5Padding no sale
                cipher = Cipher.getInstance("DES/ECB/NoPadding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                return cipher;
            case 2:
                //AlgorithmParameterSpec algorithmParameterSpec = new IvParameterSpec(vectorIivECBnicializacion);
                cipher = Cipher.getInstance("DES/CBC/NoPadding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey, vectorInicializacion);
                return cipher;
            case 3:
                cipher = Cipher.getInstance("DES/CFB/NoPadding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey, vectorInicializacion);
                return cipher;
            case 4:
                cipher = Cipher.getInstance("DES/OFB/NoPadding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey, vectorInicializacion);
                return cipher;
        }
        
        //return cipher;
        return null;
    }
    
    public static byte[] leerArchivo(File archivo) throws IOException {
        byte[] bytes;
        try (FileInputStream fis = new FileInputStream(archivo)) {
            bytes = new byte[(int) archivo.length()];
            fis.read(bytes);
            //fis.close();
        }
        
        return bytes;
    }
   
    
    public static void guardarArchivo(File archivo, byte[] contenido) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            fos.write(contenido);
        }
    }
    
}

package com.mycompany.cifrador_bloques.utils;

import java.util.Scanner;

public class ClaveUtils {
  public static byte[] obtenerClave() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.print("Introduce una llave de 8 caracteres: ");
      String clave = scanner.nextLine();
      if (clave.length() == 8) {
          return clave.getBytes();
      } else {
          System.out.println("La llave debe tener exactamente 8 caracteres.");
      }
    }
  }

  public static byte[] obtenerIV() {
    Scanner scanner = new Scanner(System.in);
    byte[] iv = new byte[8];
    while (true) {
      System.out.print("Introduce un vector de inicialización de 8 valores numéricos (0-255), separados por espacio: ");
      String[] valores = scanner.nextLine().split(" ");
      if (valores.length == 8) {
          try {
              for (int i = 0; i < 8; i++) iv[i] = (byte) Integer.parseInt(valores[i]);
              return iv;
          } catch (NumberFormatException e) {
              System.out.println("Valores inválidos. Intente de nuevo.");
          }
      } else {
          System.out.println("Debe ingresar exactamente 8 valores.");
      }
    }
  }
}

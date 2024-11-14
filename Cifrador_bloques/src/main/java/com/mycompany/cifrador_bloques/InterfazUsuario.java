package com.mycompany.cifrador_bloques;

import java.util.Scanner;

public class InterfazUsuario {
  public static int seleccionarModoOperacion() {
    System.out.println("Selecciona el modo de cifrado:\n1. ECB\n2. CBC\n3. CFB\n4. OFB");
    return seleccionarOpcion(1, 4);
  }

  public static int seleccionarAccion() {
    System.out.println("Selecciona la acción:\n1. Cifrar\n2. Descifrar");
    return seleccionarOpcion(1, 2);
  }

  private static int seleccionarOpcion(int min, int max) {
    Scanner scanner = new Scanner(System.in);
    int opcion;
    do {
        opcion = scanner.nextInt();
        if (opcion < min || opcion > max) System.out.println("Opción inválida. Intente de nuevo.");
    } while (opcion < min || opcion > max);
    return opcion;
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

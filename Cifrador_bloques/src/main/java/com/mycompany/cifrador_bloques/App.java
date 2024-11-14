package com.mycompany.cifrador_bloques;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.mycompany.cifrador_bloques.views.MainView;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class App {
 public static void main (String args[]) {
   SwingUtilities.invokeLater(() -> {
     try {
       UIManager.setLookAndFeel(new FlatDarculaLaf());
       MainView main = MainView.getInstanceMain();
       SwingUtilities.updateComponentTreeUI(main);
       main.setVisible(true);
     } catch (UnsupportedLookAndFeelException ex) {
       Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
     }
   });
 }
}

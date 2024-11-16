package com.mycompany.cifrador_bloques.views;

import com.mycompany.cifrador_bloques.utils.ArchivoUtils;
import com.mycompany.cifrador_bloques.utils.CifradoUtils;
import java.awt.Image;
import java.io.File;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

public class DES_Main extends javax.swing.JFrame {
  private static DES_Main instance;
  private File image;
  
  private DES_Main() {
    initComponents();
    setLocationRelativeTo(null);
  }
  
  public static DES_Main getInstanceDESMain() {
    if(instance == null) {
      instance = new DES_Main();
    }
    
    return instance;
  }
  
  public void setImage(File image) {
    this.image = image;
  }
  
  
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    KeyText = new javax.swing.JLabel();
    VectorText = new javax.swing.JLabel();
    Btn_Cifrar = new javax.swing.JButton();
    box_modes = new javax.swing.JComboBox<>();
    InputKey = new javax.swing.JTextField();
    vectorInit = new javax.swing.JTextField();
    Btn_Descifrar = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    KeyText.setText("Ingresa la Llave:");

    VectorText.setText("Vector de inicialización");

    Btn_Cifrar.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
    Btn_Cifrar.setText("Cifrar");
    Btn_Cifrar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        Btn_CifrarActionPerformed(evt);
      }
    });

    box_modes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar modo de operación", "ECB", "CBC", "CFB", "OFB", " " }));
    box_modes.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        box_modesActionPerformed(evt);
      }
    });

    InputKey.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        InputKeyActionPerformed(evt);
      }
    });

    vectorInit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        vectorInitActionPerformed(evt);
      }
    });

    Btn_Descifrar.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
    Btn_Descifrar.setText("Descifrar");
    Btn_Descifrar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        Btn_DescifrarActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(51, 51, 51)
        .addComponent(Btn_Cifrar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
        .addComponent(Btn_Descifrar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(42, 42, 42))
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
            .addComponent(InputKey)
            .addComponent(box_modes, 0, 200, Short.MAX_VALUE)
            .addComponent(vectorInit))
          .addComponent(KeyText, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(VectorText, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(93, 93, 93))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addGap(66, 66, 66)
        .addComponent(KeyText)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(InputKey, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(16, 16, 16)
        .addComponent(VectorText)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(vectorInit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(18, 18, 18)
        .addComponent(box_modes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(Btn_Cifrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Btn_Descifrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(50, 50, 50))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private int obtenerModoOperacion() {
    String selectedMode = (String) box_modes.getSelectedItem();
    if (selectedMode == null || selectedMode.equals("Seleccionar modo de operación")) {
        JOptionPane.showMessageDialog(this, "Por favor, selecciona un modo de operación.", "Error", JOptionPane.ERROR_MESSAGE);
        return -1;
    }

    switch (selectedMode) {
        case "ECB": return 1;
        case "CBC": return 2;
        case "CFB": return 3;
        case "OFB": return 4;
        default:
            JOptionPane.showMessageDialog(this, "Modo de operación no válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
    }
  }
  
  private void procesarOperacion(boolean isEncrypt) {
    int modoOperacion = obtenerModoOperacion();
    if (modoOperacion == -1) return;

    byte[] keyBytes = obtenerClave();
    if (keyBytes == null) return;

    byte[] ivBytes = null;
    if (modoOperacion != 1) { // Si no es ECB
        ivBytes = obtenerIV();
        if (ivBytes == null) return;
    }

    try {
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "DES");
        byte[] resultado = CifradoUtils.procesarImagen(image, secretKey, modoOperacion, ivBytes, isEncrypt);
        ArchivoUtils.guardarArchivoResultado(image, resultado, modoOperacion, isEncrypt ? 1 : 2);
        String operacion = isEncrypt ? "Cifrado" : "Descifrado";
        JOptionPane.showMessageDialog(this, operacion + " realizado con éxito. Resultado guardado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al procesar la operación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
  
  private byte[] obtenerClave() {
    String keyText = InputKey.getText();
    if (keyText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa una clave válida.", "Error", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    if (keyText.length() != 8) {
        JOptionPane.showMessageDialog(this, "La clave debe tener exactamente 8 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    return keyText.getBytes();
  }

  private byte[] obtenerIV() {
    String ivText = vectorInit.getText();
    if (ivText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa un vector de inicialización (IV) válido.", "Error", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    if (ivText.length() != 8) {
        JOptionPane.showMessageDialog(this, "El vector de inicialización (IV) debe tener exactamente 8 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    return ivText.getBytes();
  }
  
  private void Btn_CifrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_CifrarActionPerformed
    procesarOperacion(true);
  }//GEN-LAST:event_Btn_CifrarActionPerformed

  private void InputKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputKeyActionPerformed
    procesarOperacion(false);
  }//GEN-LAST:event_InputKeyActionPerformed

  private void box_modesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_box_modesActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_box_modesActionPerformed

  private void vectorInitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vectorInitActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_vectorInitActionPerformed

  private void Btn_DescifrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_DescifrarActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_Btn_DescifrarActionPerformed

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(DES_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(DES_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(DES_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(DES_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new DES_Main().setVisible(true);
      }
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton Btn_Cifrar;
  private javax.swing.JButton Btn_Descifrar;
  private javax.swing.JTextField InputKey;
  private javax.swing.JLabel KeyText;
  private javax.swing.JLabel VectorText;
  private javax.swing.JComboBox<String> box_modes;
  private javax.swing.JTextField vectorInit;
  // End of variables declaration//GEN-END:variables
}

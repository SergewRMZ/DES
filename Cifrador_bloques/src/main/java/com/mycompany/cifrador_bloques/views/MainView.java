package com.mycompany.cifrador_bloques.views;

import com.mycompany.cifrador_bloques.utils.ArchivoUtils;
import com.mycompany.cifrador_bloques.utils.ImageLoader;
import java.io.File;
import javax.swing.Icon;
import javax.swing.JOptionPane;

public class MainView extends javax.swing.JFrame {
  private static MainView instance;
  private String pathBG = "/BGImage.jpg";
  private String pathLogo = "/Escom.png";
  
  public MainView() {
    initComponents();
    setLocationRelativeTo(null);
    setupBackground();
    setDescription();
  }
  
  public static MainView getInstanceMain () {
    if (instance == null) {
      instance = new MainView();
    }
    return instance;
  }
  
  private void setupBackground () {
    Icon icon = ImageLoader.loadImage(pathBG, this.getWidth(), this.getHeight());
    this.BGLabel.setIcon(icon);
    icon = ImageLoader.loadImage(pathLogo, LogoEscom.getWidth(), LogoEscom.getHeight());
    this.LogoEscom.setIcon(icon);
  }
  
  private void setDescription () {
    this.Label_Description.setText("<html><p>Cifrador de imágenes aplicando el algoritmo DES junto con los modos de operación.</p></html>");
  }

  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    Btn_Start = new javax.swing.JButton();
    LogoEscom = new javax.swing.JLabel();
    Label_Description = new javax.swing.JLabel();
    TitleLabel = new javax.swing.JLabel();
    BGLabel = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    Btn_Start.setBackground(new java.awt.Color(51, 204, 255));
    Btn_Start.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
    Btn_Start.setForeground(new java.awt.Color(255, 255, 255));
    Btn_Start.setText("Iniciar");
    Btn_Start.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        Btn_StartActionPerformed(evt);
      }
    });
    getContentPane().add(Btn_Start, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 520, 320, 50));

    LogoEscom.setBackground(new java.awt.Color(255, 255, 255));
    getContentPane().add(LogoEscom, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 100, 100));

    Label_Description.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
    getContentPane().add(Label_Description, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 390, 130));

    TitleLabel.setFont(new java.awt.Font("JetBrains Mono", 1, 28)); // NOI18N
    TitleLabel.setForeground(new java.awt.Color(255, 255, 255));
    TitleLabel.setText("Cifrador de Imágenes");
    getContentPane().add(TitleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 360, -1));
    getContentPane().add(BGLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 614, 601));

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void Btn_StartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_StartActionPerformed
    File imagen = ArchivoUtils.seleccionarArchivoImagen();
    if (imagen == null) {
      JOptionPane.showMessageDialog(this, "No se seleccionó ningún archivo", "Error", JOptionPane.WARNING_MESSAGE);
    }
    
    else {
      this.setVisible(false);
      DES_Main desMain = DES_Main.getInstanceDESMain();
      desMain.setVisible(true);
      desMain.setImage(imagen);
    }
  }//GEN-LAST:event_Btn_StartActionPerformed

  /**
   * @param args the command line arguments
   */
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel BGLabel;
  private javax.swing.JButton Btn_Start;
  private javax.swing.JLabel Label_Description;
  private javax.swing.JLabel LogoEscom;
  private javax.swing.JLabel TitleLabel;
  // End of variables declaration//GEN-END:variables
}

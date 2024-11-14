package com.mycompany.cifrador_bloques.utils;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageLoader {
  public static Icon loadImage(String path, int width, int height) {
    ImageIcon imageIcon = new ImageIcon(ImageLoader.class.getResource(path));
    Image image = imageIcon.getImage();
    return new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
  }
}

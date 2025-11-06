package obj;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FabricaImagen {
    public ImagenP4 fabricarDesdeArchivo(String filename) {
        try {
            BufferedImage img = ImageIO.read(new File(filename));
            int width = img.getWidth();
            int height = img.getHeight();
            ImagenP4 imagenP4 = new ImagenP4(width, height);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    imagenP4.setPixel(x, y, img.getRGB(x, y));
                }
            }
            return imagenP4;
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
            return null;
        }
    }
}
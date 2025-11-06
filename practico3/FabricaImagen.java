import java.io.IOException;
import javax.imageio.ImageIO; //leer y escribir imagenes en diferentes formatos de una imagen
import java.awt.image.BufferedImage;//cargar imagen
import java.io.File; //especificar rutas de archivo

public class FabricaImagen {
    public FabricaImagen() {}

    public Imagen fabricarDesdeArchivo(String filename) {
        BufferedImage bi = null;
        try {
            File f = new File(filename);
            bi = ImageIO.read(f);//lee y carga la imagen
        } catch (IOException e) {
            e.printStackTrace();
        }

        int w = bi.getWidth();
        int h = bi.getHeight();
        Imagen resultado = new Imagen(w, h); 
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                resultado.setPixel(i, j, bi.getRGB(i, j));
            }
        }
        return resultado;
    }
}
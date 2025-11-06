package obj;

public class Imagen extends ElementoGrafico {
    private ImagenP4 imagen;

    public Imagen(int posX, int posY, ImagenP4 imagen) {
        super(posX, posY);
        this.imagen = imagen;
        this.color = 0x000000;
    }

    @Override
    public int getAncho() {
        return imagen.getWidth();
    }

    @Override
    public int getAlto() {
        return imagen.getHeight();
    }

    @Override
    public void dibujar(ImagenP4 img) {
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                img.setPixel(posX + i, posY + j, imagen.getPixel(i, j));
            }
        }
        if (seleccionado) {
            img.lineaHorizontal(posX, posY + imagen.getHeight() + 2, imagen.getWidth(), 0xFF0000);
        }
    }

    @Override
    public boolean posicionDentroDeObjeto(int x, int y) {
        return x >= posX && x <= posX + imagen.getWidth() &&
               y >= posY && y <= posY + imagen.getHeight();
    }
}
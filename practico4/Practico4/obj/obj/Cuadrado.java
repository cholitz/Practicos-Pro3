package obj; 

public class Cuadrado extends ElementoGrafico {
    private int lado;

    public Cuadrado(int posX, int posY, int lado) {
        super(posX, posY);
        this.lado = lado;
    }

    @Override
    public int getAncho() {
        return lado;
    }

    @Override
    public int getAlto() {
        return lado;
    }

    @Override
    public void dibujar(ImagenP4 img) {
        img.lineaHorizontal(posX, posY, lado, color);
        img.lineaHorizontal(posX, posY + lado - 1, lado, color);
        img.lineaVertical(posX, posY, lado, color);
        img.lineaVertical(posX + lado - 1, posY, lado, color);
        if (seleccionado) {
            img.lineaHorizontal(posX, posY + lado + 2, lado, 0xFF0000);
        }
    }

    @Override
    public boolean posicionDentroDeObjeto(int x, int y) {
        return x >= posX && x <= posX + lado && y >= posY && y <= posY + lado;
    }
}
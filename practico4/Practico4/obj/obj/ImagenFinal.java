package obj;

import java.awt.Component;

public class ImagenFinal {
    private ListaP4<ElementoGrafico> listaElementos;
    private Component observador;
    private ImagenP4 image;

    public ImagenFinal(int width, int height) {
        image = new ImagenP4(width, height);
    }

    public void setListaElementos(ListaP4<ElementoGrafico> lista) {
        this.listaElementos = lista;
    }

    public void setObservador(Component observador) {
        this.observador = observador;
    }

    public void actualizar() {
        image.lienzoBlanco();
        if (listaElementos != null) {
            for (ElementoGrafico el : listaElementos) {
                el.dibujar(image);
            }
        }
        if (observador != null) {
            observador.repaint();
            System.out.println("ImagenFinal actualizada - Notificación al observador");
        }
    }

    public ImagenP4 getImagenP4() {
        return image;
    }
}
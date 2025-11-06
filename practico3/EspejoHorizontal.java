public class EspejoHorizontal implements IOperacionImagen {
    private Imagen target;

    public EspejoHorizontal(Imagen img) {
        target = img;
    }

    @Override
    public void ejecutar() {
        Imagen resultado = new Imagen(target.getAncho(), target.getAlto());
        for (int i = 0; i < target.getAncho(); i++) {
            for (int j = 0; j < target.getAlto(); j++) {
                int xEspejo = target.getAncho() - 1 - i; 
                int r = target.getR(xEspejo, j);
                int g = target.getG(xEspejo, j);
                int b = target.getB(xEspejo, j);
                resultado.setPixel(i, j, r, g, b);
            }
        }
        target.setPixeles(resultado.getAncho(), resultado.getAlto(), resultado.getPixeles());
    }
}
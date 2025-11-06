public class EspejoVertical implements IOperacionImagen {
    private Imagen target;

    public EspejoVertical(Imagen img) {
        target = img;
    }

    @Override
    public void ejecutar() {
        Imagen resultado = new Imagen(target.getAncho(), target.getAlto());
        for (int i = 0; i < target.getAncho(); i++) {
            for (int j = 0; j < target.getAlto(); j++) {
                int yEspejo = target.getAlto() - 1 - j; 
                int r = target.getR(i, yEspejo);
                int g = target.getG(i, yEspejo);
                int b = target.getB(i, yEspejo);
                resultado.setPixel(i, j, r, g, b);
            }
        }
        target.setPixeles(resultado.getAncho(), resultado.getAlto(), resultado.getPixeles());
    }
}
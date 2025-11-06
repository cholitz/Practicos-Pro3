import java.util.List;

public class QuickSort {

    public void ordenar(List<Persona> lista) {
        log("Iniciando ordenamiento de " + lista.size() + " personas a las " + getCurrentTime() + ".");
        quickSort(lista, 0, lista.size() - 1); //llama al metodo recursivo
        log("Ordenamiento finalizado a las " + getCurrentTime() + ".");
    }

    private void quickSort(List<Persona> lista, int inicio, int fin) {
        log("Entrando a quickSort con inicio=" + inicio + ", fin=" + fin);
        if (inicio < fin) {
            int pivoteIdx = particion(lista, inicio, fin);
            quickSort(lista, inicio, pivoteIdx - 1);
            quickSort(lista, pivoteIdx + 1, fin);
        }
    }

    private int particion(List<Persona> lista, int inicio, int fin) {
        int pivote = lista.get(fin).getCi();
        int i = inicio - 1;
        for (int j = inicio; j < fin; j++) { //recorrer
            if (lista.get(j).getCi() <= pivote) {
                i++;
                Persona temp = lista.get(i);
                lista.set(i, lista.get(j));
                lista.set(j, temp);
            }
        }
        Persona temp = lista.get(i + 1);
        lista.set(i + 1, lista.get(fin));
        lista.set(fin, temp);

        log("Partición completada: pivote CI " + pivote + " a las " + getCurrentTime() + ".");
        return i + 1;
    }

    private String getCurrentTime() {
        return java.time.LocalDateTime.now().toString();
    }

    private void log(String mensaje) {
        System.out.println(mensaje);
    }
}

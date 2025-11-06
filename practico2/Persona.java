
public class Persona {
    private String nombre;
    private String apellido;
    private int ci;
    private String direccion;
    private String telefono;

    public Persona(String nombre, String apellido, int ci, String direccion, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.ci = ci;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
    public int getCi() {
        return ci;
    }
    public String getDireccion() {
        return direccion;
    }
    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Apellido: " + apellido + ", CI: "
         + ci + ", Dirección: " + direccion + ", Teléfono: " + telefono;
}


}

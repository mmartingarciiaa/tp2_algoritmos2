package objetosAuxiliares;

// Class Jugador es la clase donde se almacenan los datos del jugador
// como la nave, la base y sus coordenadas para acceder a ellos
// en cualquier momento del juego

public class Jugador {
    private Pieza base;
    private Pieza[] naves;
    private Pieza[] satelites;
    private final String nombre;

    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public boolean puedeAtacar() {
        return naves != null && naves.length > 0;
    }

    // Método obtenerNave obtiene el valor de la nave
    public Pieza[] obtenerNaves() {
        return naves;
    }

    // Método asignarNave asigna un valor a la nave
    public void agregarNave(Pieza nave) {
        naves[naves.length] = nave;
    }

    // Método obtenerBase obtiene el valor de la base
    public String obtenerBase() {
        return base;
    }

    // Método asignarBase asigna un valor a la base
    public void asignarBase(Pieza base) {
        this.base = base;
    }

    // Método destruirNave destruye la nave
    public void destruirNave() {
        nave = " ";
    }

    // Método obtenerCoordenadasNave obtiene las coordenadas de la base
    public int[] obtenerCoordenadasBase() {
        int[] coordenadas = {baseX, baseY, baseZ};
        return coordenadas;
    }
}
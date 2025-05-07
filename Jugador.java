import tdas.cola.ColaEnlazada;
import tdas.lista.IteradorLista;
import tdas.lista.ListaEnlazada;

// Class Jugador es la clase donde se almacenan los datos del jugador
// como la nave, la base y sus coordenadas para acceder a ellos
// en cualquier momento del juego

public class Jugador {
    private ListaEnlazada<Base> bases = null;
    private ListaEnlazada<Nave> naves = null;
    private ListaEnlazada<Satelite> satelites = null;
    private ColaEnlazada<Carta> cartas = null;
    private final String nombre;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.bases = new ListaEnlazada<>();
        this.naves = new ListaEnlazada<>();
        this.satelites = new ListaEnlazada<>();
        this.cartas = new ColaEnlazada<>();
    }

    public String obtenerNombre() {
        return nombre;
    }

    public boolean puedeAtacar() {
        return naves.largo() > 0;
    }

    // Método obtenerBase obtiene el valor de la base
    public ListaEnlazada<Base> obtenerBases() {
        return bases;
    }
    
    // Método asignarBase asigna un valor a la base
    public void agregarBase(Base base) {
        this.bases.insertarUltimo(base);
    }

    
    // Método obtenerNave obtiene el valor de la nave
    public ListaEnlazada<Nave> obtenerNaves() {
        return naves;
    }
    
    // Método asignarNave asigna un valor a la nave
    public void agregarNave(Nave nave) {
        this.naves.insertarUltimo(nave);
    }
    
    
    public void eliminarNave(int x, int y, int z) {
        IteradorLista<Nave> iter = naves.iterador();
        while (iter.haySiguiente()) {
            Nave nave = iter.verActual();
            int[] coords = nave.obtenerCoordenadas();
            if (coords[0] == x && coords[1] == y && coords[2] == z) {
                iter.borrar();
            }
            iter.siguiente();
        }
    }
    
    public void agregarCarta(Carta carta) {
        cartas.encolar(carta);
    }

    public Carta sacarCarta() {
        return cartas.desencolar();
    }

    public void agregarSatelite(Satelite satelite) {
        this.satelites.insertarUltimo(satelite);
    }
}
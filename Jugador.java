package objetosAuxiliares;

import tdas.cola.ColaEnlazada;
import tdas.lista.IteradorLista;
import tdas.lista.ListaEnlazada;

// Class Jugador es la clase donde se almacenan los datos del jugador
// como la nave, la base y sus coordenadas para acceder a ellos
// en cualquier momento del juego

public class Jugador {
    private ListaEnlazada<Pieza> bases;
    private ListaEnlazada<Pieza> naves;
    private ListaEnlazada<Pieza> satelites;
    private ColaEnlazada<Carta> cartas;
    private final String nombre;

    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public boolean puedeAtacar() {
        return naves.largo() > 0;
    }

    // Método obtenerBase obtiene el valor de la base
    public ListaEnlazada<Pieza> obtenerBases() {
        return bases;
    }
    
    // Método asignarBase asigna un valor a la base
    public void agregarBase(Pieza base) {
        this.bases.insertarUltimo(base);
    }

    
    // Método obtenerNave obtiene el valor de la nave
    public ListaEnlazada<Pieza> obtenerNaves() {
        return naves;
    }
    
    // Método asignarNave asigna un valor a la nave
    public void agregarNave(Pieza nave) {
        this.naves.insertarUltimo(nave);
    }
    
    
    public void eliminarNave(int x, int y, int z) {
        IteradorLista<Pieza> iter = naves.iterador();
        while (iter.haySiguiente()) {
            Pieza nave = iter.verActual();
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
    
    public String toStringBases() {
        StringBuilder sb = new StringBuilder("Bases del jugador:\n");
        bases.iterar(pieza -> {
            sb.append("  - ").append(pieza).append("\n");
            return true;
        });
        return sb.toString();
    }

    public String toStringNaves() {
        StringBuilder sb = new StringBuilder("Naves del jugador:\n");
        naves.iterar(pieza -> {
            sb.append("  - ").append(pieza).append("\n");
            return true;
        });
        return sb.toString();
    }

    public void agregarSatelite(Pieza satelite) {
        this.satelites.insertarUltimo(satelite);
    }
}
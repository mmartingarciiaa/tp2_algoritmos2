package menu;

// Importaciones necesarias
import estructuras.cola.ColaEnlazada;
import estructuras.lista.IteradorLista;
import estructuras.lista.ListaSimplementeEnlazada;
import mazo.Carta;
import piezas.Base;
import piezas.Nave;
import piezas.Satelite;

// Class Jugador es la clase donde se almacenan los datos del jugador
// como la nave, la base y sus coordenadas para acceder a ellos
// en cualquier momento del juego

public class Jugador {
    private ListaSimplementeEnlazada<Base> bases = null;
    private ListaSimplementeEnlazada<Nave> naves = null;
    private ListaSimplementeEnlazada<Satelite> satelites = null;
    private ColaEnlazada<Carta> cartas = null;
    private final String nombre;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.bases = new ListaSimplementeEnlazada<>();
        this.naves = new ListaSimplementeEnlazada<>();
        this.satelites = new ListaSimplementeEnlazada<>();
        this.cartas = new ColaEnlazada<>();
    }

    public String obtenerNombre() {
        return nombre;
    }

    public boolean puedeAtacar() {
        return naves.largo() > 0;
    }

    // Método obtenerBase obtiene el valor de la base
    public ListaSimplementeEnlazada<Base> obtenerBases() {
        return bases;
    }
    
    // Método asignarBase asigna un valor a la base
    public void agregarBase(Base base) {
        this.bases.insertarUltimo(base);
    }

    public void eliminarBase(int x, int y, int z) {
        IteradorLista<Base> iter = bases.iterador();
        while (iter.haySiguiente()) {
            Base base = iter.verActual();
            int[] coords = base.obtenerCoordenadas();
            if (coords[0] == x && coords[1] == y && coords[2] == z) {
                iter.borrar();
                break;
            }
            iter.siguiente();
        }
    }
    
    // Método obtenerNave obtiene el valor de la nave
    public ListaSimplementeEnlazada<Nave> obtenerNaves() {
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
                break;
            }
            iter.siguiente();
        }
    }
    
    public ColaEnlazada<Carta> obtenerCartas() {
        return cartas;
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

    /**
     * Devuelve una lista enlazada de los satélites asociados al jugador.
     *
     * @return ListaEnlazada<Satelite> que contiene los satélites del jugador.
     * @author Patricio Alaniz
     */
    public ListaSimplementeEnlazada<Satelite> obtenerSatelites() {
        return this.satelites;
    }

    public void eliminarSatelite(int x, int y, int z) {
        IteradorLista<Satelite> iter = satelites.iterador();
        while (iter.haySiguiente()) {
            Satelite satelite = iter.verActual();
            int[] coords = satelite.obtenerCoordenadas();
            if (coords[0] == x && coords[1] == y && coords[2] == z) {
                iter.borrar();
                break;
            }
            iter.siguiente();
        }
    }
}
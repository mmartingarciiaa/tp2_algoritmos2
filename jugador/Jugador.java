package jugador;

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
// en cualquier momento del juego, además de las cartas y alianzas que posee.
public class Jugador {
    private ListaSimplementeEnlazada<Base> bases = null;
    private ListaSimplementeEnlazada<Nave> naves = null;
    private ListaSimplementeEnlazada<Satelite> satelites = null;
    private ColaEnlazada<Carta> cartas = null;
    private ListaSimplementeEnlazada<Alianza> alianzas = null;
    private final String nombre;

    /**
     * Constructor de la clase Jugador.
     * Crea un nuevo jugador con un nombre específico y inicializa sus estructuras de datos.
     * 
     * @param nombre El nombre del jugador.
     * @throws IllegalArgumentException Si el nombre es nulo o vacío.
     */
    public Jugador(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        this.nombre = nombre;
        this.bases = new ListaSimplementeEnlazada<>();
        this.naves = new ListaSimplementeEnlazada<>();
        this.satelites = new ListaSimplementeEnlazada<>();
        this.cartas = new ColaEnlazada<>();
        this.alianzas = new ListaSimplementeEnlazada<>();
    }

    /**
     * Obtiene el nombre del jugador.
     * 
     * @return El nombre del jugador.
     */
    public String obtenerNombre() {
        return nombre;
    }

    /**
     * Verifica si el jugador tiene naves disponibles para atacar.
     * 
     * @return true si el jugador tiene al menos una nave, false en caso contrario.
     */
    public boolean puedeAtacar() {
        return naves.largo() > 0;
    }

    /**
     * Obtiene una lista enlazada de las bases asociadas al jugador.
     * 
     * @return ListaSimplementeEnlazada<Base> que contiene las bases del jugador.
     */
    public ListaSimplementeEnlazada<Base> obtenerBases() {
        return bases;
    }
    
    /**
     * Agrega una base a la lista de bases del jugador.
     */
    public void agregarBase(Base base) {
        this.bases.insertarUltimo(base);
    }

    /**
     * Elimina una base de la lista de bases del jugador.
     * 
     * @param x Coordenada x de la base a eliminar.
     * @param y Coordenada y de la base a eliminar.
     * @param z Coordenada z de la base a eliminar.
     */
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

    /**
     * Obtiene una lista enlazada de las naves asociadas al jugador.
     *
     * @return ListaSimplementeEnlazada<Nave> que contiene las naves del jugador.
     */
    public ListaSimplementeEnlazada<Nave> obtenerNaves() {
        return naves;
    }

    /**
     * Agrega una nave a la lista de naves del jugador.
     *
     * @param nave La nave a agregar.
     */
    public void agregarNave(Nave nave) {
        this.naves.insertarUltimo(nave);
    }
    
    /**
     * Elimina una nave de la lista de naves del jugador.
     *
     * @param x Coordenada x de la nave a eliminar.
     * @param y Coordenada y de la nave a eliminar.
     * @param z Coordenada z de la nave a eliminar.
     */
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
    
    /**
     * Obtiene una cola enlazada de las cartas del jugador.
     *
     * @return ColaEnlazada<Carta> que contiene las cartas del jugador.
     */
    public ColaEnlazada<Carta> obtenerCartas() {
        return cartas;
    }

    /**
     * Agrega una carta a la cola de cartas del jugador.
     *
     * @param carta La carta a agregar.
     */
    public void agregarCarta(Carta carta) {
        cartas.encolar(carta);
    }

    /**
     * Saca una carta de la cola de cartas del jugador.
     *
     * @return Carta que ha sido sacada de la cola o null si la cola está vacía.
     */
    public Carta sacarCarta() {
        if (cartas.estaVacia()) {
            return null;
        }
        return cartas.desencolar();
    }

    /**
     * Agrega un satélite a la lista de satélites del jugador.
     *
     * @param satelite El satélite a agregar.
     */
    public void agregarSatelite(Satelite satelite) {
        this.satelites.insertarUltimo(satelite);
    }

    /**
     * Devuelve una lista enlazada de los satélites asociados al jugador.
     *
     * @return ListaEnlazada<Satelite> que contiene los satélites del jugador.
     */
    public ListaSimplementeEnlazada<Satelite> obtenerSatelites() {
        return this.satelites;
    }

    /**
     * Elimina un satélite de la lista de satélites del jugador.
     *
     * @param x Coordenada x del satélite a eliminar.
     * @param y Coordenada y del satélite a eliminar.
     * @param z Coordenada z del satélite a eliminar.
     */
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

    /**
     * Agrega una nueva alianza al jugador.
     */
    public void agregarAlianza(Alianza nuevaAlianza) {
        alianzas.insertarUltimo(nuevaAlianza);
    }

    /**
     * Elimina una alianza del jugador.
     *
     * @param alianza La alianza a eliminar.
     */
    public void eliminarAlianza(Alianza alianza) {
        IteradorLista<Alianza> iter = alianzas.iterador();
        while (iter.haySiguiente()) {
            if (iter.verActual().equals(alianza)) {
                iter.borrar();
                break;
            }
            iter.siguiente();
        }
    }

    /**
     * Obtiene una lista de alianzas del jugador.
     *
     * @return ListaSimplementeEnlazada<Alianza> que contiene las alianzas del jugador.
     */
    public ListaSimplementeEnlazada<Alianza> obtenerAlianzas() {
        return alianzas;
    }

    /**
     * Obtiene una lista de los jugadores aliados del jugador actual.
     *
     * @return ListaSimplementeEnlazada<Jugador> que contiene los jugadores aliados.
     */
    public ListaSimplementeEnlazada<Jugador> obtenerJugadoresAliados() {
        ListaSimplementeEnlazada<Jugador> jugadoresAliados = new ListaSimplementeEnlazada<>();
        IteradorLista<Alianza> iter = alianzas.iterador();
        while (iter.haySiguiente()) {
            Alianza alianza = iter.verActual();
            Jugador aliado = alianza.obtenerOtroJugador(this);
            if (aliado != null) {
                jugadoresAliados.insertarUltimo(aliado);
            }
            iter.siguiente();
        }
        return jugadoresAliados;
    }

    /**
     * Verifica si el jugador es aliado de otro jugador.
     *
     * @param otroJugador El otro jugador a verificar.
     * @return true si el jugador es aliado del otro jugador, false en caso contrario.
     */
    public boolean esAliado(Jugador otroJugador) {
        IteradorLista<Alianza> iter = alianzas.iterador();
        while (iter.haySiguiente()) {
            Alianza alianza = iter.verActual();
            if (alianza.contieneJugador(otroJugador)) {
                return true;
            }
            iter.siguiente();
        }
        return false;
    }
    
    /**
     * Verifica si el jugador tiene alianzas activas.
     * 
     * @return true si el jugador tiene al menos una alianza activa, false en caso contrario.
     */
    public boolean tieneAlianzas() {
        return !alianzas.estaVacia();
    }
}
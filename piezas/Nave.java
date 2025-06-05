package piezas;

// Importaciones necesarias
import enums.TipoPieza;
import jugador.Jugador;
import utils.ValidacionesUtils;

/**
 * Clase que representa una nave en el juego Invasión Galáctica.
 * Hereda de la clase Pieza.
 * 
 * Las naves pueden moverse, atacar, recibir escudos (temporales o permanentes),
 * y modificar su daño de ataque mediante cartas u otras acciones.
 */

public class Nave extends Pieza {
    private static final int ESCUDO_INICIAL = 0;

    private int danio;

    /**
     * Constructor de la nave.
     * @param duenio        Jugador propietario de la nave
     * @param x             Coordenada X
     * @param y             Coordenada Y
     * @param z             Coordenada Z
     * @param nombre        Representación de la nave en el tablero
     * @param vida          Vida inicial de la nave
     * @param danioInicial  Poder de ataque inicial de la nave (debe ser mayor a cero)
     */
    public Nave(Jugador duenio, int x, int y, int z, String nombre, int vida, int danioInicial) {
        super(TipoPieza.NAVE, duenio, x, y, z, nombre, vida, ESCUDO_INICIAL);
        ValidacionesUtils.validarMayorACero(danioInicial, "Daño inicial de la nave");
        this.danio = danioInicial;
    }

    /**
     * Obtiene el daño de ataque actual de la nave.
     * 
     * @return el valor de daño como entero
     */
    public int obtenerDanio() {
        return danio;
    }

    /**
     * Establece un nuevo valor de daño de ataque.
     * 
     * @param nuevoDanio el nuevo daño de la nave (debe ser mayor a cero)
     */
    public void setDanio(int nuevoDanio) {
        ValidacionesUtils.validarMayorACero(nuevoDanio, "Nuevo daño de la nave");
        this.danio = nuevoDanio;
    }

    /**
     * Incrementa el daño de la nave.
     * 
     * @param extra cantidad extra de daño a sumar (debe ser mayor a cero)
     */
    public void aumentarDanio(int extra) {
        ValidacionesUtils.validarMayorACero(extra, "Aumento de daño");
        this.danio += extra;
    }

    /**
     * Realiza un ataque con la nave.
     * 
     * @return el daño infligido por la nave
     */
    public int atacar() { 
        return danio;
    }
}

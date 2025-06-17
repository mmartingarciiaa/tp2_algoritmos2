package piezas;

// Importaciones necesarias
import Coordenada.Coordenada;
import enums.TipoPieza;
import jugador.Jugador;
import utils.ValidacionesUtils;

/**
 * Clase que representa una nave en el juego Invasión Galáctica.
 * Hereda de la clase Pieza.
 * Las naves pueden moverse, atacar, recibir escudos (temporales o permanentes),
 * y modificar su daño de ataque mediante cartas u otras acciones.
 */

public class Nave extends Pieza {
    private static final int ESCUDO = 0;
    private int danio;

    /**
     * Constructor de la nave.
     * @param duenio        Jugador propietario de la nave
     * @param coordenadas   Coordenadas de la nave en el tablero
     * @param nombre        Representación de la nave en el tablero
     * @param vida          Vida inicial de la nave
     * @param danioInicial  Poder de ataque inicial de la nave (debe ser mayor a cero)
     * 
     * @throws RuntimeException si la creación de la pieza no es válida
     * @throws IllegalArgumentException si el daño inicial es menor o igual a cero
     */
    public Nave(Jugador duenio, Coordenada coordenadas, String nombre, int vida, int danioInicial) {
        super(TipoPieza.NAVE, duenio, coordenadas, nombre, vida, ESCUDO);
        ValidacionesUtils.validarMayorACero(danioInicial, "Daño inicial de la nave");
        this.danio = danioInicial;
    }

    /**
     * Obtiene el daño de ataque actual de la nave.
     * 
     * @return el valor de daño como entero
     */
    public int obtenerDanio() {
        return this.danio;
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

}

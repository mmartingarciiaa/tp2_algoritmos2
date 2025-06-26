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
	 * @param duenio: Jugador dueño de la pieza 
	 * @param coordenadas: Coordenadas de la pieza en el tablero no pueden ser nulas
	 * @param nombre: Nombre de la pieza no puede ser nulo y debe tener al menos 1 caracter
	 * @param vida: Vida inicial de la pieza (debe ser mayor a cero)
	 * @param danioInicial  Poder de ataque inicial de la nave (debe ser mayor a cero)
     * 
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

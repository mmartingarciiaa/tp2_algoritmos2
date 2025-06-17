package tablero;

// Importaciones necesarias
import Coordenada.Coordenada;
import piezas.Pieza;
import utils.ValidacionesUtils;

/**
 * Clase Sector representa un sector del tablero de juego.
 * Cada sector tiene coordenadas y un valor que puede ser una pieza
 * como una nave, base, etc.
 */

public class Sector {
    private final Coordenada coordenadas;
    private Pieza valor;

    /**
     * Constructor de la clase Sector.
     * Inicializa un sector con coordenadas y un valor.
     *
     * @param coordenadas coordenadas del sector
     * @param valor valor del sector (puede ser una nave, base, etc.)
     * 
     * @throws RuntimeException si el valor del sector es nulo
     */
    public Sector(Coordenada coordenadas, Pieza valor) {
        this.coordenadas = coordenadas;
        this.asignarValor(valor);
    }

    /**
     * Obtiene el valor del sector.
     * 
     * @return valor del sector (puede ser una nave, base, etc.)
     */
    public Pieza obtenerValor() {
        return valor;
    }

    /**
     * Asigna un nuevo valor al sector.
     */
    public void asignarValor(Pieza valor) {
        ValidacionesUtils.noNulo(valor, "del sector");
        this.valor = valor;
    }

    /**
     * Obtiene las coordenadas del sector.
     *
     * @return Coordenada del sector
     */
    public Coordenada obtenerCoordenadas() {
        return coordenadas;
    }
}
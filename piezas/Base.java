package piezas;

// Importaciones necesarias
import Coordenada.Coordenada;
import enums.TipoPieza;
import jugador.Jugador;

/**
 * Clase Base que representa una pieza de tipo base en el juego.
 * Hereda de la clase Pieza y define las características específicas de una base.
 */
public class Base extends Pieza {
    /**
     * Constructor de la clase Pieza 
	 * @param duenio: Jugador dueño de la pieza 
	 * @param coordenadas: Coordenadas de la pieza en el tablero no pueden ser nulas
	 * @param nombre: Nombre de la pieza no puede ser nulo y debe tener al menos 1 caracter
	 * @param vida: Vida inicial de la pieza (debe ser mayor a cero)
	 * @param escudo: Escudo inicial de la pieza (debe ser mayor o igual a cero)
     */
	public Base(Jugador duenio, Coordenada coordenadas, String nombre, int vida, int escudo) {
        // Llama al constructor de la clase base Pieza
		super(TipoPieza.BASE, duenio, coordenadas, nombre, vida, escudo);
    }
}
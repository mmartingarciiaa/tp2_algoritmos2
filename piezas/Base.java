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
     * @param duenio: jugador al que pertenece la base
     * @param coordenadas: coordenadas de la base 
     * @param nombre: nombre de la base
     * @param vida: vida de la base 
     * @param escudo: escudo de la base
     * 
     * @throws RuntimeException si la creación de la pieza no es válida
     */
	public Base(Jugador duenio, Coordenada coordenadas, String nombre, int vida, int escudo) {
        // Llama al constructor de la clase base Pieza
		super(TipoPieza.BASE, duenio, coordenadas, nombre, vida, escudo);
        if (!creacionValida()) {
            throw new RuntimeException("Creación de pieza Base inválida: " + 
                    "Dueño: " + duenio + ", " +
                    "Nombre: " + nombre + ", " +
                    "Vida: " + vida + ", " +
                    "Escudo: " + escudo + ", " +
                    "Coordenadas: [" + coordenadas.getX() + ", " + coordenadas.getY() + ", " + coordenadas.getZ() + "]");
        }
	}

}
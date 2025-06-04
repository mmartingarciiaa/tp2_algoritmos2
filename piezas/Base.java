package piezas;

// Importaciones necesarias
import enums.TipoPieza;
import menu.Jugador;

public class Base extends Pieza {
    /**
     * Constructor de la clase Pieza 
     * @param duenio: jugador al que pertenece la base
     * @param x: coordenada x de la base
     * @param y: coordenada y de la base
     * @param z: coordenada z de la base 
     * @param nombre: nombre de la base
     * @param vida: vida de la base 
     * @param escudo: escudo de la base
     */
	public Base(Jugador duenio, int x, int y, int z, String nombre, int vida, int escudo) {
		super(TipoPieza.BASE, duenio, x, y, z, nombre, vida, escudo);
	}
}
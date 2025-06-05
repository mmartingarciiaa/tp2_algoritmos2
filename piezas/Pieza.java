package piezas;

// Importaciones necesarias
import enums.TipoPieza;
import jugador.Jugador;
import utils.ValidacionesUtils;

public class Pieza {
	private TipoPieza tipo;
	private String nombre;
	private final int[] coordenadas;
	private Jugador duenio;
	private int vida;
	private int escudo;
	
	public Pieza(TipoPieza tipo, Jugador duenio, int x, int y, int z, String nombre, int vida, int escudo) {
		this.tipo = tipo;
		this.duenio = duenio;
		this.coordenadas = new int[] {x, y, z};
		this.nombre = nombre;
		this.vida = vida;
		this.escudo = escudo;
	}
	
	/**
	 * Devuelve el nombre del dueño de la base
	 * @return
	 */
	public Jugador obtenerDuenio() {
		return duenio;
	}

	/**
	 * @return: Devuelve como esta representada la pieza en el tablero 
	 */
	public String obtenerNombre() {
		return nombre;
	}
	
	/**
	 * Cambia el nombre de la pieza.
	 * 
	 * @param nuevoNombre: El nuevo nombre que se le asignará a la pieza.
	 */
	public void cambiarNombre(String nuevoNombre) {
		this.nombre = nuevoNombre;
	}

	/**
	 * 
	 * @return: Devuelve el tipo de pieza 
	 */
	
	public TipoPieza obtenerTipo() {
		return tipo;
	}

	/**
	 * pre:
	 * @param tipo: tiene que pertenecer a TipoPieza
	 * 
	 * pos: cambia el tipo de pieza 
	 */
	
	public void cambiarTipo(TipoPieza tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * 
	 * @return: Devuelve las coordenadas pasadas 
	 */
	
	public int[] obtenerCoordenadas() {
		return coordenadas;
	}
	
	public void cambiarCoordenadas(int x, int y, int z) {
		this.coordenadas[0] = x;
		this.coordenadas[1] = y;
		this.coordenadas[2] = z;
	}
	
	public void aumentarVida(int vida) {
		this.vida += vida;
	}
	
	public int obtenerVida() {
		return vida;
	}

	public void reducirVida(int danioInfligido) {
		this.vida -= danioInfligido;
	}

	public void destruirPieza() {
		this.duenio = null;
		this.cambiarNombre(null);
		this.cambiarTipo(TipoPieza.RADIACION);
	}

	/**
	 * Obtiene el valor del escudo de la pieza.
	 *
	 * @return el estado actual del escudo como un valor entero.
	 * @author Patricio Alaniz
	 */
	public int obtenerEscudo() {
		return this.escudo;
	}

	/**
	 * Establece el valor del escudo de la pieza.
	 * El valor especificado debe ser mayor a cero.
	 * Si el valor proporcionado no cumple con esta condición, se lanza una excepción.
	 *
	 * @param escudo el valor del escudo que se establecerá. Debe ser mayor a cero.
	 * @throws RuntimeException si el valor del escudo no es mayor a cero.
	 * @author Patricio Alaniz
	 */
	public void aumentarEscudo(int escudo) {
		ValidacionesUtils.validarMayorACero(escudo, "Escudo");
		this.escudo = escudo;
	}

	/**
	 * Reduce el valor del escudo en función de la cantidad de daño infligido. Si el daño excede el valor
	 * actual del escudo, el exceso se resta de la vida restante de la pieza. En caso de que el escudo sea
	 * menor a cero, este se establece en cero.
	 *
	 * @param danioInfligido el valor del daño que se infligirá al escudo. Debe ser mayor a cero.
	 * @throws RuntimeException si el daño infligido no es mayor a cero.
	 * @author Patricio Alaniz
	 */
	public void reducirEscudo(int danioInfligido) {
		ValidacionesUtils.validarMayorACero(danioInfligido, "Danio infligido");
		escudo-=danioInfligido;
		if (escudo < 0) {
			this.reducirVida(Math.abs(escudo));
			escudo = 0;
		}
	}
}

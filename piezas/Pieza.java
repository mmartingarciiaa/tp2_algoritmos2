package piezas;

// Importaciones necesarias
import Coordenada.Coordenada;
import enums.TipoPieza;
import jugador.Jugador;
import utils.ValidacionesUtils;

/**
 * Clase que representa una pieza en el juego Invasión Galáctica.
 * Esta clase es la base para todas las piezas del juego, como naves, satélites y bases.
 * Contiene información sobre el tipo de pieza, su dueño, coordenadas, nombre, vida y escudo.
 */
public class Pieza {
	private TipoPieza tipo;
	private String nombre;
	private Coordenada coordenadas;
	private Jugador duenio;
	private int vida;
	private int escudo;
	
	/**
	 * Constructor de la clase Pieza.
	 * 
	 * @param tipo: Tipo de la pieza (debe pertenecer a TipoPieza)
	 * @param duenio: Jugador dueño de la pieza 
	 * @param coordenadas: Coordenadas de la pieza en el tablero no pueden ser nulas
	 * @param nombre: Nombre de la pieza no puede ser nulo y debe tener al menos 1 caracter
	 * @param vida: Vida inicial de la pieza (debe ser mayor a cero)
	 * @param escudo: Escudo inicial de la pieza (debe ser mayor o igual a cero)
	 */
	public Pieza(TipoPieza tipo, Jugador duenio, Coordenada coordenadas, String nombre, int vida, int escudo) {
		ValidacionesUtils.validarMayorACero(vida, "Vida");
		ValidacionesUtils.validarPositivo(escudo, "Escudo");
		this.tipo = tipo;
		this.duenio = duenio;
		this.cambiarCoordenadas(coordenadas);
		this.cambiarNombre(nombre);
		this.vida = vida;
		this.escudo = escudo;
	}
	
	
	/**
	 * Incrementa el valor de la vida de la pieza.
	 * 
	 * @param vida: cantidad de vida a aumentar (debe ser mayor a cero)
	 */
	public void aumentarVida(int vida) {
		ValidacionesUtils.validarMayorACero(vida, "Vida");
		this.vida += vida;
	}
	
	/**
	 * Reduce la vida de la pieza.
	 * 
	 * @param danioInfligido: cantidad de daño infligido (debe ser mayor a cero)
	 */
	public void reducirVida(int danioInfligido) {
		ValidacionesUtils.validarMayorACero(danioInfligido, "Danio infligido");
		this.vida -= danioInfligido;
	}

	/**
	 * Establece el valor del escudo de la pieza.
	 * El valor especificado debe ser mayor a cero.
	 * Si el valor proporcionado no cumple con esta condición, se lanza una excepción.
	 *
	 * @param escudo el valor del escudo que se establecerá. Debe ser mayor a cero.
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
	 */
	public void reducirEscudo(int danioInfligido) {
		ValidacionesUtils.validarMayorACero(danioInfligido, "Danio infligido");
		this.escudo-=danioInfligido;
		if (this.escudo < 0) {
			this.reducirVida(Math.abs(this.escudo));
			this.escudo = 0;
		}
	}

	/**
	 * Devuelve el valor del escudo de la pieza.
	 *
	 * @author Patricio Alaniz
	 */
	public int obtenerEscudo() {
		return this.escudo;
	}

	/**
	 * Devuelve el dueño de la pieza.
	 * @return 
	 */
	public Jugador obtenerDuenio() {
		return this.duenio;
	}

	/**
	 * Devuelve el nombre de la pieza.
	 * @return
	 */
	public String obtenerNombre() {
		return this.nombre;
	}
	
	/**
	 * Devuelve el tipo de pieza de la pieza.
	 * @return 
	 */
	public TipoPieza obtenerTipo() {
		return this.tipo;
	}
	
	/**
	 * Devuelve las coordenas de la pieza.
	 * @return 
	 */
	public Coordenada obtenerCoordenadas() {
		return this.coordenadas;
	}
	
	/**
	 * Devuelve el valor de la vida de la pieza.
	 * @return 
	 */
	public int obtenerVida() {
		return this.vida;
	}

	/**
	 * Cambia el nombre de la pieza.
	 * @param nombre: no puede ser nulo y debe tener al menos un caracter
	 */
	private void cambiarNombre(String nombre) {
		ValidacionesUtils.noNulo(nombre, "Nombre asignado");
		if (nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre de la pieza debe tener al menos un caracter.");
		}
		this.nombre = nombre;
	}


	/**
	 * Cambia las coordenadas de la pieza.
	 * 
	 * @param coordenadas: no puede ser nulo
	 */
	public void cambiarCoordenadas(Coordenada coordenadas) {
		ValidacionesUtils.noNulo(coordenadas, "Coordenadas");
		this.coordenadas = coordenadas;
	}
	/**
	 * Cambia el dueño de la pieza.
	 * 
	 * @param nuevoDuenio: nuevo dueño de la pieza (no puede ser nulo)
	 * @throws IllegalArgumentException si el nuevo dueño es nulo.
	 */
	public void cambiarDuenio(Jugador nuevoDuenio) {
		if (nuevoDuenio == null) {
			throw new IllegalArgumentException("El nuevo dueño no puede ser nulo.");
		}
		this.duenio = nuevoDuenio;
	}
	
}

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
	private final TipoPieza tipo;
	private final String nombre;
	private Coordenada coordenadas;
	private Jugador duenio;
	private int vida;
	private int escudo;
	
	/**
	 * Constructor de la clase Pieza.
	 * 
	 * @param tipo: Tipo de la pieza (debe pertenecer a TipoPieza)
	 * @param duenio: Jugador dueño de la pieza
	 * @param coordenadas: Coordenadas de la pieza en el tablero
	 * @param nombre: Nombre de la pieza
	 * @param vida: Vida inicial de la pieza (debe ser mayor a cero)
	 * @param escudo: Escudo inicial de la pieza (debe ser mayor o igual a cero)
	 */
	public Pieza(TipoPieza tipo, Jugador duenio, Coordenada coordenadas, String nombre, int vida, int escudo) {
		this.tipo = tipo;
		this.duenio = duenio;
		this.coordenadas = coordenadas;
		this.nombre = nombre;
		this.vida = vida;
		this.escudo = escudo;
	}

	/**
	 * Verifica si la creación de la pieza es válida.
	 * 
	 * @return true si la creación es válida, false en caso contrario
	 */
	protected boolean creacionValida() {
    	return duenio != null &&
				nombre != null && 
				!nombre.isEmpty() && 
				vida > 0 && 
				escudo >= 0 && 
				coordenadas != null && 
				coordenadas != null;
	}

	/**
	 * Verifica si la pieza pertenece a un jugador específico.
	 * 
	 * @param jugador: Jugador a verificar
	 * @return true si la pieza pertenece al jugador, false en caso contrario
	 */
	public boolean perteneceAJugador(Jugador jugador) {
		return this.duenio.equals(jugador);
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
	 * @return: Devuelve el tipo de pieza 
	 */
	public TipoPieza obtenerTipo() {
		return tipo;
	}
	
	/**
	 * @return: Devuelve las coordenadas pasadas 
	 */
	public Coordenada obtenerCoordenadas() {
		return coordenadas;
	}
	
	/**
	 * Obtiene la vida actual de la pieza.
	 *
	 * @return el valor de vida actual de la pieza.
	 */
	public int obtenerVida() {
		return vida;
	}

	/**
	 * Cambia las coordenadas de la pieza.
	 * 
	 * @param coordenadas: nuevas coordenadas de la pieza
	 */
	public void cambiarCoordenadas(Coordenada coordenadas) {
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
	
	/**
	 * Aumenta la vida de la pieza.
	 * 
	 * @param vida: cantidad de vida a aumentar (debe ser mayor a cero)
	 * @throws RuntimeException si la cantidad de vida a aumentar no es mayor a cero.
	 */
	public void aumentarVida(int vida) {
		ValidacionesUtils.validarMayorACero(vida, "Vida");
		this.vida += vida;
	}
	
	/**
	 * Reduce la vida de la pieza.
	 * 
	 * @param danioInfligido: cantidad de daño infligido (debe ser mayor a cero)
	 * @throws RuntimeException si la cantidad de daño infligido no es mayor a cero.
	 */
	public void reducirVida(int danioInfligido) {
		ValidacionesUtils.validarMayorACero(danioInfligido, "Danio infligido");
		this.vida -= danioInfligido;
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

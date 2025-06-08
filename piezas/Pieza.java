package piezas;

// Importaciones necesarias
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
	private final int[] coordenadas;
	private final Jugador duenio;
	private int vida;
	private int escudo;
	
	/**
	 * Constructor de la clase Pieza.
	 * 
	 * @param tipo: Tipo de la pieza (debe pertenecer a TipoPieza)
	 * @param duenio: Jugador dueño de la pieza
	 * @param x: Coordenada X de la pieza
	 * @param y: Coordenada Y de la pieza
	 * @param z: Coordenada Z de la pieza
	 * @param nombre: Nombre de la pieza
	 * @param vida: Vida inicial de la pieza (debe ser mayor a cero)
	 * @param escudo: Escudo inicial de la pieza (debe ser mayor o igual a cero)
	 */
	public Pieza(TipoPieza tipo, Jugador duenio, int x, int y, int z, String nombre, int vida, int escudo) {
		this.tipo = tipo;
		this.duenio = duenio;
		this.coordenadas = new int[] {x, y, z};
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
				coordenadas.length == 3;
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
	public int[] obtenerCoordenadas() {
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
	 * @param x: nueva coordenada X
	 * @param y: nueva coordenada Y
	 * @param z: nueva coordenada Z
	 */
	public void cambiarCoordenadas(int x, int y, int z) {
		this.coordenadas[0] = x;
		this.coordenadas[1] = y;
		this.coordenadas[2] = z;
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

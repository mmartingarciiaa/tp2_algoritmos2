public class Pieza {

	private TipoPieza tipo;
	private String nombre;
	private final int[] coordenadas;
	private final Jugador duenio;
	private int vida;
	
	public Pieza(TipoPieza tipo, Jugador duenio, int x, int y, int z, String nombre, int vida) {
		this.tipo = tipo;
		this.duenio = duenio;
		this.coordenadas = new int[] {x, y, z};
		this.nombre = nombre;
		this.vida = vida;
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
}

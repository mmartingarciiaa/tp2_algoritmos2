
public class Pieza {

	private TipoPieza tipo;
	private String nombre;
	private int[] coordenadas = new int[3];
	
	
	public Pieza(TipoPieza tipo, int x, int y, int z, String nombre) {
		
		this.tipo = tipo;
		this.coordenadas[0] = x;
		this.coordenadas[1] = y;
		this.coordenadas[2] = z;
		this.nombre = nombre;
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
	 * @return: Devuelve el tipo de pieza 
	 */
	
	public TipoPieza obtenerTipo() {
		return tipo;
	}
	
	/**
	 * 
	 * @return: Devuelve las coordenadas pasadas 
	 */
	
	public int[] obtenerCoordenadas() {
		return coordenadas;
	}
	
	/**
	 * @return: Devuelve como esta representada la pieza en el tablero 
	 */
	public String obtenerNombre() {
		return nombre;
	}
	

}

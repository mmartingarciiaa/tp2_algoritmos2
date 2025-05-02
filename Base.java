//Clase hija de Pieza 

public class Base extends Pieza {
	
	private static final int VALOR_INICIAL_ESCUDO = 5;

	private int escudo;
	private String duenio;

	public Base(TipoPieza tipo, String duenio, int x, int y, int z, String nombre) {	
		super(tipo, x, y, z, nombre);
		this.escudo = VALOR_INICIAL_ESCUDO;
		this.duenio = duenio;
	}
	
	/**
	 * Devuelve el nombre del dueño de la base
	 * @return
	 */
	public String obtenerDuenio() {
		return duenio;
	}
	
	/**
	 * Reduce el escudo de la base
	 * 
	 */
    public void reducirEscudo() {
    	escudo-=1;
    }
    
    /**
     * Devuelve el estado del escudo
     * @return
     */
    
    public int obtenerEscudo() {
    	return escudo; 
    }
	
    
	
}

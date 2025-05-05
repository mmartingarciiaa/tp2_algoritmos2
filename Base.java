//Clase hija de Pieza

public class Base extends Pieza {
	
	private static final int VALOR_INICIAL_ESCUDO = 0;

	private int escudo;

	public Base(TipoPieza tipo, Jugador duenio, int x, int y, int z, String nombre, int vida) {
		super(tipo, duenio, x, y, z, nombre, vida);
		this.escudo = VALOR_INICIAL_ESCUDO;
	}
	
	public void aumentarEscudo(int escudo) {
		this.escudo += escudo;
	}

	/**
	 * Reduce el escudo de la base
	 * 
	 */
    public void reducirEscudo(int danioInfligido) {
    	escudo-=danioInfligido;
		if (escudo < 0) {
			this.reducirVida(Math.abs(escudo));
			escudo = 0;
		}
    }
    
    /**
     * Devuelve el estado del escudo
     * @return
     */
    public int obtenerEscudo() {
    	return escudo; 
    }
	
	
}

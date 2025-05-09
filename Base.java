
public class Base extends Pieza {
	
    /**
     * Constructor de la clase Pieza 
     * @param duenio: jugador al que pertenece la base
     * @param x: coordenada x de la base
     * @param y: coordenada y de la base
     * @param z: coordenada z de la base 
     * @param nombre: nombre de la base
     * @param vida: vida de la base 
     */
	public Base(Jugador duenio, int x, int y, int z, String nombre, int vida) {
		super(TipoPieza.BASE, duenio, x, y, z, nombre, vida);
	}
	

    /**
     * Verifica si una nave puede conquistar la base lo cual pasa si la base no tiene
     * escudo (el valor del escudo es 0) pero todavia tiene vida (la base esta debilitada) 
     *  
     * @return: devuelve true si se puede conquistar la base y false si no se puede 
     */
    public boolean esConquistable() {
        return this.obtenerEscudo() == 0 && this.obtenerVida() > 0;
    }
	
}
/**
 * Clase que representa un satélite espía en el juego Invasión Galáctica
 * Hereda de la clase Pieza
 */
public class Satelite extends Pieza {
    private int radioDeteccion;
    private boolean activo;

    /**
     * Constructor  del Satélite
     * @param tipo     Tipo de pieza
     * @param duenio   Jugador propietario del satélite
     * @param x        Coordenada X
     * @param y        Coordenada Y
     * @param z        Coordenada Z
     * @param nombre   Representación del satélite en el tablero
     * @param vida     Vida innicial del satélite
     *
     */
    public Satelite(TipoPieza tipo, Jugador duenio, int x, int y, int z, String nombre, int vida, int radioDeteccion) {
        super(tipo, duenio, x, y, z, nombre, vida);
        this.radioDeteccion = radioDeteccion;
        this.activo = true;
    }

    /**
     * Activa el satélite, permitiendo que funcione
     */
    public void activar() {
        this.activo = true;
    }

    /**Desactiva el satélite, impidiendo su uso */
    public void desactivar() {
        this.activo = false;
    }

    /**Indica si el satélite esta operativo
     * @return true si puede funcionar, false en caso contrario
     */
    public boolean estaActivo() {
        return activo && this.obtenerVida() > 0;
    }

    /**
     * Verifica si este satélite colisiona con otro
     * @param otro Satélite con el que comparar
     * @return true si chocan, false si no
     */
    public boolean verificarColisionCon(Satelite otro) {
        int[] coords1 = this.obtenerCoordenadas();
        int[] coords2 = otro.obtenerCoordenadas();
        return coords1[0] == coords2[0] && coords1[1] == coords2[1] && coords1[2] == coords2[2];
    }

    /**
     * Devuelve el radio de detección del satélite.
     *
     * @return el radio de detección configurado para el satélite.
     * @author Patricio Alaniz
     */
    public int obtenerRadioDeteccion() {
        return this.radioDeteccion;
    }
}
package piezas;

// Importaciones necesarias
import enums.TipoPieza;
import jugador.Jugador;
import utils.ValidacionesUtils;

/**
 * Clase que representa un satélite espía en el juego Invasión Galáctica
 * Hereda de la clase Pieza
 */

public class Satelite extends Pieza {
    private final int radioDeteccion;
    private boolean activo;

    /**
     * Constructor  del Satélite
     * @param duenio   Jugador propietario del satélite
     * @param x        Coordenada X
     * @param y        Coordenada Y
     * @param z        Coordenada Z
     * @param nombre   Representación del satélite en el tablero
     * @param vida     Vida inicial del satélite
     * @param radioDeteccion Radio de detección del satélite
     *    
     */
    public Satelite(Jugador duenio, int x, int y, int z, String nombre, int vida, int radioDeteccion) {
        super(TipoPieza.SATELITE, duenio, x, y, z, nombre, vida, 0);
        ValidacionesUtils.validarMayorACero(radioDeteccion, "Radio de detección");
        this.radioDeteccion = radioDeteccion;
        this.activo = true;
    }

    /**
     * Activa el satélite, para que pueda detectar naves
     */
    public void activar() {
        this.activo = true;
    }

    /*Desactiva el satélite, impidiendo su uso */
    public void desactivar() {
        this.activo = false;
    }

    /**Indica si el satélite esta activo y con vida
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
        if (otro == null) {
            return false;
        }
        int[] coords1 = this.obtenerCoordenadas();
        int[] coords2 = otro.obtenerCoordenadas();
        return coords1[0] == coords2[0] && coords1[1] == coords2[1] && coords1[2] == coords2[2];
    }

    /**
     * Verifica si una nave está dentro del rango de detección del satélite
     * @param nave , la nave que se movio
     * @return true si la nave fue detectada, false en caso contrario
     */
    public boolean detectarMovimiento(Nave nave) {
        //Si el satélite no está activo o está destruido, no detecta nada
        if (!estaActivo() || nave == null || nave.obtenerDuenio() == null || nave.obtenerDuenio().equals(this.obtenerDuenio())) {
            return false;
        }
        //Obtener coordenadas
        int[] coordsSatelite = this.obtenerCoordenadas();
        int[] coordsNave = nave.obtenerCoordenadas();

        return calcularDistancia(coordsSatelite, coordsNave) <= radioDeteccion;
    }

    /**
     * Calcula la distancia entre dos puntos en el espacio 3D
     * @param coords1 coordenadas del primer punto
     * @param coords2 coordenadas del segundo punto
     * @return la distancia 
     */ 
    private double calcularDistancia(int[] coords1, int[] coords2)  {
        double dx = coords1[0] - coords2[0];
        double dy = coords1[1] - coords2[1];
        double dz = coords1[2] - coords2[2];
        return Math.sqrt(dx * dx + dy * dy + dz * dz);

    }

    /**
     * Obtiene el radio de detección del satélite
     * @return El radio de detección como un entero
     */
    public int obtenerRadioDeteccion() {
        return radioDeteccion;
    }

    /**
     * Verifica si este satélite colisiona con otro y, en caso afirmativo
     * destruye ambos satélites 
     * @param otro Satélite en el que se verifica la colisión
     * @return true si hubo colisión y los satélites fueron destruidos, false en caso contrario
     */
    public boolean colisionarYDestruir(Satelite otro) {
        if (otro != null && verificarColisionCon(otro)) {
            //Destruir ambos satélites
            this.reducirVida(this.obtenerVida());
            otro.reducirVida(otro.obtenerVida());
            return true;
        }
        return false;
    }

    /*
    * Genera un mensaje informativo sobre la detección de una nave
    * @param nave, la nave detectada
    * @return Un mensaje descriptivo sobre la detección 
    
    public String generarInformeDeteccion(Nave nave) {
        if (nave == null || nave.obtenerDuenio() == null) {
            return "Error: No se puede generar informe con una nave inválida";
        }

        int[] coordsNave = nave.obtenerCoordenadas();
        int[] coordsSatelite = this.obtenerCoordenadas();

        return "¡Alerta! Tu satélite en (" + coordsSatelite[0] + "," + coordsSatelite[1] + "," + coordsSatelite[2] + ")
            ha detectado una nave enemiga de " + nave.obtenerDuenio().obtenerNombre() + " en coordenadas 
            (" + coordsNave[0] + "," + coordsNave[1] + "," + coordNave[2] + ").";
    }
    */

}
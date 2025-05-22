/**
 * Clase que representa una nave en el juego Invasión Galáctica.
 * Hereda de la clase Pieza.
 * 
 * Las naves pueden moverse, atacar, recibir escudos (temporales o permanentes),
 * y modificar su daño de ataque mediante cartas u otras acciones.
 */
public class Nave extends Pieza {
    private static final int ESCUDO_INICIAL = 0;

    private int escudo;
    private int danio;
    //Atributo para el efecto de la carta Doble Salto Hiperespacial
    private boolean dobleMovimientoActivo = false;
    private int escudoTemporalTurnos = 0;

    /**
     * Constructor de la nave.
     * @param tipo          Tipo de pieza 
     * @param duenio        Jugador propietario de la nave
     * @param x             Coordenada X
     * @param y             Coordenada Y
     * @param z             Coordenada Z
     * @param nombre        Representación de la nave en el tablero
     * @param vida          Vida inicial de la nave
     * @param danioInicial  Poder de ataque inicial de la nave (debe ser mayor a cero)
     */
    public Nave(TipoPieza tipo,Jugador duenio, int x, int y, int z, String nombre, int vida, int danioInicial) {
        super(tipo, duenio, x, y, z, nombre, vida);
        ValidacionesUtils.validarMayorACero(danioInicial, "Daño inicial de la nave");
        this.escudo = ESCUDO_INICIAL;
        this.danio = danioInicial;
    }

    /**
     * Otorga escudo temporal a la nave que dura un turno.
     * Este escudo se suma al escudo actual y se eliminará automáticamente al finalizar el turno.
     *
     * @param cantidad la cantidad de escudo temporal a otorgar (debe ser mayor a cero)
     */
    public void otorgarEscudoPorUnTurno(int cantidad) {
        ValidacionesUtils.validarMayorACero(cantidad, "Escudo temporal");
        this.escudo += cantidad;
        this.escudoTemporalTurnos = 1;
    }
    
    /**
     * Activa el doble movimiento por efecto de la  carta
     * La nave podra moverse dos veces durante este turno
     */
    public void activarDobleMovimiento() {
        this.dobleMovimientoActivo = true;
    }

    /**
     * Verifica si la nave tiene doble movimiento activo
     * @return true si la nave puede moverse dos veces, false si no
     */
    public boolean puedeMoverDosVeces() {
        return this.dobleMovimientoActivo;
    }

    /**
     * Procesa el final del turno para la nave.
     * Se elimina el escudo temporal y se desactiva el doble movimiento
     */
    public void procesarFinDeTurno() {
        if (escudoTemporalTurnos > 0) {
            escudoTemporalTurnos--;
            if (escudoTemporalTurnos == 0) {
                this.escudo = 0;
            }
        }
        this.dobleMovimientoActivo = false;
    }

    /**
     * Aumenta el escudo de la nave de forma permanente.
     * 
     * @param cantidad la cantidad de escudo a añadir (debe ser mayor a cero)
     */
    public void aumentarEscudo(int cantidad) {
        ValidacionesUtils.validarMayorACero(cantidad, "Cantidad de escudo a aumentar");
        this.escudo += cantidad;
    }

    /**
     * Reduce el escudo de la nave. Si el escudo es menor que el daño recibido,
     * el exceso se aplica a la vida de la nave.
     * 
     * @param cantidad la cantidad de daño a aplicar al escudo (debe ser mayor a cero)
     */
    public void reducirEscudo(int cantidad) {
        ValidacionesUtils.validarMayorACero(cantidad, "Cantidad de escudo a reducir");
        escudo -= cantidad;
        if (escudo < 0) {
            super.reducirVida(Math.abs(escudo));
            escudo = 0;
        }
    }

    /**
     * Obtiene el valor actual del escudo de la nave.
     * 
     * @return el valor entero del escudo actual
     */
    public int obtenerEscudo() {
        return escudo;
    }

    /**
     * Obtiene el daño de ataque actual de la nave.
     * 
     * @return el valor de daño como entero
     */
    public int obtenerDanio() {
        return danio;
    }

    /**
     * Establece un nuevo valor de daño de ataque.
     * 
     * @param nuevoDanio el nuevo daño de la nave (debe ser mayor a cero)
     */
    public void setDanio(int nuevoDanio) {
        ValidacionesUtils.validarMayorACero(nuevoDanio, "Nuevo daño de la nave");
        this.danio = nuevoDanio;
    }

    /**
     * Incrementa el daño de la nave.
     * 
     * @param extra cantidad extra de daño a sumar (debe ser mayor a cero)
     */
    public void aumentarDanio(int extra) {
        ValidacionesUtils.validarMayorACero(extra, "Aumento de daño");
        this.danio += extra;
    }

    /**
     * Realiza un ataque con la nave.
     * 
     * @return el daño infligido por la nave
     */
    public int atacar() { 
        return danio;
    }
}

package mazo;

// importaciones necesarias
import enums.TipoCarta;
import utils.ValidacionesUtils;

/* por Patricio */
/**
 * Clase que representa una carta dentro del juego.
 *
 * Una carta tiene un nombre, una descripción y un tipo específico representado
 * por la enumeración {@link TipoCarta}. Las cartas pueden ser utilizadas para
 * realizar acciones o aplicar efectos dentro del juego.
 * @author Patricio Alaniz
 */

public class Carta {
    private String nombre = null;
    private String descripcion = null;
    private TipoCarta tipo = null;

    /**
     * Constructor que inicializa una instancia de la clase objetosAuxiliares.Carta con un nombre, una descripción y un tipo específico.
     *
     * @param nombre      el nombre de la carta como una cadena de texto.
     * @param descripcion la descripción de la carta como una cadena de texto.
     * @param tipo        el tipo de la carta como una instancia de {@link TipoCarta}.
     */
    public Carta(String nombre, String descripcion, TipoCarta tipo) throws RuntimeException {
        this.setNombre(nombre);
        this.setDescripcion(descripcion);
        this.setTipo(tipo);
    }

    /**
     * Obtiene el nombre de la carta.
     *
     * @return el nombre de la carta como una cadena de texto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la descripción de la carta.
     *
     * @return la descripción de la carta como una cadena de texto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene el tipo de la carta.
     *
     * @return el tipo de la carta como una instancia de {@link TipoCarta}.
     */
    public TipoCarta getTipo() {
        return tipo;
    }

    /**
     * Establece el nombre de la carta.
     *
     * @param nombre el nuevo nombre de la carta como una cadena de texto.
     */
    private void setNombre(String nombre) throws RuntimeException {
        ValidacionesUtils.noNulo(nombre, "Nombre");
        ValidacionesUtils.validarMinimoDeCaracteres(nombre, 3, "Nombre");

        String auxNombre = nombre.trim();
        this.nombre = auxNombre;
    }

    /**
     * Establece la descripción de la carta.
     *
     * @param descripcion la nueva descripción de la carta como una cadena de texto.
     */
    private void setDescripcion(String descripcion) throws RuntimeException {
        ValidacionesUtils.noNulo(descripcion, "Descripcion");
        ValidacionesUtils.validarMinimoDeCaracteres(descripcion, 3, "Descripcion");

        String auxDescripcion = descripcion.trim();
        this.descripcion = auxDescripcion;
    }

    /**
     * Establece el tipo de la carta.
     *
     * @param tipo el nuevo tipo de la carta como una instancia de {@link TipoCarta}.
     */
    private void setTipo(TipoCarta tipo) throws RuntimeException {
        ValidacionesUtils.noNulo(tipo, "Tipo de carta");
        this.tipo = tipo;
    }
}

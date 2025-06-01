package enums;

/**
 * Enumeración que representa los diferentes tipos de cartas disponibles en el sistema.
 * Las cartas tienen distintos efectos y funcionalidades dentro del juego.
 *
 * Los posibles valores de esta enumeración son:
 * - CAMPO_DE_FUERZA: Carta que se relaciona con la generación de un campo defensivo.
 * - RASTREADOR_CUANTICO: Carta que ofrece seguimiento avanzado basado en tecnología cuántica.
 * - DOBLE_SALTO_HIPERESPACIAL: Carta que otorga movilidad adicional mediante un salto especial.
 * - BASE_ADICIONAL: Carta que permite agregar una base extra al juego.
 * - SUMAR_VIDA_A_BASE: Carta que incrementa los puntos de vida de una base existente.
 * - NAVE_HACE_DAÑO_EXTRA: Carta que mejora el daño que realiza una nave en combate.
 * - NAVE_HACE_DAÑO_EN_AREA: Carta que permite a una nave realizar daño en área.
 * - NAVE_OBTIENE_ESCUDO: Carta que otorga protección adicional a una nave mediante un escudo.
 * @author Patricio Alaniz
 */
public enum TipoCarta {
    CAMPO_DE_FUERZA,
    RASTREADOR_CUANTICO,
    DOBLE_SALTO_HIPERESPACIAL,
    BASE_ADICIONAL,
    SUMAR_VIDA_A_BASE,
    NAVE_HACE_DAÑO_EXTRA,
    NAVE_HACE_DAÑO_EN_AREA,
    NAVE_OBTIENE_ESCUDO
}

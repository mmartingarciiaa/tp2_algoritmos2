package objetosAuxiliares;/* por Patricio */
/**
 * Enumeración que representa los diferentes tipos de cartas que pueden existir en el juego.
 *
 * Cada tipo de carta define una categoría específica de habilidades o efectos que pueden ser utilizados
 * durante el transcurso del juego. Los tipos disponibles son:
 *
 * - CAMPO_DE_FUERZA: Representa un efecto de protección o defensa.
 * - RASTREADOR_CUANTICO: Representa una habilidad de localización avanzada.
 * - DOBLE_SALTO_HIPERESPACIAL: Representa una capacidad de movimiento avanzado.
 * - BASE_ADICIONAL: Representa una ventaja en la construcción o capacidad estratégica.
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

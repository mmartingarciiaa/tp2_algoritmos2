package enums;

/**
 * Enumeración que representa los diferentes tipos de piezas que pueden existir en el juego.
 * Cada tipo de pieza tiene un propósito y características específicas dentro del tablero.
 *
 * Los posibles valores de esta enumeración son:
 * - NAVE: Representa una nave espacial que puede moverse y atacar.
 * - BASE: Representa una base que puede generar naves y satélites.
 * - SATELITE: Representa un satélite que puede detectar naves enemigas.
 * - RADIACION: Representa una zona de radiación que afecta a las naves.
 * - VACIO: Representa un espacio vacío en el tablero sin ninguna pieza.
 */
public enum TipoPieza {
	NAVE,
    BASE,
    SATELITE,
    RADIACION,
    VACIO
}

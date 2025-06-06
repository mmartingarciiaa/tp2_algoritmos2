package enums;

// Enumeración para las acciones posibles en el menú
public enum Accion {
    AGREGAR_NAVE,
    MOVER_NAVE,
    AGREGAR_SATELITE,
    ATACAR_SECTOR,
    ROBAR_CARTA,
    USAR_CARTA,
    ALIANZA,
    DEJAR_DE_JUGAR,
    NO_VALIDO;

    /**
     * Método para obtener la acción correspondiente al código proporcionado.
     *
     * @param codigo El código de la acción.
     * @return La acción correspondiente o NO_VALIDO si el código es inválido.
     */
    public static Accion desdeCodigo(int codigo) {
        if (codigo >= 0 && codigo < values().length - 1) { // Excluye NO_VALIDO
            return values()[codigo];
        }
        return NO_VALIDO;
    }
}
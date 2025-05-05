import tdas.lista.ListaEnlazada;
/* por Patricio */
/**
 * Clase que permite crear una lista de cartas base.
 */
public class CartaUtils {
    /**
     * Crea una lista de cartas base con una cantidad específica de copias de cada tipo de carta.
     *
     * Este método genera una lista de cartas predefinidas con sus respectivas descripciones y tipos.
     * La cantidad de copias de cada carta en la lista es determinada por el parámetro proporcionado.
     * Si el número de copias es menor a 1, se lanza una excepción.
     *
     * @param cantidadDeCopiasDeCadaCarta la cantidad de copias de cada tipo de carta que se debe generar.
     *                                    Este valor debe ser mayor o igual a 1.
     * @return una lista de cartas base con la cantidad especificada de copias de cada tipo de carta.
     * @throws RuntimeException si la cantidad de copias de cada carta es menor a 1.
     */
    public static ListaEnlazada<Carta> crearListaDeCartasBase(int cantidadDeCopiasDeCadaCarta) throws RuntimeException {
        ListaEnlazada<Carta> cartas = new ListaEnlazada<>();

        if(cantidadDeCopiasDeCadaCarta < 1) {
            throw new RuntimeException("La cantidad de copias de cartas debe ser mayor a 0.");
        }

        for(int i = 0; i < cantidadDeCopiasDeCadaCarta; i++) {
            cartas.insertarUltimo(
                new Carta(
                    "Campo de Fuerza",
                    "Aumenta la defensa de una base durante un número de turnos.",
                    TipoCarta.CAMPO_DE_FUERZA
                )
            );

            cartas.insertarUltimo(
                new Carta(
                    "Rastreador Cuantico",
                    "Revela si hay naves enemigas en un radio de L (valor random) sectores desde la posición elegida.",
                    TipoCarta.RASTREADOR_CUANTICO
                )
            );

            cartas.insertarUltimo(
                new Carta(
                    "Doble salto hiperespacial",
                    "Permite mover una nave dos veces en el mismo turno.",
                    TipoCarta.DOBLE_SALTO_HIPERESPACIAL
                )
            );

            cartas.insertarUltimo(
                new Carta(
                    "Base adicional",
                    "Permite agregar una base adicional.",
                    TipoCarta.BASE_ADICIONAL
                )
            );

            cartas.insertarUltimo(
                new Carta(
                    "Vida adicional a la base",
                    "Aumenta la vida de la base de forma permanente.",
                    TipoCarta.SUMAR_VIDA_A_BASE
                )
            );

            cartas.insertarUltimo(
                new Carta(
                    "Daño extra",
                    "La nave hace un daño extra al atacar.",
                    TipoCarta.NAVE_HACE_DAÑO_EXTRA
                )
            );

            cartas.insertarUltimo(
                new Carta(
                    "Daño en area",
                    "La nave hace daño en area al atacar.",
                    TipoCarta.NAVE_HACE_DAÑO_EN_AREA
                )
            );

            cartas.insertarUltimo(
                new Carta(
                    "Escudo para nave",
                    "La nave recibe un escudo durante un turno.",
                    TipoCarta.NAVE_OBTIENE_ESCUDO
                )
            );
        }

        return cartas;
    }
}

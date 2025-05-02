/* por Patricio */
import java.util.Random;
import tdas.lista.IteradorLista;
import tdas.lista.ListaEnlazada;
import tdas.pila.PilaDinamica;

public class Mazo<T> {
    private PilaDinamica<T> cartasDelMazo = null;

    /**
     * Constructor de la clase Mazo. Inicializa un mazo de cartas a partir de la lista de cartas proporcionada.
     * La lista de cartas se mezcla de forma aleatoria y se almacena internamente para su gestión.
     *
     * @param cartas una lista enlazada de objetos de tipo Carta que será utilizada para inicializar el mazo.
     *               No debe ser nula; si es nula, se lanzará una excepción.
     * @throws RuntimeException si la lista de cartas proporcionada es nula.
     */
    public Mazo(ListaEnlazada<T> cartas) throws RuntimeException {
        if(cartas == null) {
            throw new RuntimeException("La lista de cartas no puede ser nula.");
        }

        this.cartasDelMazo = new PilaDinamica<>();
        this.mezclarMazo(cartas);
    }

    /**
     * Mezcla una lista de cartas de manera aleatoria y las almacena en la pila interna de cartas mezcladas.
     *
     * @param cartas una lista de cartas que será mezclada. No debe ser nula y debe contener elementos.
     */
    private void mezclarMazo(ListaEnlazada<T> cartas) throws RuntimeException {
        while(cartas.largo() > 0) {
            IteradorLista<T> iteradorDeCarta = cartas.iterador();
            Random random = new Random();
            int indiceAleatorio = random.nextInt(cartas.largo());
            int i = 0;

            while (i < indiceAleatorio) {
                iteradorDeCarta.siguiente();
                i++;
            }

            cartasDelMazo.apilar(iteradorDeCarta.borrar());
        }
    }

    /**
     * Extrae una carta del mazo y la elimina de la pila interna de cartas mezcladas.
     * Si no hay cartas disponibles en el mazo, lanza una excepción.
     *
     * @return la carta extraída de la parte superior de la pila interna.
     * @throws RuntimeException si el mazo no tiene más cartas disponibles.
     */
    public T sacarCarta() throws RuntimeException {
        if (this.cartasDelMazo.estaVacia()) {
            throw new RuntimeException("No hay mas cartas en el mazo.");
        }
        return cartasDelMazo.desapilar();
    }

    /**
     * Devuelve la cantidad de cartas que quedan en el mazo.
     *
     * @return el número total de cartas restantes en el mazo.
     */
    public int cantidadDeCartas() {
        return this.cartasDelMazo.cantidad();
    }
}

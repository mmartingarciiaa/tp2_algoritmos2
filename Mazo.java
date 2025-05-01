/* por Patricio */
import java.util.List;
public class Mazo {
    private Pila<Carta> cartasMezcladas = new Pila<Carta>();

    /**
     * Constructor de la clase Mazo que inicializa un mazo de cartas mezclado a partir de una lista proporcionada.
     *
     * @param cartas una lista de cartas inicial para el mazo. No debe ser nula.
     * @throws RuntimeException si la lista de cartas es nula.
     */
    public Mazo(List<Carta> cartas) throws RuntimeException {
        if(cartas == null) {
            throw new RuntimeException("La lista de cartas no puede ser nula.");
        }

        this.mezclarMazo(cartas);
    }

    /**
     * Mezcla una lista de cartas de manera aleatoria y las almacena en la pila interna de cartas mezcladas.
     *
     * @param cartas una lista de cartas que será mezclada. No debe ser nula y debe contener elementos.
     */
    private void mezclarMazo(List<Carta> cartas) {
        while(cartas.size() > 0) {
            int indiceAleatorio = (int) (Math.random() * cartas.size());
            cartasMezcladas.apilar(cartas.remove(indiceAleatorio));
        }
    }

    /**
     * Extrae una carta del mazo y la elimina de la pila interna de cartas mezcladas.
     * Si no hay cartas disponibles en el mazo, lanza una excepción.
     *
     * @return la carta extraída de la parte superior de la pila interna.
     * @throws RuntimeException si el mazo no tiene más cartas disponibles.
     */
    public Carta sacarCarta() throws RuntimeException {
        if (this.cartasMezcladas.estaVacia()) {
            throw new RuntimeException("No hay mas cartas en el mazo.");
        }
        return cartasMezcladas.desapilar();
    }

    /**
     * Devuelve la cantidad de cartas que quedan en el mazo.
     *
     * @return el número total de cartas restantes en el mazo.
     */
    public int cantidadDeCartas() {
        return this.cartasMezcladas.contarElementos();
    }
}

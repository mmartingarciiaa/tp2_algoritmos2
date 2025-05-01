package tdas.pila;

/**
 * Una interfaz genérica para una pila (stack).
 *
 * @param <T> el tipo de elementos almacenados en la pila.
 */
public interface Pila<T> {

    /**
     * Devuelve true si la pila está vacía, false en caso contrario.
     *
     * @return true si está vacía, false si no.
     */
    boolean estaVacia();

    /**
     * Devuelve el elemento en el tope de la pila sin removerlo.
     *
     * @return el valor del tope.
     * @throws IllegalStateException si la pila está vacía.
     */
    T verTope();

    /**
     * Agrega un elemento al tope de la pila.
     *
     * @param elem el elemento a apilar.
     */
    void apilar(T elem);

    /**
     * Remueve y devuelve el elemento del tope de la pila.
     *
     * @return el elemento removido.
     * @throws IllegalStateException si la pila está vacía.
     */
    T desapilar();
}

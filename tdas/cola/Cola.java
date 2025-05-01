package tdas.cola;

/**
 * Interfaz genérica para una cola (queue).
 *
 * @param <T> el tipo de datos en la cola
 */
public interface Cola<T> {

    /**
     * Retorna true si la cola está vacía.
     */
    boolean estaVacia();

    /**
     * Devuelve el primer elemento en la cola sin eliminarlo.
     * @throws IllegalStateException si la cola está vacía.
     */
    T verPrimero();

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar
     */
    void encolar(T elemento);

    /**
     * Elimina y retorna el primer elemento de la cola.
     * @throws IllegalStateException si la cola está vacía.
     */
    T desencolar();
}

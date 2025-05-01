package tdas.lista;

/**
 * Interfaz genérica para una lista (list).
 *
 * @param <T> el tipo de datos en la lista
 */
public interface Lista<T> {
    
    /**
     * Retorna true si la lista está vacía.
     */
    boolean estaVacia();
    
    /**
     * Devuelve el primer elemento en la lista sin eliminarlo.
     * @throws RuntimeException si la lista está vacía.
     */
    T verPrimero();
    
    /**
     * Devuelve el último elemento en la lista sin eliminarlo.
     * @throws RuntimeException si la lista está vacía.
     */
    T verUltimo();

    /**
     * Devuelve el número de elementos en la lista.
     * @return el número de elementos en la lista
     */
    int largo();
    
    /**
     * Inserta un elemento al principio de la lista.
     * @param dato el elemento a insertar
     */
    void insertarPrimero(T dato);
    
    /**
     * Inserta un elemento al final de la lista.
     * @param dato el elemento a insertar
     */
    void insertarUltimo(T dato);

    /**
     * Inserta un elemento en la lista en una posición específica.
     * @param dato el elemento a insertar
     * @throws RuntimeException si la lista está vacía
     */
    T borrarPrimero();
    
    /**
     * Recorre la lista y aplica una función a cada elemento.
     * Si la función devuelve false, se detiene el recorrido.
     * La función no debe modificar la lista.
     * @param visitar la función a aplicar a cada elemento
     */
    void iterar(Visitador<T> visitar);
    
    /**
     * Devuelve un iterador para recorrer la lista.
     * @return un iterador para recorrer la lista
     */
    IteradorLista<T> iterador();
}
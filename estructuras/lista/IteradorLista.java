package estructuras.lista;

/**
 * Interfaz para un iterador de una lista.
 * Permite recorrer la lista y realizar operaciones como ver el elemento actual,
 * avanzar al siguiente, insertar un nuevo elemento y borrar el elemento actual.
 *
 * @param <T> Tipo de los elementos en la lista.
 */
public interface IteradorLista<T> {

    /**
     * Verifica si hay un elemento actual en el iterador.
     * 
     * @throws IllegalStateException si no hay un elemento actual.
     */
    T verActual();

    /**
     * Verifica si hay un siguiente elemento en el iterador.
     * 
     * @return true si hay un siguiente elemento, false en caso contrario.
     */
    boolean haySiguiente();
    
    /**
     * Avanza al siguiente elemento en el iterador.
     * 
     * @throws IllegalStateException si no hay un siguiente elemento.
     */
    void siguiente();

    /**
     * Inserta un nuevo elemento en la lista en la posición actual del iterador.
     * 
     * @param dato el elemento a insertar.
     */
    void insertar(T dato);

    /**
     * Borra el elemento actual del iterador.
     * 
     * @return el elemento borrado.
     * @throws IllegalStateException si no hay un elemento actual.
     */
    T borrar();
}
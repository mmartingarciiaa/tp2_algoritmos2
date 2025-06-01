package estructuras.lista;

/**
 * Interfaz funcional para un visitador de elementos en una lista.
 * Permite aplicar una función a cada elemento de la lista durante el recorrido.
 *
 * @param <T> el tipo de datos en la lista
 */
public interface Visitador<T> {
    /**
     * Método que se aplica a cada elemento de la lista.
     * @param dato el elemento actual
     * @return true para continuar el recorrido, false para detenerlo
     */
    boolean visitar(T dato);
}
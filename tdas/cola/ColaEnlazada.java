package tdas.cola;

/**
 * Implementación de una cola enlazada genérica.
 *
 * @param <T> el tipo de datos en la cola
 */
public class ColaEnlazada<T> implements Cola<T> {

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    private Nodo<T> primero;
    private Nodo<T> ultimo;

    public ColaEnlazada() {
        this.primero = null;
        this.ultimo = null;
    }

    @Override
    public boolean estaVacia() {
        return primero == null && ultimo == null;
    }

    @Override
    public T verPrimero() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola esta vacia");
        }
        return primero.dato;
    }

    @Override
    public void encolar(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        if (estaVacia()) {
            primero = nuevo;
        } else {
            ultimo.siguiente = nuevo;
        }
        ultimo = nuevo;
    }

    @Override
    public T desencolar() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola esta vacia");
        }
        T dato = primero.dato;
        primero = primero.siguiente;
        if (primero == null) {
            ultimo = null;
        }
        return dato;
    }
}

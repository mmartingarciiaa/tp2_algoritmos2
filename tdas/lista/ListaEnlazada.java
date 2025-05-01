package tdas.lista;

/**
 * Implementación de una lista enlazada genérica.
 *
 * @param <T> el tipo de datos en la lista
 */
public class ListaEnlazada<T> implements Lista<T> {

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
    private int largo;

    public ListaEnlazada() {
    this.primero = null;
    this.ultimo = null;
    this.largo = 0;
    }

    @Override
    public boolean estaVacia() {
        return primero == null && ultimo == null;
    }

    @Override
    public T verPrimero() {
        if (estaVacia()) throw new RuntimeException("La lista está vacía");
        return primero.dato;
    }

    @Override
    public T verUltimo() {
        if (estaVacia()) throw new RuntimeException("La lista está vacía");
        return ultimo.dato;
    }

    @Override
    public int largo() {
        return largo;
    }

    @Override
    public void insertarPrimero(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (estaVacia()) {
            ultimo = nuevo;
        } else {
            nuevo.siguiente = primero;
        }
        primero = nuevo;
        largo++;
    }

    @Override
    public void insertarUltimo(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (estaVacia()) {
            primero = nuevo;
        } else {
            ultimo.siguiente = nuevo;
        }
        ultimo = nuevo;
        largo++;
    }

    @Override
    public T borrarPrimero() {
        if (estaVacia()) throw new RuntimeException("La lista está vacía");
        T dato = primero.dato;
        if (primero == ultimo) {
            ultimo = null;
        }
        primero = primero.siguiente;
        largo--;
        return dato;
    }

    @Override
    public void iterar(Visitador<T> visitar) {
        Nodo<T> actual = primero;
        while (actual != null) {
            if (!visitar.visitar(actual.dato)) break;
            actual = actual.siguiente;
        }
    }

    @Override
    public IteradorLista<T> iterador() {
        return new IteradorListaEnlazada();
    }

    private class IteradorListaEnlazada implements IteradorLista<T> {
        private Nodo<T> actual = primero;
        private Nodo<T> anterior = null;

        @Override
        public T verActual() {
            if (!haySiguiente()) throw new RuntimeException("No hay siguiente");
            return actual.dato;
        }

        @Override
        public boolean haySiguiente() {
            return actual != null;
        }

        @Override
        public void siguiente() {
            if (!haySiguiente()) throw new RuntimeException("No hay siguiente");
            anterior = actual;
            actual = actual.siguiente;
        }

        @Override
        public void insertar(T dato) {
            Nodo<T> nuevo = new Nodo<>(dato);
            if (anterior == null) {
                nuevo.siguiente = primero;
                primero = nuevo;
            } else {
                nuevo.siguiente = actual;
                anterior.siguiente = nuevo;
            }
            if (actual == null) {
                ultimo = nuevo;
            }
            actual = nuevo;
            largo++;
        }

        @Override
        public T borrar() {
            if (!haySiguiente()) throw new RuntimeException("No hay siguiente");
            T dato = actual.dato;
            if (anterior == null) {
                primero = actual.siguiente;
            } else {
                anterior.siguiente = actual.siguiente;
            }
            if (actual.siguiente == null) {
                ultimo = anterior;
            }
            actual = actual.siguiente;
            largo--;
            return dato;
        }
    }
}
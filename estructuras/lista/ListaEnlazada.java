package estructuras.lista;

/**
 * Implementación de una lista enlazada genérica.
 *
 * @param <T> el tipo de datos en la lista
 */
public class ListaEnlazada<T> {

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

    public boolean estaVacia() {
        return primero == null && ultimo == null;
    }

    public T verPrimero() {
        if (estaVacia()) throw new RuntimeException("La lista está vacía");
        return primero.dato;
    }

    public T verUltimo() {
        if (estaVacia()) throw new RuntimeException("La lista está vacía");
        return ultimo.dato;
    }

    public int largo() {
        return largo;
    }

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

    public T obtenerEnPosicion(int posicion) {
        if (posicion < 0 || posicion >= largo) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + posicion);
        }
    
        Nodo<T> actual = primero;
        for (int i = 0; i < posicion; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }

    /**
     * Inserta un elemento en la lista enlazada en una posición específica.
     * Si la posición es menor a 1, se lanza una excepción. Si la posición es mayor al tamaño
     * actual de la lista, el elemento se agrega al final. Si la posición es 1, el elemento
     * se inserta al principio. En otros casos, el elemento se inserta en la posición indicada.
     *
     * @param dato el elemento a insertar
     * @param posicion la posición en la lista donde se debe insertar el elemento (1-indexado)
     * @throws RuntimeException si la posición indicada es menor a 1
     * @author Patricio Alaniz
     */
    public void insertarEnPosicion(T dato, int posicion) throws RuntimeException {
        if (posicion < 1) {
            throw new RuntimeException("La posicion debe ser mayor a 0");
        }

        if (posicion > this.largo) {
            this.insertarUltimo(dato);
            return;
        }

        if (posicion == 1) {
            this.insertarPrimero(dato);
            return;
        }

        Nodo<T> nuevo = new Nodo<>(dato);
        Nodo<T> actual = this.primero;

        for (int i = 1; i < posicion - 1; i++) {
            actual = actual.siguiente;
        }

        nuevo.siguiente = actual.siguiente;
        actual.siguiente = nuevo;
        this.largo++;
    }

    /**
     * Elimina un elemento de la lista enlazada en una posición específica y lo retorna.
     * Si la posición es menor a 1, se lanza una excepción. Si la posición es mayor
     * al tamaño actual de la lista, se eliminará el último elemento. Si la posición
     * es 1, se eliminará el primer elemento. En otros casos, se eliminará el elemento
     * en la posición indicada.
     *
     * @param posicion la posición del elemento a eliminar en la lista (1-indexado)
     * @return el elemento eliminado de la lista
     * @throws RuntimeException si la posición es menor a 1
     * @author Patricio Alaniz
     */
    public T borrarEnPosicion(int posicion) throws RuntimeException {
        if (posicion < 1) {
            throw new RuntimeException("La posicion debe ser mayor a 0");
        }
        if (posicion > this.largo) {
            return this.borrarUltimo();
        }
        if (posicion == 1) {
            return this.borrarPrimero();
        }

        Nodo<T> actual = this.primero;

        for (int i = 1; i < posicion - 1; i++) {
            actual = actual.siguiente;
        }

        T dato = actual.siguiente.dato;

        actual.siguiente = actual.siguiente.siguiente;
        this.largo--;

        return dato;
    }

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

    /**
     * Borra el último elemento de la lista enlazada y lo devuelve.
     * Si la lista está vacía, lanza una excepción de runtime.
     *
     * @return el dato almacenado en el último nodo eliminado de la lista
     * @throws RuntimeException si la lista está vacía
     * @author Patricio Alaniz
     */
    public T borrarUltimo() throws RuntimeException {
        if (this.estaVacia()) {
            throw new RuntimeException("La lista esta vacia");
        }

        T dato = this.ultimo.dato;

        if (this.primero == this.ultimo) {
            this.primero = null;
        }

        Nodo<T> nodoAux = this.primero;

        while (nodoAux.siguiente != this.ultimo) {
            nodoAux = nodoAux.siguiente;
        }

        nodoAux.siguiente = null;

        this.ultimo = nodoAux;
        this.largo--;

        return dato;
    }

    public void iterar(Visitador<T> visitar) {
        Nodo<T> actual = primero;
        while (actual != null) {
            if (!visitar.visitar(actual.dato)) break;
            actual = actual.siguiente;
        }
    }

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
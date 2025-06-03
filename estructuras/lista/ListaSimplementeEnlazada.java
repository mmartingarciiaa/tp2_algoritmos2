package estructuras.lista;

import estructuras.nodos.NodoSimplementeEnlazado;

/**
 * Implementación de una lista enlazada genérica.
 *
 * @param <T> el tipo de datos en la lista
 */
public class ListaSimplementeEnlazada<T> extends Lista<T> {

    private NodoSimplementeEnlazado<T> ultimo;

    public ListaSimplementeEnlazada() {
        this.ultimo = null;
    }

    /**
     * post: devuelve true si el elemento está en la Lista, false en caso contrario.
     * en caso de que el elemento sea null, devuelve false.
     * @return
     */
    @Override
    public boolean contains(T elemento) {
        if (elemento == null) {
            return false;
        }

        NodoSimplementeEnlazado<T> actual = primero;
        while (actual != null) {
            if (actual.getDato().equals(elemento)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    /**
     * post: devuelve el último elemento de la Lista.
     */
    @Override
    public T verUltimo() throws Exception {
        if (estaVacia()) {
            throw new Exception("La lista está vacía");
        }
        return ultimo.getDato();
    }

    /**
     * post: agrega el elemento al principio de la Lista
     */
    @Override
    public void insertarPrimero(T elemento) {
        NodoSimplementeEnlazado<T> nuevo = new NodoSimplementeEnlazado<>(elemento);
        if (estaVacia()) {
            ultimo = nuevo;
        } else {
            nuevo.setSiguiente(primero);
        }
        primero = nuevo;
        largo++;
    }

    /**
     * post: agrega el elemento al final de la lista
     */
    @Override
    public void insertarUltimo(T elemento) {
        NodoSimplementeEnlazado<T> nuevo = new NodoSimplementeEnlazado<>(elemento);
        if (estaVacia()) {
            primero = nuevo;
        } else {
            ultimo.setSiguiente(nuevo);
        }
        ultimo = nuevo;
        largo++;
    }

    /**
     * post: borra el primer elemento de la Lista. Si la Lista está vacía, lanza una excepción.
     * @throws Exception si la lista está vacía
     */
    @Override
    public T borrarPrimero() throws Exception {
        if (estaVacia()) {
            throw new Exception("La lista está vacía");
        }
        T dato = primero.getDato();
        if (primero == ultimo) {
            ultimo = null;
        }
        primero = primero.getSiguiente();
        largo--;
        return dato;
    }

    /**
     * post: borra el último elemento de la Lista. Si la Lista está vacía, lanza una excepción.
     * @throws Exception si la lista está vacía
     */
    @Override
    public T borrarUltimo() throws Exception {
        if (this.estaVacia()) {
            throw new RuntimeException("La lista esta vacia");
        }

        T dato = this.ultimo.getDato();

        if (this.primero == this.ultimo) {
            this.primero = null;
        }

        NodoSimplementeEnlazado<T> nodoAux = this.primero;
        while (nodoAux.getSiguiente() != this.ultimo) {
            nodoAux = nodoAux.getSiguiente();
        }
        nodoAux.setSiguiente(null);
        this.ultimo = nodoAux;
        this.largo--;

        return dato;
    }

    /**
     * obtener el elemento en la posición indicada.
     * Si la posición es menor o igual a 1, devuelve el primer elemento.
     * Si la posición es mayor al tamaño actual de la lista, devuelve el último elemento.
     * 
     * @param posicion la posición del elemento a obtener (1-indexado)
     * @return el elemento en la posición indicada
     */
    @Override
    public T obtenerEnPosicion(int posicion) {
        if (posicion <= 1) {
            return primero.getDato();
        }
        
        if (posicion > largo) {
            return ultimo.getDato();
        }

        NodoSimplementeEnlazado<T> actual = primero;
        for (int i = 1; i < posicion; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
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
     */
    @Override
    public void insertarEnPosicion(T dato, int posicion) throws Exception {
        if (posicion < 1) {
            throw new Exception("Posición inválida");
        }

        if (posicion > largo + 1) {
            insertarUltimo(dato);
            return;
        }

        if (posicion == 1) {
            insertarPrimero(dato);
            return;
        }

        NodoSimplementeEnlazado<T> nuevo = new NodoSimplementeEnlazado<>(dato);
        NodoSimplementeEnlazado<T> actual = primero;

        for (int i = 1; i < posicion - 1; i++) {
            actual = actual.getSiguiente();
        }

        nuevo.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(nuevo);
        largo++;
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
     */
    @Override
    public T borrarEnPosicion(int posicion) throws Exception {
        if (posicion < 1) {
            throw new RuntimeException("La posicion debe ser mayor a 0");
        }
        if (posicion > this.largo) {
            return this.borrarUltimo();
        }
        if (posicion == 1) {
            return this.borrarPrimero();
        }

        NodoSimplementeEnlazado<T> actual = this.primero;

        for (int i = 1; i < posicion - 1; i++) {
            actual = actual.getSiguiente();
        }

        T dato = actual.getSiguiente().getDato();

        actual.setSiguiente(actual.getSiguiente().getSiguiente());
        this.largo--;

        return dato;
    }

    @Override
    public IteradorLista<T> iterador() {
        return new IteradorListaEnlazada();
    }

    private class IteradorListaEnlazada implements IteradorLista<T> {
        private NodoSimplementeEnlazado<T> actual = primero;
        private NodoSimplementeEnlazado<T> anterior = null;

        @Override
        public T verActual() {
            if (!haySiguiente()) throw new RuntimeException("No hay siguiente");
            return actual.getDato();
        }

        @Override
        public boolean haySiguiente() {
            return actual != null;
        }

        @Override
        public void siguiente() {
            if (!haySiguiente()) throw new RuntimeException("No hay siguiente");
            anterior = actual;
            actual = actual.getSiguiente();
        }

        @Override
        public void insertar(T dato) {
            NodoSimplementeEnlazado<T> nuevo = new NodoSimplementeEnlazado<>(dato);
            if (anterior == null) {
                nuevo.setSiguiente(primero);
                primero = nuevo;
            } else {
                nuevo.setSiguiente(actual);
                anterior.setSiguiente(nuevo);
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
            T dato = actual.getDato();
            if (anterior == null) {
                primero = actual.getSiguiente();
            } else {
                anterior.setSiguiente(actual.getSiguiente());
            }
            if (actual.getSiguiente() == null) {
                ultimo = anterior;
            }
            actual = actual.getSiguiente();
            largo--;
            return dato;
        }
    }
}
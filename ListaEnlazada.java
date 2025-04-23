public class ListaEnlazada<T> {
    private Nodo<T> primero;
    private Nodo<T> ultimo;
    private int longitud;

    public ListaEnlazada() {
        this.primero = null;
        this.ultimo = null;
        this.longitud = 0;
    }

    public boolean estaVacia() {
        return this.primero == null && this.ultimo == null;
    }
    
    public T verPrimero() {
        if (estaVacia()){
            throw new RuntimeException("La lista está vacía");
        }
        return this.primero.dato;
    }

    public T verUltimo() {
        if (estaVacia()) {
            throw new RuntimeException("La lista está vacía");
        }
        return this.ultimo.dato;
    }

    public int longitud () {
        return this.longitud;
    }

    public void insertarPrimero(T dato){
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (estaVacia()){
            this.ultimo = nuevoNodo;
        } else {
            nuevoNodo.siguiente = this.primero;
        }
        this.primero = nuevoNodo;
        this.longitud++;
    }

    public void insertarUltimo(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (estaVacia()) {
            this.primero = nuevoNodo;
        } else {
            this.ultimo.siguiente = nuevoNodo;
        }
        this.ultimo = nuevoNodo;
        this.longitud++;
    }
    
    public T borrarPrimero() {
        if (estaVacia()) {
            throw new RuntimeException("La lista está vacía");
        }
        T elemBorrado = this.primero.dato;
        if (this.primero == this.ultimo) {
            this.primero = null;
        }
        this.primero = this.primero.siguiente;
        this.longitud--;
        return elemBorrado;
    }
}

class Nodo<T> {
    T dato;
    Nodo<T> siguiente;

    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

}
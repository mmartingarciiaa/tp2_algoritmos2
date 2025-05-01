
public class Pila<T> {
  private Nodo<T> tope = null;
  private int tamanio = 0;


  /**
   * pre:
   * post: inicializa la pila vacia para su uso
   */
  public Pila() {
    this.tope = null;
    this.tamanio = 0;
  }

  /**
   * post: indica si la cola tiene algún elemento.
   */
  public boolean estaVacia() {
    return (this.tamanio == 0);
  }

  /**
   * pre: el elemento no es vacio
   * post: agrega el elemento a la pila
   */
  public void apilar(T elemento) {
    Nodo<T> nuevo = new Nodo<T>(elemento);
    nuevo.setSiguiente(this.tope);
    this.tope = nuevo;
    this.tamanio++;
  }

  /**
   * pre: el elemento no es vacio
   * post: agrega el elemento a la pila
   */
  public void apilar(ListaEnlazada<T> lista) {
    //validar
    while (lista.longitud() > 0) {
      this.apilar(lista.verPrimero());
      lista.borrarPrimero();
    }
  }

  /**
   * pre :
   * post: devuelve el elemento en el tope de la pila y achica la pila en 1.
   */
  public T desapilar() {
    T elemento = null;
    if (!this.estaVacia()) {
      elemento = this.tope.getDato();
      this.tope = this.tope.getSiguiente();
      this.tamanio--;
    }
    return elemento;
  }

  /**
   * pre: -
   * post: devuelve el elemento en el tope de la pila (solo lectura)
   */
  public T obtener() {
    T elemento = null;
    if (!this.estaVacia()) {
      elemento = this.tope.getDato();
    }
    return elemento;
  }

  /**
   * post: devuelve la cantidad de elementos que tiene la cola.
   */
  public int contarElementos() {
    return this.tamanio;
  }
}

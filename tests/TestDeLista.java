package tests;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import estructuras.lista.*;

public class TestDeLista {

  private static final int CARGA_MAXIMA = 10000;
  private static final int RANGO_TESTS = 5;
  private static final int CORTE_VOLUMEN = 4000;

  private void panicListaVacia(ListaSimplementeEnlazada<Object> lista) {
    assertThrows(IllegalStateException.class, () -> lista.verPrimero());
    assertThrows(IllegalStateException.class, () -> lista.verUltimo());
    assertThrows(IllegalStateException.class, () -> lista.borrarPrimero());
  }

  private void panicIteradorSinSiguiente(IteradorLista<Object> iter) {
    assertThrows(IllegalStateException.class, () -> iter.siguiente());
    assertThrows(IllegalStateException.class, () -> iter.verActual());
    assertThrows(IllegalStateException.class, () -> iter.borrar());
  }

  private <T> void verificarPrimeroYUltimo(ListaSimplementeEnlazada<T> lista, T primero, T ultimo) {
    assertEquals(primero, lista.verPrimero());
    assertEquals(ultimo, lista.verUltimo());
  }

  private <T> void vaciarLista(ListaSimplementeEnlazada<T> lista) {
    while (!lista.estaVacia()) {
      lista.borrarPrimero();
    }
  }

  @Test
  public void testComportamientoListaVacia() {
    ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

    assertTrue(lista.estaVacia());
    panicListaVacia(lista);

    lista.insertarPrimero(1);
    assertFalse(lista.estaVacia());
    verificarPrimeroYUltimo(lista, 1, 1);

    assertEquals(1, lista.borrarPrimero());
    assertTrue(lista.estaVacia());

    panicListaVacia(lista);
  }

  @Test
  public void testInsertarYBorrarUnElemento() {
    ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

    assertTrue(lista.estaVacia());

    lista.insertarPrimero(1);
    verificarPrimeroYUltimo(lista, 1, 1);
    assertFalse(lista.estaVacia());

    assertEquals(1, lista.borrarPrimero());
    assertTrue(lista.estaVacia());

    panicListaVacia(lista);
  }

  @Test
  public void testVolumenInsertarPrimero() {
    ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

    assertTrue(lista.estaVacia());

    for (int i = 0; i < CARGA_MAXIMA; i++) {
      lista.insertarPrimero(i);
      verificarPrimeroYUltimo(lista, i, 0);
      assertFalse(lista.estaVacia());
    }

    for (int i = CARGA_MAXIMA - 1; i >= 0; i--) {
      verificarPrimeroYUltimo(lista, i, 0);
      assertEquals(i, lista.borrarPrimero());
    }

    assertTrue(lista.estaVacia());
    panicListaVacia(lista);
  }
}

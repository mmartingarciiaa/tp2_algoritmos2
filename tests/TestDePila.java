package tests;

import estructuras.pila.PilaEnlazada;
import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.*;

public class TestDePila {
  private static final int CARGA_MAXIMA = 10000;
  private static final int CARGA_MINIMA = 10;

  @Test
  public void testComportamientoDePilaVacia() {
    PilaEnlazada<Integer> pila = new PilaEnlazada<>();

    // Pila recién creada debe estar vacía.
    assertTrue(pila.estaVacia());

    // Verifico que no se le puede ver el tope ni desapilar una pila vacia.
    assertThrows(EmptyStackException.class, () -> pila.verTope());
    assertThrows(EmptyStackException.class, () -> pila.desapilar());

    // Apilo un elemento, verifico que ya no está vacia y que se le puede ver tope.
    pila.apilar(1);
    assertFalse(pila.estaVacia());
    assertEquals(Integer.valueOf(1), pila.verTope());

    // Desapilo el elemento, verifico que está vacía nuevamente.
    pila.desapilar();
    assertTrue(pila.estaVacia());

    // Verifico que no se puede ver el tope ni desapilar una pila recientemente vaciada,
    // la cual se comporta de la misma manera que una pila recien creada.
    assertThrows(EmptyStackException.class, () -> pila.desapilar());
    assertThrows(EmptyStackException.class, () -> pila.verTope());
  }

  @Test
  public void testApilarYDesapilarUnElemento() {
    PilaEnlazada<Integer> pila = new PilaEnlazada<>();

    // Verifico que está vacía.
    assertTrue(pila.estaVacia());

    // Apilo un elemento, verifico que se pueda ver el tope y que la pila no está vacia.
    pila.apilar(1);
    assertEquals(Integer.valueOf(1), pila.verTope());
    assertFalse(pila.estaVacia());

    // Desapilo el elemento, verifico que está vacía nuevamente.
    assertEquals(Integer.valueOf(1), pila.desapilar());
    assertTrue(pila.estaVacia());

    // Verifico que se comporta de igual forma que una pila recién creada.
    assertThrows(EmptyStackException.class, () -> pila.desapilar());
    assertThrows(EmptyStackException.class, () -> pila.verTope());
  }

  @Test
  public void testApilarYDesapilarVariosElementos() {
    PilaEnlazada<Integer> pila = new PilaEnlazada<>();

    // Verifico que está vacía.
    assertTrue(pila.estaVacia());

    // Apilo varios elementos, verifico que se pueda ver el tope y que la pila no está vacia.
    for (int i = 0; i <= CARGA_MINIMA; i++) {
      pila.apilar(i);
      assertEquals(Integer.valueOf(i), pila.verTope());
      assertFalse(pila.estaVacia());
    }

    // Desapilo los elementos, verificando que lo hagan en orden.
    for (int i = CARGA_MINIMA; i >= 0; i--) {
      assertFalse(pila.estaVacia());
      assertEquals(Integer.valueOf(i), pila.verTope());
      assertEquals(Integer.valueOf(i), pila.desapilar());
    }

    // Verifico que se vació correctamente.
    assertTrue(pila.estaVacia());

    // Verifico que se comporta de igual forma que una pila recién creada.
    assertThrows(EmptyStackException.class, () -> pila.desapilar());
    assertThrows(EmptyStackException.class, () -> pila.verTope());
  }

  @Test
  public void testApilarYDesapilarStrings() {
    PilaEnlazada<String> pila = new PilaEnlazada<>();

    // Verifico que está vacía.
    assertTrue(pila.estaVacia());

    // Apilo varios strings, verifico que se pueda ver el tope y que la pila no está vacia.
    String[] strings = {"hola", "mundo", "como", "estas", "hoy"};
    for (int i = 0; i < strings.length; i++) {
      pila.apilar(strings[i]);
      assertEquals(strings[i], pila.verTope());
      assertFalse(pila.estaVacia());
    }

    // Desapilo los strings, verificando que lo hagan en orden.
    for (int i = strings.length - 1; i >= 0; i--) {
      assertFalse(pila.estaVacia());
      assertEquals(strings[i], pila.verTope());
      assertEquals(strings[i], pila.desapilar());
    }

    // Verifico que se vació correctamente.
    assertTrue(pila.estaVacia());

    // Verifico que se comporta de igual forma que una pila recién creada.
    assertThrows(EmptyStackException.class, () -> pila.desapilar());
    assertThrows(EmptyStackException.class, () -> pila.verTope());
  }

  @Test
  public void testApilarYDesapilarBooleanos() {
    PilaEnlazada<Boolean> pila = new PilaEnlazada<>();

    // Verifico que está vacía.
    assertTrue(pila.estaVacia());

    // Apilo varios booleanos, verifico que se pueda ver el tope y que la pila no está vacia.
    Boolean[] booleanos = {true, false, true, false, true, true, false, false};
    for (int i = 0; i < booleanos.length; i++) {
      pila.apilar(booleanos[i]);
      assertEquals(booleanos[i], pila.verTope());
      assertFalse(pila.estaVacia());
    }

    // Desapilo los booleanos, verificando que lo hagan en orden.
    for (int i = booleanos.length - 1; i >= 0; i--) {
      assertFalse(pila.estaVacia());
      assertEquals(booleanos[i], pila.verTope());
      assertEquals(booleanos[i], pila.desapilar());
    }

    // Verifico que se vació correctamente.
    assertTrue(pila.estaVacia());

    // Verifico que se comporta de igual forma que una pila recién creada.
    assertThrows(EmptyStackException.class, () -> pila.desapilar());
    assertThrows(EmptyStackException.class, () -> pila.verTope());
  }

  @Test
  public void testVolumen() {
    PilaEnlazada<Integer> pila = new PilaEnlazada<>();

    // Verifico que está vacía.
    assertTrue(pila.estaVacia());

    // Apilo muchos elementos, verifico que se pueda ver el tope y que la pila no está vacia.
    for (int i = 0; i <= CARGA_MAXIMA; i++) {
      pila.apilar(i);
      assertEquals(Integer.valueOf(i), pila.verTope());
      assertFalse(pila.estaVacia());
    }

    // Desapilo los elementos, verificando que lo hagan en orden.
    for (int i = CARGA_MAXIMA; i >= 0; i--) {
      assertFalse(pila.estaVacia());
      assertEquals(Integer.valueOf(i), pila.verTope());
      assertEquals(Integer.valueOf(i), pila.desapilar());
    }

    // Verifico que se vació correctamente.
    assertTrue(pila.estaVacia());

    // Verifico que se comporta de igual forma que una pila recién creada.
    assertThrows(EmptyStackException.class, () -> pila.desapilar());
    assertThrows(EmptyStackException.class, () -> pila.verTope());
  }
}

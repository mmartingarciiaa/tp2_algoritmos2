package tests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mazo.Mazo;

import static org.junit.jupiter.api.Assertions.*;
import estructuras.lista.ListaEnlazada;

/**
 * La clase TestDeMazo contiene un conjunto de pruebas unitarias para la clase Mazo.
 * Estas pruebas validan el comportamiento y la funcionalidad de la clase Mazo,
 * incluyendo escenarios como la creación correcta de un mazo, el manejo de listas nulas,
 * extracción de cartas y asegurar que el orden de las cartas sea aleatorio.
 * @author Patricio Alaniz
 */
public class TestDeMazo {
  private ListaEnlazada<String> cartas = null;
  private Mazo<String> mazo = null;

  @BeforeEach
  public void inicializarListaDeCartas() {
    this.cartas = new ListaEnlazada<>();

    for (int i = 1; i <= 10; i++) {
      this.cartas.insertarUltimo("carta" + i);
    }

    this.mazo = new Mazo<>(this.cartas);
  }

  @Test
  public void testCrearMazoCorrectamente() {
    assertEquals(10, this.mazo.cantidadDeCartas());
  }

  @Test
  public void testCrearMazoListaNula() {
    assertThrows(RuntimeException.class, () -> {
      new Mazo<String>(null);
    });
  }

  @Test
  public void testSacarCartaDelMazo() {
    String carta = this.mazo.sacarCarta();
    
    assertNotNull(carta);
    assertEquals(9, this.mazo.cantidadDeCartas());
  }

  @Test
  public void testSacarTodasLasCartas() {

    for (int i = 0; i < 10; i++) {
      this.mazo.sacarCarta();
    }
    
    assertEquals(0, this.mazo.cantidadDeCartas());
  }

  @Test
  public void testSacarCartaMazoVacio() {
    
    for (int i = 0; i < 10; i++) {
      this.mazo.sacarCarta();
    }
    
    assertThrows(RuntimeException.class, () -> {
      this.mazo.sacarCarta();
    });
  }

  @Test
  public void testOrdenDeLasCartasDiferenteAlOrdenDeInsercion() {
    String[] ordenOriginal = new String[10];
    String[] ordenMazo = new String[10];

    for (int i = 0; i < 10; i++) {
      ordenMazo[i] = this.mazo.sacarCarta();
    }

    for (int i = 0; i < 10; i++) {
      ordenOriginal[i] = "carta" + (i + 1);
    }

    boolean sonDiferentes = false;

    for (int i = 0; i < 10; i++) {
      if (!ordenMazo[i].equals(ordenOriginal[i])) {
        sonDiferentes = true;
        break;
      }
    }

    assertTrue(sonDiferentes);
  }
}
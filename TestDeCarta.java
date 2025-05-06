import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDeCarta {

  @Test
  public void testCrearCartaCorrectamente() {
    Carta carta = new Carta("Nombre", "Descripcion", TipoCarta.NAVE_OBTIENE_ESCUDO);
    assertEquals("Nombre", carta.getNombre());
    assertEquals("Descripcion", carta.getDescripcion());
    assertEquals(TipoCarta.NAVE_OBTIENE_ESCUDO, carta.getTipo());
  }

  @Test
  public void testCrearCartaNombreNulo() {
    assertThrows(RuntimeException.class, () -> {
      new Carta(null, "Descripcion", TipoCarta.CAMPO_DE_FUERZA);
    });
  }

  @Test
  public void testCrearCartaDescripcionNula() {
    assertThrows(RuntimeException.class, () -> {
      new Carta("Nombre", null, TipoCarta.CAMPO_DE_FUERZA);
    });
  }

  @Test
  public void testCrearCartaTipoNulo() {
    assertThrows(RuntimeException.class, () -> {
      new Carta("Nombre", "Descripcion", null);
    });
  }

  @Test
  public void testCrearCartaNombreCorto() {
    assertThrows(RuntimeException.class, () -> {
      new Carta("No", "Descripcion", TipoCarta.CAMPO_DE_FUERZA);
    });
  }

  @Test
  public void testCrearCartaDescripcionCorta() {
    assertThrows(RuntimeException.class, () -> {
      new Carta("Nombre", "De", TipoCarta.CAMPO_DE_FUERZA);
    });

  }
}

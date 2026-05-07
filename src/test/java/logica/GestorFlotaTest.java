package logica;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GestorFlotaTest {
    @BeforeEach
    void setUp() {
        // Limpiamos la flota antes de cada test
        GestorFlota.flota.clear();
    }

    @Test
    void testEjecutarAltaCoche() {
        // Caso 1: Alta exitosa
        Coche c1 = new Coche("1234ABC", "Seat", "Ibiza", true, TipoCoche.Pequeño, 5);
        boolean resultadoOk = GestorFlota.ejecutarAltaCoche(c1);

        assertTrue(resultadoOk, "El coche debería guardarse correctamente");
        assertEquals(1, GestorFlota.flota.size());

        // Caso 2: Matrícula duplicada
        Coche c2 = new Coche("1234ABC", "Audi", "A3", true, TipoCoche.Deportivo, 4);
        boolean resultadoError = GestorFlota.ejecutarAltaCoche(c2);

        assertFalse(resultadoError, "No debería permitir matrículas duplicadas");
        assertEquals(1, GestorFlota.flota.size());
    }

    @Test
    void testEjecutarAltaFurgoneta() {
        // Caso 1: Alta exitosa
        Furgoneta f1 = new Furgoneta("5555XYZ", "Ford", "Transit", true, true, 1000);
        assertTrue(GestorFlota.ejecutarAltaFurgoneta(f1));

        // Caso 2: Objeto nulo
        assertFalse(GestorFlota.ejecutarAltaFurgoneta(null));
    }

    @Test
    void testListarVehiculosDisponibles() {
        // Caso A: Lista vacía
        assertDoesNotThrow(() -> GestorFlota.listarVehiculosDisponibles());

        // Caso B: Con vehículos
        Coche disp = new Coche("1", "A", "B", true, TipoCoche.Familiar, 5);
        Coche noDisp = new Coche("2", "C", "D", false, TipoCoche.Pequeño, 4);
        noDisp.setDisponible(false);

        GestorFlota.ejecutarAltaCoche(disp);
        GestorFlota.ejecutarAltaCoche(noDisp);

        assertDoesNotThrow(() -> GestorFlota.listarVehiculosDisponibles());
    }
}
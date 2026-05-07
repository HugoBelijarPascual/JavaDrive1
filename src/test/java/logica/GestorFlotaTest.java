package logica;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GestorFlotaTest {
    @BeforeEach
    void setUp() {
        // Limpiamos la flota antes de cada test para que no se mezclen los datos
        GestorFlota.flota.clear();
    }

    @Test
    void testEjecutarAltaCoche() {
        // Caso 1: Alta exitosa
        Coche c1 = new Coche("1234ABC", "Seat", "Ibiza", true, TipoCoche.Pequeño, 5);
        boolean resultadoOk = GestorFlota.ejecutarAltaCoche(c1);

        assertTrue(resultadoOk, "El coche debería guardarse correctamente");
        assertEquals(1, GestorFlota.flota.size());

        // Caso 2: Matrícula duplicada (Cubre la rama 'Branch' del IF)
        Coche c2 = new Coche("1234ABC", "Audi", "A3", true, TipoCoche.Deportivo, 4);
        boolean resultadoError = GestorFlota.ejecutarAltaCoche(c2);

        assertFalse(resultadoError, "No debería permitir matrículas duplicadas");
        assertEquals(1, GestorFlota.flota.size()); // Sigue habiendo solo 1
    }

    @Test
    void testEjecutarAltaFurgoneta() {
        // Caso 1: Alta exitosa
        Furgoneta f1 = new Furgoneta("5555XYZ", "Ford", "Transit", true, true, 1000);
        assertTrue(GestorFlota.ejecutarAltaFurgoneta(f1));

        // Caso 2: Objeto nulo (Cubre otra rama del código)
        assertFalse(GestorFlota.ejecutarAltaFurgoneta(null));
    }

    @Test
    void testListarVehiculosDisponibles() {
        // Caso A: Lista vacía (Cubre el "No hay vehículos disponibles")
        assertDoesNotThrow(() -> GestorFlota.listarVehiculosDisponibles());

        // Caso B: Con vehículos (Cubre el bucle FOR y el IF de disponibilidad)
        Coche disp = new Coche("1", "A", "B", true, TipoCoche.Familiar, 5);
        Coche noDisp = new Coche("2", "C", "D", false, TipoCoche.Pequeño, 4);
        noDisp.setDisponible(false); // Forzamos que no esté disponible

        GestorFlota.ejecutarAltaCoche(disp);
        GestorFlota.ejecutarAltaCoche(noDisp);

        assertDoesNotThrow(() -> GestorFlota.listarVehiculosDisponibles());
    }
}
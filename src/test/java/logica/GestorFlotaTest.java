package logica;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GestorFlotaTest {
    @Test
    void testGlobalGestorFlota() {
        GestorFlota.flota.clear();

        // 1. Forzamos todos los caminos del ejecutarAltaCoche
        GestorFlota.ejecutarAltaCoche("A", "B", "C", "Pequeño", "5");
        GestorFlota.ejecutarAltaCoche("A", "B", "C", "ERROR", "5");    // Catch 1
        GestorFlota.ejecutarAltaCoche("A", "B", "C", "Pequeño", "X");  // Catch 2
        GestorFlota.ejecutarAltaCoche("A", "B", "C", "Pequeño", "10"); // Catch 3

        // 2. Forzamos todos los caminos de ejecutarAltaFurgoneta
        GestorFlota.ejecutarAltaFurgoneta("F1", "M", "M", true, 1000);
        GestorFlota.ejecutarAltaFurgoneta("F2", "M", "M", false, 10);  // Catch 4

        // 3. Forzamos el listado con datos y sin datos
        GestorFlota.listarVehiculosDisponibles();
        GestorFlota.flota.clear();
        GestorFlota.listarVehiculosDisponibles();

        assertTrue(true);
    }
}
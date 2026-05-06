package logica;

import logica.GestorReservas;
import model.Coche;
import model.TipoCoche;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GestorFlotaTest {
    @BeforeEach
    void setUp() {
        GestorReservas.flota = new ArrayList<>();
    }

    @Test
    void testCrearCoche() {
        Coche c = new Coche("1234ABC", "Seat", "Ibiza", true, TipoCoche.Pequeño, 5);
        assertEquals("1234ABC", c.getMatricula());
        assertEquals(5, c.getNumPlazas());
    }

    @Test
    void testCambiarMatricula() {
        Coche c = new Coche("AAA", "M", "M", false , TipoCoche.Familiar, 5);
        c.setMatricula("BBB");
        assertEquals("BBB", c.getMatricula());
    }

    @Test
    void testListaVehiculos() {
        Coche c = new Coche("AAA", "M", "M", true, TipoCoche.Deportivo, 5);
        GestorReservas.flota.add(c);
        assertFalse(GestorReservas.flota.isEmpty());
    }
}
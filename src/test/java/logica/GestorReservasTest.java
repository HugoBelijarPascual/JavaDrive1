package logica;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class GestorReservasTest {

    @BeforeEach
    void setUp() {
        GestorClientes.clientes.clear();
        GestorFlota.flota.clear();
    }

    @Test
    void testBusquedas() {
        Cliente c = new Cliente("123", "Pepe", "600");
        GestorClientes.clientes.add(c);

        // Test búsqueda éxito y fallo
        assertNotNull(GestorReservas.buscarCliente("123"));
        assertNull(GestorReservas.buscarCliente("999"));
    }

    @Test
    void testRealizarReservaExito() {
        Cliente c = new Cliente("1", "A", "1");
        Coche v = new Coche("M1", "X", "X", true, TipoCoche.Pequeño, 5);
        GestorClientes.clientes.add(c);
        GestorFlota.flota.add(v);

        Reserva r = GestorReservas.realizarReserva(c, v, 5);

        assertNotNull(r);
        assertFalse(v.isDisponible(), "El vehículo debería quedar NO disponible");
    }

    @Test
    void testRealizarReservaFallo() {
        Cliente c = new Cliente("1", "A", "1");
        Coche v = new Coche("M1", "X", "X", false, TipoCoche.Pequeño, 5);
        v.setDisponible(false); // Ya está ocupado

        // Intentar reservar vehículo no disponible
        Reserva r = GestorReservas.realizarReserva(c, v, 3);
        assertNull(r, "No debería permitir reservar un coche no disponible");
    }

    @Test
    void testBusquedaVehiculo() {
        Vehiculo v = new Coche("ABC", "Seat", "Ibiza", true, TipoCoche.Pequeño, 5);
        GestorFlota.flota.add(v);

        assertNotNull(GestorReservas.buscarVehiculo("ABC"));
        assertNull(GestorReservas.buscarVehiculo("XYZ"));
    }




}
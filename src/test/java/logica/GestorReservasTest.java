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
        // Limpiamos las listas que SI existen en tu GestorReservas
        GestorReservas.flota = new ArrayList<>();
        GestorReservas.clientes = new ArrayList<>();
        Reserva.setNextId(1);
    }

    @Test
    void testBuscarCliente() {
        Cliente c = new Cliente("123", "Pepe", "666");
        GestorReservas.clientes.add(c);

        // Probamos el método buscarCliente de tu lógica
        assertEquals(c, GestorReservas.buscarCliente("123"));
        assertNull(GestorReservas.buscarCliente("999"));
    }

    @Test
    void testBuscarVehiculo() throws NumPlazasException {
        Vehiculo v = new Coche("ABC", "Seat", "Ibiza", true, TipoCoche.Pequeño, 5);
        GestorReservas.flota.add(v);

        // Probamos el método buscarVehiculo de tu lógica
        assertEquals(v, GestorReservas.buscarVehiculo("ABC"));
    }

    @Test
    void testRealizarReservaCambiaEstado() throws NumPlazasException {
        Cliente c = new Cliente("1", "A", "1");
        Vehiculo v = new Coche("ABC", "S", "I", false,  TipoCoche.Familiar, 5);
        v.setDisponible(true);

        // Al realizar reserva, el vehículo debe pasar a NO disponible
        GestorReservas.realizarReserva(c, v, LocalDate.now(), LocalDate.now().plusDays(1));

        assertFalse(v.isDisponible(), "El vehículo debería estar ocupado tras la reserva");
    }

    @Test
    void testGenerarTicketCompleto() throws NumPlazasException {
        Cliente c = new Cliente("123", "Pepe", "666");
        Vehiculo v = new Coche("ABC", "Seat", "Ibiza", true, TipoCoche.Pequeño, 5);
        Reserva r = new Reserva(c, v, LocalDate.now(), LocalDate.now().plusDays(2));

        String ticket = r.GenerarLineaTicket();

        // Verificamos que el ticket se genera con los datos de tu clase Reserva
        assertTrue(ticket.contains("Pepe"));
        assertTrue(ticket.contains("ABC"));
        assertTrue(ticket.contains("Total días: 2"));
    }

    @Test
    void testIdReservaIncremental() {
        Reserva r1 = new Reserva(null, null, null, null);
        Reserva r2 = new Reserva(null, null, null, null);

        assertEquals(1, r1.getIdReserva());
        assertEquals(2, r2.getIdReserva());
    }
}
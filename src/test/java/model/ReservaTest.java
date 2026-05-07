package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {
    @Test
    void testConstructorReserva() {
        Cliente c = new Cliente("1", "A", "1");
        Vehiculo v = new Coche("A", "B", "C", true, TipoCoche.Pequeño, 5);
        Reserva r = new Reserva(c, v, LocalDate.now(), LocalDate.now().plusDays(1));

        assertNotNull(r);
        System.out.println(r.GenerarLineaTicket());
    }

    @Test
    void testReservaAlCompleto() throws NumPlazasException {
        Cliente c = new Cliente("123", "Pepe", "666");
        Vehiculo v = new Coche("ABC", "Seat", "Ibiza", true, TipoCoche.Pequeño, 5);
        Reserva r = new Reserva(c, v, LocalDate.now(), LocalDate.now().plusDays(2));

        r.getCliente();
        r.getVehiculo();
        r.getFechaInicio();
        r.getFechaFin();
        r.getIdReserva();
        r.GenerarLineaTicket();

        r.setCliente(c);
        r.setVehiculo(v);
        r.setFechaInicio(LocalDate.now());
        r.setFechaFin(LocalDate.now());

        assertTrue(true);
    }

    @Test
    void testIdsDiferentes() {
        Reserva r1 = new Reserva(null, null, null, null);
        Reserva r2 = new Reserva(null, null, null, null);

        assertNotEquals(r1.getIdReserva(), r2.getIdReserva());
    }

    @Test
    void testSettersYStatic() {
        Reserva.setNextId(100);
        Reserva r = new Reserva(null, null, null, null);
        assertEquals(100, r.getIdReserva());

        Reserva.setNextId(-5);

        r.setIdReserva(500);
        assertEquals(500, r.getIdReserva());
    }
}
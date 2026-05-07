package logica;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestorPersistenciaTest {

    @Test
    void testPersistenciaClientes() {
        List<Cliente> listaOriginal = new ArrayList<>();
        listaOriginal.add(new Cliente("123", "Test", "666"));

        // Guardamos
        GestorPersistencia.gestor.guardarClientes(listaOriginal);

        // Cargamos y comparamos
        List<Cliente> listaCargada = GestorPersistencia.gestor.cargarClientes();
        assertFalse(listaCargada.isEmpty());
        assertEquals("123", listaCargada.get(0).getDni());
    }

    @Test
    void testPersistenciaVehiculos() {
        List<Vehiculo> flotaOriginal = new ArrayList<>();
        flotaOriginal.add(new Coche("COCHE1", "Seat", "Ibiza", true, TipoCoche.Pequeño, 5));
        flotaOriginal.add(new Furgoneta("FURGO1", "Ford", "Transit", true, true, 1000));

        // Guardamos
        GestorPersistencia.gestor.guardarVehiculos(flotaOriginal);

        // Cargamos
        List<Vehiculo> flotaCargada = GestorPersistencia.gestor.cargarVehiculos();
        assertEquals(2, flotaCargada.size());
        assertTrue(flotaCargada.get(0) instanceof Coche);
        assertTrue(flotaCargada.get(1) instanceof Furgoneta);
    }

    @Test
    void testTicketsYIds() {
        // Creamos una reserva ficticia (necesitas mock o datos reales)
        Cliente c = new Cliente("1", "A", "1");
        Coche v = new Coche("M", "M", "M", true, TipoCoche.Pequeño, 5);

        LocalDate hoy = LocalDate.now();
        LocalDate manana = hoy.plusDays(1);
        Reserva r = new Reserva(c, v, hoy, manana); // Suponiendo este constructor

        // Test exportar
        assertDoesNotThrow(() -> GestorPersistencia.gestor.exportarTicket(r));

        int nextId = GestorPersistencia.gestor.calcularSiguienteIdReserva();
        assertTrue(nextId >= 1);
    }

    @Test
    void testPersistenciaVehiculosCompleto() {
        List<Vehiculo> flotaOriginal = new ArrayList<>();
        flotaOriginal.add(new Coche("C1", "Seat", "Ibiza", true, TipoCoche.Pequeño, 5));
        flotaOriginal.add(new Furgoneta("F1", "Ford", "Transit", true, true, 1000)); // Carga
        flotaOriginal.add(new Furgoneta("F2", "VW", "Multivan", true, false, 7));    // Pasajeros (NUEVO)

        GestorPersistencia.gestor.guardarVehiculos(flotaOriginal);

        List<Vehiculo> flotaCargada = GestorPersistencia.gestor.cargarVehiculos();
        assertEquals(3, flotaCargada.size());
        assertFalse(((Furgoneta)flotaCargada.get(2)).isEsDeCarga());
    }

    @Test
    void testArchivosNoExisten() {
        new File("clientes.txt").delete();
        new File("vehiculos.txt").delete();

        List<Cliente> clientes = GestorPersistencia.gestor.cargarClientes();
        List<Vehiculo> vehiculos = GestorPersistencia.gestor.cargarVehiculos();

        assertTrue(clientes.isEmpty(), "Debería devolver lista vacía si no hay archivo");
        assertTrue(vehiculos.isEmpty(), "Debería devolver lista vacía si no hay archivo");
    }

    @Test
    void testEscrituraInvalida() {
        assertDoesNotThrow(() -> GestorPersistencia.gestor.guardarClientes(null));
    }



}
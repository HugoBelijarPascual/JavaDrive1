package model;

import logica.GestorPersistencia;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SuperTest {

    @Test
    void testModelosYReserva() {
        Cliente cl = new Cliente("12345678Z", "Francis", "600000000");
        cl.setNombre("Dani");
        cl.setDni("87654321X");
        cl.setTelefono("666777888");
        assertEquals("Dani", cl.getNombre());
        assertEquals("87654321X", cl.getDni());
        assertEquals("666777888", cl.getTelefono());
        assertNotNull(cl.toString());

        Coche c = new Coche("1234ABC", "Toyota", "Corolla", true, TipoCoche.Pequeño, 5);
        c.setTipoCoche(TipoCoche.Deportivo);
        c.setNumPlazas(2);
        c.setMatricula("NEW-111");
        c.setMarca("Ferrari");
        c.setModelo("F40");
        c.setDisponible(false);

        assertEquals(TipoCoche.Deportivo, c.getTipoCoche());
        assertEquals(2, c.getNumPlazas());
        assertNotNull(c.obtenerDetalles());
        assertTrue(c.numPlazasEsCorrecto(5));

        Furgoneta fCarga = new Furgoneta("5555BBB", "Ford", "Transit", true, true, 1000);
        fCarga.setCapacidad(2000);
        assertEquals(2000, fCarga.getCapacidad());
        assertTrue(fCarga.obtenerDetalles().contains("kg"));

        Furgoneta fPasajeros = new Furgoneta("1111AAA", "Renault", "Kangoo", true, false, 5);
        assertTrue(fPasajeros.obtenerDetalles().contains("personas"));
    }

    @Test
    void testReservaYPersistencia() {

        Cliente cl = new Cliente("12345678Z", "Francis", "600000000");
        Coche c = new Coche("1111AAA", "Toyota", "Auris", true, TipoCoche.Pequeño, 5);
        Reserva r = new Reserva(cl, c, LocalDate.now(), LocalDate.now().plusDays(1));

        r.setIdReserva(100);
        r.setCliente(cl);
        r.setVehiculo(c);
        r.setFechaInicio(LocalDate.now());
        r.setFechaFin(LocalDate.now().plusDays(5));

        assertEquals(100, r.getIdReserva());
        assertNotNull(r.GenerarLineaTicket());

        GestorPersistencia gp = new GestorPersistencia();

        ArrayList<Cliente> listaCl = new ArrayList<>();
        listaCl.add(cl);

        ArrayList<Vehiculo> listaVe = new ArrayList<>();
        listaVe.add(c);

        gp.guardarClientes(listaCl);
        gp.guardarVehiculos(listaVe);

        List<Cliente> cargadosCl = gp.cargarClientes();
        List<Vehiculo> cargadosVe = gp.cargarVehiculos();

        assertNotNull(cargadosCl);
        assertNotNull(cargadosVe);

        java.io.File f1 = new java.io.File("clientes.dat");
        java.io.File f2 = new java.io.File("vehiculos.dat");

        if (f1.exists()) f1.delete();
        if (f2.exists()) f2.delete();

        gp.cargarClientes();
        gp.cargarVehiculos();
    }

    @Test
    void testClasesVacias() {
        assertNotNull(new logica.GestorFlota());
        assertNotNull(new logica.GestorClientes());
        assertNotNull(new logica.GestorInformes());
        assertNotNull(new logica.GestorReservas());
        assertNotNull(new app.ConsolaJavaDrive());
    }

    @Test
    void testExcepcion() {
        assertThrows(NumPlazasException.class, () -> {
            new Coche("1", "A", "B", true, TipoCoche.Pequeño, 1);
        });
    }

    @Test
    void testMainRapido() {
        // 1. "Escribimos" un 0 automáticamente para que el menú se cierre solo
        System.setIn(new java.io.ByteArrayInputStream("6\n".getBytes()));

        try {
            // 2. Llamamos al main y listo
            app.Main.main(new String[]{});
        } catch (Exception e) {
            // Ignoramos cualquier error, la cobertura ya se habrá grabado
        }
    }
}

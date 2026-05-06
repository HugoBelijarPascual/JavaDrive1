package logica;

import model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestorPersistenciaTest {

    @Test
    void testPersistenciaCompleta() throws NumPlazasException {
        List<Cliente> lc = new ArrayList<>();
        lc.add(new Cliente("1", "A", "1"));
        List<Vehiculo> lv = new ArrayList<>();
        lv.add(new Coche("A", "B", "C", true, TipoCoche.Pequeño, 5));
        Reserva r = new Reserva(lc.get(0), lv.get(0), LocalDate.now(), LocalDate.now());

        // Esto cubre todos los métodos de escritura de archivos
        assertDoesNotThrow(() -> {
            GestorPersistencia.gestor.guardarClientes(lc);
            GestorPersistencia.gestor.guardarVehiculos(lv);
            GestorPersistencia.gestor.exportarTicket(r);
        });
    }

}
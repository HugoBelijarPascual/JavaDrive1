package app;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testMenusPrincipales() {
        // Simulamos que el usuario escribe: DNI, Nombre, Telefono
        String entradaSimulada = "123\nPepe\n666\n";
        InputStream in = new ByteArrayInputStream(entradaSimulada.getBytes());
        System.setIn(in); // Cambiamos la entrada del sistema

        // Re-inicializamos el scanner del Main para que use nuestra entrada
        Main.sc = new java.util.Scanner(System.in);

        assertDoesNotThrow(() -> Main.menuAltaCliente());
    }

    @Test
    void testMetodosEstaticosMain() {
        assertDoesNotThrow(() -> Main.cargarDatos());
        assertDoesNotThrow(() -> Main.guardarTodoAlSalir());
    }

    @Test
    void testSalirDelPrograma() {
        String entradaSimulada = "6\n";
        InputStream in = new ByteArrayInputStream(entradaSimulada.getBytes());
        System.setIn(in);
        Main.sc = new Scanner(System.in);

        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }
}
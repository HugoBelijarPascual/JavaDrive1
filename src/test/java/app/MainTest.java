package app;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testMenusPrincipales() {
        // Aqui simulamos un nuevo usuario
        String entradaSimulada = "12345678Z\nPepe Perez\n666555444\n";
        InputStream in = new ByteArrayInputStream(entradaSimulada.getBytes());

        // Le decimos que el input sea el que simulamos
        System.setIn(in);

        Main.sc = new java.util.Scanner(System.in);

        assertDoesNotThrow(() -> Main.menuAltaCliente() );
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

    @Test
    void testCoberturaCompletaMenu() {
        // Simulamos:
        // 3 -> Listar vehículos
        // 5 -> Listar clientes
        // 6 -> Salir
        String entradaSimulada = "3\n5\n6\n";

        System.setIn(new ByteArrayInputStream(entradaSimulada.getBytes()));
        Main.sc = new Scanner(System.in);

        // Al ejecutar el main, pasará por el case 3, el 5 y finalmente el 6 para salir
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }
}
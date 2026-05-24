package app;

import logica.*;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {


    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        GestorConexion.probarConexion();
        cargarDatos();
        int opcion;

        do {
            opcion = mostrarMenu();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    menuAltaCliente();
                    break;
                case 2:
                    menuAltaVehiculo();
                    break;
                case 3:
                    GestorFlota.listarVehiculosDisponibles();
                    break;
                case 4:
                    menuReserva();
                    break;
                case 5:
                    GestorClientes.listarClientes();
                    break;
                case 6:
                    System.out.println("Saliendo del programa...");
                    guardarTodoAlSalir();
                    System.out.println("Datos guardados correctamente.");
                    break;

            }

        } while (opcion != 6);

    }

    public static int mostrarMenu(){
        System.out.println("\n =============== MENU ===============");
        System.out.println("1. Alta Cliente");
        System.out.println("2. Alta Vehiculo");
        System.out.println("3. Listar Vehiculos Disponibles");
        System.out.println("4. Realizar Reserva");
        System.out.println("5. Listar Clientes");
        System.out.println("6. Salir");
        System.out.println("\nElegir una opcion:");
        return sc.nextInt();

    }

    public static void menuAltaCliente() {

        System.out.println("==== ALTA DE NUEVO CLIENTE ====");
        System.out.print("DNI: ");
        String dni = sc.nextLine();

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Tlf: ");
        String telefono = sc.nextLine();

        Cliente nuevo = new Cliente(dni, nombre, telefono);

        if (GestorClientes.crearCliente(nuevo)) {
            System.out.println("Cliente registrado con éxito.");
            String sql = "INSERT INTO cliente (dni, nombre, telefono) VALUES (?, ?, ?)";

            try (Connection conn = GestorConexion.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, nuevo.getDni());
                pstmt.setString(2, nuevo.getNombre());
                pstmt.setString(3, nuevo.getTelefono());
                pstmt.executeUpdate();

            }catch (SQLException e) {
                System.out.println( "Error al insertar en la base de datos: " + e.getMessage());
            }
        } else {
            System.out.println("Error: El DNI ya existe o los datos son incorrectos.");
        }
    }

    public static void menuAltaVehiculo() {
        try {
            System.out.println("==== ALTA VEHICULO ====");
            System.out.print("¿Coche (C) o Furgoneta (F)? ");
            String tipo = sc.nextLine();

            System.out.print("Matricula: ");
            String mat = sc.nextLine();
            System.out.print("Marca: ");
            String mar = sc.nextLine();
            System.out.print("Modelo: ");
            String mod = sc.nextLine();
            //TODO FALTA ARREGLAR QUE LA MATRICULA ES FOREAN KEY DE VEHICULO
            if (tipo.equalsIgnoreCase("C")) {
                System.out.print("Tipo (Pequeño, Familiar, Deportivo): ");
                TipoCoche tc = TipoCoche.valueOf(sc.nextLine());
                System.out.print("Plazas: ");
                int plazas = Integer.parseInt(sc.nextLine());

                if (GestorFlota.ejecutarAltaCoche(new Coche(mat, mar, mod, true, tc, plazas))) {
                    System.out.println("Coche registrado.");

                    String sql = "INSERT INTO coche (matricula, tipoCoche, nPlazas) VALUES (?, ?, ?)";

                    try (Connection conn = GestorConexion.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

                        pstmt.setString(1, mat);
                        pstmt.setString(2, tc.name());
                        pstmt.setInt(3, plazas);

                        pstmt.executeUpdate();

                    }catch (SQLException e) {
                        System.out.println( "Error al insertar en la base de datos: " + e.getMessage());
                    }


                } else {
                    System.out.println("Error: Matrícula duplicada.");
                }

            } else if (tipo.equalsIgnoreCase("F")) {
                System.out.print("¿Carga? (true/false): ");
                boolean carga = sc.nextBoolean();
                System.out.print(carga ? "Kilos: " : "Personas: ");
                int cap = sc.nextInt();
                sc.nextLine();

                if (GestorFlota.ejecutarAltaFurgoneta(new Furgoneta(mat, mar, mod, true, carga, cap))) {
                    System.out.println("Furgoneta registrada.");

                    String sql = "INSERT INTO furgoneta (matricula, tipo, capacidad) VALUES (?, ?, ?)";

                    try (Connection conn = GestorConexion.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, mat);
                        pstmt.setString(2, carga ? "Carga" : "Pasajeros");
                        pstmt.setInt(3, cap);
                        pstmt.executeUpdate();
                    }catch (SQLException e) {
                        System.out.println( "Error al insertar en la base de datos: " + e.getMessage());
                    }

                } else {
                    System.out.println("Error: Matrícula duplicada.");
                }
            }

            String sql = "INSERT INTO vehiculo (matricula, marca, modelo, disponibilidad) VALUES (?, ?, ?, ?)";

            try(Connection conn = GestorConexion.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, mat);
                pstmt.setString(2, mar);
                pstmt.setString(3, mod);
                pstmt.setBoolean(4, true);
                pstmt.executeUpdate();
            }catch (SQLException e) {
                System.out.println("Error al insertar en la base de datos: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error en los datos introducidos.");
        }


    }

    public static void cargarDatos() {
        // Cargamos las listas desde los archivos .txt usando el gestor
        GestorFlota.flota = GestorPersistencia.gestor.cargarVehiculos();
        GestorClientes.clientes = GestorPersistencia.gestor.cargarClientes();

        // Ajustamos el ID de las reservas basado en los archivos existentes
        int proximoId = GestorPersistencia.gestor.calcularSiguienteIdReserva();
        Reserva.setNextId(proximoId);

        System.out.println("Sincronización completada: " +
                GestorFlota.flota.size() + " vehículos y " +
                GestorClientes.clientes.size() + " clientes cargados.");
    }

    public static void guardarTodoAlSalir() {
        // Invocamos los métodos de guardado del gestor
        GestorPersistencia.gestor.guardarClientes(GestorClientes.clientes);
        GestorPersistencia.gestor.guardarVehiculos(GestorFlota.flota);

        GestorReservas.guardarDatos();
    }


    public static void menuReserva(){
        System.out.println("==== REALIZAR RESERVA ====");
        System.out.print("DNI del cliente: ");
        String dniRes = sc.nextLine();
        Cliente cRes = GestorReservas.buscarCliente(dniRes);

        System.out.print("Matrícula del vehículo: ");
        String matRes = sc.nextLine();
        Vehiculo vRes = GestorReservas.buscarVehiculo(matRes);

        if (cRes != null && vRes != null && vRes.isDisponible()) {
            System.out.print("¿Cuántos días? ");
            int dias = sc.nextInt();
            sc.nextLine();

            Reserva r = GestorReservas.realizarReserva(cRes, vRes, dias);
            if (r != null) {
                System.out.println("Reserva realizada. Ticket generado.");
            }
        } else {
            System.out.println("ERROR: Datos inválidos o vehículo no disponible.");
        }

    }


}

package app;

import logica.*;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
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
//                    GestorClientes.listarClientes();
                    for (Cliente c : GestorClientes.ListarClientesBD()) {
                        System.out.println(c.toString());
                        System.out.println("----------------------------------------");
                    }
                    break;
                case 6:
                    for (Vehiculo v : GestorFlota.ListarFlotaBD()){
                        System.out.println(v.toString());
                        System.out.println("----------------------------------------");
                    }
                    break;
                case 7:
                    for (Reserva r : GestorReservas.ListarReservasBD()){
                        System.out.println(r.toString());
                        System.out.println("----------------------------------------");
                    }
                    break;
                case 8:
                    menuEliminar();
                    break;
                case 9:
                    menuModificar();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    guardarTodoAlSalir();
                    System.out.println("Datos guardados correctamente.");
                    break;

            }

        } while (opcion != 0);

    }

    public static int mostrarMenu(){
        System.out.println("\n =============== MENU ===============");
        System.out.println("1. Alta Cliente");
        System.out.println("2. Alta Vehiculo");
        System.out.println("3. Listar Vehiculos Disponibles");
        System.out.println("4. Realizar Reserva");
        System.out.println("5. Listar Clientes");
        System.out.println("6. Listar Vehiculos");
        System.out.println("7. Listar Reservas");
        System.out.println("8. Elimiar de registro de BD");
        System.out.println("9. Modificar registro de BD");
        System.out.println("0. Salir");
        System.out.println("\nElegir una opcion:");
        return sc.nextInt();

    }
    //TODO RECORDAR QUE LOS ATRIBUTOS DEL STRING SQL PUEDEN CAMBIAR
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
    //TODO RECORDAR QUE LOS ATRIBUTOS DEL STRING SQL PUEDEN CAMBIAR
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

            //TODO FALTA ARREGLAR QUE LA MATRICULA ES FOREAN KEY DE VEHICULO. ##ARREGLADO##


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

            if (tipo.equalsIgnoreCase("C")) {
                System.out.print("Tipo (Pequeño, Familiar, Deportivo): ");
                TipoCoche tc = TipoCoche.valueOf(sc.nextLine());
                System.out.print("Plazas: ");
                int plazas = Integer.parseInt(sc.nextLine());

                if (GestorFlota.ejecutarAltaCoche(new Coche(mat, mar, mod, true, tc, plazas))) {
                    System.out.println("Coche registrado.");

                    String sqlC = "INSERT INTO coche (matricula, tipoCoche, nPlazas) VALUES (?, ?, ?)";

                    try (Connection conn = GestorConexion.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sqlC)) {

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

                    String sqlF = "INSERT INTO furgoneta (matricula, tipo, capacidad) VALUES (?, ?, ?)";

                    try (Connection conn = GestorConexion.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sqlF)) {
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

    //TODO RECORDAR QUE LOS ATRIBUTOS DEL STRING PUEDEN CAMBIAR SQL
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

            String sql = "INSERT INTO reservar (dni, matricula, fechaInicio, fechFin) VALUES (?, ?, ?, ?)";

            try (Connection conn = GestorConexion.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, cRes.getDni());
                pstmt.setString(2, vRes.getMatricula());
                pstmt.setString(3, r.getFechaInicio().toString());
                pstmt.setString(4, r.getFechaFin().toString());
                pstmt.executeUpdate();

                System.out.println("Reserva insertada en la base de datos.");

            }catch (SQLException e) {
                System.out.println( "Error al insertar en la base de datos: " + e.getMessage());
            }

        } else {
            System.out.println("ERROR: Datos inválidos o vehículo no disponible.");
        }

    }

    //TODO RECORDAR QUE LOS ATRIBUTOS DEL STRING PUEDEN CAMBIAR SQL
    public static void menuModificar(){
        System.out.println("==== MODIFICAR DATOS ====");
        System.out.println("¿Que quieres modificar? (Cliente, Vehiculo, Reserva)");
        String opcion = sc.nextLine();
        switch (opcion) {
            case "Cliente":
                System.out.println("DNI del cliente a modificar:");
                String dni = sc.nextLine();
                System.out.println("Nuevo nombre: ");
                String nombreNuevo = sc.nextLine();
                System.out.println("Nuevo telefono: ");
                String telefonoNuevo = sc.nextLine();

                String sqlC = "UPDATE cliente SET nombre = ?, telefono = ? WHERE dni = ?";

                try (Connection conn = GestorConexion.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sqlC)) {

                    pstmt.setString(1, nombreNuevo);
                    pstmt.setString(2, telefonoNuevo);
                    pstmt.setString(3, dni);
                    pstmt.executeUpdate();

                    System.out.println("Cliente modificado.");

                }catch (SQLException e) {
                    System.out.println( "Error al insertar en la base de datos: " + e.getMessage());
                }
                break;

            case "Vehiculo":
                System.out.println("Matricula del vehiculo a modificar:");
                String matricula = sc.nextLine();
                System.out.println("Nuevo marca: ");
                String marcaNuevo = sc.nextLine();
                System.out.println("Nuevo modelo: ");
                String modeloNuevo = sc.nextLine();
                System.out.println("Nuevo disponibilidad: ");
                boolean disponibleNuevo = sc.nextBoolean();
                sc.nextLine();


                String sqlV = "UPDATE vehiculo SET marca = ?, modelo = ?, disponibilidad = ? WHERE matricula = ?";

                try (Connection conn = GestorConexion.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sqlV)) {

                    pstmt.setString(1, marcaNuevo);
                    pstmt.setString(2, modeloNuevo);
                    pstmt.setBoolean(3, disponibleNuevo);
                    pstmt.setString(4, matricula);
                    pstmt.executeUpdate();

                    System.out.println("Vehiculo modificado.");

                }catch (SQLException e) {
                    System.out.println( "Error al insertar en la base de datos: " + e.getMessage());
                }

                System.out.println("Es un Coche o una Furgoneta? (C/F)");
                String tipo = sc.nextLine();
                if (tipo.equalsIgnoreCase("C")) {
                    System.out.print("Nuevo tipo de coche: ");
                    TipoCoche tipoNuevo = TipoCoche.valueOf(sc.nextLine());
                    System.out.print("Nuevo numero de plazas: ");
                    int plazasNuevo = sc.nextInt();
                    sc.nextLine();

                    String sqlCoche = "UPDATE coche SET tipoCoche = ?, nPlazas = ? WHERE matricula = ?";

                    try (Connection conn = GestorConexion.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sqlCoche)) {

                        pstmt.setString(1, tipoNuevo.name());
                        pstmt.setInt(2, plazasNuevo);
                        pstmt.setString(3, matricula);
                        pstmt.executeUpdate();

                        System.out.println("Coche modificado.");

                    }catch (SQLException e) {
                        System.out.println( "Error al insertar en la base de datos: " + e.getMessage());
                    }
                }else if (tipo.equalsIgnoreCase("F")) {
                    System.out.println("Nuevo tipo de furgoneta: (Carga/Pasajeros)");
                    String tipoFurgoneta = sc.nextLine();
                    System.out.print("Nuevo numero de pasajeros: ");
                    int pasajerosNuevo = sc.nextInt();
                    sc.nextLine();

                    String sqlFurgoneta = "UPDATE furgoneta SET tipo = ?, capacidad = ? WHERE matricula = ?";

                    try (Connection conn = GestorConexion.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sqlFurgoneta)) {

                        pstmt.setString(1, tipoFurgoneta);
                        pstmt.setInt(2, pasajerosNuevo);
                        pstmt.setString(3, matricula);
                        pstmt.executeUpdate();

                        System.out.println("Furgoneta modificada.");

                    }catch (SQLException e) {
                        System.out.println( "Error al insertar en la base de datos: " + e.getMessage());
                    }
                }
                break;

            case "Reserva":
                System.out.println("DNI del Cliente: ");
                String dniReserva = sc.nextLine();
                Cliente cReserva = GestorReservas.buscarCliente(dniReserva);
                String fechaInicio = LocalDate.now().toString();
                System.out.println("Cuantos dias desde hoy quieres reservar? ");
                int diasReserva = sc.nextInt();
                sc.nextLine();
                LocalDate fechaFin = LocalDate.now().plusDays(diasReserva);

                String sqlR = "UPDATE reservar SET fechaInicio = ?, fechFin = ? WHERE dni = ?";

                try (Connection conn = GestorConexion.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sqlR)) {

                    pstmt.setString(1, fechaInicio);
                    pstmt.setString(2, fechaFin.toString());
                    pstmt.setString(3, dniReserva);
                    pstmt.executeUpdate();

                    System.out.println("Reserva modificada.");

                }catch (SQLException e) {
                    System.out.println( "Error al insertar en la base de datos: " + e.getMessage());
                }
        }
    }

    //TODO RECORDAR QUE LOS ATRIBUTOS DEL STRING SQL PUEDEN CAMBIAR
    public static void menuEliminar(){
        System.out.println("==== ELIMINAR DATOS ====");
        System.out.println("¿Que quieres eliminar? (Cliente, Vehiculo, Reserva)");
        String opcion = sc.nextLine();
        switch (opcion) {
            case "Cliente":
                System.out.println("DNI del cliente a eliminar:");
                String dni = sc.nextLine();

                String sqlResCli = "delete from reservar where dni = ?";
                try (Connection conn = GestorConexion.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sqlResCli)) {
                    pstmt.setString(1, dni);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Error al eliminar reservas del cliente: " + e.getMessage());
                }

                String sqlC = "delete from cliente where dni = ?";

                try (Connection conn = GestorConexion.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sqlC)) {

                    pstmt.setString(1, dni);
                    pstmt.executeUpdate();

                    System.out.println("Cliente eliminado.");

                }catch (SQLException e) {
                    System.out.println( "Error al insertar en la base de datos: " + e.getMessage());
                }
                break;

            case "Vehiculo":
                System.out.println("Matricula del vehiculo a eliminar:");
                String matricula = sc.nextLine();

                String sqlResVeh = "delete from reservar where matricula = ?";
                try (Connection conn = GestorConexion.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sqlResVeh)) {
                    pstmt.setString(1, matricula);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Error al eliminar reservas del vehiculo: " + e.getMessage());
                }

                System.out.println("Es un Coche o una Furgoneta? (C/F)");
                String tipo = sc.nextLine();
                if (tipo.equalsIgnoreCase("C")) {

                    String sqlCoche = "delete from coche where matricula = ?";

                    try (Connection conn = GestorConexion.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sqlCoche)) {

                        pstmt.setString(1, matricula);
                        pstmt.executeUpdate();

                        System.out.println("Coche eliminado.");

                    }catch (SQLException e) {
                        System.out.println( "Error al insertar en la base de datos: " + e.getMessage());
                    }
                }else if (tipo.equalsIgnoreCase("F")) {

                    String sqlFurgoneta = "delete from furgoneta where matricula = ?";

                    try (Connection conn = GestorConexion.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sqlFurgoneta)) {

                        pstmt.setString(1, matricula);
                        pstmt.executeUpdate();

                        System.out.println("Furgoneta eliminada.");

                    }catch (SQLException e) {
                        System.out.println( "Error al insertar en la base de datos: " + e.getMessage());
                    }
                }

                String sqlV = "delete from vehiculo where matricula = ?";

                try (Connection conn = GestorConexion.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sqlV)) {

                    pstmt.setString(1, matricula);
                    pstmt.executeUpdate();

                    System.out.println("Vehiculo eliminado.");
                
                }catch (SQLException e) {
                    System.out.println( "Error al insertar en la base de datos: " + e.getMessage());
                }

                break;

            case "Reserva":
                System.out.println("DNI del Cliente: ");
                String dniReserva = sc.nextLine();

                String sqlR = "delete from reservar where dni = ?";

                try (Connection conn = GestorConexion.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sqlR)) {

                    pstmt.setString(1, dniReserva);
                    pstmt.executeUpdate();

                    System.out.println("Reserva eliminada.");

                }catch (SQLException e) {
                    System.out.println( "Error al insertar en la base de datos: " + e.getMessage());
                }
        }
    }
}

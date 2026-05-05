package app;

import logica.GestorReservas;
import model.*;

import java.util.*;

public class Main {


    public static void main(String[] args) {

        cargarDatos();
        int opcion;

        do {
            opcion = mostrarMenu();
            GestorReservas.sc.nextLine();
            switch (opcion) {
                case 1:
                    crearCliente();
                    break;
                case 2:
                    crearVehiculo();
                    break;
                case 3:
                    listarVehiculosDisponibles();
                    break;
                case 4:
                    GestorReservas.pedirDatosReserva();
                    break;
                case 5:
                    listarClientes();
                    break;
                case 6:
                    System.out.println("Saliendo del programa...");
                    GestorReservas.guardarDatos();
                    System.out.println("Datos guardados correctamente.");
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
        return GestorReservas.sc.nextInt();

    }

    public static void crearCliente() {
        System.out.println("====ALTA DE NUEVO CLIENTE====");
        System.out.println("DNI: ");
        String dni = GestorReservas.sc.nextLine();

        System.out.println("Nombre: ");
        String nombre = GestorReservas.sc.nextLine();

        System.out.println("Tlf: ");
        String telefono = GestorReservas.sc.nextLine();

        GestorReservas.clientes.add(new Cliente(dni, nombre, telefono));
    }

    public static void crearVehiculo() {
        System.out.println("====ALTA DE NUEVO VEHICULO====");
        System.out.println("¿Coche (C) o Furgoneta (F)?");
        String tipo = GestorReservas.sc.nextLine();

        System.out.println("Introduzca los datos del coche:");
        System.out.println("Matricula: ");
        String matricula = GestorReservas.sc.nextLine();

        System.out.println("Marca: ");
        String marca = GestorReservas.sc.nextLine();

        System.out.println("Modelo: ");
        String modelo = GestorReservas.sc.nextLine();


        if (tipo.equalsIgnoreCase("C")) {
            try {
                System.out.println("Tipo de coche (Pequeño, Familiar, Deportivo):");
                String tipoCoche = GestorReservas.sc.nextLine();
                TipoCoche tipoC = TipoCoche.valueOf(tipoCoche);

                System.out.println("Numero de plazas: ");
                int numPlazas = Integer.parseInt(GestorReservas.sc.nextLine());

                GestorReservas.flota.add(new Coche(matricula, marca, modelo, true, tipoC, numPlazas));
                System.out.println("Coche registrado correctamente.");
            } catch (NumPlazasException e) {
                System.out.println("ERROR: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("ERROR: debe introducir un dígito");
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR: el tipo de coche no existe.");
            }
        } else if (tipo.equalsIgnoreCase("F")) {
            try {
                System.out.println("¿Es de carga? (true/false)");
                boolean esDeCarga = GestorReservas.sc.nextBoolean();
                GestorReservas.sc.nextLine();
                int cap;

                if (esDeCarga) {
                    System.out.print("Capacidad en kilos: ");
                    cap = GestorReservas.sc.nextInt();

                } else {
                    System.out.print("Número de personas (2-7): ");
                    cap = GestorReservas.sc.nextInt();
                }
                GestorReservas.sc.nextLine();

                GestorReservas.flota.add(new Furgoneta(matricula, marca, modelo, true, esDeCarga, cap));
                System.out.println("Furgoneta registrada correctamente.");
            } catch (InputMismatchException e) {
                System.out.println("ERROR: debes introducir el formato correcto (true/false)");
                GestorReservas.sc.nextLine();
            } catch (NumPlazasException e){
                System.out.println("ERROR: " +e.getMessage());
            }

        } else {
            System.out.println("Opción no válida. Saliendo del alta del vehículo.");
        }
    }

    public static void listarClientes() {
        boolean encontrado = false;

        for (Cliente c : GestorReservas.clientes) {
            System.out.println(c);
            System.out.println("----------------------------------------");
            encontrado = true;
        }

        if (!encontrado) {
            System.out.println("No se han encontrado clientes.");
        }
    }

    public static void listarVehiculosDisponibles() {
        boolean encontrado = false;

        for (Vehiculo v : GestorReservas.flota) {
            if (v.isDisponible()) {
                System.out.println(v);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No hay vehículos disponibles");
        }

    }

    public static void cargarDatos() {
        GestorReservas.flota = GestorReservas.gestor.cargarVehiculos();
        GestorReservas.clientes = GestorReservas.gestor.cargarClientes();
        Reserva.setNextId(GestorReservas.gestor.calcularSiguienteIdReserva());
    }

}

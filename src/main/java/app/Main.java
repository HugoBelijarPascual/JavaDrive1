package app;

import logica.GestorPersistencia;
import model.*;

import java.time.LocalDate;
import java.util.*;

public class Main {


    private static List<Vehiculo> flota = new ArrayList<>();
    private static List<Cliente> clientes = new ArrayList<>();

    private static GestorPersistencia gestor = new GestorPersistencia();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        cargarDatos();
        int opcion;

        do {
            opcion = mostrarMenu();
            sc.nextLine();
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
                    pedirDatosReserva();
                    break;
                case 5:
                    listarClientes();
                    break;
                case 6:
                    System.out.println("Saliendo del programa...");
                    guardarDatos();
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
        return sc.nextInt();

    }

    public static void pedirDatosReserva(){
        System.out.println("Ingrese el DNI del cliente:");
        String dni = sc.nextLine();
        Cliente c = buscarCliente(dni);


        System.out.println("Matrícula del vehículo: ");
        String matricula = sc.nextLine();
        Vehiculo v = buscarVehiculo(matricula);

        if (c == null || v == null || !v.isDisponible()) {
            System.out.println("ERROR: Cliente no existe o vehículo no disponible.");
        } else {
            System.out.println("¿Cuántos días de alquiler?");
            int dias = sc.nextInt();
            sc.nextLine();

            realizarReserva(c, v, LocalDate.now(), LocalDate.now().plusDays(dias));
        }
    }

    public static void crearCliente() {
        System.out.println("====ALTA DE NUEVO CLIENTE====");
        System.out.println("DNI: ");
        String dni = sc.nextLine();

        System.out.println("Nombre: ");
        String nombre = sc.nextLine();

        System.out.println("Tlf: ");
        String telefono = sc.nextLine();

        clientes.add(new Cliente(dni, nombre, telefono));
    }

    public static void crearVehiculo() {
        System.out.println("====ALTA DE NUEVO VEHICULO====");
        System.out.println("¿Coche (C) o Furgoneta (F)?");
        String tipo = sc.nextLine();

        System.out.println("Introduzca los datos del coche:");
        System.out.println("Matricula: ");
        String matricula = sc.nextLine();

        System.out.println("Marca: ");
        String marca = sc.nextLine();

        System.out.println("Modelo: ");
        String modelo = sc.nextLine();


        if (tipo.equalsIgnoreCase("C")) {
            try {
                System.out.println("Tipo de coche (Pequeño, Familiar, Deportivo):");
                String tipoCoche = sc.nextLine();
                TipoCoche tipoC = TipoCoche.valueOf(tipoCoche);

                System.out.println("Numero de plazas: ");
                int numPlazas = Integer.parseInt(sc.nextLine());

                flota.add(new Coche(matricula, marca, modelo, true, tipoC, numPlazas));
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
                boolean esDeCarga = sc.nextBoolean();
                sc.nextLine();
                int cap;

                if (esDeCarga) {
                    System.out.print("Capacidad en kilos: ");
                    cap = sc.nextInt();

                } else {
                    System.out.print("Número de personas (2-7): ");
                    cap = sc.nextInt();
                }
                sc.nextLine();

                flota.add(new Furgoneta(matricula, marca, modelo, true, esDeCarga, cap));
                System.out.println("Furgoneta registrada correctamente.");
            } catch (InputMismatchException e) {
                System.out.println("ERROR: debes introducir el formato correcto (true/false)");
                sc.nextLine();
            } catch (NumPlazasException e){
                System.out.println("ERROR: " +e.getMessage());
            }

        } else {
            System.out.println("Opción no válida. Saliendo del alta del vehículo.");
        }
    }

    public static Cliente buscarCliente(String dni) {
        for (Cliente c : clientes) {
            if (c.getDni().equals(dni)) {
                return c;
            }
        }
        return null;
    }

    public static Vehiculo buscarVehiculo(String matricula) {
        for (Vehiculo v : flota) {
            if (v.getMatricula().equals(matricula)) {
                return v;
            }
        }
        return null;
    }

    public static void listarClientes() {
        boolean encontrado = false;

        for (Cliente c : clientes) {
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

        for (Vehiculo v : flota) {
            if (v.isDisponible()) {
                System.out.println(v);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No hay vehículos disponibles");
        }

    }

    public static void realizarReserva(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio, LocalDate fechaFin) {
        vehiculo.setDisponible(false);
        Reserva reserva = new Reserva(cliente, vehiculo, fechaInicio, fechaFin);
        exportarTicket(reserva);
        guardarDatos();
    }

    public static void cargarDatos() {
        flota = gestor.cargarVehiculos();
        clientes = gestor.cargarClientes();
    }
    public static void guardarDatos() {
        gestor.guardarVehiculos(flota);
        gestor.guardarClientes(clientes);
    }

    public static void exportarTicket(Reserva reserva) {

        gestor.exportarTicket(reserva);
    }
}

package logica;

import app.Main;
import model.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class GestorFlota {
    public static List<Vehiculo> flota = new ArrayList<>();

    public static void crearVehiculo() {
        System.out.println("====ALTA DE NUEVO VEHICULO====");
        System.out.println("¿Coche (C) o Furgoneta (F)?");
        String tipo = Main.sc.nextLine();

        System.out.println("Introduzca los datos del coche:");
        System.out.println("Matricula: ");
        String matricula = Main.sc.nextLine();

        System.out.println("Marca: ");
        String marca = Main.sc.nextLine();

        System.out.println("Modelo: ");
        String modelo = Main.sc.nextLine();

        if (tipo.equalsIgnoreCase("C")) {
            System.out.println("Tipo de coche (Pequeño, Familiar, Deportivo):");
            String tipoCoche = Main.sc.nextLine();
            System.out.println("Numero de plazas: ");
            String numPlazas = Main.sc.nextLine();
            ejecutarAltaCoche(matricula, marca, modelo, tipoCoche, numPlazas);
        } else if (tipo.equalsIgnoreCase("F")) {
            try {
                System.out.println("¿Es de carga? (true/false)");
                boolean esDeCarga = Main.sc.nextBoolean();
                Main.sc.nextLine();
                int cap;
                if (esDeCarga) {
                    System.out.print("Capacidad en kilos: ");
                    cap = Main.sc.nextInt();
                } else {
                    System.out.print("Número de personas (2-7): ");
                    cap = Main.sc.nextInt();
                }
                Main.sc.nextLine();
                ejecutarAltaFurgoneta(matricula, marca, modelo, esDeCarga, cap);
            } catch (InputMismatchException e) {
                System.out.println("ERROR: debes introducir el formato correcto (true/false)");
                Main.sc.nextLine();
            }
        } else {
            System.out.println("Opción no válida. Saliendo del alta del vehículo.");
        }
    }

    public static void ejecutarAltaCoche(String mat, String mar, String mod, String tipoC, String plazas) {
        try {
            TipoCoche tipo = TipoCoche.valueOf(tipoC);
            int numPlazas = Integer.parseInt(plazas);
            GestorFlota.flota.add(new Coche(mat, mar, mod, true, tipo, numPlazas));
            System.out.println("Coche registrado correctamente.");
        } catch (NumPlazasException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("ERROR: debe introducir un dígito");
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: el tipo de coche no existe.");
        }
    }

    public static void ejecutarAltaFurgoneta(String mat, String mar, String mod, boolean carga, int cap) {
        try {
            GestorFlota.flota.add(new Furgoneta(mat, mar, mod, true, carga, cap));
            System.out.println("Furgoneta registrada correctamente.");
        } catch (NumPlazasException e) {
            System.out.println("ERROR: " + e.getMessage());
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
}

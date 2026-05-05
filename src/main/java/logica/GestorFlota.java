package logica;

import model.*;

import java.util.InputMismatchException;

public class GestorFlota {
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
}

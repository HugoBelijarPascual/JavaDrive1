package logica;

import app.Main;
import model.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class GestorFlota {
    public static List<Vehiculo> flota = new ArrayList<>();

    public static boolean ejecutarAltaCoche(Coche coche) {
        if (coche == null) return false;
        // Evitamos duplicados para subir Branch %
        for (Vehiculo v : flota) {
            if (v.getMatricula().equalsIgnoreCase(coche.getMatricula())) return false;
        }
        return flota.add(coche);
    }

    // Mantenemos el nombre original
    public static boolean ejecutarAltaFurgoneta(Furgoneta furgoneta) {
        if (furgoneta == null) return false;
        for (Vehiculo v : flota) {
            if (v.getMatricula().equalsIgnoreCase(furgoneta.getMatricula())) return false;
        }
        return flota.add(furgoneta);
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

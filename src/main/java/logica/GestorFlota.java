package logica;

import app.Main;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class GestorFlota {
    public static List<Vehiculo> flota = new ArrayList<>();

    private static TipoCoche parseTipoCoche(String raw) {
        if (raw == null) throw new IllegalArgumentException("tipoCoche nulo");
        String trimmed = raw.trim();
        if (trimmed.isEmpty()) throw new IllegalArgumentException("tipoCoche vacío");

        String lower = trimmed.toLowerCase();
        String noAccents = Normalizer.normalize(lower, Normalizer.Form.NFD).replaceAll("\\p{M}", "");

        if (lower.equals("pequeño") || noAccents.equals("pequeno")) return TipoCoche.Pequeño;
        if (lower.equals("familiar")) return TipoCoche.Familiar;
        if (lower.equals("deportivo")) return TipoCoche.Deportivo;

        try {
            return TipoCoche.valueOf(trimmed);
        } catch (IllegalArgumentException ex) {

            String cap = trimmed.substring(0,1).toUpperCase() + trimmed.substring(1).toLowerCase();
            return TipoCoche.valueOf(cap);
        }
    }

    public static boolean ejecutarAltaCoche(Coche coche) {
        if (coche == null) return false;
        for (Vehiculo v : flota) {
            if (v.getMatricula().equalsIgnoreCase(coche.getMatricula())) return false;
        }
        return flota.add(coche);
    }

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
    //TODO RECORDAR QUE LOS ATRIBUTOS DEL STRING SQL PUEDEN CAMBIAR
    public static List<Vehiculo> ListarFlotaBD() {
        List<Vehiculo> lista = new ArrayList<>();

        String sqlC = "SELECT c.tipoCoche, c.nPlazas, v.* FROM coche c join vehiculo v on c.matricula = v.matricula";

        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sqlC);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()){
                String tipo = rs.getString("tipoCoche");
                String nPlazas = rs.getString("nPlazas");
                String matricula = rs.getString("matricula");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                Boolean disponible = rs.getBoolean("disponibilidad");

                Coche c = new Coche(matricula, marca, modelo, disponible, parseTipoCoche(tipo), Integer.parseInt(nPlazas));
                lista.add(c);
            }

        }catch (SQLException e) {
            System.out.println( "Error al insertar en la base de datos: " + e.getMessage());
        }

        String sqlF = "SELECT f.tipo, f.capacidad, v.* FROM furgoneta f JOIN vehiculo v ON f.matricula = v.matricula";

        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sqlF);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()){
                Boolean carga = rs.getString("tipo").equalsIgnoreCase("Carga");
                String capacidad = rs.getString("capacidad");
                String matricula = rs.getString("matricula");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                Boolean disponible = rs.getBoolean("disponibilidad");

                Furgoneta f = new Furgoneta(matricula, marca, modelo, disponible,carga, Integer.parseInt(capacidad));
                lista.add(f);
            }

        }catch (SQLException e) {
            System.out.println( "Error al insertar en la base de datos: " + e.getMessage());
        }

        System.out.println("Se han cargado los vehiculos");
        System.out.println("----------------------------------------");

        return lista;
    }

//
}

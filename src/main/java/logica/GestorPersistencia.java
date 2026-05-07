package logica;

import model.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GestorPersistencia {
    public static GestorPersistencia gestor = new GestorPersistencia();

    public List<Vehiculo> cargarVehiculos() {
        List<Vehiculo> lista = new ArrayList<>();
        File f = new File("vehiculos.txt");
        if (!f.exists()) return lista; // Evita entrar al catch innecesariamente

        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.isEmpty()) continue;
                String[] campos = line.split(";");
                if (campos[0].equals("COCHE")) {
                    lista.add(new Coche(campos[1], campos[2], campos[3],
                            Boolean.parseBoolean(campos[4]), TipoCoche.valueOf(campos[5]),
                            Integer.parseInt(campos[6])));
                } else if (campos[0].equals("FURGONETA")) {
                    lista.add(new Furgoneta(campos[1], campos[2], campos[3],
                            Boolean.parseBoolean(campos[4]), Boolean.parseBoolean(campos[5]),
                            Integer.parseInt(campos[6])));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar los vehiculos");
        }
        return lista;
    }

    public void guardarVehiculos(List<Vehiculo> flota) {
        try (PrintWriter pw = new PrintWriter("vehiculos.txt")) {
            for (Vehiculo v : flota) {
                if (v instanceof Coche c) {
                    pw.println("COCHE;" + c.getMatricula() + ";" + c.getMarca() + ";" + c.getModelo() + ";" + c.isDisponible() + ";" + c.getTipoCoche() + ";" + c.getNumPlazas());
                } else if (v instanceof Furgoneta f) {
                    pw.println("FURGONETA;" + f.getMatricula() + ";" + f.getMarca() + ";" + f.getModelo() + ";" + f.isDisponible() + ";" + f.isEsDeCarga() + ";" + f.getCapacidad());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> cargarClientes() {
        List<Cliente> lista = new ArrayList<>();
        File f = new File("clientes.txt");
        if (!f.exists()) return lista;

        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.isEmpty()) continue;
                String[] campos = line.split(";");
                lista.add(new Cliente(campos[0], campos[1], campos[2]));
            }
        } catch (Exception e) {
            System.out.println("Error al cargar los clientes");
        }
        return lista;
    }

    public void guardarClientes(List<Cliente> clientes) {
        try (PrintWriter pw = new PrintWriter("clientes.txt")) {
            for (Cliente c : clientes) {
                pw.println(c.getDni() + ";" + c.getNombre() + ";" + c.getTelefono());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportarTicket(Reserva reserva) {
        String carpeta = "Reservas";
        File dir = new File(carpeta);
        if (!dir.exists()) dir.mkdirs();

        String nombreArchivo = "reservas_" + reserva.getIdReserva() + ".txt";
        File destino = new File(dir, nombreArchivo);
        try (PrintWriter pw = new PrintWriter(destino)) {
            pw.print(reserva.GenerarLineaTicket());
        } catch (Exception e) {
            System.out.println("Error al exportar el ticket");
        }
    }

    public int calcularSiguienteIdReserva() {
        String carpeta = "Reservas";
        File dir = new File(carpeta);
        if (!dir.exists()) {
            dir.mkdirs();
            return 1;
        }
        int maxId = 0;
        Pattern p = Pattern.compile("reservas_([0-9]+)\\.txt");
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                Matcher m = p.matcher(f.getName());
                if (m.matches()) {
                    int id = Integer.parseInt(m.group(1));
                    if (id > maxId) maxId = id;
                }
            }
        }
        return maxId + 1;
    }

}

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
    public List<Vehiculo> cargarVehiculos() {
        List<Vehiculo> lista = new ArrayList<>();

        try (Scanner sc = new Scanner(new File("vehiculos.txt"))){
            while (sc.hasNextLine()) {
                String [] campos = sc.nextLine().split(";");
                if (campos[0].equals("COCHE")) {
                    lista.add(new Coche(campos[1], campos[2], campos[3],
                            Boolean.parseBoolean(campos[4]), TipoCoche.valueOf(campos[5]),
                            Integer.parseInt(campos[6])));
                } else {
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
                    pw.println("COCHE;"+c.getMatricula()+";"+c.getMarca()+";"+c.getModelo()+";"+c.isDisponible()+";"+c.getTipoCoche()+";"+c.getNumPlazas());
                } else if (v instanceof Furgoneta f) {
                    pw.println("FURGONETA;"+f.getMatricula()+";"+f.getMarca()+";"+f.getModelo()+";"+f.isDisponible()+";"+f.isEsDeCarga()+";"+f.getCapacidad());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Cliente> cargarClientes() {
        List<Cliente> lista = new ArrayList<>();

        try (Scanner sc = new Scanner(new File("clientes.txt"))){
            while (sc.hasNextLine()) {
                String [] campos = sc.nextLine().split(";");
                lista.add(new Cliente(campos[0], campos[1], campos[2]));
            }
        } catch (Exception e) {
            System.out.println("Error al cargar los vehiculos");

        }
        return lista;
    }

    public void guardarClientes(List<Cliente> clientes) {

        try (PrintWriter pw = new PrintWriter("clientes.txt")) {
            for (Cliente c : clientes) {
               pw.println(c.getDni()+";"+c.getNombre()+";"+c.getTelefono());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void exportarTicket(Reserva reserva) {
        String carpeta = "Reservas";
        File dir = new File(carpeta);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String nombreArchivo = "reservas_"+ reserva.getIdReserva() +".txt";
        File destino = new File(dir, nombreArchivo);
        try (PrintWriter pw = new PrintWriter(destino)) {
            pw.print(reserva.GenerarLineaTicket());
            System.out.println("Ticket generado con éxito: " + destino.getPath());
        } catch (Exception e) {
            System.out.println("Error al exportar el ticket: " + e.getMessage());
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
                    try {
                        int id = Integer.parseInt(m.group(1));
                        if (id > maxId) maxId = id;
                    } catch (NumberFormatException ignore) {
                    }
                }
            }
        }
        return maxId + 1;
    }

}

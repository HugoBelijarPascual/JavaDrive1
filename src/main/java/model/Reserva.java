package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reserva {

    private static int nextId = 1;
    private int idReserva;
    private Cliente cliente;
    private Vehiculo vehiculo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    public Reserva(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio, LocalDate fechaFin) {
        this.idReserva = nextId++;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    public static void setNextId(int next) {
        if (next > 0) {
            nextId = next;
        }
    }
    public String GenerarLineaTicket(){
        long totalDias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        return
                "============================================"+"\n"+
                        " CONTRATO DE ALQUILER - JAVADRIVE"+"\n"+
                        "============================================"+"\n"+
                        "ID RESERVA: "+idReserva+"\n"+
                        "FECHA EMISIÓN: "+fechaInicio+"\n"+
                        "--------------------------------------------"+"\n"+
                        "DATOS DEL CLIENTE:"+"\n"+
                        "Nombre: "+ cliente.getNombre()+"\n"+
                        "DNI: "+ cliente.getDni()+"\n"+
                        "Teléfono: "+cliente.getTelefono()+"\n"+
                        "--------------------------------------------"+"\n"+
                        "DATOS DEL VEHÍCULO: "+"\n"+
                        "Marca y Modelo: "+vehiculo.getMarca()+" "+vehiculo.getModelo()+"\n"+
                        "Matrícula: "+vehiculo.getMatricula()+"\n"+
                        "\nDetalles: " +vehiculo.obtenerDetalles()+
                        "--------------------------------------------"+"\n"+
                        "PERIODO DE ALQUILER: "+"\n"+
                        "Fecha de recogida: "+fechaInicio+"\n"+
                        "Fecha de devolución: "+fechaFin+"\n"+
                        "Total días: "+totalDias+"\n"+
                        "--------------------------------------------"+"\n"+
                        "ESTADO: Confirmado y Pendiente de pago";
    }
    @Override
    public String toString() {
        String clienteStr = (cliente != null)
                ? cliente.getNombre() + " (" + cliente.getDni() + ")"
                : "null";
        String vehiculoStr = (vehiculo != null)
                ? vehiculo.getMarca() + " " + vehiculo.getModelo() + " [" + vehiculo.getMatricula() + "]"
                : "null";
        return "Reserva{" +
                "id=" + idReserva +
                ", cliente=" + clienteStr +
                ", vehiculo=" + vehiculoStr +
                ", inicio=" + fechaInicio +
                ", fin=" + fechaFin +
                '}';
    }
    public int getIdReserva() {
        return idReserva;
    }
    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Vehiculo getVehiculo() {
        return vehiculo;
    }
    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

}

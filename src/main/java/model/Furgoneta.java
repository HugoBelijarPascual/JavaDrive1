package model;

public class Furgoneta extends Vehiculo{

    private boolean esDeCarga;
    private int capacidad;
    public Furgoneta(String matricula, String marca, String modelo, boolean disponible, boolean esDeCarga, int capacidad) {
        super(matricula, marca, modelo, disponible);
        this.esDeCarga = esDeCarga;
        if (esDeCarga) {
// Kilos
            this.capacidad = capacidad;
        } else {
// Personas
            if (numPlazasEsCorrecto(capacidad)){
                this.capacidad = capacidad;
            } else {
                throw new NumPlazasException("El numero de plazas debe estar entre 2 y 7");
            }
        }
    }
    public String obtenerDetalles() {
        if (esDeCarga) {
            return " Furgoneta de Carga ["+capacidad+" kg]\n";
        } else {
            return " Furgoneta de Pasajeros ["+capacidad+" personas]\n";
        }
    }
    private boolean numPlazasEsCorrecto(int numPlazas) {
        return numPlazas >= 2 && numPlazas <= 7;
    }
    public boolean isEsDeCarga() {
        return esDeCarga;
    }
    public void setEsDeCarga(boolean esDeCarga) {
        this.esDeCarga = esDeCarga;
    }
    public int getCapacidad() {
        return capacidad;
    }
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

}

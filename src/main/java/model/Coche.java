package model;

public class Coche extends Vehiculo{

    Enum tipoCoche;
    int numPlazas;
    public Coche(String matricula, String marca, String modelo, boolean
            disponible, Enum tipoCoche, int numPlazas) {
        super(matricula, marca, modelo, disponible);
        this.tipoCoche = tipoCoche;
        if(numPlazasEsCorrecto(numPlazas)) {
            this.numPlazas = numPlazas;
        } else {
            throw new NumPlazasException("El numero de plazas debe estar entre 2 y 7");
        }
    }
    public String obtenerDetalles() {
        return "\nCoche ["+tipoCoche+"]\nNº Plazas: " +
                numPlazas +"\n";
    }
    public boolean numPlazasEsCorrecto(int numPlazas) {
        return numPlazas >= 2 && numPlazas <= 7;
    }

    public Enum getTipoCoche() {
        return tipoCoche;
    }

    public int getNumPlazas() {
        return numPlazas;
    }

}

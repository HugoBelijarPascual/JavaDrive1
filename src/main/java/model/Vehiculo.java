package model;

public abstract class Vehiculo {

    private String matricula;
    private String marca;
    private String modelo;
    private boolean disponible;
    public Vehiculo(String matricula, String marca, String modelo, boolean
            disponible) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.disponible = disponible;
        disponible = true;
    }
    @Override
    public String toString() {
        if (disponible) {
            return "[" +matricula+"] " + marca + " " + modelo + " | Disponible";
        } else {
            return "[" +matricula+"]" + marca + " " + modelo + " | No disponible";
        }

    }

    public abstract String obtenerDetalles();

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public boolean isDisponible() {
        return disponible;
    }

}

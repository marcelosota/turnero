/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.dinardap.turno.dto;

/**
 *
 * @author christian.gaona
 */
public class HorarioDTO {

    private String hora;
    private Integer numeroVentanillas;
    private Integer turnosDisponibles;

    public HorarioDTO() {
    }

    public HorarioDTO(String hora, Integer numeroVentanillas, Integer turnosDisponibles) {
        this.hora = hora;
        this.numeroVentanillas = numeroVentanillas;
        this.turnosDisponibles = turnosDisponibles;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Integer getNumeroVentanillas() {
        return numeroVentanillas;
    }

    public void setNumeroVentanillas(Integer numeroVentanillas) {
        this.numeroVentanillas = numeroVentanillas;
    }

    public Integer getTurnosDisponibles() {
        return turnosDisponibles;
    }

    public void setTurnosDisponibles(Integer turnosDisponibles) {
        this.turnosDisponibles = turnosDisponibles;
    }

}

package ec.gob.dinardap.turno.servicio;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.servicio.GenericService;
import ec.gob.dinardap.turno.modelo.Turno;
import java.util.Date;
import java.util.List;

@Local
public interface TurnoServicio extends GenericService<Turno, Integer> {

    public void crearTurno(Turno turno);

    public Turno buscarTurno(String validador);
    
    public Turno buscarTurno(String validador, Integer registroMercantilId);

    public Boolean actualizarAtendido(Turno turno);

    public Integer getTurnosDisponibles(Integer ventanillas, Date dia, String hora, Integer registroMercantilId);

    public Boolean validacionDiariaPersona(Turno turno);

    public Turno getTurno(Turno turno);

    public List<Turno> getTurnos(Integer registroMercantilId, Date dia, String hora);

}

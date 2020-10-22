package ec.gob.dinardap.turno.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.turno.dto.AgendadaAtendidasDto;
import ec.gob.dinardap.turno.dto.TurnosAgendadosDto;
import ec.gob.dinardap.turno.modelo.Turno;

@Local
public interface TurnoDao extends GenericDao<Turno, Integer> {

    public List<AgendadaAtendidasDto> reporteAgendamiento(Integer registroMercantilId, String fecha, Short estadoAgendado, Short estadoAtendido);

    public Integer getTurnosDisponibles(Integer ventanillas, Date dia, String hora, Integer planificacionId);

    public List<Turno> getTurnos(Turno turno);

    public List<Turno> getTurnos(Integer registroMercantilId, Date dia, String hora);
    
    public List<TurnosAgendadosDto> totalTurnos(Date fechaDesde, Date fechasHasta);

    public Integer getContador(Date fechaTurno, Integer registroMercantilId, String valor, Integer parametro, Integer cuota);

}

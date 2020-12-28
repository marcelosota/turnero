package ec.gob.dinardap.turno.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.turno.modelo.Atencion;

@Local
public interface AtencionDao extends GenericDao<Atencion, Integer> {

	public List<Atencion> obtenerAtencionParaTodaInstitucion();
	public List<Atencion> obtenerAtencionSuspensionPorInstitucionFecha(Integer registroMercantilId, Short atencionSuspension, Date fecha, Short tipoAtencionId);
	public List<Atencion> obtenerSuspensionTemporalPorInstitucionFecha(Integer registroMercantilId, Short atencionSuspension, Date fecha, List<Short> tipoAtencionId);
}

package ec.gob.dinardap.turno.dao.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ec.gob.dinardap.persistence.dao.ejb.GenericDaoEjb;
import ec.gob.dinardap.turno.dao.AtencionDao;
import ec.gob.dinardap.turno.modelo.Atencion;
import ec.gob.dinardap.util.constante.EstadoEnum;

@Stateless(name="AtencionDao")
public class AtencionDaoEjb extends GenericDaoEjb<Atencion, Integer> implements AtencionDao {

	public AtencionDaoEjb() {
		super(Atencion.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Atencion> obtenerAtencionParaTodaInstitucion() {
		Query query = em.createQuery("SELECT a FROM Atencion a WHERE "
                + "a.registroMercantil.registroMercantilId is null "
                + "AND a.estado =:estado");
        query.setParameter("estado", EstadoEnum.ACTIVO.getEstado());
        
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Atencion> obtenerAtencionSuspensionPorInstitucionFecha(Integer registroMercantilId, Short atencionSuspension, Date fecha, Short tipoAtencionId) {
		StringBuilder sql = new StringBuilder("SELECT a FROM Atencion a WHERE a.registroMercantil.registroMercantilId ");
		if(registroMercantilId != null)
			sql.append("= :registroMercantilId ");
		else
			sql.append("is null ");
		sql.append("AND a.atencionSuspension = :atencionSuspension AND a.fechaDesde >= :fecha AND a.estado = :estado AND ");
		sql.append("a.tipoAtencion.tipoAtencionId = :tipoAtencionId");
		Query query = em.createQuery(sql.toString());
		if(registroMercantilId != null)
			query.setParameter("registroMercantilId", registroMercantilId);
		query.setParameter("atencionSuspension", atencionSuspension);
		query.setParameter("fecha", fecha);
		query.setParameter("estado", EstadoEnum.ACTIVO.getEstado());
		query.setParameter("tipoAtencionId", tipoAtencionId);
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Atencion> obtenerSuspensionTemporalPorInstitucionFecha(Integer registroMercantilId,
			Short atencionSuspension, Date fecha, List<Short> tipoAtencionId) {
		Query query = em.createQuery("SELECT a FROM Atencion a WHERE (a.registroMercantil.registroMercantilId IS NULL OR a.registroMercantil.registroMercantilId = :registroMercantilId) AND "
				+ "a.atencionSuspension = :atencionSuspension AND a.fechaDesde <= :fecha AND (a.fechaHasta is null OR :fecha <= a.fechaHasta) "
				+ "AND a.estado = :estado AND "
				+ "a.tipoAtencion.tipoAtencionId IN :tipoAtencionId ");
		query.setParameter("registroMercantilId", registroMercantilId);
		query.setParameter("atencionSuspension", atencionSuspension);
		query.setParameter("fecha", fecha);
		query.setParameter("estado", EstadoEnum.ACTIVO.getEstado());
		query.setParameter("tipoAtencionId", tipoAtencionId);
		
		return query.getResultList();
	}

}

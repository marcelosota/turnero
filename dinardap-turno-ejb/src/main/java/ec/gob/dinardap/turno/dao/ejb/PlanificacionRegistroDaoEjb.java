package ec.gob.dinardap.turno.dao.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ec.gob.dinardap.persistence.dao.ejb.GenericDaoEjb;
import ec.gob.dinardap.turno.dao.PlanificacionRegistroDao;
import ec.gob.dinardap.turno.modelo.PlanificacionRegistro;
import ec.gob.dinardap.util.constante.EstadoEnum;

@Stateless(name="PlanificacionReistroDao")
public class PlanificacionRegistroDaoEjb extends GenericDaoEjb<PlanificacionRegistro, Integer>
		implements PlanificacionRegistroDao {

	public PlanificacionRegistroDaoEjb() {
		super(PlanificacionRegistro.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlanificacionRegistro> getPlanificacionRegistroFechas(Integer registroMercantilId, Date fecha) {
		Query query = em.createQuery("SELECT p FROM PlanificacionRegistro p WHERE p.registroMercantil.registroMercantilId = :registroMercantilId "
				+ "AND p.fechaVigencia <= :fecha AND (p.fechaCaducidad is null OR :fecha <= p.fechaCaducidad) AND p.estado = :estado" );
		query.setParameter("registroMercantilId", registroMercantilId);
		query.setParameter("fecha", fecha);
		query.setParameter("estado", EstadoEnum.ACTIVO.getEstado());
		
		return query.getResultList();
	}

}

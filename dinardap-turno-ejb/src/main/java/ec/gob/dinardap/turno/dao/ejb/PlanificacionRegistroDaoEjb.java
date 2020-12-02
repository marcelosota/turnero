package ec.gob.dinardap.turno.dao.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ec.gob.dinardap.persistence.dao.ejb.GenericDaoEjb;
import ec.gob.dinardap.turno.dao.PlanificacionRegistroDao;
import ec.gob.dinardap.turno.modelo.PlanificacionRegistro;

@Stateless(name="PlanificacionReistroDao")
public class PlanificacionRegistroDaoEjb extends GenericDaoEjb<PlanificacionRegistro, Integer>
		implements PlanificacionRegistroDao {

	public PlanificacionRegistroDaoEjb() {
		super(PlanificacionRegistro.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlanificacionRegistro> getPlanificacionRegistroFechas(Integer registroMercantilId, Date desde) {
		Query query = em.createQuery("SELECT p FROM Planificacion WHERE :desde BETWEEN p.fechaVigencia AND p.fechaCaducidad");
		query.setParameter("desde", desde);
		
		return query.getResultList();
	}

}

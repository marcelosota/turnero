package ec.gob.dinardap.turno.dao.ejb;

import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.ejb.GenericDaoEjb;
import ec.gob.dinardap.turno.dao.PlanificacionRegistroDao;
import ec.gob.dinardap.turno.modelo.PlanificacionRegistro;

@Stateless(name="PlanificacionReistroDao")
public class PlanificacionRegistroDaoEjb extends GenericDaoEjb<PlanificacionRegistro, Integer>
		implements PlanificacionRegistroDao {

	public PlanificacionRegistroDaoEjb() {
		super(PlanificacionRegistro.class);
	}

}

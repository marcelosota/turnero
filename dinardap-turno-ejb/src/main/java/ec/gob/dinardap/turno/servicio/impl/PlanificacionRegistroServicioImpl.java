package ec.gob.dinardap.turno.servicio.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.persistence.servicio.impl.GenericServiceImpl;
import ec.gob.dinardap.turno.dao.PlanificacionRegistroDao;
import ec.gob.dinardap.turno.modelo.PlanificacionRegistro;
import ec.gob.dinardap.turno.servicio.PlanificacionRegistroServicio;

@Stateless(name="PlanificacionRegistroServicio")
public class PlanificacionRegistroServicioImpl extends GenericServiceImpl<PlanificacionRegistro, Integer>
		implements PlanificacionRegistroServicio {

	@EJB
	private PlanificacionRegistroDao planificacionServicioDao;
	
	@Override
	public GenericDao<PlanificacionRegistro, Integer> getDao() {
		return planificacionServicioDao;
	}

}

package ec.gob.dinardap.turno.servicio.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.persistence.servicio.impl.GenericServiceImpl;
import ec.gob.dinardap.turno.dao.TurnoDao;
import ec.gob.dinardap.turno.modelo.Turno;
import ec.gob.dinardap.turno.servicio.TurnoServicio;

@Stateless(name="TurnoServicio")
public class TurnoServicioImpl extends GenericServiceImpl<Turno, Integer> implements TurnoServicio {

	@EJB
	private TurnoDao turnoDao;
	
	@Override
	public GenericDao<Turno, Integer> getDao() {
		return turnoDao;
	}

}

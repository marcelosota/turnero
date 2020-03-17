package ec.gob.dinardap.turno.servicio.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.persistence.servicio.impl.GenericServiceImpl;
import ec.gob.dinardap.turno.dao.RegistroMercantilDao;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.servicio.RegistroMercantilServicio;

@Stateless(name="RegistroMercantilServicio")
public class RegistroMercantilServicioImpl extends GenericServiceImpl<RegistroMercantil, Integer>
		implements RegistroMercantilServicio {

	@EJB
	private RegistroMercantilDao registroMercantilDao;
	
	@Override
	public GenericDao<RegistroMercantil, Integer> getDao() {
		return registroMercantilDao;
	}

}

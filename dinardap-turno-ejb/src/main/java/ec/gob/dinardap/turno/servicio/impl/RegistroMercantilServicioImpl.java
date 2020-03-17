package ec.gob.dinardap.turno.servicio.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.constante.CriteriaTypeEnum;
import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.persistence.servicio.impl.GenericServiceImpl;
import ec.gob.dinardap.persistence.util.Criteria;
import ec.gob.dinardap.turno.dao.RegistroMercantilDao;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.servicio.RegistroMercantilServicio;
import ec.gob.dinardap.util.constante.EstadoEnum;

@Stateless(name="RegistroMercantilServicio")
public class RegistroMercantilServicioImpl extends GenericServiceImpl<RegistroMercantil, Integer>
		implements RegistroMercantilServicio {

	@EJB
	private RegistroMercantilDao registroMercantilDao;
	
	@Override
	public GenericDao<RegistroMercantil, Integer> getDao() {
		return registroMercantilDao;
	}

	@Override
	public List<RegistroMercantil> obtenerRegistrosMercantiles() {
		String[] criteriaNombres = {"tipo"};
		CriteriaTypeEnum[] criteriaTipos = {CriteriaTypeEnum.SHORT_EQUALS};
		Object[] criteriaValores = {EstadoEnum.ACTIVO.getEstado()};
		
		Criteria criteria = new Criteria(criteriaNombres, criteriaTipos, criteriaValores);
		return findByCriterias(criteria);
	}

}

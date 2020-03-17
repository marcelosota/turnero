package ec.gob.dinardap.turno.servicio.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

<<<<<<< HEAD
=======

>>>>>>> origin/jpaspuel
import ec.gob.dinardap.persistence.constante.CriteriaTypeEnum;
import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.persistence.servicio.impl.GenericServiceImpl;
import ec.gob.dinardap.persistence.util.Criteria;
import ec.gob.dinardap.turno.constante.TipoTurnoEnum;
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
	public List<RegistroMercantil> obtenerRegistros(Short tipo) {
		String[] criteriaNombres = {"tipo"};
		CriteriaTypeEnum[] criteriaTipos = {CriteriaTypeEnum.SHORT_EQUALS};
		Object[] criteriaValores = {tipo};
		String[] orderBy = {"nombre"};
		boolean[] asc = {true};
		Criteria criteria = new Criteria(criteriaNombres, criteriaTipos, criteriaValores, orderBy, asc);
		return findByCriterias(criteria);
	}

	@Override
	public List<RegistroMercantil> obtenerRegistrosMercantiles() {
		String[] criteriaNombres = {"tipo"};
		CriteriaTypeEnum[] criteriaTipos = {CriteriaTypeEnum.SHORT_EQUALS};
		Object[] criteriaValores = {TipoTurnoEnum.RM.getTipo()};
		
		Criteria criteria = new Criteria(criteriaNombres, criteriaTipos, criteriaValores);
		return findByCriterias(criteria);
	}

}

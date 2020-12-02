package ec.gob.dinardap.turno.servicio.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.constante.CriteriaTypeEnum;
import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.persistence.servicio.impl.GenericServiceImpl;
import ec.gob.dinardap.persistence.util.Criteria;
import ec.gob.dinardap.turno.dao.AtencionDao;
import ec.gob.dinardap.turno.modelo.Atencion;
import ec.gob.dinardap.turno.servicio.AtencionServicio;

@Stateless(name="AtencionServicio")
public class AtencionServicioImpl extends GenericServiceImpl<Atencion, Integer> implements AtencionServicio {

	@EJB
	private AtencionDao atencionDao;
	
	@Override
	public GenericDao<Atencion, Integer> getDao() {
		return atencionDao;
	}
	
	@Override
	public List<Atencion> obtenerVacacionesPorIstitucion(Integer registroMercantilId) {
		String[] criteriaNombre = {"registroMercantil.registroMercantilId"};
		CriteriaTypeEnum[] criteriaTipos = {CriteriaTypeEnum.INTEGER_EQUALS};
		Object[] criteriaValores = {registroMercantilId};
		String[] orderBy = {"fechaCreacion"};
		boolean[] asc = {true};
		Criteria criteria = new Criteria(criteriaNombre, criteriaTipos, criteriaValores, orderBy, asc);
		return findByCriterias(criteria);
	}

	@Override
	public List<Atencion> obtenerAtencionSuspensionPorInstitucion(Integer registroMercantilId, Short atencionSuspension) {
		String[] criteriaNombre = {"registroMercantil.registroMercantilId", "atencionSuspension"};
		CriteriaTypeEnum[] criteriaTipos = {CriteriaTypeEnum.INTEGER_EQUALS, CriteriaTypeEnum.SHORT_EQUALS};
		Object[] criteriaValores = {registroMercantilId, atencionSuspension};
		String[] orderBy = {"fechaCreacion"};
		boolean[] asc = {true};
		Criteria criteria = new Criteria(criteriaNombre, criteriaTipos, criteriaValores, orderBy, asc);
		return findByCriterias(criteria);
	}

}

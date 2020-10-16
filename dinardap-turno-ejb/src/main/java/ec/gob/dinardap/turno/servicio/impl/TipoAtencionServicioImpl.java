package ec.gob.dinardap.turno.servicio.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.constante.CriteriaTypeEnum;
import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.persistence.servicio.impl.GenericServiceImpl;
import ec.gob.dinardap.persistence.util.Criteria;
import ec.gob.dinardap.turno.dao.TipoAtencionDao;
import ec.gob.dinardap.turno.modelo.TipoAtencion;
import ec.gob.dinardap.turno.servicio.TipoAtencionServicio;

@Stateless(name="TipoAtencionServicio")
public class TipoAtencionServicioImpl extends GenericServiceImpl<TipoAtencion, Short>
		implements TipoAtencionServicio {

	@EJB
	private TipoAtencionDao tipoAtencionDao;
	
	@Override
	public GenericDao<TipoAtencion, Short> getDao() {
		return tipoAtencionDao;
	}

	@Override
	public void guardarTipoAtencion(TipoAtencion tipoAtencion) {
		if(tipoAtencion.getTipoAtencionId() == null)
			create(tipoAtencion);
		else
			update(tipoAtencion);
		
	}

	@Override
	public List<TipoAtencion> obtenerTipoAtencionPorEstado(Short estado) {
		String[] criteriaNombre = {"estado"};
		CriteriaTypeEnum[] criteriaTipos = {CriteriaTypeEnum.SHORT_EQUALS};
		Object[] criteriaValores = {estado};
		String[] orderBy = {"descripcion"};
		boolean[] asc = {true};
		Criteria criteria = new Criteria(criteriaNombre, criteriaTipos, criteriaValores, orderBy, asc);
		return findByCriterias(criteria);
	}

}

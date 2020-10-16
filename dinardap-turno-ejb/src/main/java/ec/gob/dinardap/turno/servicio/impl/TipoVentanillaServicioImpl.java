package ec.gob.dinardap.turno.servicio.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.constante.CriteriaTypeEnum;
import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.persistence.servicio.impl.GenericServiceImpl;
import ec.gob.dinardap.persistence.util.Criteria;
import ec.gob.dinardap.turno.dao.TipoVentanillaDao;
import ec.gob.dinardap.turno.modelo.TipoVentanilla;
import ec.gob.dinardap.turno.servicio.TipoVentanillaServicio;

@Stateless(name="TipoVentanillaServicio")
public class TipoVentanillaServicioImpl extends GenericServiceImpl<TipoVentanilla, Integer>
		implements TipoVentanillaServicio {

	@EJB
	private TipoVentanillaDao tipoVentanillaDao;
	
	@Override
	public GenericDao<TipoVentanilla, Integer> getDao() {
		return tipoVentanillaDao;
	}

	@Override
	public List<TipoVentanilla> obtenerTipoVentanillaPorEstado(Short estado) {
		String[] criteriaNombre = {"estado"};
		CriteriaTypeEnum[] criteriaTipos = {CriteriaTypeEnum.SHORT_EQUALS};
		Object[] criteriaValores = {estado};
		String[] orderBy = {"nombre"};
		boolean[] asc = {true};
		Criteria criteria = new Criteria(criteriaNombre, criteriaTipos, criteriaValores, orderBy, asc);
		return findByCriterias(criteria);
	}

	@Override
	public void guardarTipoVentanilla(TipoVentanilla tipoVentanilla) {
		if(tipoVentanilla.getTipoVentanillaId() == null)
			create(tipoVentanilla);
		else
			update(tipoVentanilla);
		
	}

}

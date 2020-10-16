package ec.gob.dinardap.turno.servicio.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.persistence.servicio.impl.GenericServiceImpl;
import ec.gob.dinardap.turno.dao.ListaBlancaDao;
import ec.gob.dinardap.turno.modelo.ListaBlanca;
import ec.gob.dinardap.turno.servicio.ListaBlancaServicio;

@Stateless(name="ListaBlancaServicio")
public class ListaBlancaServicioImpl extends GenericServiceImpl<ListaBlanca, Integer> implements ListaBlancaServicio {

	@EJB
	private ListaBlancaDao listaBlancaDao;
	
	@Override
	public GenericDao<ListaBlanca, Integer> getDao() {
		return listaBlancaDao;
	}

}

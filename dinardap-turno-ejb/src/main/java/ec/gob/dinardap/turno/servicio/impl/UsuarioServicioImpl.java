package ec.gob.dinardap.turno.servicio.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.constante.CriteriaTypeEnum;
import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.persistence.servicio.impl.GenericServiceImpl;
import ec.gob.dinardap.persistence.util.Criteria;
import ec.gob.dinardap.turno.dao.UsuarioDao;
import ec.gob.dinardap.turno.modelo.Usuario;
import ec.gob.dinardap.turno.servicio.UsuarioServicio;
import ec.gob.dinardap.util.constante.EstadoEnum;

@Stateless(name="UsuarioServicio")
public class UsuarioServicioImpl extends GenericServiceImpl<Usuario, Integer> implements UsuarioServicio {

	@EJB
	private UsuarioDao usuarioDao;
	
	@Override
	public GenericDao<Usuario, Integer> getDao() {
		return usuarioDao;
	}

	@Override
	public boolean validarUsuario(String usuario, String contrasena, Integer entidad) {
		String[] criteriaNombres = {"usuario","contrasena","registroMercantil.registroMercantilId", "estado"};
		CriteriaTypeEnum[] criteriaTipos = {CriteriaTypeEnum.STRING_EQUALS, CriteriaTypeEnum.STRING_EQUALS, CriteriaTypeEnum.INTEGER_EQUALS, CriteriaTypeEnum.SHORT_EQUALS};
		Object[] criteriaValores = {usuario, contrasena, entidad, EstadoEnum.ACTIVO.getEstado()};
		
		Criteria criteria = new Criteria(criteriaNombres, criteriaTipos, criteriaValores);
		List<Usuario> listado = findByCriterias(criteria);
		if(listado != null && listado.size() == 1)
			return true;
		else
			return false;
	}

}

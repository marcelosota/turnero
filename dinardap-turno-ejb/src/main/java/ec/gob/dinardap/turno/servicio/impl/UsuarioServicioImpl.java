package ec.gob.dinardap.turno.servicio.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.constante.CriteriaTypeEnum;
import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.persistence.servicio.impl.GenericServiceImpl;
import ec.gob.dinardap.persistence.util.Criteria;
import ec.gob.dinardap.seguridad.modelo.Perfil;
import ec.gob.dinardap.turno.dao.UsuarioDao;
import ec.gob.dinardap.turno.dto.UsuarioDto;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
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
	public Usuario validarUsuario(String usuario, String contrasena, Integer entidad) {
		String[] criteriaNombres = {"cedula","contrasena","registroMercantil.registroMercantilId", "estado"};
		CriteriaTypeEnum[] criteriaTipos = {CriteriaTypeEnum.STRING_EQUALS, CriteriaTypeEnum.STRING_EQUALS, CriteriaTypeEnum.INTEGER_EQUALS, CriteriaTypeEnum.SHORT_EQUALS};
		Object[] criteriaValores = {usuario, contrasena, entidad, EstadoEnum.ACTIVO.getEstado()};
		
		Criteria criteria = new Criteria(criteriaNombres, criteriaTipos, criteriaValores);
		List<Usuario> listado = findByCriterias(criteria);
		if(listado != null && listado.size() == 1) {
			return listado.get(0);
		}else
			return null;
	}

	@Override
	public void crearUsuario(UsuarioDto usuarioDto) {
		Usuario usuario = new Usuario();
		usuario.setPerfil(new Perfil());
		usuario.setRegistroMercantil(new RegistroMercantil());
		usuario.setCedula(usuarioDto.getCedula());
		usuario.setContrasena(usuarioDto.getContrasena());
		usuario.setNombre(usuarioDto.getNombre());
		usuario.getRegistroMercantil().setRegistroMercantilId(usuarioDto.getRegistroMercantilId());
		usuario.getPerfil().setPerfilId(usuarioDto.getPerfilId());
		usuario.setEstado(EstadoEnum.ACTIVO.getEstado());
		create(usuario);
		
	}

	@Override
	public void modificarUsuario(UsuarioDto usuarioDto) {
		Usuario usuario = new Usuario();
		usuario.setPerfil(new Perfil());
		usuario.setRegistroMercantil(new RegistroMercantil());
		usuario.setUsuarioId(usuarioDto.getUsuarioId());
		usuario.setCedula(usuarioDto.getCedula());
		usuario.setNombre(usuarioDto.getNombre());
		usuario.setContrasena(usuarioDto.getContrasena());
		usuario.getRegistroMercantil().setRegistroMercantilId(usuarioDto.getRegistroMercantilId());
		usuario.getPerfil().setPerfilId(usuarioDto.getPerfilId());
		usuario.setEstado(usuarioDto.getEstado());
		update(usuario);
	}

}

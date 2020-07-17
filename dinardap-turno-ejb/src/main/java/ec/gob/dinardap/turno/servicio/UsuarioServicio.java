package ec.gob.dinardap.turno.servicio;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.servicio.GenericService;
import ec.gob.dinardap.turno.dto.UsuarioDto;
import ec.gob.dinardap.turno.modelo.UsuarioT;

@Local
public interface UsuarioServicio extends GenericService<UsuarioT, Integer> {

	public UsuarioT validarUsuario(String usuario, String contrasena, Integer entidad);
	public void crearUsuario(UsuarioDto usuarioDto);
	public void modificarUsuario(UsuarioDto usuarioDto);
	public UsuarioT buscarPorCedula(String cedula);
	public UsuarioT verificarCredenciales(String cedula, String contrasena);
}

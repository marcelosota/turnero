package ec.gob.dinardap.turno.servicio;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.servicio.GenericService;
import ec.gob.dinardap.turno.dto.UsuarioDto;
import ec.gob.dinardap.turno.modelo.Usuario;

@Local
public interface UsuarioServicio extends GenericService<Usuario, Integer> {

	public Usuario validarUsuario(String usuario, String contrasena, Integer entidad);
	public void crearUsuario(UsuarioDto usuarioDto);
	public void modificarUsuario(UsuarioDto usuarioDto);
	public Usuario buscarPorCedula(String cedula);
	public Usuario verificarCredenciales(String cedula, String contrasena);
}

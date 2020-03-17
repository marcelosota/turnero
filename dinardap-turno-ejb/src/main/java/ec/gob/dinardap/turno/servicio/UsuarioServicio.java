package ec.gob.dinardap.turno.servicio;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.servicio.GenericService;
import ec.gob.dinardap.turno.modelo.Usuario;

@Local
public interface UsuarioServicio extends GenericService<Usuario, Integer> {

	public boolean validarUsuario(String usuario, String contrasena, Integer entidad);
}

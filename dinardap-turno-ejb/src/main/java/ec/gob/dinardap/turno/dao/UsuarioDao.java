package ec.gob.dinardap.turno.dao;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.turno.modelo.UsuarioT;

@Local
public interface UsuarioDao extends GenericDao<UsuarioT, Integer> {

}

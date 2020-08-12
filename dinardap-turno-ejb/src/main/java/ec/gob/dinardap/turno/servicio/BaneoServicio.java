package ec.gob.dinardap.turno.servicio;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.servicio.GenericService;
import ec.gob.dinardap.turno.modelo.Baneo;
import java.util.List;

@Local
public interface BaneoServicio extends GenericService<Baneo, Integer> {

    public List<Baneo> getBanList();

    public List<Baneo> getBanList(Integer parametro);

}

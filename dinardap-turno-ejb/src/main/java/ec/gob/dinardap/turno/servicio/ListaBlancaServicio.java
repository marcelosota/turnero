package ec.gob.dinardap.turno.servicio;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.servicio.GenericService;
import ec.gob.dinardap.turno.modelo.ListaBlanca;

@Local
public interface ListaBlancaServicio extends GenericService<ListaBlanca, Integer> {

}

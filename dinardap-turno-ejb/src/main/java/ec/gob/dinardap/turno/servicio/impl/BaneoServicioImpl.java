package ec.gob.dinardap.turno.servicio.impl;

import ec.gob.dinardap.persistence.constante.CriteriaTypeEnum;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.persistence.servicio.impl.GenericServiceImpl;
import ec.gob.dinardap.persistence.util.Criteria;
import ec.gob.dinardap.turno.dao.BaneoDao;
import ec.gob.dinardap.turno.modelo.Baneo;
import ec.gob.dinardap.turno.servicio.BaneoServicio;
import ec.gob.dinardap.util.constante.EstadoEnum;
import java.util.ArrayList;
import java.util.List;

@Stateless(name = "BaneoServicio")
public class BaneoServicioImpl extends GenericServiceImpl<Baneo, Integer> implements BaneoServicio {

    @EJB
    private BaneoDao baneoDao;

    @Override
    public GenericDao<Baneo, Integer> getDao() {
        return baneoDao;
    }

    @Override
    public List<Baneo> getBanList() {
        List<Baneo> banList = new ArrayList<Baneo>();
        String[] criteriaNombres = {"estado"};
        CriteriaTypeEnum[] criteriaTipos = {CriteriaTypeEnum.SHORT_EQUALS};
        Object[] criteriaValores = {EstadoEnum.ACTIVO.getEstado()};
        String[] orderBy = {"baneoId"};
        boolean[] asc = {true};
        Criteria criteria = new Criteria(criteriaNombres, criteriaTipos, criteriaValores, orderBy, asc);
        if (!findByCriterias(criteria).isEmpty()) {
            banList = findByCriterias(criteria);
        }
        return banList;
    }

    @Override
    public List<Baneo> getBanList(Integer parametro) {
        List<Baneo> banList = new ArrayList<Baneo>();
        String[] criteriaNombres = {"estado", "tipoRestriccion.tipoRestriccionId"};
        CriteriaTypeEnum[] criteriaTipos = {CriteriaTypeEnum.SHORT_EQUALS, CriteriaTypeEnum.INTEGER_EQUALS};
        Object[] criteriaValores = {EstadoEnum.ACTIVO.getEstado(), parametro};
        String[] orderBy = {"baneoId"};
        boolean[] asc = {true};
        Criteria criteria = new Criteria(criteriaNombres, criteriaTipos, criteriaValores, orderBy, asc);
        if (!findByCriterias(criteria).isEmpty()) {
            banList = findByCriterias(criteria);
        }
        return banList;
    }

}
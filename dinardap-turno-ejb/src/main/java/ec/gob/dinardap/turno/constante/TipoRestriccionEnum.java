package ec.gob.dinardap.turno.constante;

import java.util.Objects;

public enum TipoRestriccionEnum {

    IDENTIFICACION(1), CORREO(2), CELULAR(3);

    private Integer parametro;

    private TipoRestriccionEnum(Integer parametro) {
        this.parametro = parametro;
    }

    public Integer getParametro() {
        return parametro;
    }

    public void setEstado(Integer parametro) {
        this.parametro = parametro;
    }

    public static TipoRestriccionEnum obtenerEstadoPorCodigo(Integer parametro) {
        TipoRestriccionEnum valor = null;
        for (TipoRestriccionEnum e : TipoRestriccionEnum.values()) {
            if (Objects.equals(e.getParametro(), parametro)) {
                valor = e;
                break;
            }
        }
        return valor;
    }
}

package br.ufsc.ine5608.model;

import br.ufsc.ine5608.shared.TipoCartaEnum;

public class Carta {

    private Long id;
    private Long number;
    private TipoCartaEnum tipoCartaEnum;

    public Carta(Long number, TipoCartaEnum tipoCartaEnum) {
        this.id = id;
        this.number = number;
        this.tipoCartaEnum = tipoCartaEnum;
    }

    public Long getId() {
        return id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public TipoCartaEnum getTipoCartaEnum() {
        return tipoCartaEnum;
    }

    public void setTipoCartaEnum(TipoCartaEnum tipoCartaEnum) {
        this.tipoCartaEnum = tipoCartaEnum;
    }
}

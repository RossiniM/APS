package br.ufsc.ine5608.model;

import br.ufsc.ine5608.shared.CardTypeEnum;

public class Card {

    private Long id;
    private Long number;
    private CardTypeEnum cardTypeEnum;

    public Card(Long number, CardTypeEnum cardTypeEnum) {
        this.id = id;
        this.number = number;
        this.cardTypeEnum = cardTypeEnum;
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

    public CardTypeEnum getCardTypeEnum() {
        return cardTypeEnum;
    }

    public void setCardTypeEnum(CardTypeEnum cardTypeEnum) {
        this.cardTypeEnum = cardTypeEnum;
    }
}

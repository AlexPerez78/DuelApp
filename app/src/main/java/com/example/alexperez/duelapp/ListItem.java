package com.example.alexperez.duelapp;

/**
 * Created by Alex Perez on 10/22/2017.
 */

public class ListItem {
    private String head;
    private String desc;
    private String cardType;
    private String remark;

    public ListItem(String cardType, String head, String desc, String remark) {
        this.cardType = cardType;
        this.head = head;
        this.desc = desc;
        this.remark = remark;

    }

    public String getHead() {
        return head;
    }

    public String getDesc() {

        return desc;
    }

    public String getCardType() {
        return cardType;
    }

    public String getRemark() {
        return remark;
    }
}

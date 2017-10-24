package com.example.alexperez.duelapp;

/**
 * Created by Alex Perez on 10/22/2017.
 */

public class ListItem {
    private String head;
    private String desc;

    public ListItem(String head, String desc) {
        this.head = head;
        this.desc = desc;
    }

    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }
}

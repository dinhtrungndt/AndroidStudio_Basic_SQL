package com.example.asm_ht_nguyendinhtrung_pk02294.home_list;

import java.io.Serializable;

public class Clazz  implements Serializable {
    private Integer id;
    private String name, time, room;

    public Clazz(int i, String name, String time, String room, String id) {
    }

    public Clazz(Integer id, String name, String time, String room) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.room = room;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return this.name + " " + this.time;
    }
}

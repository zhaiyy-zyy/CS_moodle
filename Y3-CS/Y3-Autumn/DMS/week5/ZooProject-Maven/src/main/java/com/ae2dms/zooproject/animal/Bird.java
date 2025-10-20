package com.ae2dms.zooproject.animal;

import com.ae2dms.zooproject.misc.XMLable;

public abstract class Bird extends Animal implements XMLable {
    private Feather feather;

    public Bird(String name) { super(name); }

    public String toXML() {
        return "<Bird><Name>" + getName() + "</Name></Bird>";
    }
}



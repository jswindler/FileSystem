package com.joe;

/**
 * Created by joe on 4/26/15.
 */
public class ZipFile extends Entity {

    public ZipFile(String name) {
        super(name);
    }

    @Override
    public int getSize() {
        // get size of all children and divide by 2
        return super.getSize() / 2;
    }
}

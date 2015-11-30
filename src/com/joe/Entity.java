package com.joe;

import java.util.ArrayList;

/**
 * Created by joe on 4/27/15.
 */
public abstract class Entity {
    protected String name = "New";
    protected Entity parent = null;
    protected ArrayList<Entity> children = new ArrayList<Entity>();

    public Entity(String name) {
        this.name = name;
    }

    public EntityType getEntityType() {
        if (this.getClass() == Drive.class) {
            return EntityType.DRIVE;
        }
        else if (this.getClass() == Folder.class) {
            return EntityType.FOLDER;
        }
        else if (this.getClass() == TextFile.class) {
            return EntityType.TEXTFILE;
        }
        else if (this.getClass() == ZipFile.class) {
            return EntityType.ZIPFILE;
        }
        else {
            return EntityType.NONE;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Entity getParent() {
        return this.parent;
    }

    public void setParent(Entity parent) {
        this.parent = parent;
    }

    public String getPath() {
        if (this.parent == null) {
            // top level entity
            return this.getName();
        }

        // get path from parent and concatenate this entity's name to it
        return this.parent.getPath() + "/" + this.getName();
    }

    public void addChild(Entity child) {
        children.add(child);
        child.setParent(this);
    }

    public void removeChild(Entity child) {
        children.remove(child);
        child.setParent(null);
    }

    public void removeFromParent() {
        // remove this entity from its parent
        parent.removeChild(this);
    }

    Entity getEntity(String path) {
        for (Entity entity : children) {
            if (entity.getPath().equals(path)) {
                return entity;
            }

            // recurse
            Entity findEntity = entity.getEntity(path);
            if (findEntity != null) {
                return findEntity;
            }
        }

        return null;
    }

    public int getSize() {
        // add size of all children
        int size = 0;
        for (Entity entity : children) {
            size += entity.getSize();
        }

        return size;
    }


    public ArrayList<Entity> getChildren() {
        return this.children;
    }

    void listEntities() {
        System.out.println(getPath() + "   " + getSize() + " bytes");

        for (Entity child : children) {
            child.listEntities();
        }
    }

}
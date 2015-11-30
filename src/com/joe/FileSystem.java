package com.joe;

import java.util.ArrayList;

/**
 * Created by joe on 4/27/15.
 */
public class FileSystem {
    ArrayList<Drive> drives = new ArrayList<Drive>();

    // Creates a new entity.
    // Exceptions: Path not found; Path already exists; Illegal File System Operation.
    void create(EntityType entityType, String name, String parentPath) throws Exception {
        String newPath = parentPath + "/" + name;
        if (getEntity(newPath) != null) {
            // entity already exists
            throw new Exception("Path already exists.");
        }

        Entity parent = getEntity(parentPath);
        if (parent != null) {
            // Found the parent entity and the child entity is make sure this is a valid operation
            if (!isValidOperation(parent.getEntityType(), entityType)) {
                throw new Exception("Illegal file system operation.");
            }
        }
        else if (entityType != EntityType.DRIVE) {
            throw new Exception("Path not found.");
        }

        Entity entity = null;
        switch (entityType) {
            case DRIVE:
                Drive drive = new Drive(name);
                drives.add(drive);
                break;

            case FOLDER:
                entity = new Folder(name);
                parent.addChild(entity);
                break;

            case ZIPFILE:
                entity = new ZipFile(name);
                parent.addChild(entity);
                break;

            case TEXTFILE:
                entity = new TextFile(name);
                parent.addChild(entity);
                break;

            default:
                break;
        }
    }

    boolean isValidOperation(EntityType parentType, EntityType childType) {
        if ((childType == EntityType.DRIVE) ||
            (parentType == EntityType.TEXTFILE)) {
            return false;
        }

        return true;
    }

    // Return the entity at the given path or null if not found.
    Entity getEntity(String path) {
        //Compare with drive names first, then continue drilling down.
        for (Drive drive : drives) {
            if (drive.getPath().equals(path)) {
                return drive;
            }

            Entity entity = drive.getEntity(path);
            if (entity != null) {
                return entity;
            }
        }

        return null;
    }

    // Deletes an existing entity (and all the entities it contains).
    // Exceptions: Path not found.
    void delete(String path) throws Exception {
        Entity entity = getEntity(path);
        if (entity == null) {
            throw new Exception("Path not found.");
        }

        entity.removeFromParent();
    }

    // Changes the parent of an entity.
    // Exceptions: Path not found; Path already exists, Illegal File System Operation.
    void move(String sourcePath, String destPath) throws Exception {
        Entity entity = getEntity(sourcePath);
        if (entity == null) {
            throw new Exception("Path not found.");
        }

        if (getEntity(destPath + "/" + entity.getName()) != null) {
            throw new Exception("Path already exists.");
        }

        Entity newParent = getEntity(destPath);
        if (!isValidOperation(newParent.getEntityType(), entity.getEntityType())) {
            throw new Exception("Illegal file system operation.");
        }

        // Remove child from its current parent and add to the new parent.
        entity.removeFromParent();
        newParent.addChild(entity);
    }

    // Changes the content of a text file.
    // Exceptions: Path not found; Not a text file.
    void writeToFile(String path, String content) throws Exception {
        Entity entity = getEntity(path);
        if (entity == null) {
            throw new Exception("Path not found.");
        }

        if (entity.getEntityType() != EntityType.TEXTFILE) {
            throw new Exception("Not a text file.");
        }
        else {
            TextFile file = (TextFile)entity;
            file.content = content;
        }
    }

    void listEntities() {
        System.out.println("File System Listing:");
        for (Drive drive : drives) {
            drive.listEntities();
        }
    }
}

package com.joe;

/**
 * Created by joe on 4/26/15.
 */
public class TextFile extends Entity {
    String content = "";

    public TextFile(String name) {
        super(name);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public int getSize() {
        return content.length();
    }
}

package com.presentech.handsup;

/**
 * Created by Alex on 25/02/2016.
 */
public class Video extends VisualMediaElement {
    String sourceFile;
    boolean loop;

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }


}

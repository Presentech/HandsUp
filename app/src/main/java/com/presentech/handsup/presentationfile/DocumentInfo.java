package com.presentech.handsup.presentationfile;

/**
 * Created by Alex Butcher on 22/02/2016.
 */
public class DocumentInfo {
    String title;
    String author;
    String version;
    String comment;

    public DocumentInfo(String title, String author, String version, String comment) {
        this.title = title;
        this.author = author;
        this.version = version;
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getVersion() {
        return version;
    }

    public String getComment() {
        return comment;
    }



}

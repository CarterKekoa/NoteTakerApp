/**This code was created by Carter Mooring and Jackson Lindsay
 * PA6 Gina Sprint
 * CPSC 312
 * This function is the Note.java so we can translate the info to and from screens
 * Note.java
 */
package com.cartermooring.notetakingapp;

public class Note {

    private String title;
    private String content;
    private String type;

    //DVC
    public Note(){
        title = "BLANK TITLE";
        content = "NO CONTENT";
        type = "DEFAULT";
    }

    public Note(String title, String content, String type){
        this.title = title;
        this.content = content;
        this.type = type;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

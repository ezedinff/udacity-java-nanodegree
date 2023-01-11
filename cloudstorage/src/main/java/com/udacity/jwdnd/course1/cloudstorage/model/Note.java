package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    private Integer noteid;
    private Integer userid;
    private String notetitle;
    private String notedescription;

    public Note(Integer userid, String notetitle, String description) {
        this.noteid = noteid;
        this.userid = userid;
        this.notetitle = notetitle;
        this.notedescription = description;
    }

    public Integer getNoteid() {
        return noteid;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteid=" + noteid +
                ", userid=" + userid +
                ", notetitle='" + notetitle + '\'' +
                ", notedescription='" + notedescription + '\'' +
                '}';
    }

    public Integer getUserid() {
        return userid;
    }

    public String getNotetitle() {
        return notetitle;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }
}

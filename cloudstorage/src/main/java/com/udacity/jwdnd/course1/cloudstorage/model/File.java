package com.udacity.jwdnd.course1.cloudstorage.model;

import java.sql.Blob;
import java.sql.SQLException;

public class File {
    private Integer fileId;
    private Integer userid;
    private String filename;
    private String contenttype;
    private String filesize;
    private byte[] filedata;


    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) throws SQLException {
        this.filedata = filedata;//.getBytes(1, (int)filedata.length());
    }

//    public Blob getFiledata() {
//        return filedata;
//    }
//
//    public void setFiledata(Blob filedata) {
//        this.filedata = filedata;
//    }
}

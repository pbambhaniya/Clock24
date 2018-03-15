package com.multipz.multipzapps;

/**
 * Created by Admin on 24-07-2017.
 */

public class MyAppModel {
    int tbl_app_info_id;
    String appname, imgname, link, descri, app_id;

    public MyAppModel() {

    }

    public MyAppModel(int tbl_app_info_id, String appname, String imgname, String link, String descri, String app_id) {
        this.tbl_app_info_id = tbl_app_info_id;
        this.appname = appname;
        this.imgname = imgname;
        this.link = link;
        this.descri = descri;
        this.app_id = app_id;
    }

    public int getTbl_app_info_id() {
        return tbl_app_info_id;
    }

    public void setTbl_app_info_id(int tbl_app_info_id) {
        this.tbl_app_info_id = tbl_app_info_id;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }
}

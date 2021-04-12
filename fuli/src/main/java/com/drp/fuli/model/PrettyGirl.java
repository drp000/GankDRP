package com.drp.fuli.model;


/**
 * @author durui
 * @date 2021/3/11
 * @description
 */
public class PrettyGirl {
    public String _id;
    public String desc;
    public String createdAt;
    public String publishedAt;
    public String source;
    public String type;
    public String url;
    public String who;
    public boolean used;

    @Override
    public String toString() {
        return "PrettyGirl{" +
                "_id='" + _id + '\'' +
                ", desc='" + desc + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", who='" + who + '\'' +
                ", used=" + used +
                '}';
    }
}

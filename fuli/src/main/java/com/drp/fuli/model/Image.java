package com.drp.fuli.model;

import android.graphics.Bitmap;

/**
 * @author durui
 * @date 2021/3/11
 * @description
 */
public class Image {
    private int width;
    private int height;
    private Bitmap bitmap;
    private String url;

    public Image(int width, int height, Bitmap bitmap) {
        this.width = width;
        this.height = height;
        this.bitmap = bitmap;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

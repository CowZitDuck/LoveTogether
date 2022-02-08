package com.example.lovetogether.FragmentPageView.Memory;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

public class Memory {
    private String name;
    private String date;
    private String content;
    private byte[] image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Memory(String name, String date, String content, byte[] image) {
        this.name = name;
        this.date = date;
        this.content = content;
        this.image = image;
    }

    public Memory() {

    }

}

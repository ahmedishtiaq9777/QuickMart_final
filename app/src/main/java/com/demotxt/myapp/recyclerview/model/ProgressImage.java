package com.demotxt.myapp.recyclerview.model;

public class ProgressImage {
    public int image;
    public String title;
    public boolean progress = false;

    public ProgressImage() {
    }

    public ProgressImage(boolean progress) {
        this.progress = progress;
    }

    public ProgressImage(int image, String title, boolean section) {
        this.image = image;
        this.title = title;
        this.progress = section;
    }
}

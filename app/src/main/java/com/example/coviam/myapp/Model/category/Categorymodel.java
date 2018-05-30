package com.example.coviam.myapp.Model.category;

public class Categorymodel {
    private int imageId;
    private String name;
    private long categoryid;

    public Categorymodel(int imageId, String name, int categoryid) {
        this.imageId = imageId;
        this.name = name;
        this.categoryid = categoryid;
    }

    public long getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}

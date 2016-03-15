package com.hbln.myadvertisingtest.Model;

/**
 * 这个类的作用
 * Created by lwc on 2016/3/8.
 */
public class Travel {
    private String img;

    private String shijian;

    private String title;

    private String price;

    private String id;

    public Travel() {
    }

    public Travel(String img, String shijian, String title, String price) {
        this.img = img;
        this.shijian = shijian;
        this.title = title;
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Travel{" +
                "img='" + img + '\'' +
                ", shijian='" + shijian + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

package com.example.derekjames.uiproject;

public class Chime_cardview {

    private String img;
    private  String title;
    private  String href;

    public Chime_cardview(String img, String title,String href)
    {
        this.img = img;
        this.title = title;
        this.href = href;
    }

    public String getImg(){ return  img; }

    public String getHref(){ return  href; }

    public String getTitle()
    {
        return title;
    }

}

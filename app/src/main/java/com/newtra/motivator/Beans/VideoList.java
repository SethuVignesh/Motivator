package com.newtra.motivator.Beans;

/**
 * Created by sethu on 3/3/2016.
 */
public class VideoList {
    String title;
    String font;
public VideoList(String _title, String _font){
    title=_title;
    font=_font;

}
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }
}

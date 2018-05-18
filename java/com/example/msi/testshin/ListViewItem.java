package com.example.msi.testshin;

import android.graphics.drawable.Drawable;

/**
 * Created by hansangjun on 2017. 2. 24..
 */
public class ListViewItem {
    private Drawable iconDrawable ;
    private String titleStr ;
    private String idStr ;
    private String disccusionStr;
    private String dateStr;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }

    public void setIdStr(String id){idStr=id;}
    public void setDisccusionStr(String discussion){disccusionStr = discussion;}
    public void setDateStr(String date){dateStr=date;}


    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getIdStr() { return this.idStr ; }
    public String getDisccusionStr() { return this.disccusionStr ; }
    public String getDateStr() { return this.dateStr ; }


}

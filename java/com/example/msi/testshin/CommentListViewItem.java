package com.example.msi.testshin;

import android.graphics.drawable.Drawable;

/**
 * Created by hansangjun on 2017. 2. 25..
 */
public class CommentListViewItem {

    private Drawable iconDrawable ;
    private String idStr ;
    private String commentStr;
    private String good;
    private String prosCons;
    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setIdStr(String id){idStr=id;}
    public void setCommentStr(String comment){commentStr = comment;}
    public void setGood(String g){good =g;}
    public void setProsCons(String p){prosCons = p;}
    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getIdStr() { return this.idStr ; }
    public String getCommentStr() { return this.commentStr ; }
    public String getGood(){return this.good;}
    public String getProsCons(){return this.prosCons;}
}
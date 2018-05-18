package com.example.msi.testshin;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hansangjun on 2017. 2. 25..
 */
public class CommentListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<CommentListViewItem> commentlistViewItemList = new ArrayList<CommentListViewItem>();

    // ListViewAdapter의 생성자
    public CommentListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return commentlistViewItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();


        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.comment_listview, parent, false);
        }
        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1);
        TextView idTextView = (TextView) convertView.findViewById(R.id.idText);
        TextView commentTextView = (TextView) convertView.findViewById(R.id.commentText);
        TextView goodTextView = (TextView) convertView.findViewById(R.id.Good);
        TextView prosConsTextView = (TextView)convertView.findViewById(R.id.prosCons);
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        CommentListViewItem commentlistViewItem = commentlistViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(commentlistViewItem.getIcon());

        idTextView.setText(commentlistViewItem.getIdStr());
        commentTextView.setText(commentlistViewItem.getCommentStr());
        goodTextView.setText(commentlistViewItem.getGood());
        prosConsTextView.setText(commentlistViewItem.getProsCons());
        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return commentlistViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon, String id, String comment, String g,String p) {
        CommentListViewItem item = new CommentListViewItem();
        item.setIcon(icon);
        item.setIdStr(id);
        item.setCommentStr(comment);
        item.setGood(g);
        item.setProsCons(p);
        commentlistViewItemList.add(item);
    }
}
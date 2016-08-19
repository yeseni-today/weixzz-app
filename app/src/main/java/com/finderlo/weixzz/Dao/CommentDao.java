package com.finderlo.weixzz.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.finderlo.weixzz.base.WeiException;
import com.finderlo.weixzz.model.bean.AbsBean;
import com.finderlo.weixzz.model.bean.Comment;

import java.util.ArrayList;
import java.util.List;

import static com.finderlo.weixzz.database.Table.CommentTable.TABLE_NAME_COMMENT;

/**
 * Created by Finderlo on 2016/8/19.
 */

public class CommentDao extends AbsDao {
    private static final String TABLE_NAME = TABLE_NAME_COMMENT;

    public static final String TYPE_MID = " mid ";
    private static CommentDao sCommentDao;

    private CommentDao(){}

    public static CommentDao getInstance() {
        if (sCommentDao == null) {
            sCommentDao = new CommentDao();
        }
        return sCommentDao;
    }

    @Override
    public List<? extends AbsBean> query() {
        return query(0);
    }

    @Override
    public List<? extends AbsBean> query(int count) {
        if (count==0) count=Integer.MAX_VALUE;
        ArrayList<Comment> comments = new ArrayList<>();

        Cursor cursor = sDatabase.query(TABLE_NAME,null,null,null,null,null,null);
        if (cursor.moveToFirst()){

            do {
                comments.add(queryByCursor(cursor));
                if (comments.size()>=count) break;

            }while (cursor.moveToNext());
            Log.d(TAG, "query: The comment list size is "+comments.size());
        }
        cursor.close();
        return comments;
    }

    @Override
    public void insert(AbsBean bean) throws WeiException {
        isCurrentBean(bean);
        Comment comment = (Comment) bean;

        if (isDataAlreadyExist(TYPE_IDSTR,comment.idstr)){
            Log.d(TAG, "insert: The data is already exist");
            return;
        }

        ContentValues values  =  new ContentValues();

        values.put(TYPE_ID,comment.id);
        values.put(TYPE_MID,comment.mid);
        values.put(TYPE_IDSTR,comment.idstr);
        values.put(JSON,comment.getJsonString());

        sDatabase.insert(TABLE_NAME,null,values);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void insert(List<? extends AbsBean> list) throws WeiException {
        List<Comment> comments = (List<Comment>) list;
        for (Comment comment:comments) {
            insert(comment);
        }
    }

    @Override
    public void clear() {

    }

    @Override
    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public Comment queryByCursor(Cursor cursor) {
        Comment comment = null;
        if (cursor.moveToFirst()) {
            comment = queryByCursor(cursor, cursor.getPosition());
        }
        return comment;
    }

    public Comment queryByCursor(Cursor cursor, int position) {
        Comment comment = null;
        if (cursor.moveToPosition(position)) {
            String userjson = cursor.getString(cursor.getColumnIndex(JSON));
            comment = Comment.parse(userjson);
        }
        return comment;
    }

    @Override
    void isCurrentBean(AbsBean bean) throws WeiException {
        if (! (bean instanceof Comment))
            throw new WeiException("bean is not cast");
    }
}

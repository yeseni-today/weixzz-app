//package com.finderlo.weixzz.model;
//
//import android.content.Context;
//
//import com.finderlo.weixzz.base.WeiException;
//import com.finderlo.weixzz.base.WeiXzzApplication;
//import com.finderlo.weixzz.model.bean.BaseModel;
//import com.finderlo.weixzz.model.bean.CommentModel;
//import com.finderlo.weixzz.model.bean.CommentListModel;
//import com.finderlo.weixzz.model.bean.StatusListModel;
//import com.finderlo.weixzz.sinaApi.openapi.CommentsAPI;
//import com.sina.weibo.sdk.exception.WeiboException;
//import com.sina.weibo.sdk.net.RequestListener;
//
//import java.util.List;
//
///**
// * Created by Finderlo on 2016/8/19.
// */
//
//public class CommentsAgency {
//
//    private static CommentsAPI sCommentsAPI;
//    private static CommentDao sCommentDao;
//
//    static {
//        sCommentsAPI = APIManger.getCommentsAPI();
//        sCommentDao = CommentDao.getInstance();
//    }
//
//
//
//    /**
//     * 获取当前登录用户的最新评论包括接收到的与发出的。
//     *
//     * @param since_id  若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0。
//     * @param max_id    若指定此参数，则返回ID小于或等于max_id的评论，默认为0。
//     * @param count     单页返回的记录条数，默认为50。
//     * @param page      返回结果的页码，默认为1。
//     * @param trim_user 返回值中user字段开关，false：返回完整user字段、true：user字段仅返回user_id，默认为false。
//     * @param listener  异步请求回调接口
//     */
//    public static void timeline(long since_id, long max_id, int count, int page, boolean trim_user,
//                         final CommentsAgencyRequestListener listener) {
//        RequestListener requestListener = new RequestListener() {
//            @Override
//            public void onComplete(String s) {
//                CommentListModel mCommentModelList = CommentListModel.parse(s);
//
//                    insert(mCommentModelList);
//
//                listener.onComplete(mCommentModelList);
//            }
//            @Override
//            public void onWeiboException(WeiboException e) {
//                listener.onError(e);
//            }
//        };
//
//        sCommentsAPI.timeline(since_id, max_id, count, page, trim_user, requestListener);
//    }
//
//    public static CommentListModel query() {
//        return sCommentDao.query();
//    }
//
//    public static CommentListModel query(int count) {
//        return sCommentDao.query(count);
//    }
//
//    public static void insert(CommentModel bean) {
//        sCommentDao.insert(bean);
//    }
//
//    public static void insert(CommentListModel list) {
//        sCommentDao.insert(list);
//    }
//
//    public static void clear() {
//        sCommentDao.clear();
//    }
//
//    public interface CommentsAgencyRequestListener {
//        void onComplete(CommentListModel mCommentModelList);
//        void onError(Exception e);
//    }
//}

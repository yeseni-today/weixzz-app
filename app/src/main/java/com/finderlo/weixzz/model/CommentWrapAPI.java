package com.finderlo.weixzz.model;

import android.content.Context;

import com.finderlo.weixzz.base.WeiXzzApplication;
import com.finderlo.weixzz.model.api.StatusesAPI;
import com.finderlo.weixzz.sinaApi.openapi.CommentsAPI;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;

/**
 * Created by Finderlo on 2016/8/19.
 */

public class CommentWrapAPI {

    private static CommentWrapAPI sCommentWrapAPI;
    private static Context sContext;
    private static CommentsAPI sCommentsAPI;

    private CommentWrapAPI(){
        sCommentsAPI = APIManger.getCommentsAPI();
        sContext = WeiXzzApplication.getContext();
    }

    public static CommentWrapAPI getInstance(){
        if (sCommentWrapAPI==null){
            sCommentWrapAPI = new CommentWrapAPI();
        }
        return sCommentWrapAPI;
    }

    /**
     * 获取当前登录用户的最新评论包括接收到的与发出的。
     *
     * @param since_id  若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0。
     * @param max_id    若指定此参数，则返回ID小于或等于max_id的评论，默认为0。
     * @param count     单页返回的记录条数，默认为50。
     * @param page      返回结果的页码，默认为1。
     * @param trim_user 返回值中user字段开关，false：返回完整user字段、true：user字段仅返回user_id，默认为false。
     * @param listener  异步请求回调接口
     */
    public void timeline(long since_id, long max_id, int count, int page, boolean trim_user, RequestListener listener) {
        sCommentsAPI.timeline(since_id, max_id, count, page, trim_user, listener);
    }
}

///*
// * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.finderlo.weixzz.model;
//
//import com.finderlo.weixzz.dao.timeline.StatusDao;
//import com.finderlo.weixzz.model.api.StatusesAPI;
//import com.finderlo.weixzz.model.bean.BaseModel;
//import com.finderlo.weixzz.model.bean.StatusModel;
//import com.finderlo.weixzz.model.bean.StatusListModel;
//import com.sina.weibo.sdk.exception.WeiboException;
//import com.sina.weibo.sdk.net.RequestListener;
//
//import java.util.List;
//
///**
// * 该类封装了微博接口。
// * 详情请参考<a href="http://t.cn/8F3e7SE">微博接口</a>
// *
// * @author SINA
// * @since 2014-03-03
// */
//public  class StatusesAgency  {
//
//    private static StatusesAPI sStatusesAPI;
//    private static StatusDao sStatusDao;
//
//    static {
//        sStatusesAPI = APIManger.getStatusesAPI();
//        sStatusDao = StatusDao.getInstance();
//    }
//
//
//
//    /**
//     * 获取最新的提到登录用户的微博列表，即@我的微博。
//     *
//     * @param since_id      若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
//     * @param max_id        若指定此参数，则返回ID小于或等于max_id的微博，默认为0
//     * @param count         单页返回的记录条数，默认为50
//     * @param page          返回结果的页码，默认为1
//     * @param authorType    作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。可为以下几种：
//     *                      <li> {@link #AUTHOR_FILTER_ALL}
//     *                      <li> {@link #AUTHOR_FILTER_ATTENTIONS}
//     *                      <li> {@link #AUTHOR_FILTER_STRANGER}
//     * @param sourceType    来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论。可分为以下几种：
//     *                      <li> {@link #SRC_FILTER_ALL}
//     *                      <li> {@link #SRC_FILTER_WEIBO}
//     *                      <li> {@link #SRC_FILTER_WEIQUN}
//     * @param filterType    原创筛选类型，0：全部微博、1：原创的微博，默认为0。可分为以下几种：
//     *                      <li> {@link #TYPE_FILTER_ALL}
//     *                      <li> {@link #TYPE_FILTER_ORIGAL}
//     * @param trim_user     返回值中user字段开关，false：返回完整user字段、true：user字段仅返回user_id，默认为false
//     * @param listener      异步请求回调接口
//     */
//    public static void mentions(long since_id, long max_id, int count, int page, int authorType, int sourceType,
//                          int filterType, boolean trim_user, final StatusedAgencyRequestListener listener) {
//
//        RequestListener requestListener = new RequestListener() {
//            @Override
//            public void onComplete(String s) {
//                StatusListModel mStatusModelList = StatusListModel.parse(s);
//                    insert(mStatusModelList);
//
//                listener.onComplete(mStatusModelList);
//            }
//            @Override
//            public void onWeiboException(WeiboException e) {
//                listener.onError(e);
//            }
//        };
//
//        sStatusesAPI.mentions(since_id, max_id, count, page, authorType, sourceType, filterType, trim_user, requestListener);
//
//    }
//
//    /**
//     * 获取当前登录用户及其所关注用户的最新微博。
//     *
//     * @param since_id      若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
//     * @param max_id        若指定此参数，则返回ID小于或等于max_id的微博，默认为0
//     * @param count         单页返回的记录条数，默认为50
//     * @param page          返回结果的页码，默认为1
//     * @param base_app      是否只获取当前应用的数据。false为否（所有数据），true为是（仅当前应用），默认为false
//     * @param featureType   过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0
//     *                      <li> {@link #FEATURE_ALL}
//     *                      <li> {@link #FEATURE_ORIGINAL}
//     *                      <li> {@link #FEATURE_PICTURE}
//     *                      <li> {@link #FEATURE_VIDEO}
//     *                      <li> {@link #FEATURE_MUSICE}
//     * @param trim_user     返回值中user字段开关，false：返回完整user字段、true：user字段仅返回user_id，默认为false
//     * @param listener      异步请求回调接口
//     */
//    public static void friendsTimeline(long since_id, long max_id, int count, int page, boolean base_app, int featureType,
//                             boolean trim_user, final StatusedAgencyRequestListener listener) {
//        RequestListener requestListener = new RequestListener() {
//            @Override
//            public void onComplete(String s) {
//                StatusListModel mStatusModelList = StatusListModel.parse(s);
//                    insert(mStatusModelList);
//                listener.onComplete(mStatusModelList);
//            }
//            @Override
//            public void onWeiboException(WeiboException e) {
//                listener.onError(e);
//            }
//        };
//        sStatusesAPI.friendsTimeline(since_id,max_id,count,page,base_app,featureType,trim_user,requestListener);
//
//    }
//
//
//    public static List<? extends BaseModel> query() {
//        return sStatusDao.query().mStatusModelList;
//    }
//
//    public static List<? extends BaseModel> query(int count) {
//        return sStatusDao.query(count).mStatusModelList;
//    }
//
//    public static void insert(StatusModel bean) {
//        sStatusDao.insert(bean);
//    }
//
//    public static void insert(StatusListModel bean) {
//        sStatusDao.insert(bean);
//    }
//
//
//    public static void clear() {
//        sStatusDao.clear();
//    }
//
//
//    public interface StatusedAgencyRequestListener {
//        void onComplete(StatusListModel mStatusModelList);
//        void onError(Exception e);
//    }
//
//
//
//
//
//
//
//}

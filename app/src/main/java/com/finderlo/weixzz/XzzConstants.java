/*
 * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.finderlo.weixzz;

import com.sina.weibo.sdk.openapi.models.Geo;
import com.sina.weibo.sdk.openapi.models.User;
import com.sina.weibo.sdk.openapi.models.Visible;

import java.util.ArrayList;

/**
 * 该类定义了微博授权时所需要的参数。
 *
 * @author SINA
 * @since 2013-09-29
 */
public interface XzzConstants {

    /** 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY */

    /**
     * 这是finderlo的APP-key
     */
    public static final String APP_KEY = "1595953644";

    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * <p>
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
//    public static final String REDIRECT_URL = "http://www.sina.com";
    public static final String REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";

    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     * <p>
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     * <p>
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     * <p>
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
//    public static final String SCOPE = "";


    /**
     *下面这些参数是status实体类在database中存储的列名
     **/
    /**
     * 微博创建时间
     */
    public static final String STATUS_CREATED_AT = "created_at";
    /**
     * 微博ID
     */
    public static final String STATUS_ID = "id";
    /**
     * 微博MID
     */
    public static final String STATUS_MID = "mid";
    /**
     * 字符串型的微博ID
     */
    public static final String STATUS_IDSTR = "idstr";

    public static final String STATUS_USER_IDSTR = "user_idstr";
    /**
     * 微博信息内容
     */
    public static final String STATUS_TEXT = "text";
    /**
     * 微博来源
     */
    public static final String STATUS_SOURCE = "source";
    /**
     * 是否已收藏，true：是，false：否
     */
    public static final String STATUS_FAVORITED = "favorited";
    /**
     * 是否被截断，true：是，false：否
     */
    public static final String STATUS_TRUNCATED = "truncated";
    /**
     * （暂未支持）回复ID
     */
    public static final String STATUS_IN_REPLY_TO_STATUS_ID = "in_reply_to_status_id";
    /**
     * （暂未支持）回复人UID
     */
    public static final String STATUS_IN_REPLY_TO_USER_ID = "in_reply_to_user_id";
    /**
     * （暂未支持）回复人昵称
     */
    public static final String STATUS_IN_REPLY_TO_SCAEEN_NAME = "in_reply_to_screen_name";
    /**
     * 缩略图片地址（小图），没有时不返回此字段
     */
    public static final String STATUS_THUMNAIL_PIC = "thumbnail_pic";
    /**
     * 中等尺寸图片地址（中图），没有时不返回此字段
     */
    public static final String STATUS_BMIDDLE_PIC = "bmiddle_pic";
    /**
     * 原始图片地址（原图），没有时不返回此字段
     */
    public static final String STATUS_ORIGINAL_PIC = "original_pic";
    /**
     * 地理信息字段
     */
    public static final String STATUS_GEO = "geo";
    /**
     * 微博作者的用户信息字段
     */
    public static final String STATUS_USER = "user";
    /**
     * 被转发的原微博信息字段，当该微博为转发微博时返回
     */
    public static final String STATUS_RETWEETED_STATUS = "retweeted_status";
    /**
     * 转发数
     */
    public static final String STATUS_REPOSTS_COUNT = "reposts_count";
    /**
     * 评论数
     */
    public static final String STATUS_COMMENTS_COUNT = "comments_count";
    /**
     * 表态数
     */
    public static final String STATUS_ATTITUDES_COUNT = "attitudes_count";
    /**
     * 暂未支持
     */
    public static final String STATUS_MLEVEL = "mlevel";
    /**
     * 微博的可见性及指定可见分组信息。该 object 中 type 取值，
     * 0：普通微博，1：私密微博，3：指定分组微博，4：密友微博；
     * list_id为分组的组号
     */
    public static final String STATUS_VISIBLE = "visible";
    /** 微博配图地址。多图时返回多图链接。无配图返回"[]" */
//    public ArrayList<String> pic_urls;
    /** 微博流内的推广微博ID */
    //public Ad ad;


    /**
     *下面这些参数是user实体类在database中存储的列名
     **/
    /**
     * 用户UID（int64）
     */
    public static final String USER_ID = "id";
    /**
     * 字符串型的用户 UID
     */
    public static final String USER_IDSTR = "idstr";
    /**
     * 用户昵称
     */
    public static final String USER_SCREEN_NAME = "screen_name";
    /**
     * 友好显示名称
     */
    public static final String USER_NAME = "name";
    /**
     * 用户所在省级ID
     */
    public static final String USER_PROVINCE = "province";
    /**
     * 用户所在城市ID
     */
    public static final String USER_CITY = "city";
    /**
     * 用户所在地
     */
    public static final String USER_LOCATION = "location";
    /**
     * 用户个人描述
     */
    public static final String USER_DESCRIPTION = "description";
    /**
     * 用户博客地址
     */
    public static final String USER_URL = "url";
    /**
     * 用户头像地址，50×50像素
     */
    public static final String USER_PROFILE_IMAGE_URL = "profile_image_url";
    /**
     * 用户的微博统一URL地址
     */
    public static final String USER_PROFILE_URL = "profile_url";
    /**
     * 用户的个性化域名
     */
    public static final String USER_DOMAIN = "domain";
    /**
     * 用户的微号
     */
    public static final String USER_WEIHAO = "weihao";
    /**
     * 性别，m：男、f：女、n：未知
     */
    public static final String USER_GENDER = "gender";
    /**
     * 粉丝数
     */
    public static final String USER_FOLLOWERS_COUNT = "followers_count";
    /**
     * 关注数
     */
    public static final String USER_FRIENDS_COUNT = "friends_count";
    /**
     * 微博数
     */
    public static final String USER_STATUSES_COUNT = "statuses_count";
    /**
     * 收藏数
     */
    public static final String USER_FAVORITES_COUNT = "favourites_count";
    /**
     * 用户创建（注册）时间
     */
    public static final String USER_CREATED_AT = "created_at";
    /**
     * 暂未支持
     */
    public static final String USER_FOLLOWING = "following";
    /**
     * 是否允许所有人给我发私信，true：是，false：否
     */
    public static final String USER_ALL_ACT_MSG = "allow_all_act_msg";
    /**
     * 是否允许标识用户的地理位置，true：是，false：否
     */
    public static final String USER_GEO_ENABLED = "geo_enabled";
    /**
     * 是否是微博认证用户，即加V用户，true：是，false：否
     */
    public static final String USER_VERIFIED = "verified";
    /**
     * 暂未支持
     */
    public static final String USER_VERIFIED_TYPE = "verified_type";
    /**
     * 用户备注信息，只有在查询用户关系时才返回此字段
     */
    public static final String USER_REMARK = "remark";
    /**
     * 用户的最近一条微博信息字段
     */
    public static final String USER_STATUS = "status";
    /**
     * 是否允许所有人对我的微博进行评论，true：是，false：否
     */
    public static final String USER_ALLOW_ALL_COMMENT = "allow_all_comment";
    /**
     * 用户大头像地址
     */
    public static final String USER_AVATAR_LARGE = "avatar_large";
    /**
     * 用户高清大头像地址
     */
    public static final String USER_AVATAR_HD = "avatar_hd";
    /**
     * 认证原因
     */
    public static final String USER_VERIFIED_REASON = "verified_reason";
    /**
     * 该用户是否关注当前登录用户，true：是，false：否
     */
    public static final String USER_FOLLOW_ME = "follow_me";
    /**
     * 用户的在线状态，0：不在线、1：在线
     */
    public static final String USER_ONLINE_STATUS = "online_status";
    /**
     * 用户的互粉数
     */
    public static final String USER_BI_FOLLOWERS_COUNT = "bi_followers_count";
    /**
     * 用户当前的语言版本，zh-cn：简体中文，zh-tw：繁体中文，en：英语
     */
    public static final String USER_LANG = "lang";

    /**
     * 注意：以下字段暂时不清楚具体含义，OpenAPI 说明文档暂时没有同步更新对应字段
     */
    public static final String USER_STAR = "star";
    public String USER_MBTYPE = "mbtype";
    public static final String USER_MBRANK = "mbrank";
    public static final String USER_BLOCK_WORD = "block_word";

}

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

    /**
     * 微博ID
     */
    public static final String STATUS_ID = "id";
    /**微博MID*/
    public static final String STATUS_MID = "mid";
    /** 字符串型的微博ID*/
    public static final String STATUS_IDSTR = "idstr";


    /**
     *下面这些参数是user实体类在database中存储的列名
     **/
    /**用户UID（int64）*/
    public static final String USER_ID = "id";
    /**字符串型的用户 UID*/
    public static final String USER_IDSTR = "idstr";

    public static final String TEST_STATUS_JSON_STRING_1 =
            "{\"created_at\":\"Sun Aug 07 21:06:43 +0800 2016\",\"id\":4005945106838619,\"mid\":\"4005945106838619\",\"idstr\":\"4005945106838619\",\"text\":\"看到说GAY骗婚的帖子，楼主以自己的亲身经历说明为什么有些女孩会被骗婚\",\"textLength\":67,\"source_allowclick\":0,\"source_type\":2,\"source\":\"<a href=\\\"http:\\/\\/weibo.com\\/\\\" rel=\\\"nofollow\\\">iPhone 6s<\\/a>\",\"favorited\":false,\"truncated\":false,\"in_reply_to_status_id\":\"\",\"in_reply_to_user_id\":\"\",\"in_reply_to_screen_name\":\"\",\"pic_urls\":[{\"thumbnail_pic\":\"http:\\/\\/ww1.sinaimg.cn\\/thumbnail\\/005Hk6S7gw1f6lhiv40ppj30cl6axx00.jpg\"},{\"thumbnail_pic\":\"http:\\/\\/ww4.sinaimg.cn\\/thumbnail\\/005Hk6S7gw1f6lhirylc5j30cl6aqh6f.jpg\"},{\"thumbnail_pic\":\"http:\\/\\/ww3.sinaimg.cn\\/thumbnail\\/005Hk6S7gw1f6lhj18n3sj30cl7ufhdj.jpg\"},{\"thumbnail_pic\":\"http:\\/\\/ww1.sinaimg.cn\\/thumbnail\\/005Hk6S7gw1f6lhjfli2vj30cl6ap1e7.jpg\"},{\"thumbnail_pic\":\"http:\\/\\/ww1.sinaimg.cn\\/thumbnail\\/005Hk6S7gw1f6lhjsbb9mj30cl7wmquh.jpg\"},{\"thumbnail_pic\":\"http:\\/\\/ww1.sinaimg.cn\\/thumbnail\\/005Hk6S7gw1f6lhjv7u4hj30cl4oenhp.jpg\"}],\"thumbnail_pic\":\"http:\\/\\/ww1.sinaimg.cn\\/thumbnail\\/005Hk6S7gw1f6lhiv40ppj30cl6axx00.jpg\",\"bmiddle_pic\":\"http:\\/\\/ww1.sinaimg.cn\\/bmiddle\\/005Hk6S7gw1f6lhiv40ppj30cl6axx00.jpg\",\"original_pic\":\"http:\\/\\/ww1.sinaimg.cn\\/large\\/005Hk6S7gw1f6lhiv40ppj30cl6axx00.jpg\",\"geo\":null,\"user\":{\"id\":5220839587,\"idstr\":\"5220839587\",\"class\":1,\"screen_name\":\"豆瓣说\",\"name\":\"豆瓣说\",\"province\":\"44\",\"city\":\"1000\",\"location\":\"广东\",\"description\":\"嘻嘻\",\"url\":\"\",\"profile_image_url\":\"http:\\/\\/tva3.sinaimg.cn\\/crop.0.0.900.900.50\\/005Hk6S7jw8f26wtswleoj30p00p0jw9.jpg\",\"cover_image\":\"http:\\/\\/ss15.sinaimg.cn\\/crop.0.-25.980.245\\/OUdes&690\",\"cover_image_phone\":\"http:\\/\\/ww1.sinaimg.cn\\/crop.0.0.640.640.640\\/549d0121tw1egm1kjly3jj20hs0hsq4f.jpg\",\"profile_url\":\"u\\/5220839587\",\"domain\":\"\",\"weihao\":\"\",\"gender\":\"m\",\"followers_count\":2311748,\"friends_count\":90,\"pagefriends_count\":0,\"statuses_count\":767,\"favourites_count\":400,\"created_at\":\"Thu Jul 17 22:18:25 +0800 2014\",\"following\":true,\"allow_all_act_msg\":false,\"geo_enabled\":true,\"verified\":true,\"verified_type\":0,\"remark\":\"\",\"ptype\":0,\"allow_all_comment\":true,\"avatar_large\":\"http:\\/\\/tva3.sinaimg.cn\\/crop.0.0.900.900.180\\/005Hk6S7jw8f26wtswleoj30p00p0jw9.jpg\",\"avatar_hd\":\"http:\\/\\/tva3.sinaimg.cn\\/crop.0.0.900.900.1024\\/005Hk6S7jw8f26wtswleoj30p00p0jw9.jpg\",\"verified_reason\":\"微博知名博主\",\"verified_trade\":\"3370\",\"verified_reason_url\":\"\",\"verified_source\":\"\",\"verified_source_url\":\"\",\"verified_state\":0,\"verified_level\":3,\"verified_type_ext\":0,\"verified_reason_modified\":\"\",\"verified_contact_name\":\"\",\"verified_contact_email\":\"\",\"verified_contact_mobile\":\"\",\"follow_me\":false,\"online_status\":0,\"bi_followers_count\":43,\"lang\":\"zh-cn\",\"star\":0,\"mbtype\":12,\"mbrank\":3,\"block_word\":0,\"block_app\":1,\"credit_score\":80,\"user_ability\":8,\"urank\":17},\"annotations\":[{\"client_mblogid\":\"iPhone-0E2E0C60-AFB4-491D-9229-342FE23142B3\"},{\"mapi_request\":true}],\"picStatus\":\"0:1,1:1,2:1,3:1,4:1,5:1\",\"reposts_count\":2,\"comments_count\":6,\"attitudes_count\":17,\"isLongText\":false,\"mlevel\":0,\"visible\":{\"type\":0,\"list_id\":0},\"biz_feature\":4294967300,\"hasActionTypeCard\":0,\"darwin_tags\":[],\"hot_weibo_tags\":[],\"text_tag_tips\":[],\"rid\":\"2_0_1_2666885570820909429\",\"userType\":0,\"positive_recom_flag\":0,\"gif_ids\":\"\"}\n";
}

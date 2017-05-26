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

package me.ticknick.weixzz.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用户信息结构体。
 * 
 * @author SINA
 * @since 2013-11-24
 */
public class UserModel implements Parcelable{

    public transient long timestamp = System.currentTimeMillis(); // Time when wrote to database


    public long id;    //** 用户UID（int64） */
    public String idstr;    //** 字符串型的用户 UID */
    public String screen_name;    //** 用户昵称 */
    public String name;    //** 友好显示名称 */

    public int province;    //** 用户所在省级ID */
    public int city;    //** 用户所在城市ID */

    public String location;    //** 用户所在地 */
    public String description;    //** 用户个人描述 */
    public String url;    //** 用户博客地址 */
    public String profile_image_url;    //** 用户头像地址，50×50像素 */
    public String profile_url;    //** 用户的微博统一URL地址 */
    public String domain;    //** 用户的个性化域名 */
    public String weihao;    //** 用户的微号 */
    public String gender;    //** 性别，m：男、f：女、n：未知 */

    public int followers_count;    //** 粉丝数 */
    public int friends_count;    //** 关注数 */
    public int statuses_count;    //** 微博数 */
    public int favourites_count;    //** 收藏数 */

    public String created_at;    //** 用户创建（注册）时间 */

    public boolean following;    //** 暂未支持 */
    public boolean allow_all_act_msg;    //** 是否允许所有人给我发私信，true：是，false：否 */
    public boolean geo_enabled;    //** 是否允许标识用户的地理位置，true：是，false：否 */
    public boolean verified;    //** 是否是微博认证用户，即加V用户，true：是，false：否 */

    public int verified_type;    //** 暂未支持 */

    public String remark;    //** 用户备注信息，只有在查询用户关系时才返回此字段 */
    public StatusModel status;    //** 用户的最近一条微博信息字段 */
    public boolean allow_all_comment;    //** 是否允许所有人对我的微博进行评论，true：是，false：否 */
    public String avatar_large;    //** 用户大头像地址 */
    public String avatar_hd;    //** 用户高清大头像地址 */
    public String verified_reason;    //** 认证原因 */
    public boolean follow_me;    //** 该用户是否关注当前登录用户，true：是，false：否 */
    public int online_status;    //** 用户的在线状态，0：不在线、1：在线 */
    public int bi_followers_count;    //** 用户的互粉数 */
    public String lang;    //** 用户当前的语言版本，zh-cn：简体中文，zh-tw：繁体中文，en：英语 */


    //** 注意：以下字段暂时不清楚具体含义，OpenAPI 说明文档暂时没有同步更新对应字段 */
    public String star;
    public String mbtype;
    public String mbrank;
    public String block_word;

    public String cover_image = "";
    public String cover_image_phone = "";

    public String getCover() {
        return cover_image.trim().equals("") ? cover_image_phone : cover_image;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(screen_name);
        dest.writeString(name);
        dest.writeString(remark);
        dest.writeInt(province);
        dest.writeInt(city);
        dest.writeString(location);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeString(profile_image_url);
        dest.writeString(domain);
        dest.writeString(gender);
        dest.writeString(created_at);
        dest.writeString(avatar_large);
        dest.writeString(verified_reason);
        dest.writeInt(followers_count);
        dest.writeInt(friends_count);
        dest.writeInt(statuses_count);
        dest.writeInt(favourites_count);
        dest.writeInt(verified_type);
        dest.writeInt(online_status);
        dest.writeInt(bi_followers_count);
        dest.writeString(cover_image_phone);
        dest.writeString(cover_image);
        dest.writeBooleanArray(new boolean[]{following, allow_all_act_msg, geo_enabled, verified, allow_all_comment});
    }

    public static final Parcelable.Creator<UserModel> CREATOR = new Parcelable.Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel input) {
            UserModel ret = new UserModel();
            ret.id = input.readLong();
            ret.screen_name = input.readString();
            ret.name = input.readString();
            ret.remark = input.readString();
            ret.province = input.readInt();
            ret.city = input.readInt();
            ret.location = input.readString();
            ret.description = input.readString();
            ret.url = input.readString();
            ret.profile_image_url = input.readString();
            ret.domain = input.readString();
            ret.gender = input.readString();
            ret.created_at = input.readString();
            ret.avatar_large = input.readString();
            ret.verified_reason = input.readString();
            ret.followers_count = input.readInt();
            ret.friends_count = input.readInt();
            ret.statuses_count = input.readInt();
            ret.favourites_count = input.readInt();
            ret.verified_type = input.readInt();
            ret.online_status = input.readInt();
            ret.bi_followers_count = input.readInt();
            ret.cover_image_phone = input.readString();
            ret.cover_image = input.readString();

            boolean[] array = new boolean[5];
            input.readBooleanArray(array);

            ret.following = array[0];
            ret.allow_all_act_msg = array[1];
            ret.geo_enabled = array[2];
            ret.verified = array[3];
            ret.allow_all_comment = array[4];

            return ret;
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}

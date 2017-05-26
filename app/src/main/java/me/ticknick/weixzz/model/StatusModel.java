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
import android.text.SpannableString;
import android.util.Log;


import java.util.ArrayList;

/**
 * 微博结构体。
 *
 * @author SINA
 * @since 2013-11-22
 */
public class StatusModel implements Parcelable {

    public static final String TAG = "StatusModel";


    public String created_at;    // 微博创建时间 */
    public long id;
    public long mid;

    public String idstr;    // 字符串型的微博ID */
    public String text;    // 微博信息内容 */
    public String source;    //** 微博来源 */

    public boolean favorited;    //** 是否已收藏，true：是，false：否  */
    public boolean truncated;    //** 是否被截断，true：是，false：否 */
    public boolean liked;

    public String in_reply_to_status_id;   // /**（暂未支持）回复ID */
    public String in_reply_to_user_id;    //**（暂未支持）回复人UID */
    public String in_reply_to_screen_name;    //**（暂未支持）回复人昵称 */

    public String thumbnail_pic;    //** 缩略图片地址（小图），没有时不返回此字段 */
    public String bmiddle_pic;    //** 中等尺寸图片地址（中图），没有时不返回此字段 */
    public String original_pic;    //** 原始图片地址（原图），没有时不返回此字段 */

    public GeoModel geo;    //** 地理信息字段 */
    public UserModel user;    //** 微博作者的用户信息字段 */
    public StatusModel retweeted_status;    //** 被转发的原微博信息字段，当该微博为转发微博时返回 */

    public int reposts_count;    //** 转发数 */
    public int comments_count;    //** 评论数 */
    public int attitudes_count;    //** 表态数 */

    public int mlevel;    //** 暂未支持 */

    /**
     * 微博的可见性及指定可见分组信息。该 object 中 type 取值，
     * 0：普通微博，1：私密微博，3：指定分组微博，4：密友微博；
     * list_id为分组的组号
     */
//    public com.finderlo.weixzz.sinaapi.openapi.models.Visible visible;
    /**
     * 微博配图地址。多图时返回多图链接。无配图返回"[]"
     */
    public ArrayList<PictureUrl> pic_urls = new ArrayList<PictureUrl>();

    public transient SpannableString span, origSpan;
//    public transient long millis;



    @Override
    public boolean equals(Object o) {
        if (o instanceof StatusModel) {
            return ((StatusModel) o).id == id;
        } else {
            return super.equals(o);
        }
    }

    @Override
    public int hashCode() {
        return idstr.hashCode();
    }


    public static class PictureUrl implements Parcelable {

        // Picture url
        // OMG Sina why you use a special class for a simple data!

        private String thumbnail_pic;

        public String getThumbnail() {
            return thumbnail_pic;
        }

        public String getLarge() {
            return thumbnail_pic.replace("thumbnail", "large");
        }

        public String getMedium() {
            return thumbnail_pic.replace("thumbnail", "bmiddle");
        }

        public boolean isGif(){

            return thumbnail_pic.endsWith(".gif");
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(thumbnail_pic);
        }

        public static final Creator<PictureUrl> CREATOR = new Creator<PictureUrl>() {

            @Override
            public StatusModel.PictureUrl createFromParcel(Parcel in) {
                StatusModel.PictureUrl ret = new StatusModel.PictureUrl();
                ret.thumbnail_pic = in.readString();
                return ret;
            }

            @Override
            public StatusModel.PictureUrl[] newArray(int size) {
                return new StatusModel.PictureUrl[size];
            }


        };

    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(created_at);

        dest.writeLong(id);
        dest.writeLong(mid);

        dest.writeString(idstr);
        dest.writeString(text);
        dest.writeString(source);
        dest.writeBooleanArray(new boolean[]{favorited, truncated, liked});
        dest.writeString(in_reply_to_status_id);
        dest.writeString(in_reply_to_user_id);
        dest.writeString(in_reply_to_screen_name);
        dest.writeString(thumbnail_pic);
        dest.writeString(bmiddle_pic);
        dest.writeString(original_pic);

        dest.writeParcelable(geo, flags);
        dest.writeParcelable(user, flags);
        dest.writeParcelable(retweeted_status, flags);

        dest.writeInt(reposts_count);
        dest.writeInt(comments_count);
        dest.writeInt(attitudes_count);


            dest.writeTypedList(pic_urls);


//        dest.writeLong(millis);

    }

    public static final Creator<StatusModel> CREATOR = new Creator<StatusModel>() {

        @Override
        public StatusModel createFromParcel(Parcel in) {
            StatusModel ret = new StatusModel();
            ret.created_at = in.readString();
            ret.id = in.readLong();
            ret.mid = in.readLong();
            ret.idstr = in.readString();
            ret.text = in.readString();
            ret.source = in.readString();

            Log.d(TAG, "createFromParcel: ret.text"+ret.text);

            boolean[] array = new boolean[3];
            in.readBooleanArray(array);

            ret.favorited = array[0];
            ret.truncated = array[1];
            ret.liked = array[2];

            Log.d(TAG, "createFromParcel: ret.liked"+String.valueOf(ret.liked));

            ret.in_reply_to_status_id = in.readString();
            ret.in_reply_to_user_id = in.readString();
            ret.in_reply_to_screen_name = in.readString();
            ret.thumbnail_pic = in.readString();
            ret.bmiddle_pic = in.readString();
            ret.original_pic = in.readString();

            ret.geo = in.readParcelable(GeoModel.class.getClassLoader());
            ret.user = in.readParcelable(UserModel.class.getClassLoader());
            ret.retweeted_status = in.readParcelable(StatusModel.class.getClassLoader());

            Log.d(TAG, "createFromParcel: ret.user"+ret.user.screen_name);

            ret.reposts_count = in.readInt();
            ret.comments_count = in.readInt();
            ret.attitudes_count = in.readInt();




                in.readTypedList(ret.pic_urls, PictureUrl.CREATOR);




//            ret.millis = in.readLong();


            return ret;
        }

        @Override
        public StatusModel[] newArray(int size) {
            return new StatusModel[size];
        }


    };
}

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

/**
 * 评论结构体。
 *
 * @author SINA
 * @since 2013-11-24
 */
public class CommentModel implements Parcelable {

    public String created_at;    //** 评论创建时间 */
    public long id;    //** 评论的 ID */
    public long mid;    //** 评论的 MID */
    public String idstr;    //** 字符串型的评论 ID */

    public String text;    //** 评论的内容 */
    public String source;    //** 评论的来源 */

    public UserModel user;    //** 评论作者的用户信息字段 */
    public StatusModel status;    //** 评论的微博信息字段 */

    public CommentModel reply_comment;    //** 评论来源评论，当本评论属于对另一评论的回复时返回此字段 */


    protected CommentModel(Parcel in) {
        created_at = in.readString();
        id = in.readLong();
        mid = in.readLong();
        idstr = in.readString();
        text = in.readString();
        source = in.readString();
        user = in.readParcelable(UserModel.class.getClassLoader());
        status = in.readParcelable(StatusModel.class.getClassLoader());
        reply_comment = in.readParcelable(CommentModel.class.getClassLoader());
    }

    public static final Creator<CommentModel> CREATOR = new Creator<CommentModel>() {
        @Override
        public CommentModel createFromParcel(Parcel in) {
            return new CommentModel(in);
        }

        @Override
        public CommentModel[] newArray(int size) {
            return new CommentModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(created_at);
        parcel.writeLong(id);
        parcel.writeLong(mid);
        parcel.writeString(idstr);
        parcel.writeString(text);
        parcel.writeString(source);
        parcel.writeParcelable(user, i);
        parcel.writeParcelable(status, i);
        parcel.writeParcelable(reply_comment, i);
    }
}

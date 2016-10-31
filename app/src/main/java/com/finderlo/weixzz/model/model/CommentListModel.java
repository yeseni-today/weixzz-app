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

package com.finderlo.weixzz.model.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论列表结构体。
 *
 * @author SINA
 * @since 2013-11-24
 */
public class CommentListModel extends BaseListModel<CommentModel, CommentListModel> {

    /**
     * 微博列表
     */
    public ArrayList<CommentModel> comments = new ArrayList<CommentModel>();

    @Override
    public int getSize() {
        return comments.size();
    }

    @Override
    public CommentModel get(int position) {
        return comments.get(position);
    }

    @Override
    public List<CommentModel> getList() {
        return comments;
    }

    @Override
    public void addAll(boolean toTop, CommentListModel values) {
        if (values instanceof CommentListModel && values != null && values.getSize() > 0) {
            for (CommentModel msg : values.getList()) {
                if (!comments.contains(msg)) {
                    comments.add(toTop ? values.getList().indexOf(msg) : comments.size(),  msg);
                }
            }
            total_number = values.total_number;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(total_number);
        parcel.writeLong(previous_cursor);
        parcel.writeLong(next_cursor);
        parcel.writeTypedList(comments);
    }

    public static Parcelable.Creator<CommentListModel> CREATOR = new Creator<CommentListModel>() {
        @Override
        public CommentListModel createFromParcel(Parcel parcel) {
            CommentListModel commentListModel = new CommentListModel();
            commentListModel.total_number = parcel.readInt();
            commentListModel.previous_cursor = parcel.readLong();
            commentListModel.next_cursor = parcel.readLong();
            parcel.readTypedList(commentListModel.comments,CommentModel.CREATOR);
            return null;
        }

        @Override
        public CommentListModel[] newArray(int i) {
            return new CommentListModel[i];
        }
    };
}

package com.finderlo.weixzz.model.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finderlo on 2016/8/24.
 */


public class RepostListModel extends BaseListModel<StatusModel, RepostListModel>
{


    private List<StatusModel> reposts = new ArrayList<StatusModel>();



    @Override
    public int getSize() {
        return reposts.size();
    }

    @Override
    public StatusModel get(int position) {
        return reposts.get(position);
    }

    @Override
    public List<StatusModel> getList() {
        return reposts;
    }



    @Override
    public void addAll(boolean toTop, RepostListModel values) {
        if (values != null && values.getSize() > 0) {
            for (StatusModel msg : values.getList()) {
                if (!reposts.contains(msg) &&msg.user!=null) {
                    reposts.add(toTop ? values.getList().indexOf(msg) : reposts.size(), msg);
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(total_number);
        dest.writeLong(previous_cursor);
        dest.writeLong(next_cursor);
        dest.writeTypedList(reposts);
    }

    public static final Parcelable.Creator<RepostListModel> CREATOR = new Parcelable.Creator<RepostListModel>() {

        @Override
        public RepostListModel createFromParcel(Parcel in) {
            RepostListModel ret = new RepostListModel();
            ret.total_number = in.readInt();
            ret.previous_cursor = in.readLong();
            ret.next_cursor = in.readLong();
            in.readTypedList(ret.reposts, StatusModel.CREATOR);

            return ret;
        }

        @Override
        public RepostListModel[] newArray(int size) {
            return new RepostListModel[size];
        }

    };

}

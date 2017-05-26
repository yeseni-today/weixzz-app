/* 
 * Copyright (C) 2014 Peter Cai
 *
 * This file is part of BlackLight
 *
 * BlackLight is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlackLight is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BlackLight.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.ticknick.weixzz.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/**
  List of messages
  From timelines
  credits to: qii, PeterCxy
  author: shaw
**/
public class StatusListModel extends BaseListModel<StatusModel, StatusListModel>
{
	private class AD {
		public long id = -1;
		public String mark = "";

		@Override
		public boolean equals(Object o) {
			if (o instanceof AD) {
				return ((AD) o).id == id;
			} else {
				return super.equals(o);
			}
		}

		@Override
		public int hashCode() {
			return String.valueOf(id).hashCode();
		}
	}
	
	private List<StatusModel> statuses = new ArrayList<StatusModel>();
	private List<AD> ad = new ArrayList<AD>();
	


	
	@Override
	public int getSize() {
		return statuses.size();
	}

	@Override
	public StatusModel get(int position) {
		return statuses.get(position);
	}

	@Override
	public List<StatusModel> getList() {
		return statuses;
	}

	@Override
	public void addAll(boolean toTop, StatusListModel values) {
		if (values != null && values.getSize() > 0) {
			for (StatusModel msg : values.getList()) {
				if (!statuses.contains(msg) && !values.ad.contains(msg.id)&&msg.user!=null) {
					statuses.add(toTop ? values.getList().indexOf(msg) : statuses.size(), msg);
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
		dest.writeTypedList(statuses);
	}
	
	public static final Parcelable.Creator<StatusListModel> CREATOR = new Parcelable.Creator<StatusListModel>() {

		@Override
		public StatusListModel createFromParcel(Parcel in) {
			StatusListModel ret = new StatusListModel();
			ret.total_number = in.readInt();
			ret.previous_cursor = in.readLong();
			ret.next_cursor = in.readLong();
			in.readTypedList(ret.statuses, StatusModel.CREATOR);
			
			return ret;
		}

		@Override
		public StatusListModel[] newArray(int size) {
			return new StatusListModel[size];
		}

	};

}

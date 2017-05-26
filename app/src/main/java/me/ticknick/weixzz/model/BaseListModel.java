package me.ticknick.weixzz.model;

import android.os.Parcelable;

/**
 * Created by Finderlo on 2016/8/20.
 */

public abstract class BaseListModel<Item,List> implements Parcelable {

    public int total_number = 0;
    public long previous_cursor = 0, next_cursor = 0;

    public abstract int getSize();
    public abstract Item get(int position);

    public abstract java.util.List<Item> getList();

    /*
  @param toTop If true, add to top, else add to bottom
  @param values All values needed to be added
*/
    public abstract void addAll(boolean toTop, List values);

}

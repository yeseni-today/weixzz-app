package me.ticknick.weixzz.util;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import me.ticknick.weixzz.widgt.BaseImageView;

/**
 * Created by Finderlo on 2016/8/8.
 */
public class ImageLoader {

    public static void load(Context context, String imageUrl, BaseImageView imageView) {
        Picasso.with(context).load(imageUrl).into(imageView);
        imageView.setPic_Url(imageUrl);
    }

    public static void load(Context context, String imageUrl, ImageView imageView) {
        Picasso.with(context).load(imageUrl).into(imageView);
    }
}

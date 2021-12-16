package io.b4a.itms.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageUtil {

    public static void loadFromUrl(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
//                .placeholder(R.drawable.placeholder)
                .circleCrop() // create an effect of a round profile picture
                .into(imageView);
    }
}

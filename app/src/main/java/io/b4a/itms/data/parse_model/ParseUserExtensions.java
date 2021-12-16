package io.b4a.itms.data.parse_model;

import android.content.Context;
import android.widget.ImageView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import io.b4a.itms.R;
import io.b4a.itms.utils.ImageUtil;

public class ParseUserExtensions {
    private final static String KEY_AVATAR = "avatar";
    private final static String KEY_NICKNAME = "nickname";
    private final static String KEY_PHONE = "phone";
    private final static String KEY_ADDRESS = "address";

    private static final String emptyProperty(String text) {
        return "暂无" + text;
    }

    public static final void setAddress(ParseUser parseUser, String address) {
        parseUser.put(KEY_ADDRESS, address);
    }

    public static final String getAddress(ParseUser parseUser) {
        String out = parseUser.getString(KEY_ADDRESS);
        if (null == out) {
            out = emptyProperty("居住地址");
        }
        return out;
    }

    public static final void setPhone(ParseUser parseUser, String phone) {
        parseUser.put(KEY_PHONE, phone);
    }

    public static final String getPhone(ParseUser parseUser) {
        String out = parseUser.getString(KEY_PHONE);
        if (null == out) {
            out = emptyProperty("联系电话");
        }
        return out;
    }

    public static final void setNickname(ParseUser parseUser, String nickname) {
        parseUser.put(KEY_NICKNAME, nickname);
    }

    public static final String getNickname(ParseUser parseUser) {
        String out = parseUser.getString(KEY_NICKNAME);
        if (null == out) {
            out = emptyProperty("用户昵称");
        }
        return out;
    }

    public static final ParseFile getAvatar(ParseUser parseUser) {
        try {
            return parseUser.fetchIfNeeded().getParseFile(KEY_AVATAR);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final void loadAvatar(Context context, ParseUser parseUser, ImageView imageView) {
        ParseFile avatar = ParseUserExtensions.getAvatar(parseUser);
        if (avatar != null) {
            ImageUtil.loadFromUrl(context, avatar.getUrl(), imageView);
        } else {
            imageView.setImageResource(R.drawable.icons8_test_account_100);
        }
    }
}

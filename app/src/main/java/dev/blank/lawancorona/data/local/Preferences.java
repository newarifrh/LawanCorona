package dev.blank.lawancorona.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dev.blank.lawancorona.R;

public class Preferences {
    private static final String KEY_WILAYAH = "wilayah";

    private static SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setWilayah(Context context, String wilayah) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_WILAYAH, wilayah);
        editor.apply();
    }

    public static String getWilayah(Context context) {
        return getSharedPreference(context).getString(KEY_WILAYAH, context.getString(R.string.dunia));
    }
}

package com.app.engineeringaipracticaltest.utility;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import timber.log.Timber;

public class Utility {

    private static final String DATE_TIME_MM_dd_yyyy_hh_mm_ss = "MM/dd/yyyy hh:mm:ss";

    private static final String DATE_TIME_MM_dd_yyyy_hh_mm_a = "MM/dd/yyyy hh:mm a";


    private Utility() {//Private constructor
    }

    // check object is null or not.
    public static boolean isNotEmpty(Object object) {
        return object != null && !((("").equals(object.toString().trim())) || (("null")
            .equalsIgnoreCase(object.toString().trim())));
    }

    // check object is null or not.
    public static boolean isEmpty(@Nullable List list) {
        return list == null || list.isEmpty();
    }

    // check object is null or not.
    public static int size(@Nullable List list) {
        return list == null ? 0 : list.size();
    }

    /**
     * checks network availability and shows toast if not available
     *
     * @param context current context
     * @return true if available, false otherwise
     */
    public static boolean isNetworkAvailable(final Context context) {
        if (context == null)
            return false;
        ConnectivityManager cm = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public static Date getDateFromString(@Nullable String dateString, String dateFormate) {
        if (dateString == null)
            return null;
        SimpleDateFormat format = new SimpleDateFormat(dateFormate, Locale.getDefault());
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            Timber.e("ParseException" + e);
        }
        return date;
    }

    public static String getStringFromDate(@NonNull Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_MM_dd_yyyy_hh_mm_ss,
            Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(date);
    }

    public static String getStringFromDate(@NonNull Date date, @NonNull String formatString) {
        SimpleDateFormat format = new SimpleDateFormat(formatString, Locale.getDefault());
        return format.format(date);
    }


    @Nullable
    public static Date getDateFromString(@Nullable String dateString) {
        if (dateString == null)
            return null;
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_MM_dd_yyyy_hh_mm_a,
            Locale.getDefault());
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            Timber.e("ParseException" + e);
        }
        return null;
    }

    public static void loadCircularImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
            .load(imageUrl).apply(new RequestOptions().circleCrop())
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(view);
    }

    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(view);
    }

    public static String convertTimestampToDate(Long timeStamp) {
        if (timeStamp > 0) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    DATE_TIME_MM_dd_yyyy_hh_mm_a, Locale.getDefault());
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                return simpleDateFormat.format(new Date(timeStamp));
            } catch (Exception e) {
                return null;
            }

        } else {
            return null;
        }
    }
}

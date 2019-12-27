package com.app.engineeringaipracticaltest.utility;

import java.util.List;

import androidx.annotation.Nullable;

public class Utility {

    private Utility() {//Private constructor
    }

    // check object is null or not.
    public static boolean isEmpty(@Nullable List list) {
        return list == null || list.isEmpty();
    }
}

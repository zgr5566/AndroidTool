package com.zgr.tool.other;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.UUID;

public class Utils {

    /**
     * 获取去除 - 的UUID
     */
    public static String getFormatUUID() {
        String id = "";
        try {
            String[] ids = UUID.randomUUID().toString().split("-");
            for (int i = 0; i < ids.length; i++) {
                id = id.concat(ids[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }


    public static void setEditFocus(EditText view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setSelection(view.getText().length());
    }

    public static void softInputOpenChange(Activity activity, boolean open) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (null == imm) {
                return;
            }
            if (open) {
                if (activity.getCurrentFocus() != null) {
                    imm.showSoftInput(activity.getCurrentFocus(), 0);
                } else {
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            } else {
                if (activity.getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

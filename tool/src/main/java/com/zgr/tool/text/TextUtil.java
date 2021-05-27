package com.zgr.tool.text;

import android.content.Context;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.TextView;


import com.zgr.tool.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 */
public class TextUtil {

    /**
     * 字符串判空
     *
     * @param str 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String str) {
        return isEmpty(str, true);
    }

    public static boolean isEmpty(String str, boolean checkNullStr) {
        if (checkNullStr && "null".equalsIgnoreCase(str)) {
            return true;
        }
        if (str == null || "".equals(str)) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static boolean isEquals(String s1, String s2) {
        return isEquals(s1, s2, true);
    }

    public static boolean isEquals(String s1, String s2, boolean nullDefault) {
        if (TextUtil.isEmpty(s1) && TextUtil.isEmpty(s2)) return nullDefault;
        if (!TextUtil.isEmpty(s1)) {
            return s1.equals(s2);
        }
        return false;
    }

    /**
     * 获取指定文字的宽度
     *
     * @param paint 笔
     * @param text  文本
     */
    public static float getFontWidth(Paint paint, String text) {
        return paint.measureText(text);
    }

    /**
     * @return 返回指定的文字高度
     */
    public static float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();    //文字基准线的下部距离-文字基准线的上部距离 = 文字高度
        return fm.descent - fm.ascent;
    }

    public static SpannableString getPartTextColorString(CharSequence content, int start, int end, int color) {
        return getPartTextColorAndClickString(content, start, end, color, null);
    }

    public static SpannableString getPartTextColorAndClickString(CharSequence content, int start, int end, int color, final View.OnClickListener onClickListener) {
        if (content == null || "".equals(content)) {
            return new SpannableString("");
        }
        SpannableString spannableString = new SpannableString(content);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(colorSpan, start, end, Spannable.SPAN_MARK_MARK);
        if (onClickListener != null) {
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClick(view);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false);
                }
            };
            spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_MARK_MARK);
        }
        return spannableString;
    }

    public static String getNonNullString(String string) {
        return string == null ? "" : string;
    }


    public static float getTextWidth(Context context, String text, int textSize) {
        return getTextWidth(context, text, textSize, false);
    }

    public static float getTextWidth(Context context, String text, int textSize, boolean isBlod) {
        if (context == null) {
            return 0;
        }
        TextPaint paint = new TextPaint();
        paint.setTextSize(textSize);
        paint.setFakeBoldText(isBlod);
        return paint.measureText(text);
    }

    public static SpannableStringBuilder getTwoMaxLineOmit(Context context, SpannableStringBuilder content, int viewWidth, int textSize) {
        if (content == null || viewWidth <= 0 || textSize <= 0) {
            return new SpannableStringBuilder();
        }

        SpannableStringBuilder firstLineBuild = new SpannableStringBuilder();
        SpannableStringBuilder secondLineBuild = new SpannableStringBuilder();
        float textWidth = getTextWidth(context, content.toString(), textSize);
        if (textWidth < viewWidth) {
            return content;
        }
        for (int i = 1; i <= content.length(); i++) {
            CharSequence subContent = content.subSequence(0, i);
            textWidth = getTextWidth(context, subContent.toString(), textSize);
            if (textWidth >= viewWidth) {
                firstLineBuild.append(subContent);
                break;
            }
        }

        if (isEquals(firstLineBuild.toString(), content.toString())) {
            return content;
        }

        CharSequence secondLineText = content.subSequence(firstLineBuild.length(), content.length());
        textWidth = getTextWidth(context, secondLineText.toString(), textSize);
        if (textWidth < viewWidth) {
            return content;
        }


        for (int i = 1; i <= secondLineText.length(); i++) {
            CharSequence subContent = secondLineText.subSequence(0, i);
            String tempText = subContent + "...";
            textWidth = getTextWidth(context, tempText, textSize);
            if (textWidth >= viewWidth) {
                secondLineBuild.append(secondLineText.subSequence(0, i - 1)).append("...");
                break;
            }
        }
        return firstLineBuild.append(secondLineBuild);
    }

    public static String getTwoMaxLineOmit(Context context, String content, int viewWidth, int textSize) {
        if (isEmpty(content) || viewWidth <= 0 || textSize <= 0) {
            return content;
        }

        float textWidth = getTextWidth(context, content, textSize);
        if (textWidth < viewWidth) {
            return content;
        }
        String firstLineText = content;
        int firstLineLastIndex = content.length();
        for (int i = 1; i <= content.length(); i++) {
            String subContent = content.substring(0, i);
            textWidth = getTextWidth(context, subContent, textSize);
            if (textWidth >= viewWidth) {
                break;
            }
            firstLineText = subContent;
            firstLineLastIndex = i;
        }
        if (isEquals(firstLineText, content)) {
            return content;
        }
        String secondLineText = content.substring(firstLineLastIndex);
        textWidth = getTextWidth(context, secondLineText, textSize);
        if (textWidth < viewWidth) {
            return content;
        }
        String omitSecondLineText = "";
        for (int i = 1; i <= secondLineText.length(); i++) {
            String subContent = secondLineText.substring(0, i).concat("...");
            textWidth = getTextWidth(context, subContent, textSize);
            if (textWidth >= viewWidth) {
                break;
            }
            omitSecondLineText = subContent;
        }
        return firstLineText + omitSecondLineText;
    }


    public static void setTextBold(TextView textView) {
        if (textView == null) {
            return;
        }
        textView.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        textView.getPaint().setStrokeWidth(0.7F);
    }

    public static SpannableString matcherSearchText(Context context, int styleResId, String text, String keyword) {
        SpannableString ss = new SpannableString(text);
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new TextAppearanceSpan(context, styleResId), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//new ForegroundColorSpan(color)
        }
        return ss;
    }

}

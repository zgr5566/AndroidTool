package com.zgr.tool.date;

import com.zgr.tool.text.TextUtil;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 日期工具
 * Created by zgr on 2016/8/30.
 */
public class DateUtil {

    public static final String FORMAT_STR_DEFAULT = "yyyy-MM-dd";
    public static final String FORMAT_STR_ALL = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_STR_SEPARATE_BY_POINT = "yyyy.MM.dd";
    public static final String FORMAT_STR_MD_POINT = "MM.dd";
    public static final String FORMAT_STR_YMD = "yyyy年MM月dd日";
    public static final String FORMAT_STR_YMD_HM = "yyyy年MM月dd日 HH:mm";
    public static final String FORMAT_STR_YMD_HM_P = "yyyy-MM-dd-HH-mm";
    public static final String FORMAT_STR_MD_HM = "MM月dd日 HH:mm";
    public static final String FORMAT_STR_TIME = "HH:mm";
    public static final String FORMAT_STR_HMS = "HH:mm:ss";
    public static final String SERVICE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSSS";
    public static final String SERVICE_TIME_FORMAT_IOS = "yyyy-MM-ddHH:mm:ss";
    public static final String FORMAT_STR_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_STR_YM = "yyyy-MM";
    public static final String FORMAT_STR_MD = "MM-dd";
    public static final String FORMAT_STR_Y = "yyyy年";
    public static final String FORMAT_STR_YM_C = "yyyy年MM月";
    public static final String FORMAT_STR_YM_C_2 = "yyyy年M月";
    public static final String FORMAT_STR_YMD_C = "yyyy年M月d日";
    public static final String FORMAT_STR_Y_I = "yyyy";
    public static final String FORMAT_STR_YM_I = "yyyyMM";
    public static final String FORMAT_STR_M_I = "MM";
    public static final String FORMAT_STR_YMD_I = "yyyyMMdd";
    public static final String FORMAT_STR_MD_X = "MM/dd";
    public static final String FORMAT_STR_MD_SC = "M 月 d 日";
    public static final String FORMAT_STR_M_SC = "M 月";


    private static final long DAY_TIME = 24 * 60 * 60 * 1000;

    /**
     * 获取当前日期星期几
     *
     * @param year  年
     * @param month 月
     * @return 星期几
     */
    public static int getWeek(int year, int month, int day) {
        try {
            Calendar instance = Calendar.getInstance();
            instance.set(Calendar.YEAR, year);
            instance.set(Calendar.MONTH, month);
            instance.set(Calendar.DATE, day);
            return instance.get(Calendar.DAY_OF_WEEK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取一个月又多少天
     */
    public static int getMonthAllDay(int year, int month) {
        return getDaysOfMonth(isLeapYear(year), month);
    }

    public static int getMonthAllDay(String date) {
        try {
            date = getFormatTime(date, FORMAT_STR_YM);
            String[] dates = date.split("-");
            int year = Integer.parseInt(dates[0]);
            int month = Integer.parseInt(dates[1]);
            return getDaysOfMonth(isLeapYear(year), month);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断是否为闰年
     */
    public static boolean isLeapYear(int year) {
        if (year % 100 == 0 && year % 400 == 0) {
            return true;
        } else return year % 100 != 0 && year % 4 == 0;
    }

    /**
     * 得到某月有多少天数
     */
    public static int getDaysOfMonth(boolean isLeapyear, int month) {
        int daysOfMonth = 30;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                daysOfMonth = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                daysOfMonth = 30;
                break;
            case 2:
                if (isLeapyear) {
                    daysOfMonth = 29;
                } else {
                    daysOfMonth = 28;
                }
        }
        return daysOfMonth;
    }

    public static String theSameDay() {
        String day = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat(FORMAT_STR_ALL);//设置日期格式
            day = df.format(new Date()).split(" ")[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TextUtil.getNonNullString(day);
    }

    public static long getTimeMillis(String strTime) {
        long returnMillis = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date d = null;
        try {
            d = sdf.parse(strTime);
            returnMillis = d.getTime();
        } catch (ParseException e) {
        }
        return returnMillis;
    }

    private String getTimeExpend(String startTime, String endTime) {
        //传入字串类型 2016/06/28 08:30
        long longStart = getTimeMillis(startTime); //获取开始时间毫秒数
        long longEnd = getTimeMillis(endTime);  //获取结束时间毫秒数
        long longExpend = longEnd - longStart;  //获取时间差

        long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数
        long longMinutes = (longExpend - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数

        return longHours + ":" + longMinutes;
    }

    private String getTimeString(String endTime, String expendTime) {
        //传入字串类型 end:2016/06/28 08:30 expend: 03:25
        long longEnd = getTimeMillis(endTime);
        String[] expendTimes = expendTime.split(":");   //截取出小时数和分钟数
        long longExpend = Long.parseLong(expendTimes[0]) * 60 * 60 * 1000 + Long.parseLong(expendTimes[1]) * 60 * 1000;
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return sdfTime.format(new Date(longEnd - longExpend));
    }

    /**
     * * 传进来一个日期列表  返回 下个月以及本月的日期列表；
     *
     * @param date 日期 貌似不能为空
     * @return 返回下个月以及本月的日期list
     */
    public static List<String> nextMonthDate(List<String> date) {
        try {
            ArrayList<String> objects = new ArrayList<>();
            objects.addAll(date);
            String[] mDate = objects.get(objects.size() - 5).split("-");
            int mYear = Integer.parseInt(mDate[0]);
            int mMonth = Integer.parseInt(mDate[1]);

            if (12 < (mMonth + 1)) {
                int allDay = getMonthAllDay((mYear + 1), (1));
                for (int i = 1; i < allDay + 1; i++) {
                    objects.add((mYear + 1) + "-" + 1 + "-" + i);
                }
            } else {
                int allDay = getMonthAllDay((mYear), (mMonth + 1));
                for (int i = 1; i < allDay + 1; i++) {
                    objects.add((mYear) + "-" + (mMonth + 1) + "-" + i);
                }
            }
            return objects;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 传进来一个日期列表  返回 上个月以及本月的日期列表；
     *
     * @param date 日期list 貌似不能为空
     * @return 返回上个月以及本月的日期list
     */
    public static List<String> upMonthDate(List<String> date) {
        try {
            ArrayList<String> save = new ArrayList<>();
            String[] day = date.get(0).split("-");

            int iYear = Integer.parseInt(day[0]);
            int iMonth = Integer.parseInt(day[1]);

            if (0 < (iMonth - 1)) {
                int allDay = getMonthAllDay(iYear, (iMonth - 1));
                for (int i = 1; i < allDay + 1; i++) {
                    save.add(iYear + "-" + (iMonth - 1) + "-" + i);
                }
            } else {
                int allDay = getMonthAllDay((iYear - 1), 12);
                for (int i = 1; i < allDay + 1; i++) {
                    save.add((iYear - 1) + "-" + 12 + "-" + i);
                }
            }

            save.addAll(date);
            return save;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * * 传进来一个日期  返回前一个月和后一个月的日期列表；
     *
     * @param dateStr 日期 格式 yyyy-MM-dd
     * @return 返回前一个月和后一个月的日期List
     */
    public static List<String> beforeAndNextMonthDate(String dateStr) {
        try {
            List<String> objects = new ArrayList<>();
            SimpleDateFormat format = new SimpleDateFormat(DateUtil.FORMAT_STR_DEFAULT);
            Date date = format.parse(dateStr);

            for (int i = 0; i < 61; i++) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DATE, i - 30);
                String day = format.format(calendar.getTime());
                objects.add(getCorrectDayString(day));
            }

            return objects;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static String getCorrectDayString(String selectDateString) {
        String[] split = selectDateString.split("-");
        String day = split[2];
        if (day.contains("0")) {
            int i = day.indexOf("0");
            int length = day.length();
            if (i == 0 && length == 2) {
                selectDateString = split[0] + "-" + split[1] + "-" + day.substring(1, length);
            }
        }
        return selectDateString;
    }

    /**
     * 传进来一个日期 返回当月的日期列表  要是传进来空的 就会返回当前月份的日期列表
     *
     * @param date 日期
     * @return 返回当前日期列表
     */
    public static List<String> thisMonthDate(String date) {
        try {
            ArrayList<String> str = new ArrayList<>();
            if (date != null && !"".equals(date) && !date.equals("null")) {
                String[] mDay = date.split("-");
                String year = mDay[0];
                String month = mDay[1];
                int iYear = Integer.parseInt(year);
                int iMonth = Integer.parseInt(month);
                int allDay = getMonthAllDay(iYear, iMonth);
                for (int i = 1; i < allDay + 1; i++) {
                    str.add(year + "-" + (month) + "-" + i);
                }
            } else {
                Calendar ca = Calendar.getInstance();
                int year = ca.get(Calendar.YEAR);//获取年份
                int month = ca.get(Calendar.MONTH);//获取月份
                int allDay = getMonthAllDay(year, month + 1);
                for (int i = 1; i < allDay + 1; i++) {
                    str.add(year + "-" + (month + 1) + "-" + i);
                }

            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 获取当前天数是第几天
     *
     * @param date
     * @return
     */
    public static int thisDay(String date) {
        try {
            int day;
            if (!TextUtil.isEmpty(date)) {
                String t = date.split("-")[2].split("T")[0];
                day = Integer.parseInt(t);
            } else {
                day = Calendar.getInstance().get(Calendar.DATE);
            }
            if (day > 0) {
                day = day - 1;
            } else {
                day = 0;
            }
            return day;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return 日期
     */

    public static Date parse(String strDate, String pattern) {
        if (TextUtil.isEmpty(strDate)) {
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.getDefault());
            return df.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return 日期字符串
     */

    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.getDefault());
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 时间字符串格式化
     *
     * @param createTime 服务器时间
     * @return yyyy-MM-dd
     */
    public static String getFormatTime(String createTime) {
        return getFormatTime(createTime, FORMAT_STR_DEFAULT);
    }

    /**
     * 时间字符串格式化
     *
     * @param createTime 服务器时间
     */
    public static String getFormatTime(String createTime, String pattern) {
        if (TextUtil.isEmpty(createTime)) return "";
        try {
            Date d1 = getDate(createTime);
            return getFormatTime(d1, pattern);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 时间字符串格式化
     */
    public static String getFormatTime(Date date, String pattern) {
        if (date == null) return "";
        try {
            String interval;
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
            interval = sdf.format(date);
            return interval;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getFormatTime(long time, String pattern) {
        try {
            Date date = new Date(time);
            return getFormatTime(date, pattern);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 是不是今天
     *
     * @param intervalDate 日期
     * @return 是不是今天
     */
    public static boolean isCurrentDay(Date intervalDate) {
        try {
            String currentDate = getCurrentDate(FORMAT_STR_DEFAULT);
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_STR_DEFAULT, Locale.getDefault());
            String intervalString = sdf.format(intervalDate);
            return currentDate.equals(intervalString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isCurrentDay(String date) {
        try {
            String currentDate = getCurrentDate(FORMAT_STR_DEFAULT);
            date = getFormatTime(date, FORMAT_STR_DEFAULT);
            return currentDate.equals(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isCurrentDay(Long time) {
        try {
            return isCurrentDay(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isCurrentMonth(String date) {
        try {
            String currentDate = getCurrentDate(FORMAT_STR_YM);
            date = getFormatTime(date, FORMAT_STR_YM);
            return currentDate.equals(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isMonthEquality(String date1, String date2) {
        try {
            date1 = getFormatTime(date1, FORMAT_STR_YM);
            date2 = getFormatTime(date2, FORMAT_STR_YM);
            return date1.equals(date2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isTodayOrYesterday(String time) {
        boolean isToday = false;
        boolean isYesterday = false;
        try {
            Date date = getDate(time, FORMAT_STR_DEFAULT);
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_STR_DEFAULT, Locale.getDefault());
            String dateStr = sdf.format(date);
            String todayTime = getCurrentDate(FORMAT_STR_DEFAULT);
            String yesterdayTime = getFormatTime(getPreviousDay(todayTime), FORMAT_STR_DEFAULT);
            isToday = todayTime.equals(dateStr);
            isYesterday = yesterdayTime.equals(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isToday || isYesterday;
    }

    /**
     * IOS的时间格式：把服务器传回的时间转换成Date
     *
     * @param createTime 服务器时间
     * @return 日期
     */
    private static Date getManualDate(String createTime) {
        createTime = createTime.replace("T", "");
        createTime = createTime.replace("Z", "");
        SimpleDateFormat sd = new SimpleDateFormat(SERVICE_TIME_FORMAT_IOS, Locale.getDefault());
        ParsePosition pos = new ParsePosition(0);
        return sd.parse(createTime, pos);
    }

    /**
     * 把服务器传回的时间转换成Date
     *
     * @param createTime 服务器时间
     * @return 日期
     */
    public static Date getDate(String createTime) {
        if (TextUtil.isEmpty(createTime)) {
            return null;
        }
        try {
            String[] createTimes = createTime.split("T");
            SimpleDateFormat sd;
            if (createTimes.length > 1) {
                createTime = createTime.replace("T", "");
                createTime = createTime.replace("Z", "");
                sd = new SimpleDateFormat(SERVICE_TIME_FORMAT, Locale.getDefault());
            } else {
                String[] createTimes1 = createTime.split(" ");
                if (createTimes1.length > 1) {
                    sd = new SimpleDateFormat(FORMAT_STR_YMDHMS, Locale.getDefault());
                } else {
                    String[] dates = createTimes1[0].split("-");
                    String format;
                    if (dates.length == 2) {
                        format = FORMAT_STR_YM;
                    } else {
                        format = FORMAT_STR_DEFAULT;
                    }

                    sd = new SimpleDateFormat(format, Locale.getDefault());
                }
            }

            ParsePosition pos = new ParsePosition(0);

            Date parse = sd.parse(createTime, pos);
            if (parse == null) {
                //IOS上传的时间的格式有问题。。。
                parse = getManualDate(createTime);
            }
            return parse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDate(Long time) {
        return new Date(time);
    }

    public static Date getDate(String createTime, String format) {
        if (TextUtil.isEmpty(createTime)) {
            return null;
        }
        try {
            SimpleDateFormat sd = new SimpleDateFormat(format, Locale.getDefault());
            ParsePosition pos = new ParsePosition(0);

            Date parse = sd.parse(createTime, pos);
            if (parse == null) {
                //IOS上传的时间的格式有问题。。。
                parse = getManualDate(createTime);
            }
            return parse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期格式转换
     */
    public static String dateFormat(String clientTime, String currentFormat, String targetFormat) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(currentFormat);
            Date date = format.parse(clientTime);
            SimpleDateFormat formatter = new SimpleDateFormat(targetFormat);
            return formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static Date getDateNotServer(String createTime) {
        if (TextUtil.isEmpty(createTime)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_STR_DEFAULT);
        Date date = null;
        try {
            date = formatter.parse(createTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 把服务器返回的时间格式化
     *
     * @param createTime 服务器时间
     * @return HH:mm
     */
    public static String getIntervalTime(String createTime) {
        try {
            Date d1 = getDate(createTime);
            String interval;
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_STR_TIME, Locale.getDefault());
            interval = sdf.format(d1);
            return interval;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取时间
     *
     * @param dateString 服务器时间
     * @return 刚刚/X分钟前/X小时前/X年X月X日
     */
    public static String timeDifference(String dateString) {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_STR_YMDHMS, Locale.getDefault());
        String format1 = df.format(new Date());
        if (dateString == null) {
            return "未知时间";
        }
        Date day = getDate(dateString);
        String format = format(day, FORMAT_STR_YMDHMS);
        try {
            Date d1 = df.parse(format1);
            Date d2 = df.parse(format);
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            if (days > 0) {
                return dateString.split("-")[0] + "年" + dateString.split("-")[1] + "月" + dateString.split("-")[2]
                        .split("T")[0] + "日";
            } else {
                if (hours > 0) {
                    return hours + "小时前";
                } else {
                    if (minutes == 0) {
                        return "刚刚";
                    } else {
                        return minutes + "分钟前";
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString.split("-")[0] + "年" + dateString.split("-")[1] + "月" + dateString.split("-")[2].split("T")
                [0] + "日";
    }

    /**
     * 获取时间 不需要年
     *
     * @param dateString 服务器时间
     * @return 刚刚/X分钟前/X小时前/X月X日
     */
    public static String timeDifferenceNoYear(String dateString) {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_STR_YMDHMS, Locale.getDefault());
        String format1 = df.format(new Date());
        if (dateString == null) {
            return "未知时间";
        }
        Date day = getDate(dateString);
        String format = format(day, FORMAT_STR_YMDHMS);
        try {
            Date d1 = df.parse(format1);
            Date d2 = df.parse(format);
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            if (days > 0) {
                return dateString.split("-")[1] + "月" + dateString.split("-")[2].split("T")[0] + "日";
            } else {
                if (hours > 0) {
                    return hours + "小时前";
                } else {
                    if (minutes == 0) {
                        return "刚刚";
                    } else {
                        return minutes + "分钟前";
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString.split("-")[1] + "月" + dateString.split("-")[2].split("T")[0] + "日";
    }

    /**
     * 获取时间 不需要年
     *
     * @param dateString 服务器时间
     * @return 刚刚/X分钟前/X小时前/X月X日
     */
    public static String timeDifferenceToNumber(String dateString) {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_STR_YMDHMS, Locale.getDefault());
        String format1 = df.format(new Date());
        if (dateString == null) {
            return "未知时间";
        }
        Date day = getDate(dateString);
        String format = format(day, FORMAT_STR_YMDHMS);
        try {
            Date d1 = df.parse(format1);
            Date d2 = df.parse(format);
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            if (days > 0) {
                return getFormatTime(dateString, FORMAT_STR_ALL);
            } else {
                if (hours > 0) {
                    return hours + "小时前";
                } else {
                    if (minutes == 0) {
                        return "刚刚";
                    } else {
                        return minutes + "分钟前";
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getFormatTime(dateString, FORMAT_STR_ALL);
    }


    public static boolean isToday(String date) {
        String today = getCurrentDate(FORMAT_STR_DEFAULT);
        return isEquality(today, date);
    }


    /**
     * 竞猜是否已经结束
     *
     * @param date 服务器返回时间
     * @return 是否结束
     */
    public static boolean isEnd(String date) {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_STR_YMDHMS, Locale.getDefault());
        String format1 = df.format(new Date());

        Date day = getDate(date);
        String format = format(day, FORMAT_STR_YMDHMS);
        try {
            Date d1 = df.parse(format1);  //当前系统时间
            Date d2 = df.parse(format);  //服务端的时间
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别

            return diff > 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取当前时间
     *
     * @param pattern 日期格式
     * @return 按照要求的格式
     */
    public static String getCurrentDate(String pattern) {
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getTwoYearsAgo() {
        try {
            String curYear = getCurrentDate(DateUtil.FORMAT_STR_Y_I);
            int curYearI = Integer.parseInt(curYear);
            int twoYearsAgoI = curYearI - 2;
            return twoYearsAgoI + "-01-01";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 判断第一个时间是否小于等于第二个时间
     * 格式 2018-01-01 23:59
     */
    public static boolean isDateSmall(String date1, String date2) {
        try {
            String[] date1All = date1.split("T");
            String[] date2All = date2.split("T");

            String[] date1Half = date1All[0].split(" ");
            String[] date2Half = date2All[0].split(" ");

            String[] date1s = date1Half[0].split("-");
            String[] date2s = date2Half[0].split("-");

            int date1Year = Integer.parseInt(date1s[0]);
            int date1Month = Integer.parseInt(date1s[1]);
            int date1Day = Integer.parseInt(date1s[2]);
            int date1Hour, date1Minute;
            if (date1Half.length > 1) {
                String[] times = date1Half[1].split(":");
                date1Hour = Integer.parseInt(times[0]);
                date1Minute = Integer.parseInt(times[1]);
            } else {
                date1Hour = date1Minute = 0;
            }


            int date2Year = Integer.parseInt(date2s[0]);
            int date2Month = Integer.parseInt(date2s[1]);
            int date2Day = Integer.parseInt(date2s[2]);
            int date2Hour, date2Minute;
            if (date2Half.length > 1) {
                String[] times = date2Half[1].split(":");
                date2Hour = Integer.parseInt(times[0]);
                date2Minute = Integer.parseInt(times[1]);
            } else {
                date2Hour = date2Minute = 0;
            }


            if (date1Year != date2Year) {
                return date1Year < date2Year;
            }

            if (date1Month != date2Month) {
                return date1Month < date2Month;
            }

            if (date1Day != date2Day) {
                return date1Day < date2Day;
            }

            if (date1Hour != date2Hour) {
                return date1Hour < date2Hour;
            }

            return date1Minute == date2Minute || date1Minute < date2Minute;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean isDateSmallToNoEquality(String date1, String date2) {
        try {
            Date d1 = getDate(date1);
            Date d2 = getDate(date2);
            return d1.getTime() < d2.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isDateMoreThanThe(String date1, String date2) {
        long time1 = 0;
        long time2 = 0;
        try {
            Date d1 = getDate(date1);
            time1 = d1.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Date d2 = getDate(date2);
            time2 = d2.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time1 > time2;
    }

    /**
     * 获取当前时间转为字符串
     */
    public static String getCurrentDateString() {
        //获取当前时间，进一步转化为字符串
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_STR_ALL, Locale.getDefault());
        return format.format(date);
    }

    /**
     * 获取当前时间转为字符串
     */
    public static String getCurrentDateString(String pattern) {
        //获取当前时间，进一步转化为字符串
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(date);
    }


    /**
     * 是否超过限制时间
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param limitTime 限制时间
     */
    public static boolean isExceedLimitTime(long startTime, long endTime, long limitTime) {
        if (startTime == 0 || endTime == 0) {
            return false;
        }
        if (limitTime == 0) {
            return true;
        }
        return (endTime - startTime) >= limitTime;
    }

    /**
     * 获取年龄
     *
     * @param time 服务器时间
     */
    public static int getAge(String time) {
        try {
            String times[] = time.split("T")[0].split("-");

            String year = times[0];
            String month = times[1];
            String day = times[2];
            if (Integer.parseInt(year) == 1 && Integer.parseInt(month) == 1 && Integer.parseInt(day) == 1) {
                return -1;
            }

            String currentYear = getCurrentDate(FORMAT_STR_DEFAULT).split("-")[0];
            return Integer.parseInt(currentYear) - Integer.parseInt(year);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 计算时间差
     *
     * @param starTime 开始时间
     * @param endTime  结束时间
     * @return 返回时间差 天-时-分
     */

    public static String getTimeDifference(String starTime, String endTime) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_STR_ALL, Locale.getDefault());
            Date parse = dateFormat.parse(getFormatTime(starTime, FORMAT_STR_ALL));
            Date parse1 = dateFormat.parse(getFormatTime(endTime, FORMAT_STR_ALL));

            long diff = parse1.getTime() - parse.getTime();

            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - s * 1000);
            return day + "-" + hour + "-" + min;
        } catch (ParseException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 在某个日期上增加一些时间
     *
     * @param startDate 开始时间
     * @param addTime   增加的时间  天-时-分
     */
    public static String addSomeTime(Date startDate, String addTime) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_STR_ALL, Locale.getDefault());
            dateFormat.format(startDate);

            Date parse = dateFormat.parse(dateFormat.format(startDate));
            long time = parse.getTime();
            String[] addTimes = addTime.split("-");
            long day = Integer.parseInt(addTimes[0]);
            long hour = Integer.parseInt(addTimes[1]);
            long min = Integer.parseInt(addTimes[2]);

            day = day * 24 * 60 * 60 * 1000;
            hour = hour * 60 * 60 * 1000;
            min = min * 60 * 1000;

            Date endDate = new Date(time + day + hour + min);
            return dateFormat.format(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 判断时间是否截止
     *
     * @param deadline 天-时-分
     */
    public static boolean isTimeEnd(String[] deadline) {
        boolean isTimeEnd = false;
        try {
            int day = Integer.parseInt(deadline[0]);
            int month = Integer.parseInt(deadline[1]);
            int min = Integer.parseInt(deadline[2]);
            if (day <= 0 && month <= 0 && min <= 0) {
                isTimeEnd = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTimeEnd;
    }

    /**
     * 判断时间是否截止后一天内
     *
     * @param deadline 天-时-分
     */
    public static boolean isTimeEndOneDay(String[] deadline) {
        boolean isTimeEnd = false;
        try {
            int day = Integer.parseInt(deadline[0]);
            int month = Integer.parseInt(deadline[1]);
            int min = Integer.parseInt(deadline[2]);
            if (day <= 0 && day >= -24 && month <= 0 && min <= 0) {
                isTimeEnd = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTimeEnd;
    }


    /**
     * 格式化时间 00:00
     */
    public static String getTimerFormat(int currentMinute, int currentSecond) {
        String minute;
        String second;
        if (currentMinute < 0) {
            currentMinute = 0;
        }
        if (currentSecond < 0) {
            currentSecond = 0;
        }
        if (currentMinute < 10) {
            minute = "0" + currentMinute;
        } else {
            minute = String.valueOf(currentMinute);
        }
        if (currentSecond < 10) {
            second = "0" + currentSecond;
        } else {
            second = String.valueOf(currentSecond);
        }
        return minute + ":" + second;
    }


    /**
     * 格式化时间，如果年为当前年，不返回年，反之
     *
     * @param date 时间
     * @return 2018.01.01 00:00 or 01.01 00:00
     */
    public static String getFormatNoYearDate(String date, String symbol) {
        try {
            String currentYear = getCurrentDate(FORMAT_STR_DEFAULT).split("-")[0];
            date = getFormatTime(date, FORMAT_STR_ALL);
            String[] dateAlls = date.split(" ");

            String[] dates = dateAlls[0].split("-");
            String year = dates[0];
            String month = dates[1];
            String day = dates[2];

            int monthInt = Integer.parseInt(month);
            int dayInt = Integer.parseInt(day);

            if (monthInt > 0 && monthInt < 10) {
                month = "0".concat(String.valueOf(monthInt));
            }

            if (dayInt > 0 && dayInt < 10) {
                day = "0".concat(String.valueOf(dayInt));
            }

            if (dateAlls.length > 1) {
                if (currentYear.equals(year)) {
                    return month.concat(symbol).concat(day).concat(" ").concat(dateAlls[1]);
                } else {
                    return year.concat(symbol).concat(month).concat(symbol).concat(day).concat(" ").concat(dateAlls[1]);
                }
            } else {
                if (currentYear.equals(year)) {
                    return month.concat(symbol).concat(day);
                } else {
                    return year.concat(symbol).concat(month).concat(symbol).concat(day);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (date == null) {
            return "";
        } else {
            return date;
        }
    }

    /**
     * 格式化时间，如果年为当前年，不返回年，反之
     *
     * @param date 时间
     * @return 2018.01.01  or 01.01
     */
    public static String getFormatNoYearAndNoTimeDate(String date) {
        try {
            String currentYear = getCurrentDate(FORMAT_STR_DEFAULT).split("-")[0];
            date = getFormatTime(date, FORMAT_STR_DEFAULT);

            String[] dates = date.split("-");
            String year = dates[0];
            String month = dates[1];
            String day = dates[2];

            int monthInt = Integer.parseInt(month);
            int dayInt = Integer.parseInt(day);

            if (monthInt > 0 && monthInt < 10) {
                month = "0".concat(String.valueOf(monthInt));
            }

            if (dayInt > 0 && dayInt < 10) {
                day = "0".concat(String.valueOf(dayInt));
            }

            if (currentYear.equals(year)) {
                return month.concat(".").concat(day);
            } else {
                return year.concat(".").concat(month).concat(".").concat(day);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (date == null) {
            return "";
        } else {
            return date;
        }
    }

    /**
     * 格式化时间，如果年为当前年，不返回年，反之
     *
     * @param date 时间
     * @return 2018年1月1日 00:00 or 1月1日 00:00
     */
    public static String getFormatNoYearDate2(String date) {
        try {
            String currentYear = getCurrentDate(FORMAT_STR_DEFAULT).split("-")[0];

            date = getFormatTime(date, FORMAT_STR_ALL);

            String[] dateAlls = date.split(" ");

            String[] dates = dateAlls[0].split("-");
            String year = dates[0];
            int yearInt = Integer.parseInt(year);
            int monthInt = Integer.parseInt(dates[1]);
            int dayInt = Integer.parseInt(dates[2]);
            String hour = "00";
            String minute = "00";
            if (dateAlls.length > 1) {
                String[] times = dateAlls[1].split(":");
                if (times.length > 1) {
                    hour = times[0];
                    minute = times[1];

                    int hourInt = Integer.parseInt(hour);
                    int minuteInt = Integer.parseInt(minute);

                    if (hourInt < 10) {
                        hour = "0" + hourInt;
                    }
                    if (minuteInt < 10) {
                        minute = "0" + minuteInt;
                    }
                }
            }

            if (currentYear.equals(year)) {
                return monthInt + "月" + dayInt + "日 " + hour + ":" + minute;
            } else {
                return yearInt + "年" + monthInt + "月" + dayInt + "日 " + hour + ":" + minute;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (date == null) {
            return "";
        } else {
            return date;
        }
    }

    /**
     * 格式化时间，如果年为当前年，不返回年，反之
     *
     * @param date 时间
     * @return 2018年1月1日  or 1月1日
     */
    public static String getFormatNoYearAndNoTimeDate2(String date) {
        try {
            String currentYear = getCurrentDate(FORMAT_STR_DEFAULT).split("-")[0];
            date = getFormatTime(date, FORMAT_STR_DEFAULT);

            String[] dates = date.split("-");
            String year = dates[0];

            int yearInt = Integer.parseInt(year);
            int monthInt = Integer.parseInt(dates[1]);
            int dayInt = Integer.parseInt(dates[2]);

            if (currentYear.equals(year)) {
                return String.valueOf(monthInt).concat("月").concat(String.valueOf(dayInt)).concat("日");
            } else {
                return String.valueOf(yearInt).concat("年").concat(String.valueOf(monthInt)).concat("月")
                        .concat(String.valueOf(dayInt)).concat("日");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (date == null) {
            return "";
        } else {
            return date;
        }
    }

    /**
     * 判断两个日期是否相等
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 是否相等
     */
    public static boolean isEquality(String date1, String date2) {
        if (TextUtil.isEmpty(date1) || TextUtil.isEmpty(date2)) return false;

        try {
            date1 = getFormatTime(date1);
            date2 = getFormatTime(date2);

            String[] date1Array = date1.split("-");
            String[] date2Array = date2.split("-");

            String date1Year = date1Array[0];
            String date1Month = date1Array[1];
            String date1Day = date1Array[2];

            String date2Year = date2Array[0];
            String date2Month = date2Array[1];
            String date2Day = date2Array[2];

            return (Integer.parseInt(date1Year) == Integer.parseInt(date2Year) &&
                    Integer.parseInt(date1Month) == Integer.parseInt(date2Month) &&
                    Integer.parseInt(date1Day) == Integer.parseInt(date2Day)
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getAddSomeYear(int year) {
        String date = getCurrentDate(FORMAT_STR_DEFAULT);
        String[] time = date.split("-");
        try {
            year = Integer.parseInt(time[0]) + year;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return year;
    }

    public static int differentDays(String time1, String time2) {
        try {
            Date d1 = getDate(time1, FORMAT_STR_DEFAULT);
            Date d2 = getDate(time2, FORMAT_STR_DEFAULT);
            long disTime = d1.getTime() - d2.getTime();
            disTime = Math.abs(disTime);
            long disDay = disTime / (1000 * 60 * 60 * 24);
            return (int) disDay;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 时间
     *
     * @param position 要格式化的时间
     */
    public static String generateTime(long position) {
        int totalSeconds = position / 1000.0 % 1 == 0 ? (int) (position / 1000.0) : (int) (position / 1000.0 + 1);

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format(Locale.US, "%02d:%02d", minutes, seconds);
        }
    }

    public static String secondToTime(long second) {
        long h = second / 3600;
        long m = (second % 3600) / 60;
        long s = (second % 3600) % 60;
        String time = h > 9 ? String.valueOf(h).concat(":") : "0".concat(String.valueOf(h)).concat(":");
        time += m > 9 ? String.valueOf(m).concat(":") : "0".concat(String.valueOf(m)).concat(":");
        time += s > 9 ? String.valueOf(s) : "0".concat(String.valueOf(s));
        return time;
    }

    public static String secondToMinute(long second) {
        try {
            long h = second / 3600;
            long m = (second % 3600) / 60;
            long s = (second % 3600) % 60;
            String time = "";
            if (h > 0) {
                time = h > 9 ? String.valueOf(h).concat(":") : "0".concat(String.valueOf(h)).concat(":");
            }
            time += m > 9 ? String.valueOf(m).concat(":") : "0".concat(String.valueOf(m)).concat(":");
            time += s > 9 ? String.valueOf(s) : "0".concat(String.valueOf(s));
            return time;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "00:00";
    }

    public static String secondToMinute(float second) {
        int secondInt = (int) second;
        if (second > (float) secondInt) {
            secondInt++;
        }
        return secondToMinute(secondInt);
    }


    public static String getTimeDifferenceFormat(String timeDifference, String split) {
        if (TextUtil.isEmpty(timeDifference) || TextUtil.isEmpty(split)) return null;
        try {
            String[] times = timeDifference.split(split);
            int day = Integer.parseInt(times[0]);
            int hour = Integer.parseInt(times[1]);
            int min = Integer.parseInt(times[2]);
            int second = Integer.parseInt(times[3]);

            StringBuilder sb = new StringBuilder("");
            if (day > 0) {
                sb.append(day).append("天");
                if (hour > 0) {
                    sb.append(hour).append("小时");
                    if (min > 0) {
                        sb.append(min).append("分钟");
                    }
                } else {
                    if (min > 0) {
                        sb.append(hour).append("小时").append(min).append("分钟");
                    }
                }
            } else {
                if (hour > 0) {
                    sb.append(hour).append("小时");
                    if (min > 0) {
                        sb.append(min).append("分钟");
                    }
                } else {
                    if (min > 0) {
                        sb.append(min).append("分钟");
                    } else {
                        sb.append(second).append("秒");
                    }
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getFormatTime(int second) {
        return getFormatTime(second, true);
    }


    /**
     * 格式化时间
     *
     * @param second 时间总秒数
     * @return 时：分：秒
     */
    public static String getFormatTime(int second, boolean hasHour) {
        int minute;
        int hour;
        minute = second / 60;
        second = second % 60;
        hour = minute / 60;

        String hourS, minuteS, secondS;

        if (hour < 10) {
            hourS = "0" + hour;
        } else {
            hourS = String.valueOf(hour);
        }
        if (minute < 10) {
            minuteS = "0" + minute;
        } else {
            minuteS = String.valueOf(minute);
        }
        if (second < 10) {
            secondS = "0" + second;
        } else {
            secondS = String.valueOf(second);
        }
        if (hasHour && hour > 0) {
            return hourS + ":" + minuteS + ":" + secondS;
        }
        return minuteS + ":" + secondS;
    }

    public static int getCurrentTimeToMinute() {
        String date = getCurrentDate(FORMAT_STR_TIME);
        int allMinute = 0;
        try {
            String[] times = date.split(":");
            int hour = Integer.parseInt(times[0]);
            int minute = Integer.parseInt(times[1]);
            allMinute = hour * 60 + minute;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allMinute;
    }

    public static boolean isOneDayPre(String targetDateStr) {
        try {
            String currentDateStr = getCurrentDate(FORMAT_STR_ALL);
            Date currentDate = getDate(currentDateStr);
            long currentTime = currentDate.getTime();
            Date targetDate = getDate(targetDateStr);
            long targetTime = targetDate.getTime();
            return currentTime - targetTime >= 24 * 60 * 60 * 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取当前年份
     */
    public static int getCurrentYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     */
    public static int getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前几号
     */
    public static int getCurrentDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static String getPreviousDay(String date) {
        try {
            date = getFormatTime(date, FORMAT_STR_DEFAULT);
            String[] dates = date.split("-");
            int year = Integer.parseInt(dates[0]);
            int month = Integer.parseInt(dates[1]);
            int day = Integer.parseInt(dates[2]);
            if (day > 1) {
                return year + "-" + month + "-" + (day - 1);
            } else {
                if (month > 1) {
                    month = month - 1;
                    return year + "-" + month + "-" + getMonthAllDay(year, month);
                } else {
                    year = year - 1;
                    return year + "-" + 12 + "-" + getMonthAllDay(year, month);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getNextDay(String date) {
        try {
            date = getFormatTime(date, FORMAT_STR_DEFAULT);
            String[] dates = date.split("-");
            int year = Integer.parseInt(dates[0]);
            int month = Integer.parseInt(dates[1]);
            int day = Integer.parseInt(dates[2]);
            int allDay = getMonthAllDay(year, month);
            if (day < allDay) {
                return year + "-" + month + "-" + (day + 1);
            } else {
                if (month < 12) {
                    return year + "-" + (month + 1) + "-" + 1;
                } else {
                    return (year + 1) + "-" + 1 + "-" + 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isEmpty(String dateStr) {
        if (TextUtil.isEmpty(dateStr)) {
            return true;
        }
        try {
            Date date = getDate(dateStr, FORMAT_STR_DEFAULT);
            long dateTime = date.getTime();
            Date emptyDate = getDate("0001-01-01");
            long emptyDateTime = emptyDate.getTime();
            return dateTime == emptyDateTime;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 获取日期，加减月份
     */
    public static String getDateForMonthChange(String date, int disMonth) {
        try {
            if (disMonth == 0) {
                return date;
            }
            String formatDate = getFormatTime(date, FORMAT_STR_YM);
            String[] dates = formatDate.split("-");
            int year = Integer.parseInt(dates[0]);
            int month = Integer.parseInt(dates[1]);

            int absDisMonth = Math.abs(disMonth);
            int disYear = absDisMonth / 12;
            int remainderMonth;
            if (absDisMonth > 12) {
                remainderMonth = absDisMonth % 12;
            } else if (absDisMonth < 12) {
                remainderMonth = absDisMonth;
            } else {
                remainderMonth = 0;
            }

            if (disMonth > 0) {
                year += disYear;
                month += remainderMonth;
                if (month > 12) {
                    month %= 12;
                    year++;
                }
            } else if (disMonth < 0) {
                year -= disYear;
                month -= remainderMonth;
                if (month < 0) {
                    month += 12;
                    year--;
                }
            } else {
                return date;
            }
            String getDate = year + "-" + month + "-" + "1";
            return getFormatTime(getDate, FORMAT_STR_DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getDateMonthDifference(String date, String minusDate) {
        try {
            date = getFormatTime(date, FORMAT_STR_YM);
            minusDate = getFormatTime(minusDate, FORMAT_STR_YM);
            String[] dates = date.split("-");
            String[] minusDates = minusDate.split("-");
            int year = Integer.parseInt(dates[0]);
            int month = Integer.parseInt(dates[1]);
            int minusYear = Integer.parseInt(minusDates[0]);
            int minusMonth = Integer.parseInt(minusDates[1]);

            int disYear = year - minusYear;
            int disMonth = month - minusMonth;

            return disYear * 12 + disMonth;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getPreviousMonth(String date) {
        try {
            date = getFormatTime(date, FORMAT_STR_YM);
            String[] dates = date.split("-");
            int year = Integer.parseInt(dates[0]);
            int month = Integer.parseInt(dates[1]);
            month--;
            if (month <= 0) {
                month = 12;
                year--;
            }
            date = year + "-" + month + "-01";
            return getFormatTime(date, FORMAT_STR_YM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getNextMonth(String date) {
        try {
            date = getFormatTime(date, FORMAT_STR_YM);
            String[] dates = date.split("-");
            int year = Integer.parseInt(dates[0]);
            int month = Integer.parseInt(dates[1]);
            month++;
            if (month > 12) {
                month = 1;
                year++;
            }
            date = year + "-" + month + "-01";
            return getFormatTime(date, FORMAT_STR_YM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取日期，加减天数
     */
    public static String getDateForDayChange(String date, int disDay) {
        try {
            date = getFormatTime(date);
            Date d = getDate(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            calendar.add(Calendar.DAY_OF_MONTH, disDay);
            return getFormatTime(calendar.getTime(), FORMAT_STR_DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取相差天数
     */
    public static int getDateDayDifference(String date1, String date2) {
        try {
            if (isEquality(date1, date2)) {
                return 0;
            }

            date1 = getFormatTime(date1);
            date2 = getFormatTime(date2);

            Date d1 = getDate(date1);
            Date d2 = getDate(date2);

            long d1Time = d1.getTime();
            long d2Time = d2.getTime();

            boolean isAddition = d2Time > d1Time;

            long dayDistance = Math.abs((d2Time - d1Time) / DAY_TIME);
            int disDay = (int) dayDistance;
            return isAddition ? disDay : -disDay;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean isYesterday(String date) {
        try {
            date = getFormatTime(date, FORMAT_STR_DEFAULT);
            String todayDate = getCurrentDate(FORMAT_STR_DEFAULT);
            String yesterday = getPreviousDay(todayDate);
            return isEquality(date, yesterday);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getDateDifferenceToSecond(String date1, String date2) {
        int second = 0;
        try {
            Date d1 = getDate(date1);
            Date d2 = getDate(date2);
            long disTime = d1.getTime() - d2.getTime();
            second = (int) (disTime / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Math.abs(second);
    }

    public static boolean isTodayOrAfterTime(String time) {
        try {
            Date timeDate = getDate(time, FORMAT_STR_ALL);
            Date currentDate = getDate(getCurrentDate(FORMAT_STR_DEFAULT));
            if (timeDate == null || currentDate == null) {
                return false;
            }
            return timeDate.getTime() >= currentDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isTimeYearEqualOrLessThan(String centerTime, String time) {
        return isTimeEqualOrLessThan(centerTime, time, FORMAT_STR_Y_I);
    }

    public static boolean isTimeYearLessThan(String centerTime, String time) {
        try {
            centerTime = getFormatTime(centerTime, FORMAT_STR_Y_I);
            time = getFormatTime(time, FORMAT_STR_Y_I);
            int ct = Integer.parseInt(centerTime);
            int t = Integer.parseInt(time);
            return t < ct;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isTimeYearEqualOrGreaterThan(String centerTime, String time) {
        return isTimeEqualOrGreaterThan(centerTime, time, FORMAT_STR_Y_I);
    }

    public static boolean isTimeYearGreaterThan(String centerTime, String time) {
        return isTimeGreaterThan(centerTime, time, FORMAT_STR_Y_I);
    }

    public static boolean isTimeYearEqual(String centerTime, String time) {
        return isTimeEqual(centerTime, time, FORMAT_STR_YM_I);
    }

    public static boolean isTimeYearMonthEqualOrLessThan(String centerTime, String time) {
        return isTimeEqualOrLessThan(centerTime, time, FORMAT_STR_YM_I);
    }

    public static boolean isTimeYearMonthEqualOrGreaterThan(String centerTime, String time) {
        return isTimeEqualOrGreaterThan(centerTime, time, FORMAT_STR_YM_I);
    }

    public static boolean isTimeYearDayEqualOrLessThan(String centerTime, String time) {
        return isTimeEqualOrLessThan(centerTime, time, FORMAT_STR_YMD_I);
    }

    public static boolean isTimeYearDayEqualOrGreaterThan(String centerTime, String time) {
        return isTimeEqualOrGreaterThan(centerTime, time, FORMAT_STR_YMD_I);
    }

    public static boolean isTimeEqualOrLessThan(String centerTime, String time, String pattern) {
        try {
            centerTime = getFormatTime(centerTime, pattern);
            time = getFormatTime(time, pattern);
            int ct = Integer.parseInt(centerTime);
            int t = Integer.parseInt(time);
            return t <= ct;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isTimeEqualOrGreaterThan(String centerTime, String time, String pattern) {
        try {
            centerTime = getFormatTime(centerTime, pattern);
            time = getFormatTime(time, pattern);
            int ct = Integer.parseInt(centerTime);
            int t = Integer.parseInt(time);
            return t >= ct;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isTimeLessThan(String centerTime, String time, String pattern) {
        try {
            centerTime = getFormatTime(centerTime, pattern);
            time = getFormatTime(time, pattern);
            int ct = Integer.parseInt(centerTime);
            int t = Integer.parseInt(time);
            return t < ct;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isTimeGreaterThan(String centerTime, String time, String pattern) {
        try {
            centerTime = getFormatTime(centerTime, pattern);
            time = getFormatTime(time, pattern);
            int ct = Integer.parseInt(centerTime);
            int t = Integer.parseInt(time);
            return t > ct;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isTimeEqual(String centerTime, String time, String pattern) {
        try {
            centerTime = getFormatTime(centerTime, pattern);
            time = getFormatTime(time, pattern);
            int ct = Integer.parseInt(centerTime);
            int t = Integer.parseInt(time);
            return t == ct;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static String getNextYear(String time) {
        try {
            time = getFormatTime(time, FORMAT_STR_Y_I);
            int year = Integer.parseInt(time);
            year++;
            return year + "-01-01";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPreviousYear(String time) {
        try {
            time = getFormatTime(time, FORMAT_STR_Y_I);
            int year = Integer.parseInt(time);
            year--;
            return year + "-01-01";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}


package io.b4a.itms.utils;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

final public class DateFormatUtil {
    // 获取不同格式化风格和中国环境的日期
    final static private DateFormat shortDateFormatter = DateFormat.getDateInstance(DateFormat.SHORT, Locale.CHINA);
    final static private DateFormat fullDateFormatter = DateFormat.getDateInstance(DateFormat.FULL, Locale.CHINA);
    final static private DateFormat mediumDateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
    final static private DateFormat longDateFormatter = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINA);
    // 获取不同格式化风格和中国环境的时间
    final static private DateFormat shortTimeFormatter = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.CHINA);
    final static private DateFormat fullTimeFormatter = DateFormat.getTimeInstance(DateFormat.FULL, Locale.CHINA);
    final static private DateFormat mediumTimeFormatter = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.CHINA);
    final static private DateFormat longTimeFormatter = DateFormat.getTimeInstance(DateFormat.LONG, Locale.CHINA);

    /**
     * 格式化示例：18-10-15 上午9:30
     */
    public static String formatShort(Date date) {
        String strDate = shortDateFormatter.format(date);
        String strTime = shortTimeFormatter.format(date);
        return String.format("%s\b%s", strDate, strTime);
    }

    /**
     * 格式化示例：2018年10月15日 星期一 上午09时30分43秒 CST
     */
    public static String formatFull(Date date) {
        String strDate = fullDateFormatter.format(date);
        String strTime = fullTimeFormatter.format(date);
        return String.format("%s\b%s", strDate, strTime);
    }

    /**
     * 格式化示例：2018-10-15 9:30:43
     */
    public static String formatMedium(Date date) {
        String strDate = mediumDateFormatter.format(date);
        String strTime = mediumTimeFormatter.format(date);
        return String.format("%s\b%s", strDate, strTime);
    }

    /**
     * 格式化示例：2018年10月15日 上午09时30分43秒
     */
    public static String formatLong(Date date) {
        String strDate = longDateFormatter.format(date);
        String strTime = longTimeFormatter.format(date);
        return String.format("%s\b%s", strDate, strTime);
    }
}

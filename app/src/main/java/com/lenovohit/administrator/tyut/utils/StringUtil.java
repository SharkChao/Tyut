package com.lenovohit.administrator.tyut.utils;

/**
 * Created by Administrator on 2017/3/17.
 */

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串工具类.
 * @author sunruyi
 */
public class StringUtil {
    /**
     * 去除字符串首尾出现的某个字符.
     * @param source 源字符串.
     * @param element 需要去除的字符.
     * @return String.
     */
    public static String trimFirstAndLastChar(String source,char element){
        boolean beginIndexFlag = true;
        boolean endIndexFlag = true;
        do{
            int beginIndex = source.indexOf(element) == 0 ? 1 : 0;
            int endIndex = source.lastIndexOf(element) + 1 == source.length() ? source.lastIndexOf(element) : source.length();
            source = source.substring(beginIndex, endIndex);
            beginIndexFlag = (source.indexOf(element) == 0);
            endIndexFlag = (source.lastIndexOf(element) + 1 == source.length());
        } while (beginIndexFlag || endIndexFlag);
        return source;
    }
    public static boolean isStrEmpty(String var0) {
        return var0 != null && !"".equals(var0.trim())?(var0 = var0.replaceAll(" ", "").trim()) == null || "".equals(var0.trim()):true;
    }
    public static String getToday() {
        Date var0 = new Date();
        return (new SimpleDateFormat("yyyy-MM-dd")).format(var0);
    }
    public static boolean isNotEmpty(Object var0) {
        return var0 != null;
    }


    // 将数字转化为大写
    public static String numToUpper(int num) {
        // String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
        String u[] = { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        char[] str = String.valueOf(num).toCharArray();
        String rstr = "";
        for (int i = 0; i < str.length; i++) {
            rstr = rstr + u[Integer.parseInt(str[i] + "")];
        }
        return rstr;
    }

    // 月转化为大写
    public static String monthToUppder(int month) {
        if (month < 10) {
            return numToUpper(month);
        } else if (month == 10) {
            return "十";
        } else {
            return "十" + numToUpper(month - 10);
        }
    }

    // 日转化为大写
    public static String dayToUppder(int day) {
        if (day < 20) {
            return monthToUppder(day);
        } else {
            char[] str = String.valueOf(day).toCharArray();
            if (str[1] == '0') {
                return numToUpper(Integer.parseInt(str[0] + "")) + "十";
            } else {
                return numToUpper(Integer.parseInt(str[0] + "")) + "十"
                        + numToUpper(Integer.parseInt(str[1] + ""));
            }
        }
    }
}
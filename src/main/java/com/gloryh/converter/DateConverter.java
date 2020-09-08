package com.gloryh.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换器实现
 *
 * @author 黄光辉
 * @since 2020/9/2
 **/
public class DateConverter implements Converter<String, Date> {
    private final String pattern;

    public DateConverter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Date convert(String s) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}

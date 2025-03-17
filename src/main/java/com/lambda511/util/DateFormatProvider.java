package com.lambda511.util;

import java.text.DateFormat;

/**
 * Created by blitzer on 15.06.2016.
 */
public interface DateFormatProvider {

    String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    DateFormat getDateFormat();
}

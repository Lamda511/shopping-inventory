package com.lambda511.util.date;

import com.lambda511.util.DateFormatProvider;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by blitzer on 23.04.2016.
 */

@Component
public class DefaultDateFormatProvider implements DateFormatProvider {

    @Override
    public DateFormat getDateFormat() {
        return new SimpleDateFormat(DATE_FORMAT);
    }
}

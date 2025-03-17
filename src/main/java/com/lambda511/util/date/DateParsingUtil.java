package com.lambda511.util.date;

import com.lambda511.util.DateFormatProvider;
import com.lambda511.util.DateProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by blitzer on 23.04.2016.
 */
@Component
public class DateParsingUtil {

    @Autowired
    private DateFormatProvider dateFormatProvider;

    @Autowired
    private DateProvider dateProvider;

    public Date parseDateDefaultToMin(String dateString) {
        return parseDateDefaultTo(dateString, new Date(0));
    }

    public Date parseDateDefaultToMax(String dateString) {
        return parseDateDefaultTo(dateString, dateProvider.getCurrentDate());
    }

    private Date parseDateDefaultTo(String dateString, Date defaultDate) {
        Date date = null;

        if (StringUtils.isNotEmpty(dateString)) {
            try {
                DateFormat dateFormat = dateFormatProvider.getDateFormat();
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                //TODO: Maybe log here.
            }
        }

        return (date != null) ? date : defaultDate;
    }

}

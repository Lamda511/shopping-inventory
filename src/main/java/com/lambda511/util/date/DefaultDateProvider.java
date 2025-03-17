package com.lambda511.util.date;

import com.lambda511.util.DateProvider;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by blitzer on 7/14/15.
 */
@Component
public class DefaultDateProvider implements DateProvider {

    public Date getCurrentDate() {
        return new Date();
    }

}

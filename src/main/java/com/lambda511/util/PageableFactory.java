package com.lambda511.util;

import org.springframework.data.domain.Pageable;

/**
 * Created by blitzer on 15.06.2016.
 */
public interface PageableFactory {

    Pageable createPageRequestWith(int pageNumber, int pageSize);
}

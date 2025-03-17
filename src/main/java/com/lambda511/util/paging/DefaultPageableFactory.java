package com.lambda511.util.paging;

import com.lambda511.util.PageableFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Created by blitzer on 26.04.2016.
 */
@Component
public class DefaultPageableFactory implements PageableFactory {

    @Override
    public Pageable createPageRequestWith(int pageNumber, int pageSize) {
        return new PageRequest(pageNumber, pageSize);
    }
}

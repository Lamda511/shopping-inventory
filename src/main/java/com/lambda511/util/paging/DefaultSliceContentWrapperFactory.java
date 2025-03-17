package com.lambda511.util.paging;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by blitzer on 03.05.2016.
 */

@Component
public class DefaultSliceContentWrapperFactory implements SliceContentWrapperFactory {

    @Override
    public <T> SliceContentWrapper<T> createSliceContentWrapper(int currentSliceNumber, int numberOfElements, int maxPerPage, boolean hasNext, boolean hasPrevious, List<T> elements) {
        return new SliceContentWrapper<>(currentSliceNumber, numberOfElements, maxPerPage, hasNext, hasPrevious, elements);
    }

}

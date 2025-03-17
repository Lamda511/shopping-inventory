package com.lambda511.util.paging;

import java.util.List;

/**
 * Created by blitzer on 15.06.2016.
 */
public interface SliceContentWrapperFactory {
    <T> SliceContentWrapper<T> createSliceContentWrapper(int currentSliceNumber, int numberOfElements, int maxPerPage, boolean hasNext, boolean hasPrevious, List<T> elements);
}

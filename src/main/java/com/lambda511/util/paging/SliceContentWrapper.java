package com.lambda511.util.paging;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by blitzer on 27.04.2016.
 */
public class SliceContentWrapper<T> {

    @JsonProperty(value = "sliceNo")
    private int currentSliceNumber;
    @JsonProperty(value = "elementsInSlice")
    private int numberOfElements;
    @JsonProperty(value = "maxPerPage")
    private int maxPerPage;
    @JsonProperty(value = "hasNext")
    private boolean hasNext;
    @JsonProperty(value = "hasPrevious")
    private boolean hasPrevious;
    @JsonProperty(value = "elements")
    List<T> elements;

    public SliceContentWrapper() {
    }

    public SliceContentWrapper(int currentSliceNumber, int numberOfElements, int maxPerPage, boolean hasNext, boolean hasPrevious, List<T> elements) {
        this.currentSliceNumber = currentSliceNumber;
        this.numberOfElements = numberOfElements;
        this.maxPerPage = maxPerPage;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
        this.elements = elements;
    }

    public int getCurrentSliceNumber() {
        return currentSliceNumber;
    }

    public void setCurrentSliceNumber(int currentSlice) {
        this.currentSliceNumber = currentSlice;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getMaxPerPage() {
        return maxPerPage;
    }

    public void setMaxPerPage(int maxPerPage) {
        this.maxPerPage = maxPerPage;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }
}

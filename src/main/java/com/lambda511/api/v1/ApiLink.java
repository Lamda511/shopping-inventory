package com.lambda511.api.v1;

import java.util.List;

/**
 * Created by blitzer on 21.06.2016.
 */
public class ApiLink {

    private final List<String> links;
    private final List<String> methods;
    private List<ApiLinkParams> apiLinkParams;

    public ApiLink(List<String> links, List<String> methods, List<ApiLinkParams> apiLinkParams) {
        this.links = links;
        this.methods = methods;
        this.apiLinkParams = apiLinkParams;
    }

    public List<String> getLinks() {
        return links;
    }

    public List<String> getMethods() {
        return methods;
    }

    public List<ApiLinkParams> getApiLinkParams() {
        return apiLinkParams;
    }

    public static class ApiLinkParams {

        public final String name;
        public final boolean required;
        public final String defaultValue;

        public ApiLinkParams(String name, boolean required, String defaultValue) {
            this.name = name;
            this.required = required;
            this.defaultValue = defaultValue;
        }

        public String getName() {
            return name;
        }

        public boolean isRequired() {
            return required;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        @Override
        public String toString() {
            return "ApiLinkParams{" +
                    "name='" + name + '\'' +
                    ", required=" + required +
                    ", defaultValue='" + defaultValue + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ApiLink{" +
                "links=" + links +
                ", methods=" + methods +
                ", apiLinkParams=" + apiLinkParams +
                '}';
    }
}

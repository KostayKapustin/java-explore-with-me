package ru.practicum.ewmmain.controller;

import javax.servlet.http.HttpServletRequest;

public class RequestBuilder {
    public static String getStringFromRequest(HttpServletRequest request) {
        return request.getMethod() + ": " + request.getRequestURI() +
                (request.getQueryString() == null ? "" : "?" + request.getQueryString());
    }
}

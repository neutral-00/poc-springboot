package com.lousing.poc.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestScopedBean {
    private String requestId;

    public RequestScopedBean() {
        this.requestId = "REQUEST-" + System.currentTimeMillis();
    }

    public String getRequestId() {
        return requestId;
    }
}

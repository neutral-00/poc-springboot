package com.lousing.poc.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionScopedBean {
    private String sessionId;

    public SessionScopedBean() {
        this.sessionId = "SESSION-" + System.currentTimeMillis();
    }

    public String getSessionId() {
        return sessionId;
    }
}

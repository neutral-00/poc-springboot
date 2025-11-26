package com.lousing.poc.controllers;

import com.lousing.poc.scope.RequestScopedBean;
import com.lousing.poc.scope.SessionScopedBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebScopeDemoController {
    private final RequestScopedBean requestScopedBean;
    private final SessionScopedBean sessionScopedBean;

    public WebScopeDemoController(RequestScopedBean requestScopedBean, SessionScopedBean sessionScopedBean) {
        this.requestScopedBean = requestScopedBean;
        this.sessionScopedBean = sessionScopedBean;
    }

    @GetMapping("/demo-scopes")
    public String demoScopes() {
        String requestId = requestScopedBean.getRequestId();
        String sessionId = sessionScopedBean.getSessionId();

        return "Request ID: " + requestId + ", Session ID: " + sessionId;
    }
}

package com.eventledger.account.tracing;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TraceFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {

        String traceId =
                request.getHeader("X-Trace-Id");

        MDC.put("traceId", traceId);

        try {
            chain.doFilter(request,response);
        }
        finally {
            MDC.clear();
        }
    }
}

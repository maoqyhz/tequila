package xyz.fur.skeleton.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * ServletRequest包装过滤器
 * 解决InputStream只能读取一次的问题
 *
 * @author Fururur
 * @date 2020/5/6-14:26
 */
@Slf4j
@WebFilter(urlPatterns = "/*")
public class CachingContentFilter implements Filter {
    private static final String FORM_CONTENT_TYPE = "multipart/form-data";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String contentType = request.getContentType();
        if (request instanceof HttpServletRequest) {
            ServletRequest requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
            if (contentType != null && contentType.contains(FORM_CONTENT_TYPE)) {
                chain.doFilter(request, response);
            } else {
                chain.doFilter(requestWrapper, response);
            }
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // do nothing
    }
}
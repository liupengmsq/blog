package pengliu.me.controller;

import org.springframework.util.StringUtils;
import pengliu.me.common.CommonConstant;
import pengliu.me.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class BlogFilter implements Filter
{
    private static final String FILTERED_REQUEST = "@@session_context_filtered_request";

    // ① 需要登录才可访问的URI资源
    private static final String[] NEED_TO_LOGIN_URI = { "/management/category", "/management/tag", "/blog/listAll", "/blog/create", "/blog/delete" };

    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        // 1 保证该过滤器在一次请求中只被调用一次
        if (servletRequest != null && servletRequest.getAttribute(FILTERED_REQUEST) != null)
        {
            // 放行此请求, 不做拦截
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else
        {
            // 2 设置过滤标识，防止一次请求多次过滤
            servletRequest.setAttribute(FILTERED_REQUEST, Boolean.TRUE);
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
            User userContext = getSessionUser(httpRequest);

            // 3 用户未登录, 且当前URI资源需要登录才能访问
            if ((userContext == null || !isAdminUser(userContext)) && isURINeedToLogin(httpRequest.getRequestURI(), httpRequest))
            {
                String toUrl = httpRequest.getRequestURL().toString();
                if (!StringUtils.isEmpty(httpRequest.getQueryString()))
                {
                    toUrl += "?" + httpRequest.getQueryString();
                }

                // 4 将用户的请求URL保存在session中，用于登录成功之后，跳到目标URL
                httpRequest.getSession().setAttribute(CommonConstant.LOGIN_TO_URL, toUrl);

                // 5 拦截请求, 转发到登录页面
                servletRequest.getRequestDispatcher("/login.jsp").forward(servletRequest, servletResponse);
                return;
            }

            // 放行此请求, 不做拦截
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    /**
     * 当前URI资源是否需要登录才能访问
     * @param requestURI
     * @param request
     * @return
     */
    private boolean isURINeedToLogin(String requestURI, HttpServletRequest request)
    {
        //判断当浏览器请求的是根目录"/"的时候, 不需要跳到登陆页面
        if (request.getContextPath().equalsIgnoreCase(requestURI)
                || (request.getContextPath() + "/").equalsIgnoreCase(requestURI))
            return false;

        for (String uri : NEED_TO_LOGIN_URI)
        {
            if (requestURI != null && requestURI.contains(uri))
            {
                return true;
            }
        }
        return false;
    }

    protected User getSessionUser(HttpServletRequest request)
    {
        return (User) request.getSession().getAttribute(CommonConstant.USER_CONTEXT);
    }

    private Boolean isAdminUser(User user)
    {
        return user.getName().equals(CommonConstant.ADMIN_USER_NAME);
    }

    public void destroy()
    {

    }
}

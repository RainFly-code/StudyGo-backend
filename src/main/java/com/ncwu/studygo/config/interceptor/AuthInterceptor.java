package com.ncwu.studygo.config.interceptor;

import com.ncwu.studygo.common.result.Result;
import com.ncwu.studygo.common.result.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 权限拦截器，用于验证用户身份和权限
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行OPTIONS请求
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        // 获取请求路径
        String requestURI = request.getRequestURI();
        logger.debug("请求路径: {}", requestURI);

        // 不需要验证的路径直接放行
        if (isExcludedPath(requestURI)) {
            logger.debug("无需验证的路径: {}", requestURI);
            return true;
        }

        // 从会话中获取用户信息
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("userId") == null) {
            logger.debug("用户未登录，session为null或无userId属性");
            handleUnauthorized(response, "未登录");
            return false;
        }

        // 验证管理员权限
        Integer role = (Integer) session.getAttribute("role");
        logger.debug("用户角色: {}", role);
        
        // 将会话中的用户信息设置到请求属性中，方便控制器使用
        request.setAttribute("userId", session.getAttribute("userId"));
        request.setAttribute("username", session.getAttribute("username"));
        request.setAttribute("role", role);

        if (requestURI.startsWith("/api/admin/") && (role == null || role != 1)) {
            logger.debug("用户角色无权访问该路径, 角色: {}, 路径: {}", role, requestURI);
            handleForbidden(response, "无权限访问");
            return false;
        }

        return true;
    }

    /**
     * 判断是否为不需要验证的路径
     *
     * @param requestURI 请求路径
     * @return 是否为不需要验证的路径
     */
    private boolean isExcludedPath(String requestURI) {
        return requestURI.startsWith("/api/user/register") ||
                requestURI.startsWith("/api/user/login") ||
                requestURI.startsWith("/api/announcement/list") ||
                (requestURI.startsWith("/api/announcement/") && !requestURI.startsWith("/api/announcement/add")) ||
                requestURI.startsWith("/api/studyroom/list") ||
                requestURI.startsWith("/api/studyroom/search") ||
                (requestURI.startsWith("/api/studyroom/") && !requestURI.startsWith("/api/studyroom/add"));
    }

    /**
     * 处理未授权的请求
     *
     * @param response 响应对象
     * @param message  错误消息
     * @throws IOException IO异常
     */
    private void handleUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(Result.failed(ResultCode.UNAUTHORIZED.getCode(), message)));
    }

    /**
     * 处理禁止访问的请求
     *
     * @param response 响应对象
     * @param message  错误消息
     * @throws IOException IO异常
     */
    private void handleForbidden(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(objectMapper.writeValueAsString(Result.failed(ResultCode.FORBIDDEN.getCode(), message)));
    }
}
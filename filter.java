package com;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(urlPatterns = {"*.do","..html"},
        initParams={@WebInitParam(name = "bai" , value = "page.do?operate=pag&page=user/login")}
)


public class filter implements Filter {

    List<String> strings =null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String bai = filterConfig.getInitParameter("bai");
        String[] split = bai.split(",");
        List<String> strings = Arrays.asList(split);


    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        String string = request.getQueryString();
        String str = uri+"?"+string;
        if(strings.contains(str)){
            filterChain.doFilter(request,response);
            return;
        }else {

            HttpSession session = request.getSession();
            Object currUser = session.getAttribute("currUser");//找到用户
            if (currUser == null) {
                response.sendRedirect("page.do?operate=pag&page=user/login");//重定向
            }else {
                filterChain.doFilter(request,response);//放行
            }

        }


    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

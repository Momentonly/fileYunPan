package com.szxy.config.druid;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Druid
 *    Servlet与Fileter配置
 */
//@Configuration
public class DruidServletConfig {

    /**
     * druid内置提供一个StatViewServlet用于展示druid的统计信息
     * StatViewServlet的用途有两个:
     * 		提供监控信息展示的html页面
     * 		提供监控信息的JSON API
     * 属性配置 :
     * 		1.urlMapping - 监控界面的访问路径（默认 /druid/*）
     * 		2.loginUsername - 监控界面登录用户名称
     * 		3.loginPassword - 监控界面登录用户密码
     * 		4.allow - 允许访问的主机地址，不设置则为运行所有主机访问
     * 		5.deny - 拒绝访问的主机地址
     * 		6.restEnable - 是否可一次性清除所有监控数据
     * @return
     */
    @Bean
    public ServletRegistrationBean registrationBean() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet());    //添加初始化参数：initParams
        servletRegistrationBean.addUrlMappings("/druid/*");
        //白名单：
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny", "192.168.1.100");
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * druid连接池与web关联配置
     * 属性配置 :
     * 		1.urlPatter - 过滤的路径
     *		2.exclusions - 过滤的资源
     *		3.sessinStatMaxCount - session的最多个数
     *		4.profilEnable - 监控统计单个URL的SQL列表
     *		5.sessionStatEnable - session统计
     *		6.principalSessionName - session用户名称统计
     *		7.principalCookieName - cookie统计
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}

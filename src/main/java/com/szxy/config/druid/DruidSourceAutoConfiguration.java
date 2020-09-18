package com.szxy.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

//@Configuration
//@EnableConfigurationProperties(DruidSourceProperties.class)
public class DruidSourceAutoConfiguration {

    @Autowired
    private DruidSourceProperties properties;

    @Bean
    @Primary //Spring容器中存在多个DataSource，首先使用被@Primary标注的DataSource
    public DataSource dataSource() throws SQLException {
        DruidDataSource datasource = new DruidDataSource();

        // basic connection information configuration
        datasource.setUrl(properties.getDbUrl());
        datasource.setUsername(properties.getUsername());
        datasource.setPassword(properties.getPassword());
        datasource.setDriverClassName(properties.getDriverClassName());

        //other configuration
        datasource.setInitialSize(properties.getInitialSize());
        datasource.setMinIdle(properties.getMinIdle());
        datasource.setMaxActive(properties.getMaxActive());
        datasource.setMaxWait(properties.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        datasource.setValidationQuery(properties.getValidationQuery());
        datasource.setTestWhileIdle(properties.isTestWhileIdle());
        datasource.setTestOnBorrow(properties.isTestOnBorrow());
        datasource.setTestOnReturn(properties.isTestOnReturn());
        datasource.setPoolPreparedStatements(properties.isPoolPreparedStatements());
        datasource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());
        datasource.setFilters(properties.getFilters());
        return datasource;
    }
}

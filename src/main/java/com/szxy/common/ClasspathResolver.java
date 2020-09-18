package com.szxy.common;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 路径解析资源类
 */
public class ClasspathResolver {

    /**
     * classpath:META-INF/*.xml
     * classpath*:META-INF/*.xml
     * ...
     * @param locationPatterns 路径模式
     * @return
     */
    public static Resource[] resolveLocations(String[] locationPatterns){
        List<Resource> resourcesLists = new ArrayList<Resource>();
        if (locationPatterns != null) {
            for (String locationPattern: locationPatterns) {
                Resource[] res;
                try {
                    res = new PathMatchingResourcePatternResolver().getResources(locationPattern);
                    resourcesLists.addAll(Arrays.asList(res));
                } catch (IOException e) {
                    //
                }
            }
        }
        Resource[] resources = new Resource[resourcesLists.size()];
        resources = resourcesLists.toArray(resources);
        return resources;
    }
}

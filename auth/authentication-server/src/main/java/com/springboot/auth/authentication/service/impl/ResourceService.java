package com.springboot.auth.authentication.service.impl;

import com.springboot.cloud.sysadmin.organization.entity.po.Resource;
import com.springboot.auth.authentication.provider.ResourceProvider;
import com.springboot.auth.authentication.service.IResourceService;
import com.springboot.auth.authentication.service.NewMvcRequestMatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ResourceService implements IResourceService {

    @Autowired
    private HandlerMappingIntrospector mvcHandlerMappingIntrospector;

    @Autowired
    private ResourceProvider resourceProvider;

    /**
     * 系统中所有权限集合
     */
    private Map<RequestMatcher, ConfigAttribute> resourceConfigAttributes;

    @Override
    public void saveResource(Resource resource) {
        resourceConfigAttributes.put(
                this.newMvcRequestMatcher(resource.getUrl(), resource.getMethod()),
                new SecurityConfig(resource.getCode())
        );
        log.info("resourceConfigAttributes size:{}", this.resourceConfigAttributes.size());
    }

    @Override
    public void removeResource(Resource resource) {
        resourceConfigAttributes.remove(this.newMvcRequestMatcher(resource.getUrl(), resource.getMethod()));
        log.info("resourceConfigAttributes size:{}", this.resourceConfigAttributes.size());
    }

    @Override
    public Map<RequestMatcher, ConfigAttribute> loadResource() {
        Set<Resource> resources = resourceProvider.resources().getData();
        this.resourceConfigAttributes = resources.stream()
                .collect(Collectors.toMap(
                        resource -> this.newMvcRequestMatcher(resource.getUrl(), resource.getMethod()),
                        resource -> new SecurityConfig(resource.getCode())
                ));
        log.debug("resourceConfigAttributes:{}", this.resourceConfigAttributes);
        return this.resourceConfigAttributes;
    }

    @Override
    public ConfigAttribute findConfigAttributesByUrl(HttpServletRequest authRequest) {
                //获取map的所有key转换成流对象
        return this.resourceConfigAttributes.keySet().stream()
                //对流里面的每一个元素进行过滤，过滤参数是authRequest,此时map的key被选出了一部分
                .filter(requestMatcher -> requestMatcher.matches(authRequest))
                //（接上面的流）把流里面每一个key变成map中对应的value
                .map(requestMatcher -> this.resourceConfigAttributes.get(requestMatcher))
                //返回一个value的流
                .peek(urlConfigAttribute -> log.debug("url在资源池中配置：{}", urlConfigAttribute.getAttribute()))
                .findFirst()
                .orElse(new SecurityConfig("NONEXISTENT_URL"));
    }

    @Override
    public Set<Resource> queryByUsername(String username) {
        return resourceProvider.resources(username).getData();
    }

    /**
     * 创建RequestMatcher
     *
     * @param url
     * @param method
     * @return
     */
    private MvcRequestMatcher newMvcRequestMatcher(String url, String method) {
        return new NewMvcRequestMatcher(mvcHandlerMappingIntrospector, url, method);
    }
}
package com.springboot.auth.authorization.config;

import com.google.common.collect.Lists;
import com.springboot.auth.authorization.oauth2.enhancer.CustomTokenEnhancer;
import com.springboot.auth.authorization.exception.CustomWebResponseExceptionTranslator;
import com.springboot.auth.authorization.oauth2.granter.MobileTokenGranter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

/**
 * 授权服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthenticationServerConfig extends AuthorizationServerConfigurerAdapter {

    /** 认证管理器 **/
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    @Autowired
    @Qualifier("userDetailsService")
    UserDetailsService userDetailsService;

    /**
     * jwt 对称加密密钥
     */
    @Value("${spring.security.oauth2.jwt.signingKey}")
    private String signingKey;

    /**
     * 配置Token节点的安全策略
     * @param oauthServer 声明安全约束，哪些允许访问，哪些不允许访问
     *                    配置 AuthorizationServer 的安全属性，也就是endpoint /oauth/token
     *                    /oauth/authorize 则和其它用户 REST 一样保护。可以不配置
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        // 支持将client参数放在header或body中
        oauthServer.allowFormAuthenticationForClients();
        // 开启/oauth/check_token验证端口认证权限访问
        oauthServer.tokenKeyAccess("isAuthenticated()")
                // 开启/oauth/token_key验证端口无权限访问
                .checkTokenAccess("permitAll()");
    }

    /**
     * 配置 client客户端的信息。
     * 包括权限范围、授权方式、客户端权限等配置。
     * 授权方式有4种:implicit, client_redentials, password , authorization_code,
     * 其中密码授权方式必须结合 AuthenticationManager 进行配置。必须至少配置一个客户端。
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 配置客户端信息，从数据库中读取，对应oauth_client_details表
        clients.jdbc(dataSource);
    }

    /**
     *  配置授权Token的节点和Token服务
     *  配置AuthorizationServer 端点的非安全属性，也就是 token 存储方式、token 配置、用户授权模式等。
     *  默认不需做任何配置，除非使用 密码授权方式, 这时候必须配置 AuthenticationManager
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // 配置token的数据源、自定义的tokenServices等信息,配置身份认证器，配置认证方式，TokenStore，TokenGranter，OAuth2RequestFactory
        endpoints.tokenStore(tokenStore())
                //配置验证码服务
                .authorizationCodeServices(authorizationCodeServices())
                .approvalStore(approvalStore())
                //异常处理
                .exceptionTranslator(customExceptionTranslator())
                //自定义token
                .tokenEnhancer(tokenEnhancerChain())
                //密码认证
                .authenticationManager(authenticationManager)
                //配置获取用户认证信息的接口
                .userDetailsService(userDetailsService)
                //配置Token Granter
                .tokenGranter(tokenGranter(endpoints));

    }

    /**
     * 自定义OAuth2异常处理
     *
     * @return CustomWebResponseExceptionTranslator
     */
    @Bean
    public WebResponseExceptionTranslator<OAuth2Exception> customExceptionTranslator() {
        return new CustomWebResponseExceptionTranslator();
    }

    /**
     * 授权信息持久化实现
     *
     * @return JdbcApprovalStore
     */
    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    /**
     * 授权码模式持久化授权码code
     *
     * @return JdbcAuthorizationCodeServices
     */
    @Bean
    protected AuthorizationCodeServices authorizationCodeServices() {
        // 授权码存储等处理方式类，使用jdbc，操作oauth_code表
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * token的持久化
     *
     * @return JwtTokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * 自定义token
     *
     * @return tokenEnhancerChain
     */
    @Bean
    public TokenEnhancerChain tokenEnhancerChain() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new CustomTokenEnhancer(), accessTokenConverter()));
        return tokenEnhancerChain;
    }

    /**
     * jwt token的生成配置
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        return converter;
    }

    /**
     * 配置自定义的granter,手机号验证码登陆
     *
     * @param endpoints
     * @return
     * @auth joe_chen
     */
    public TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = Lists.newArrayList(endpoints.getTokenGranter());
        granters.add(new MobileTokenGranter(
                authenticationManager,
                endpoints.getTokenServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory()));
        return new CompositeTokenGranter(granters);
    }

}
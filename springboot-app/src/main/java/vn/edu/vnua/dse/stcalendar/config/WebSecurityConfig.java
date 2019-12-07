package vn.edu.vnua.dse.stcalendar.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.vnua.dse.stcalendar.CustomCorsFilter;
import vn.edu.vnua.dse.stcalendar.security.RestAuthenticationEntryPoint;
import vn.edu.vnua.dse.stcalendar.security.auth.ajax.AjaxAuthenticationProvider;
import vn.edu.vnua.dse.stcalendar.security.auth.ajax.AjaxLoginProcessingFilter;
import vn.edu.vnua.dse.stcalendar.security.jwt.JwtAuthenticationProvider;
import vn.edu.vnua.dse.stcalendar.security.jwt.JwtTokenAuthenticationProcessingFilter;
import vn.edu.vnua.dse.stcalendar.security.jwt.SkipPathRequestMatcher;
import vn.edu.vnua.dse.stcalendar.security.jwt.extractor.TokenExtractor;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
    public static final String AUTHENTICATION_URL = "/api/auth/login";
    public static final String REFRESH_TOKEN_URL = "/api/auth/token";
    public static final String REGISTER_URL = "/api/auth/register";
    public static final String REGISTER_CONFIRM_URL ="/api/auth/register/confirm";
    public static final String API_ROOT_URL = "/api/**";
    
    @Autowired private RestAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired private AuthenticationSuccessHandler successHandler;
    @Autowired private AuthenticationFailureHandler failureHandler;
    @Autowired private AjaxAuthenticationProvider ajaxAuthenticationProvider;
    @Autowired private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired private TokenExtractor tokenExtractor;

    @Autowired private AuthenticationManager authenticationManager;

    @Autowired private ObjectMapper objectMapper;
    
    //Tạo login filter
    protected AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter(String loginEntryPoint) throws Exception {
        AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(loginEntryPoint, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }
    
  //Tạo jwt token filter
    protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter(List<String> pathsToSkip, String pattern) throws Exception {
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, pattern);
        JwtTokenAuthenticationProcessingFilter filter
            = new JwtTokenAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }
    
    //Tạo bean AuthenticationManager
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    //Config các AuthenticationProvider
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(ajaxAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }
    //Config các filter, url, ...
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<String> permitAllEndpointList = Arrays.asList(
                AUTHENTICATION_URL,
                REFRESH_TOKEN_URL,
                REGISTER_URL,
                REGISTER_CONFIRM_URL,
                "/console"
            );

            http
                .csrf().disable() // We don't need CSRF for JWT based authentication
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)

                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                    .authorizeRequests()
                    .antMatchers(permitAllEndpointList.toArray(new String[permitAllEndpointList.size()]))
                    .permitAll()
                .and()
                    .authorizeRequests()
                    .antMatchers(API_ROOT_URL).authenticated() // Protected API End-points
                    
                    .and()
                    .addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(buildAjaxLoginProcessingFilter(AUTHENTICATION_URL), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(permitAllEndpointList,
                    API_ROOT_URL), UsernamePasswordAuthenticationFilter.class);                    
    }
}

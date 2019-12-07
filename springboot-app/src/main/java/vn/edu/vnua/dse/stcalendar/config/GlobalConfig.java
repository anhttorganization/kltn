package vn.edu.vnua.dse.stcalendar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component("globalConfig")
@Getter
public class GlobalConfig {
	@Value("${app.appURL}")
	private String APP_URL;
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
	
}

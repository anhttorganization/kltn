package vn.edu.vnua.dse.stcalendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import vn.edu.vnua.dse.stcalendar.security.model.UserContext;

@SpringBootApplication
@EnableConfigurationProperties
public class StcalendarApplication {

	public static void main(String[] args) {
//		SpringApplication.run(StcalendarApplication.class, args);
		ApplicationContext context = (ApplicationContext) SpringApplication.run(StcalendarApplication.class, args);
		
		
//		String[] beanNames = context.getBeanDefinitionNames();
//        
//        Arrays.sort(beanNames);
// 
//        for (String beanName : beanNames) {
//            System.out.println(beanName);
//        }
	
        
	}

}

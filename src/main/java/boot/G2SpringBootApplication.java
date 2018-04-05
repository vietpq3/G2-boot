package boot;

import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@SpringBootApplication(scanBasePackages = { "controller", "logicImpl", "daoImpl" })
public class G2SpringBootApplication {

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(G2SpringBootApplication.class, args);
	}

	@Bean
	public DataSource initDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));

		return dataSource;
	}

	@Bean(name = "messageSource")
	public MessageSource initMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		messageSource.setBasename(env.getProperty("spring.messages.basename"));
		messageSource.setCacheSeconds(Integer.parseInt(env.getProperty("spring.messages.cache-duration")));
		
		return messageSource;
	}

	@Bean(name = "localeResolver")
	public LocaleResolver initLocaleResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
//		localeResolver.setDefaultLocale(new Locale(env.getProperty("spring.mvc.locale")));
		localeResolver.setDefaultLocale(Locale.ENGLISH);

		return localeResolver;
	}
}

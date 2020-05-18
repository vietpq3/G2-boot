package boot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowire;
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

import constant.XOConstant;

@SpringBootApplication(scanBasePackages = { "controller", "logicImpl", "daoImpl", "config" })
public class G2BootApplication {

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(G2BootApplication.class, args);
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
	public MessageSource setMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		messageSource.setCacheSeconds(10);
		messageSource.setBasename("classpath:static/message");

		return messageSource;
	}

	@Bean(name = "localeResolver")
	public LocaleResolver initLocaleResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(Locale.ENGLISH);
		return localeResolver;
	}

	@Bean(name = "playerList", autowire = Autowire.BY_NAME)
	public List<String> getList() {
		return new ArrayList<>();
	}

	@Bean(name = "playerId")
	public String getPlayerId() {
		return new String();
	}

	@Bean(name = "previousPlayerId")
	public String getPreviousPlayerId() {
		return new String();
	}

	@Bean(name = "board")
	public String[][] getBoard() {
		return new String[XOConstant.SIZE][XOConstant.SIZE];
	}
}

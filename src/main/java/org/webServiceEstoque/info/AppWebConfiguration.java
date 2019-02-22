package org.webServiceEstoque.info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.webServiceEstoque.controllers.AutorizadorInterceptor;
import org.webServiceEstoque.controllers.HomeController;
import org.webServiceEstoque.controllers.WebService;
import org.webServiceEstoque.dao.ProdutoDAO;



@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses={WebService.class, ProdutoDAO.class, HomeController.class})
public class AppWebConfiguration extends WebMvcConfigurerAdapter {
	
	@Override
	   public void addInterceptors(InterceptorRegistry registry) {
	      // Register guest interceptor with single path pattern
	      registry.addInterceptor(new AutorizadorInterceptor());

	   }

	
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver(){
	    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	    resolver.setPrefix("/WEB-INF/views/");
	    resolver.setSuffix(".jsp");
	    return resolver;
	}
	
}

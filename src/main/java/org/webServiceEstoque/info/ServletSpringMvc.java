package org.webServiceEstoque.info;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.webServiceEstoque.controllers.AutorizadorInterceptor;

public class ServletSpringMvc extends AbstractAnnotationConfigDispatcherServletInitializer{

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppWebConfiguration.class, JPAConfiguration.class , AutorizadorInterceptor.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

}
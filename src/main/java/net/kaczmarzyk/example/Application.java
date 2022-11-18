package net.kaczmarzyk.example;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;


@SpringBootConfiguration
@ComponentScan(basePackages="net.kaczmarzyk")
@EnableAutoConfiguration
public class Application implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SpecificationArgumentResolver());
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    public static final String PREFIX = "/api";
    private static final String[] API_VERSIONS = new String[] {"v1"};

    /*
     * add prefix PREFIX and VERSION to all Webservice API Spring REST
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        for (String version : API_VERSIONS) {
            configurer.addPathPrefix(PREFIX + "/" + version, c -> c.isAnnotationPresent(RestController.class));
        }
    }
}


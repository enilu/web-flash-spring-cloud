package cn.enilu.flash.manage;

import cn.enilu.flash.common.dao.BaseRepositoryFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ApiApplication
 *
 * @author enilu
 * @version 2018/9/11 0011
 */
@EnableCaching
@SpringBootApplication
@ComponentScan(basePackages = "cn.enilu.flash")
@EntityScan(basePackages = {"cn.enilu.flash.common.bean.entity"})
@EnableJpaRepositories(basePackages = {"cn.enilu.flash.common.dao"}, repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@EnableJpaAuditing
@EnableOpenApi
public class ManageApplication extends SpringBootServletInitializer {
    private static Logger logger = LoggerFactory.getLogger(ManageApplication.class);
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(ManageApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String active = env.getProperty("spring.profiles.active");
        String port = env.getProperty("server.port");
        port = port == null ? "8080" : port;
        String path = env.getProperty("server.servlet.context-path");
        path = path == null ? "" : path;
        logger.info("\n----------------------------------------------------------\n\t" +
                "web-flash-spring-cloud is running! \n\t" +
                "系统运行环境 : \t" +active + "\n\t" +
                "本地访问地址 : \thttp://localhost:" + port + path + "/\n\t" +
                "外部访问地址 : \thttp://" + ip + ":" + port + path + "/\n\t" +
                "在线文档地址 : \thttp://" + ip + ":" + port + path + "/swagger-ui/index.html\n" +
                "----------------------------------------------------------");
    }
}

package cn.enilu.flash.message;

import cn.enilu.flash.common.dao.BaseRepositoryFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * MessageApplication
 *
 * @Author enilu
 * @Date 2021/6/22 23:27
 * @Version 1.0
 */
@EnableCaching
@SpringBootApplication
@ComponentScan(basePackages = "cn.enilu.flash")
@EntityScan(basePackages = {"cn.enilu.flash.common.bean.entity"})
@EnableJpaRepositories(basePackages = {"cn.enilu.flash.common.dao"}, repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@EnableJpaAuditing
@EnableOpenApi
public class MessageApplication {
    private static Logger logger = LoggerFactory.getLogger(MessageApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(MessageApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String appName = env.getProperty("spring.application.name");
        port = port == null ? "8012" : port;
        String path = env.getProperty("server.servlet.context-path");
        path = path == null ? "" : path;
        logger.info("\n----------------------------------------------------------\n\t" +
                appName+ " is running! \n\t" +

                "本地访问地址 : \thttp://localhost:" + port + path + "/\n\t" +
                "外部访问地址 : \thttp://" + ip + ":" + port + path + "/\n\t" +
                "在线文档地址 : \thttp://" + ip + ":" + port + path + "/swagger-ui/index.html\n" +
                "----------------------------------------------------------");
    }
}

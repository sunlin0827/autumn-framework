package org.autumn.autoconfiguration.aspectlog;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 定义自动配置类
 *
 * @author : SunLin
 * @date : 2020/8/19
 */
@Configuration
@ComponentScan("org.autumn.autoconfiguration.aspectlog")
@EnableConfigurationProperties(AspectLogProperties.class)
//控制该Configuration是否生效
@ConditionalOnProperty(prefix = "log", name = "enable", havingValue = "true", matchIfMissing = true)
public class AspectLogAutoConfiguration {

}

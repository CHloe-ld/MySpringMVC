package springIoc;


import springIoc.annotation.Bean;
import springIoc.annotation.ComponentScan;
import springIoc.annotation.Configuration;
import springIoc.test.classes.Teacher;

@Configuration
@ComponentScan({"springIoc.controller"})// 扫描controller包
public class Configure {
    // 使用配置类注入teacher类
    @Bean
    public Teacher teacher(){
        return new Teacher();
    }
}

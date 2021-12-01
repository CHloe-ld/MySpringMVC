package myioc;


import myioc.annotation.Bean;
import myioc.annotation.ComponentScan;
import myioc.annotation.Configuration;
import myioc.test.classes.Teacher;

@Configuration
@ComponentScan({"myioc.test.classes"})// 扫描myioc.test.classes包
public class Configure {
    // 使用配置文件注入teacher类
    @Bean
    public Teacher teacher(){
        return new Teacher();
    }
}

package vn.trungduc.springboot_admin_crud;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootAdminCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAdminCrudApplication.class, args);
    }

    /**
     * Đăng ký Thymeleaf Layout Dialect bean để hỗ trợ layout:decorate và layout:fragment
     */
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}

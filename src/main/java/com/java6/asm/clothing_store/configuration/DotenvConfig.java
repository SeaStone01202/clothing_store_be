package com.java6.asm.clothing_store.configuration;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure()
            .ignoreIfMissing() // <-- dòng này giúp không crash nếu file không tồn tại
            .load();
    }
}

package kg.brigada.telegrambot.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class BotConfig {

    // Имя бота заданное при регистрации
    @Value("${bot.username}")
    public String botUserName;

    // Токен полученный при регистра
    @Value("${bot.token}")
    public String token;
}
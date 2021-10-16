package kg.brigada.telegrambot.bootstrap;

import kg.brigada.telegrambot.entities.Bludo;
import kg.brigada.telegrambot.repos.BludoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Bootstrap implements CommandLineRunner {
    @Autowired
    private BludoRepo bludoRepo;

    @Override
    public void run(String... args) throws Exception {
        List<Bludo> bludoList = new ArrayList<>();
        bludoList.addAll(
                Arrays.asList(
                        Bludo.builder()
                                .name("Котлеты с говядиной")
                                .price(200.0)
                                .build(),
                        Bludo.builder()
                                .name("Котлеты с курицой")
                                .price(160.0)
                                .build(),
                        Bludo.builder()
                                .name("Лагман босо")
                                .price(220.0)
                                .build(),
                        Bludo.builder()
                                .name("Лагман гюро")
                                .price(220.0)
                                .build(),
                        Bludo.builder()
                                .name("Лагман жидкий")
                                .price(180.0)
                                .build(),
                        Bludo.builder()
                                .name("Чебуреки жаренные")
                                .price(200.0)
                                .build(),
                        Bludo.builder()
                                .name("Чечевичный суп")
                                .price(120.0)
                                .build(),
                        Bludo.builder()
                                .name("Солянка")
                                .price(175.0)
                                .build(),
                        Bludo.builder()
                                .name("Грибной")
                                .price(145.0)
                                .build(),
                        Bludo.builder()
                                .name("Шорпо")
                                .price(160.0)
                                .build(),
                        Bludo.builder()
                                .name("Борщ")
                                .price(120.0)
                                .build(),
                        Bludo.builder()
                                .name("Куриный суп-лапша")
                                .price(110.0)
                                .build(),
                        Bludo.builder()
                                .name("Мампар")
                                .price(140.0)
                                .build()
                        )
        );

        bludoRepo.saveAll(bludoList);
    }
}

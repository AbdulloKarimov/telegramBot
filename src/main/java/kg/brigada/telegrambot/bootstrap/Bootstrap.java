package kg.brigada.telegrambot.bootstrap;

import kg.brigada.telegrambot.entities.BludaCategory;
import kg.brigada.telegrambot.entities.Bludo;
import kg.brigada.telegrambot.repos.BludaCategoryRepo;
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

    @Autowired
    private BludaCategoryRepo bludaCategoryRepo;

    @Override
    public void run(String... args) throws Exception {

        BludaCategory zavtrak = BludaCategory.builder()
                .name("Завтраки")
                .build();
        bludaCategoryRepo.save(zavtrak);
        BludaCategory sup = BludaCategory.builder()
                .name("Супы")
                .build();
        bludaCategoryRepo.save(sup);
        BludaCategory salat = BludaCategory.builder()
                .name("Салаты")
                .build();
        bludaCategoryRepo.save(salat);
        BludaCategory gorBlud = BludaCategory.builder()
                .name("Горячие блюда")
                .build();
        bludaCategoryRepo.save(gorBlud);
        BludaCategory pelmen = BludaCategory.builder()
                .name("Пелмени")
                .build();
        bludaCategoryRepo.save(pelmen);
        BludaCategory vareniki = BludaCategory.builder()
                .name("Вареники")
                .build();
        bludaCategoryRepo.save(vareniki);
        BludaCategory manty = BludaCategory.builder()
                .name("Манты")
                .build();
        bludaCategoryRepo.save(manty);
        BludaCategory other = BludaCategory.builder()
                .name("Другое")
                .build();
        bludaCategoryRepo.save(other);

        List<Bludo> bludoList = new ArrayList<>();
        bludoList.addAll(
          Arrays.asList(
                  Bludo.builder()
                  .name("Блинчики со сметаной")
                  .price(99.0)
                  .bludaCategory(zavtrak)
                  .build(),
                  Bludo.builder()
                  .name("Блинчики с джемом")
                  .price(99.0)
                  .bludaCategory(zavtrak)
                  .build(),
                  Bludo.builder()
                  .name("Сырники со сметаной")
                  .price(119.0)
                  .bludaCategory(zavtrak)
                  .build(),
                  Bludo.builder()
                  .name("Глазунья из двух яиц")
                  .price(69.0)
                  .bludaCategory(zavtrak)
                  .build(),
                  Bludo.builder()
                  .name("Рисовая каша")
                  .price(79.0)
                  .bludaCategory(zavtrak)
                  .build(),
                  Bludo.builder()
                  .name("Овсяная каша")
                  .price(79.0)
                  .bludaCategory(zavtrak)
                  .build(),
                  Bludo.builder()
                  .name("Вареное яйцо (1шт)")
                  .price(20.0)
                  .bludaCategory(zavtrak)
                  .build()
          )
        );
        bludoList.addAll(
                Arrays.asList(
                        Bludo.builder()
                        .name("Свекольный")
                        .price(100.0)
                        .bludaCategory(salat)
                        .build(),
                        Bludo.builder()
                        .name("Винегрет")
                        .price(100.0)
                        .bludaCategory(salat)
                        .build(),
                        Bludo.builder()
                        .name("Оливье")
                        .price(120.0)
                        .bludaCategory(salat)
                        .build(),
                        Bludo.builder()
                        .name("Морковный")
                        .price(80.0)
                        .bludaCategory(salat)
                        .build(),
                        Bludo.builder()
                        .name("Лукошко")
                        .price(120.0)
                        .bludaCategory(salat)
                        .build(),
                        Bludo.builder()
                        .name("Витаминка")
                        .price(100.0)
                        .bludaCategory(salat)
                        .build(),
                        Bludo.builder()
                        .name("Греческий")
                        .price(120.0)
                        .bludaCategory(salat)
                        .build(),
                        Bludo.builder()
                        .name("Свежий")
                        .price(120.0)
                        .bludaCategory(salat)
                        .build(),
                        Bludo.builder()
                        .name("Пекинский")
                        .price(100.0)
                        .bludaCategory(salat)
                        .build()
                )
        );
        bludoList.addAll(
                Arrays.asList(
                        Bludo.builder()
                                .name("Чечевичный")
                                .price(120.0)
                                .bludaCategory(sup)
                                .build(),
                        Bludo.builder()
                                .name("Солянка")
                                .price(175.0)
                                .bludaCategory(sup)
                                .build(),
                        Bludo.builder()
                                .name("Грибной")
                                .price(145.0)
                                .bludaCategory(sup)
                                .build(),
                        Bludo.builder()
                                .name("Шорпо")
                                .price(160.0)
                                .bludaCategory(sup)
                                .build(),
                        Bludo.builder()
                                .name("Борщ")
                                .price(120.0)
                                .bludaCategory(sup)
                                .build(),
                        Bludo.builder()
                                .name("Куриный суп-лапша")
                                .price(110.0)
                                .bludaCategory(sup)
                                .build(),
                        Bludo.builder()
                                .name("Мампар")
                                .price(140.0)
                                .bludaCategory(sup)
                                .build()
                )
        );
        bludoList.addAll(
                Arrays.asList(
                        Bludo.builder()
                                .name("Котлеты с говядиной(гарнир на выбор, рис или гречка)")
                                .price(200.0)
                                .bludaCategory(gorBlud)
                                .build(),
                        Bludo.builder()
                                .name("Котлеты с куриные(гарнир на выбор, рис или гречка)")
                                .price(160.0)
                                .bludaCategory(gorBlud)
                                .build(),
                        Bludo.builder()
                                .name("Лагман босо")
                                .price(220.0)
                                .bludaCategory(gorBlud)
                                .build(),
                        Bludo.builder()
                                .name("Лагман гюро")
                                .price(220.0)
                                .bludaCategory(gorBlud)
                                .build(),
                        Bludo.builder()
                                .name("Лагман жидкий")
                                .price(180.0)
                                .bludaCategory(gorBlud)
                                .build(),
                        Bludo.builder()
                                .name("Беш бармак(по предзаказу за 3ч.,1кг мясо,1кг лапша)")
                                .price(1500.0)
                                .bludaCategory(gorBlud)
                                .build(),
                        Bludo.builder()
                                .name("Чебуреки жаренные")
                                .price(200.0)
                                .bludaCategory(gorBlud)
                                .build()
                )
        );
        bludoList.addAll(
                Arrays.asList(
                        Bludo.builder()
                                .name("Жадина-говядина мал.")
                                .price(170.0)
                                .bludaCategory(pelmen)
                                .build(),
                        Bludo.builder()
                                .name("Жадина-говядина бол.")
                                .price(230.0)
                                .bludaCategory(pelmen)
                                .build(),
                        Bludo.builder()
                                .name("Грибная охота мал")
                                .price(170.0)
                                .bludaCategory(pelmen)
                                .build(),
                        Bludo.builder()
                                .name("Грибная охота бол")
                                .price(230.0)
                                .bludaCategory(pelmen)
                                .build(),
                        Bludo.builder()
                                .name("Непростой барашек мал")
                                .price(190.0)
                                .bludaCategory(pelmen)
                                .build(),
                        Bludo.builder()
                                .name("Непростой барашек бол")
                                .price(240.0)
                                .bludaCategory(pelmen)
                                .build(),
                        Bludo.builder()
                                .name("ЧИИИИЗ! мал")
                                .price(170.0)
                                .bludaCategory(pelmen)
                                .build(),
                        Bludo.builder()
                                .name("ЧИИИИЗ! бол")
                                .price(250.0)
                                .bludaCategory(pelmen)
                                .build(),
                        Bludo.builder()
                                .name("Цыпа-Цыпа мал")
                                .price(180.0)
                                .bludaCategory(pelmen)
                                .build(),
                        Bludo.builder()
                                .name("Цыпа-Цыпа бол")
                                .price(230.0)
                                .bludaCategory(pelmen)
                                .build(),
                        Bludo.builder()
                                .name("Забавная пара мал")
                                .price(210.0)
                                .bludaCategory(pelmen)
                                .build(),
                        Bludo.builder()
                                .name("Забавная пара бол")
                                .price(280.0)
                                .bludaCategory(pelmen)
                                .build(),
                        Bludo.builder()
                                .name("Рыбка моя мал")
                                .price(255.0)
                                .bludaCategory(pelmen)
                                .build(),
                        Bludo.builder()
                                .name("Рыбка моя бол")
                                .price(355.0)
                                .bludaCategory(pelmen)
                                .build(),
                        Bludo.builder()
                                .name("Микс мал")
                                .price(210.0)
                                .bludaCategory(pelmen)
                                .build(),
                        Bludo.builder()
                                .name("Микс бол")
                                .price(260.0)
                                .bludaCategory(pelmen)
                                .build(),
                        Bludo.builder()
                                .name("Здоровый як мал")
                                .price(170.0)
                                .bludaCategory(pelmen)
                                .build(),
                        Bludo.builder()
                                .name("Здоровый як бол")
                                .price(240.0)
                                .bludaCategory(pelmen)
                                .build(),
                        Bludo.builder()
                                .name("Конь в пальто мал")
                                .price(160.0)
                                .bludaCategory(pelmen)
                                .build(),
                        Bludo.builder()
                                .name("Конь в пальто бол")
                                .price(230.0)
                                .bludaCategory(pelmen)
                                .build()
                )
        );
        bludoList.addAll(
                Arrays.asList(
                        Bludo.builder()
                                .name("Бабулины мал")
                                .price(130.0)
                                .bludaCategory(vareniki)
                                .build(),
                        Bludo.builder()
                                .name("Бабулины бол")
                                .price(170.0)
                                .bludaCategory(vareniki)
                                .build(),
                        Bludo.builder()
                                .name("Малиновка мал")
                                .price(150.0)
                                .bludaCategory(vareniki)
                                .build(),
                        Bludo.builder()
                                .name("Малиновка бол")
                                .price(210.0)
                                .bludaCategory(vareniki)
                                .build(),
                        Bludo.builder()
                                .name("Зимняя вишня мал")
                                .price(150.0)
                                .bludaCategory(vareniki)
                                .build(),
                        Bludo.builder()
                                .name("Зимняя вишня бол")
                                .price(210.0)
                                .bludaCategory(vareniki)
                                .build(),
                        Bludo.builder()
                                .name("Тарас бульба мал")
                                .price(140.0)
                                .bludaCategory(vareniki)
                                .build(),
                        Bludo.builder()
                                .name("Тарас бульба бол")
                                .price(180.0)
                                .bludaCategory(vareniki)
                                .build(),
                        Bludo.builder()
                                .name("Белым бело мал")
                                .price(150.0)
                                .bludaCategory(vareniki)
                                .build(),
                        Bludo.builder()
                                .name("Белым бело бол")
                                .price(210.0)
                                .bludaCategory(vareniki)
                                .build()
                )
        );
        bludoList.addAll(
                Arrays.asList(
                        Bludo.builder()
                                .name("Антошка с картошкой")
                                .price(30.0)
                                .bludaCategory(manty)
                                .build(),
                        Bludo.builder()
                                .name("Беседа с аксакалом")
                                .price(36.0)
                                .bludaCategory(manty)
                                .build(),
                        Bludo.builder()
                                .name("Белорусские")
                                .price(34.0)
                                .bludaCategory(manty)
                                .build(),
                        Bludo.builder()
                                .name("Карета золушки")
                                .price(32.0)
                                .bludaCategory(manty)
                                .build(),
                        Bludo.builder()
                                .name("Я у мамы мясоед")
                                .price(38.0)
                                .bludaCategory(manty)
                                .build(),
                        Bludo.builder()
                                .name("Микс")
                                .price(30.0)
                                .bludaCategory(manty)
                                .build(),
                        Bludo.builder()
                                .name("На джайлоо")
                                .price(32.0)
                                .bludaCategory(manty)
                                .build(),
                        Bludo.builder()
                                .name("Оранжевое счастье")
                                .price(30.0)
                                .bludaCategory(manty)
                                .build(),
                        Bludo.builder()
                                .name("Монгол")
                                .price(34.0)
                                .bludaCategory(manty)
                                .build(),
                        Bludo.builder()
                                .name("Манты деликатес")
                                .price(34.0)
                                .bludaCategory(manty)
                                .build()
                )
        );
        bludoRepo.saveAll(bludoList);
        bludoList.addAll(
                Arrays.asList(
                        Bludo.builder()
                                .name("Компот")
                                .price(30.0)
                                .bludaCategory(other)
                                .build(),
                        Bludo.builder()
                                .name("Хлеб")
                                .price(5.0)
                                .bludaCategory(other)
                                .build()
                )
        );
        bludoRepo.saveAll(bludoList);
    }
}

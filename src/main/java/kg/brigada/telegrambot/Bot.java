package kg.brigada.telegrambot;

import kg.brigada.telegrambot.configs.BotConfig;
import kg.brigada.telegrambot.entities.BludaCategory;
import kg.brigada.telegrambot.entities.Bludo;
import kg.brigada.telegrambot.entities.Order;
import kg.brigada.telegrambot.entities.Users;
import kg.brigada.telegrambot.repos.BludaCategoryRepo;
import kg.brigada.telegrambot.repos.BludoRepo;
import kg.brigada.telegrambot.repos.OrderRepo;
import kg.brigada.telegrambot.repos.UsersRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class Bot extends TelegramLongPollingBot {
    @Autowired
    private BludoRepo bludoRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private BludaCategoryRepo bludaCategoryRepo;

    final BotConfig config;

    public Bot(BotConfig config) {
        this.config = config;
    }


    @Override
    public String getBotUsername() {
        return config.botUserName;
    }

    @Override
    public String getBotToken() {
        return config.token;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            handleCallback(update.getCallbackQuery());
        }
        if (update.hasMessage()) {
            handleMessage(update.getMessage());

        }
    }

    @SneakyThrows
    private void handleCallback(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();


        String[] param = callbackQuery.getData().split(":");

        if (param[0].equals("cat")) {
            allBludasByCategory(message, buttons, param[1]);
        } else if (param[0].equals("blud")) {
            makeOrder(callbackQuery, message, param[1]);
        }

    }

    private void makeOrder(CallbackQuery callbackQuery, Message message, String s) throws org.telegram.telegrambots.meta.exceptions.TelegramApiException {
        Long id = Long.parseLong(s);
        Bludo bludo = bludoRepo.getById(id);
        System.out.println(bludo.getName());
        execute(SendMessage.builder().chatId(message.getChatId().toString())
                .text(
                        callbackQuery.getFrom().getFirstName()
                                + " выбрал "
                                + bludo.getName()
                ).build());
        Users users = null;
        if (usersRepo.getUsersByUserId(callbackQuery.getFrom().getId().toString()) == null) {
            users = usersRepo.save(Users.builder()
                    .firstName(callbackQuery.getFrom().getFirstName())
                    .lastName(callbackQuery.getFrom().getLastName())
                    .userName(callbackQuery.getFrom().getUserName())
                    .userId(callbackQuery.getFrom().getId().toString())
                    .build());
        } else {
            users = usersRepo.getUsersByUserId(callbackQuery.getFrom().getId().toString());
        }

        orderRepo.save(Order.builder()
                .bludo(bludo)
                .users(users)
                .orderDate(LocalDateTime.now())
                .build());
    }

    private void allBludasByCategory(Message message, List<List<InlineKeyboardButton>> buttons, String s) throws org.telegram.telegrambots.meta.exceptions.TelegramApiException {
        BludaCategory bludaCategory = bludaCategoryRepo.getById(Long.parseLong(s));
        List<Bludo> bludos = bludoRepo.findAllByBludaCategory(bludaCategory);
        for (Bludo bludo : bludos) {
            buttons.add(Arrays.asList(
                    InlineKeyboardButton.builder().text(bludo.getName() + " - " + bludo.getPrice() + "сом").callbackData("blud:" + bludo.getId().toString()).build()
            ));
        }
        execute(SendMessage.builder().chatId(
                message
                        .getChatId().toString())
                .text("Выберите блюдо из раздела " + bludaCategory.getName())
                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                .build()
        );
    }

    @SneakyThrows
    private void handleMessage(Message message) {
        if (message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntity = message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            if (commandEntity.isPresent()) {
                String command = "";
                if (message.getText().contains("@")) {
                    String param[] = message.getText().split("@");
                    command = param[0];
                } else {
                    command = message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                }
                switch (command) {
                    case "/startorder":
                        List<BludaCategory> bludaCategories = bludaCategoryRepo.findAll();

                        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
                        for (BludaCategory bludaCategory : bludaCategories) {
                            buttons.add(Arrays.asList(
                                    InlineKeyboardButton.builder().text(bludaCategory.getName()).callbackData("cat:" + bludaCategory.getId().toString()).build()
                            ));
                        }
                        execute(SendMessage.builder().chatId(
                                message
                                        .getChatId().toString())
                                .text("Выберите блюдо из меню ")
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                .build()
                        );
                        return;

                    case "/endorder":
                        LocalDateTime currDate = LocalDateTime.now();
                        List<Order> orders = orderRepo.findAllByOrderedIsNull();
                        orders = orders
                                .stream()
                                .filter(
                                        c -> c.getOrderDate().getYear() == currDate.getYear()
                                                && c.getOrderDate().getMonth().equals(currDate.getMonth())
                                                && c.getOrderDate().getDayOfMonth() == currDate.getDayOfMonth()
                                ).collect(Collectors.toList());
                        for (Order order : orders) {
                            order.setOrdered(true);
                        }
                        orderRepo.saveAll(orders);
                        List<TempClass> counOfBludas = new ArrayList<>();

                        for (Bludo bludo : bludoRepo.findAll()) {
                            int count = 0;
                            for (Order order : orders) {
                                if (order.getBludo().getId() == bludo.getId()) {
                                    count++;
                                }
                            }
                            counOfBludas.add(new TempClass(bludo, count));
                        }
                        counOfBludas = counOfBludas
                                .stream()
                                .filter(
                                        c -> c.count > 0
                                ).collect(Collectors.toList());

                        List<ItogForUser> itogForUsers = new ArrayList<>();
                        String text = "";
                        for (Users users : usersRepo.findAll()) {
                            ItogForUser itogForUser = new ItogForUser();
                            itogForUser.users = users;
                            itogForUser.sum = 0.0;
                            itogForUser.orders = new ArrayList<>();
                            for (Order order : orders) {
                                if (order.getUsers().getId() == users.getId()) {
                                    itogForUser.sum += order.getBludo().getPrice();
                                    itogForUser.orders.add(order);
                                }
                            }
                            itogForUsers.add(itogForUser);
                        }

                        itogForUsers = itogForUsers.stream()
                                .filter(
                                        c -> c.sum > 0
                                ).collect(Collectors.toList());

                        itogSumma(message, counOfBludas);
                        sumForOnePerson(message, itogForUsers, text);

                        return;
                }

            }
        }
    }

    private void sumForOnePerson(Message message, List<ItogForUser> itogForUsers, String text) throws TelegramApiException {
        for (ItogForUser itogForUser : itogForUsers){
            text += itogForUser.users.getFirstName() + itogForUser.users.getLastName() + " - " + itogForUser.sum * 0.9 + "\n";
        }
        execute(SendMessage.builder().chatId(message.getChatId().toString()).text(text).build());
    }

    private void itogSumma(Message message, List<TempClass> counOfBludas) throws TelegramApiException {
        Double sum = Double.valueOf(0);
        String text = "";
        for (TempClass tempClass : counOfBludas) {
            text += tempClass.bludo.getName() + " " + tempClass.bludo.getPrice() + "с  " + tempClass.count + "  =  " + tempClass.bludo.getPrice() * tempClass.count + "\n";
            sum += tempClass.bludo.getPrice();
        }
        text += "Всего: " + sum + "\n";
        text = text + "Итого с скидкой:  " + sum * 0.9;
        execute(SendMessage.builder().chatId(message.getChatId().toString()).text(text).build());
    }

    class TempClass {
        Bludo bludo;
        int count;

        public TempClass(Bludo bludo, int count) {
            this.bludo = bludo;
            this.count = count;
        }

        public Bludo getBludo() {
            return bludo;
        }

        public void setBludo(Bludo bludo) {
            this.bludo = bludo;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    class ItogForUser {
        Users users;
        List<Order> orders;
        int count;
        double sum;
    }
//    @SneakyThrows
//    public static void main(String[] args) {
//        Bot bot = new Bot();
//        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//        telegramBotsApi.registerBot(bot);
//    }
}

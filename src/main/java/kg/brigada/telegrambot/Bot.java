package kg.brigada.telegrambot;

import kg.brigada.telegrambot.configs.BotConfig;
import kg.brigada.telegrambot.entities.Bludo;
import kg.brigada.telegrambot.entities.Order;
import kg.brigada.telegrambot.entities.Users;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class Bot extends TelegramLongPollingBot {
    @Autowired
    private BludoRepo bludoRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private OrderRepo orderRepo;

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
        Long id = Long.parseLong(callbackQuery.getData());
        Bludo bludo = new Bludo();
               bludo= bludoRepo.getById(id);
        System.out.println(bludo.getName());
        execute(SendMessage.builder().chatId(message.getChatId().toString())
                .text(
                        callbackQuery.getFrom().getFirstName()
                        + " выбрал "
                        + bludo.getName()
                ).build());
        Users users = null;
        if (usersRepo.getUsersByUserId(callbackQuery.getFrom().getId().toString()) == null){
            users = usersRepo.save( Users.builder()
                    .firstName(callbackQuery.getFrom().getFirstName())
                    .lastName(callbackQuery.getFrom().getLastName())
                    .userName(callbackQuery.getFrom().getUserName())
                    .userId(callbackQuery.getFrom().getId().toString())
                    .build());
        }else {
            users = usersRepo.getUsersByUserId(callbackQuery.getFrom().getId().toString());
        }

        orderRepo.save(Order.builder()
                .bludo(bludo)
                .users(users)
                .localDateTime(LocalDateTime.now())
                .build());


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
                    case "/menu":
                        List<Bludo> bludos = new ArrayList<>();
                        bludos.addAll(bludoRepo.findAll());
                        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
                        for (Bludo bludo : bludos) {
                            buttons.add(Arrays.asList(
                                    InlineKeyboardButton.builder().text(bludo.getName() + " " + bludo.getPrice() + "сом").callbackData(bludo.getId().toString()).build()
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
                }

            }
        }
    }

//    @SneakyThrows
//    public static void main(String[] args) {
//        Bot bot = new Bot();
//        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//        telegramBotsApi.registerBot(bot);
//    }
}

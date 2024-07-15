package uz.uychiitschool.system.certificate;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;
import uz.uychiitschool.system.certificate.config.BotConfig;

@Component
public class CertificateBot extends SpringWebhookBot {
    private final BotConfig botConfig;

    public CertificateBot(BotConfig botConfig) {
        super(botConfig.setWebhookInstance(), botConfig.getBotToken());
        this.botConfig = botConfig;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }

    @Override
    public String getBotPath() {
        return this.botConfig.getBotPath();
    }

    @Override
    public String getBotUsername() {
        return this.botConfig.getUsername();
    }

    @SneakyThrows
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        execute(sendMessage);
    }
}

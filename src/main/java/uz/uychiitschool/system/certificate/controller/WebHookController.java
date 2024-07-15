package uz.uychiitschool.system.certificate.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.uychiitschool.system.certificate.CertificateBot;

@RestController
@RequestMapping("/api/v1/webhook")
@RequiredArgsConstructor
@Tag(name = "WebHook Controller")
public class WebHookController {
    private final CertificateBot bot;

    @PostMapping
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        if (update.hasMessage()){
            handleMessage(update);
        }else if(update.hasCallbackQuery()){
            handleCallbackQuery(update);
        }
        return null;
    }

    public void handleMessage(Update update) {
        Message message = update.getMessage();
        if ("/start".equals(message.getText())){
            bot.sendMessage(message.getChatId().toString(),"Salom " + message.getFrom().getFirstName());
        }
    }

    public void handleCallbackQuery(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();


    }
}

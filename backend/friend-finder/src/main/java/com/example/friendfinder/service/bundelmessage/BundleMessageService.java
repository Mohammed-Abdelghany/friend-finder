package com.example.friendfinder.service.bundelmessage;

import com.example.friendfinder.helper.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class BundleMessageService {
    private final MessageSource messageSource;
    @Autowired
    public BundleMessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    public MessageResponse getArMessage(String code) {
        return new MessageResponse(null,messageSource.getMessage(code, null, Locale.forLanguageTag("ar")));
    }

    public MessageResponse getEnMessage(String code) {
        return new MessageResponse(messageSource.getMessage(code, null, Locale.forLanguageTag("en")),null);
    }

}

/**
 * 
 */
package de.arpablo.hennibot.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.send.MessagePayload;
import com.github.messenger4j.send.NotificationType;
import com.github.messenger4j.send.message.RichMediaMessage;
import com.github.messenger4j.send.message.TemplateMessage;
import com.github.messenger4j.send.message.TextMessage;
import com.github.messenger4j.send.message.richmedia.RichMediaAsset.Type;
import com.github.messenger4j.send.message.richmedia.UrlRichMediaAsset;
import com.github.messenger4j.send.message.template.ButtonTemplate;
import com.github.messenger4j.send.message.template.button.Button;
import com.github.messenger4j.send.message.template.button.PostbackButton;
import com.github.messenger4j.userprofile.UserProfile;
import com.github.messenger4j.webhook.event.AttachmentMessageEvent;
import com.github.messenger4j.webhook.event.TextMessageEvent;

/**
 * @author arpablo
 *
 */
@Service
public class HenniService {

	
	private static final Logger log = LoggerFactory.getLogger(HenniService.class);

	@Autowired
	private Messenger messenger;

	public void processTextMessage(TextMessageEvent event) {
		String senderId = event.senderId();
		String text = event.text();
		try {
			UserProfile profile = messenger.queryUserProfile(senderId);
			if (text.equalsIgnoreCase("lecken")) {
				final TextMessage textMessage = TextMessage.create(String.format("Ok %s! Dann lecke ich Dich jetzt!!!", profile.firstName()));
		        final MessagePayload messagePayload = MessagePayload.create(senderId, textMessage);
				messenger.send(messagePayload);
			} else if (text.equalsIgnoreCase("ficken")) {
				final TextMessage textMessage = TextMessage.create(String.format("Hallo %s! Dann bohre ich Dich jetzt an!!!", profile.firstName()));
		        final MessagePayload messagePayload = MessagePayload.create(senderId, textMessage);
				messenger.send(messagePayload);
			} else if (text.equalsIgnoreCase("gucken")) {
				final NotificationType notificationType = NotificationType.NO_PUSH;
				Optional<NotificationType> oNf = Optional.of(notificationType);
				final String imageUrl = "https://henni-bot-app.herokuapp.com/henni_1.png";

				try {
					final UrlRichMediaAsset richMediaAsset = UrlRichMediaAsset.create(Type.IMAGE, new URL(imageUrl), Optional.of(true));
					final RichMediaMessage richMediaMessage = RichMediaMessage.create(richMediaAsset);
					final MessagePayload payload = MessagePayload.create(senderId, richMediaMessage);
					messenger.send(payload);
				} catch (MalformedURLException ex) {
					final TextMessage textMessage = TextMessage.create(String.format("Sorry %s! Ich finde gerade keine Mösenbilder...", profile.firstName()));
			        final MessagePayload messagePayload = MessagePayload.create(senderId, textMessage);
					messenger.send(messagePayload);
				}

			} else {
				final PostbackButton button1 = PostbackButton.create("Ficken", "ficken");
				final PostbackButton button2 = PostbackButton.create("Lecken", "lecken");
				final PostbackButton button3 = PostbackButton.create("Möse gucken", "gucken");
				

//				final TextMessage textMessage1 = TextMessage.create(String.format("https://henni-bot-app.herokuapp.com/henni_1.png", profile.firstName()));
//		        final MessagePayload messagePayload1 = MessagePayload.create(senderId, textMessage1);
//				messenger.send(messagePayload1);
//				final TextMessage textMessage = TextMessage.create(String.format("Hallo %s! Du willst ficken? JETZT??? Soll ich Dich lecken oder anbohren?", profile.firstName()));
//		        final MessagePayload messagePayload = MessagePayload.create(senderId, textMessage);

				final List<Button> buttons = Arrays.asList(button1, button2, button3);
				final ButtonTemplate buttonTemplate = ButtonTemplate.create(String.format("Hallo %s! Was möchtest Du jetzt machen?", profile.firstName()), buttons);

				final TemplateMessage templateMessage = TemplateMessage.create(buttonTemplate);
				final MessagePayload payload = MessagePayload.create(senderId, templateMessage);
				messenger.send(payload);
			}
		} catch (MessengerApiException | MessengerIOException e) {
			log.error(e.getMessage(), e);
		}
	}

	public void processAttachmentMessage(AttachmentMessageEvent event) {
	}

}

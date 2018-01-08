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
import com.github.messenger4j.common.WebviewHeightRatio;
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
import com.github.messenger4j.send.message.template.ListTemplate;
import com.github.messenger4j.send.message.template.ListTemplate.TopElementStyle;
import com.github.messenger4j.send.message.template.button.Button;
import com.github.messenger4j.send.message.template.button.PostbackButton;
import com.github.messenger4j.send.message.template.button.UrlButton;
import com.github.messenger4j.send.message.template.common.DefaultAction;
import com.github.messenger4j.send.message.template.common.Element;
import com.github.messenger4j.userprofile.UserProfile;
import com.github.messenger4j.webhook.event.AttachmentMessageEvent;
import com.github.messenger4j.webhook.event.PostbackEvent;
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
		try {
			UserProfile profile = messenger.queryUserProfile(senderId);
			final PostbackButton button1 = PostbackButton.create("Ficken", "ficken");
			final PostbackButton button2 = PostbackButton.create("Lecken", "lecken");
			final PostbackButton button3 = PostbackButton.create("Möse gucken", "gucken");
			

			final List<Button> buttons = Arrays.asList(button1, button2, button3);
			final ButtonTemplate buttonTemplate = ButtonTemplate.create(String.format("Hallo %s! Was möchtest Du jetzt machen?", profile.firstName()), buttons);

			final TemplateMessage templateMessage = TemplateMessage.create(buttonTemplate);
			final MessagePayload payload = MessagePayload.create(senderId, templateMessage);
			messenger.send(payload);
		} catch (MessengerApiException | MessengerIOException e) {
			log.error(e.getMessage(), e);
		}
	}

	public void processAttachmentMessage(AttachmentMessageEvent event) {
	}
	
	public void processPostbackMessage(PostbackEvent event) {
		String senderId = event.senderId();
		Optional<String> eventPayload = event.payload();
		try {
			if (eventPayload.isPresent()) {
				UserProfile profile = messenger.queryUserProfile(senderId);
				String text = eventPayload.get();
				if (text.equalsIgnoreCase("lecken")) {
					final TextMessage textMessage = TextMessage.create(String.format("Ok %s! Dann lecke ich Dich jetzt!!!", profile.firstName()));
			        final MessagePayload messagePayload = MessagePayload.create(senderId, textMessage);
					messenger.send(messagePayload);
				} else if (text.equalsIgnoreCase("ficken")) {
					final TextMessage textMessage = TextMessage.create(String.format("Hallo %s! Dann ficke ich Dich jetzt!!!", profile.firstName()));
			        final MessagePayload messagePayload = MessagePayload.create(senderId, textMessage);
					messenger.send(messagePayload);
				} else if (text.equalsIgnoreCase("gucken")) {
//					final NotificationType notificationType = NotificationType.NO_PUSH;
//					Optional<NotificationType> oNf = Optional.of(notificationType);
//					final String imageUrl = "https://henni-bot-app.herokuapp.com/img/henni_1.png";

					try {
						final Element element1 = Element.create("Das ist Henni!", Optional.of("Gefalle ich Dir?"),
						        Optional.of(new URL("https://henni-bot-app.herokuapp.com/img/henni_2.png")),
						        Optional.empty(), Optional.empty());
						final Element element2 = Element.create("So siehst Du es besser!", Optional.of("Was meinst Du wohl, was ich jetzt machen will?"),
						        Optional.of(new URL("https://henni-bot-app.herokuapp.com/img/henni_3.png")),
						        Optional.empty(), Optional.empty());
						final Element element3 = Element.create("Komm näher!", Optional.of("Stell Dich doch nicht so an! Ich will jetzt ficken!"),
						        Optional.of(new URL("https://henni-bot-app.herokuapp.com/img/henni_1.png")),
						        Optional.empty(), Optional.empty());
						final Element element4 = Element.create("Dann eben nicht!", Optional.of("Du hast es nicht anders gewollt!"),
						        Optional.of(new URL("https://henni-bot-app.herokuapp.com/img/henni_4.png")),
						        Optional.empty(), Optional.empty());



						final ListTemplate listTemplate = ListTemplate.create(Arrays.asList(element1, element2, element3, element4),
						        Optional.of(TopElementStyle.LARGE), Optional.empty());
						
						messenger.send(MessagePayload.create(senderId, TemplateMessage.create(listTemplate)));
//						final UrlRichMediaAsset richMediaAsset = UrlRichMediaAsset.create(Type.IMAGE, new URL(imageUrl), Optional.of(true));
//						final RichMediaMessage richMediaMessage = RichMediaMessage.create(richMediaAsset);
//						final MessagePayload payload = MessagePayload.create(senderId, richMediaMessage);
//						messenger.send(payload);
					} catch (MalformedURLException ex) {
						final TextMessage textMessage = TextMessage.create(String.format("Sorry %s! Ich finde gerade keine Mösenbilder...", profile.firstName()));
				        final MessagePayload messagePayload = MessagePayload.create(senderId, textMessage);
						messenger.send(messagePayload);
					}
				}
			}
		} catch (MessengerApiException | MessengerIOException e) {
			log.error(e.getMessage(), e);
		}
	}

}

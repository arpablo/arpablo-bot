/**
 * 
 */
package de.arpablo.hennibot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.send.MessagePayload;
import com.github.messenger4j.send.message.TextMessage;
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
		try {
			UserProfile profile = messenger.queryUserProfile(senderId);
			final TextMessage textMessage = TextMessage.create(String.format("Hallo %s!Ich will Dich ficken! JETZT!!!", profile.firstName()));
	        final MessagePayload messagePayload = MessagePayload.create(senderId, textMessage);
			messenger.send(messagePayload);
		} catch (MessengerApiException | MessengerIOException e) {
			log.error(e.getMessage(), e);
		}
	}

	public void processAttachmentMessage(AttachmentMessageEvent event) {
	}

}

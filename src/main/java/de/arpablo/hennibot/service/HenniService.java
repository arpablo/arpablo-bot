/**
 * 
 */
package de.arpablo.hennibot.service;

import org.springframework.stereotype.Service;

import com.github.messenger4j.webhook.event.AttachmentMessageEvent;
import com.github.messenger4j.webhook.event.TextMessageEvent;

/**
 * @author arpablo
 *
 */
@Service
public class HenniService {

		public void processTextMessage(TextMessageEvent event) {
		}
		
		public void processAttachmentMessage(AttachmentMessageEvent event) {
		}
	
}

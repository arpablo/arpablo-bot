/**
 * 
 */
package de.arpablo.hennibot.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerVerificationException;

import de.arpablo.hennibot.service.HenniService;

/**
 * @author arpablo
 *
 */
@RestController
@RequestMapping("/callback")
public class MessengerCallbackController {

	
	private static final Logger log = LoggerFactory.getLogger(MessengerCallbackController.class);

	@Autowired
	private Messenger messenger;
	@Autowired
	private HenniService service;
	
	@GetMapping
	public ResponseEntity<String> verify(String mode, String verifyToken) {
		log.debug("Received Webhook verification request - mode: {} | verifyToken: {}", mode, verifyToken);
		try {
			messenger.verifyWebhook(mode, verifyToken);
			return ResponseEntity.ok("ok");
		} catch (MessengerVerificationException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<Void> handleMessage(String requestPayload, Optional<String> signature) {
		try {
			messenger.onReceiveEvents(requestPayload, signature, event -> {
				if (event.isTextMessageEvent()) {
					service.processTextMessage(event.asTextMessageEvent());
				}
				else if (event.isAttachmentMessageEvent()) {
					event.asAttachmentMessageEvent();
				}
				else if (event.isAccountLinkingEvent()) {
					event.asAccountLinkingEvent();
				}
				else {
					log.info("Event not handled");;
				}
			});
			return ResponseEntity.ok().build();
		} catch (MessengerVerificationException ex) {
            log.warn("Processing of callback payload failed: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();			
		}
	}
}

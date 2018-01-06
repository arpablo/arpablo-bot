/**
 * 
 */
package de.arpablo.hennibot.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<String> verify(@RequestParam(name="hub.mode") String mode, @RequestParam(name="hub.verify_token") String verifyToken, @RequestParam(name="hub.challenge") String challenge) {
		log.info("Received Webhook verification request - mode: {} | verifyToken: {} | challenge: {}", mode, verifyToken, challenge);
		try {
			messenger.verifyWebhook(mode, verifyToken);
			return ResponseEntity.ok(challenge);
		} catch (MessengerVerificationException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<Void> handleMessage(@RequestBody String requestPayload, Optional<String> signature) {
		log.info("Received POST request with payload");
		log.info(requestPayload);
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

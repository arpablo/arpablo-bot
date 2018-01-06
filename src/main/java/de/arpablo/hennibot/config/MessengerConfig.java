/**
 * 
 */
package de.arpablo.hennibot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.messenger4j.Messenger;

/**
 * @author arpablo
 *
 */
@Configuration
public class MessengerConfig {

	
	private static final Logger log = LoggerFactory.getLogger(MessengerConfig.class);

	@Autowired
	private HenniProperties henniProperties;
	
	@Bean
	public Messenger getRequest() {
		log.info(henniProperties.toString());
		return Messenger.create(henniProperties.getPageAccessToken(), henniProperties.getAppSecret(), henniProperties.getVerifyToken());
	}
	
	
}

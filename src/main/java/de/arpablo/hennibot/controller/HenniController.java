/**
 * 
 */
package de.arpablo.hennibot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author arpablo
 *
 */
@Controller
public class HenniController {

	@GetMapping("/")
	public String index() {
		return "index";
	}

}

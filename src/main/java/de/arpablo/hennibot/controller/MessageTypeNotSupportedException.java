/**
 * 
 */
package de.arpablo.hennibot.controller;

/**
 * @author arpablo
 *
 */
public class MessageTypeNotSupportedException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public MessageTypeNotSupportedException() {
	}

	/**
	 * @param message
	 */
	public MessageTypeNotSupportedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public MessageTypeNotSupportedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MessageTypeNotSupportedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public MessageTypeNotSupportedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}

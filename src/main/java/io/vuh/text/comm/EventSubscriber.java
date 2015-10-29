package io.vuh.text.comm;

import java.util.function.Consumer;

/**
 * Interface to handle events on our event bus.
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
public interface EventSubscriber {
	
	/**
	 * Will execute a {@link Consumer} function when a new event is received.
	 * 
	 * @param callback to be executed when a new event is received
	 */
	void executeOnMessage(String topic, Consumer<String> callback);
	
}

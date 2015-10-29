package io.vuh.text.comm;

/**
 * Interface to publish an event to the event bus
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
public interface EventPublisher {
	
	/**
	 * Publishes an event.
	 * 
	 * @param topic where the event is going to be published
	 * @param event to be published
	 */
	void publishEvent(String topic, String event);
}

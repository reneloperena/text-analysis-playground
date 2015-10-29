package io.vuh.text.logging;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.fasterxml.jackson.databind.ObjectMapper;

import rx.Observable;

/**
 * @author Rene Loperena <rene@vuh.io>
 *
 */
public class Context { 
	Client client = new TransportClient().addTransportAddress(new InetSocketTransportAddress(System.getProperty("ELASTICSEARCH_IP"), 9300));
	
	/**
	 * @param injectionPoint
	 * @return
	 */
	@Produces
    public Logger createLogger (final InjectionPoint injectionPoint){
        return Logger.getLogger (injectionPoint.getMember ().getDeclaringClass ().getName ());
    }
    
    /**
     * @param injectionPoint
     * @return
     */
    @Produces
    public ObjectMapper createObjectMapper (final InjectionPoint injectionPoint){
        return new ObjectMapper();
    }
    
    /**
     * @param injectionPoint
     * @return
     */
    @Produces
    public Client createClient(final InjectionPoint injectionPoint){
    	return client;
    }
          
}

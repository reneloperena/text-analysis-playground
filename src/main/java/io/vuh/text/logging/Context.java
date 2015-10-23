package io.vuh.text.logging;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Context { 
	Client client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("192.168.1.23", 9300));
	
	@Produces
    public Logger createLogger (final InjectionPoint injectionPoint){
        return Logger.getLogger (injectionPoint.getMember ().getDeclaringClass ().getName ());
    }
    
    @Produces
    public ObjectMapper createObjectMapper (final InjectionPoint injectionPoint){
        return new ObjectMapper();
    }
    
    @Produces
    public Client createClient(final InjectionPoint injectionPoint){
    	return client;
    }
          
}

package strategies.publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import events.AbstractEvent;
import events.EventFactory;
import events.EventMessage;
import events.EventType;
import pubSubServer.ChannelEventDispatcher;
import publishers.AbstractPublisher;


public class AStrategy implements IStrategy{

	public void doPublish(int publisherId) {
		//String[] array = {"testing", "out"};
		
		List<String> listOfChannels = new ArrayList<>();
		
		// adding arbitrary channels to the channelList to post
		listOfChannels.add("default");
		listOfChannels.add("test");
		
		ChannelEventDispatcher channelDispatch = ChannelEventDispatcher.getInstance();
		
		// random message
		EventMessage message = new EventMessage("default", "message");
		
		AbstractEvent event = EventFactory.createEvent(EventType.values()[0], publisherId, message);
		
		channelDispatch.postEvent(event, listOfChannels);
		
		System.out.println("Publisher " + publisherId + " publishes event " + event);
	}
	
	public void doPublish(AbstractEvent event, int publisherId) {
				
		List<String> listOfChannels = new ArrayList<>();
				
		// adding arbitrary channels to the channelList to post
		listOfChannels.add("default");
		listOfChannels.add("test");
				
		ChannelEventDispatcher channelDispatch = ChannelEventDispatcher.getInstance();
		channelDispatch.postEvent(event, listOfChannels);
		
		System.out.println("Publisher " + publisherId + " publishes event " + event);
		
	}
}

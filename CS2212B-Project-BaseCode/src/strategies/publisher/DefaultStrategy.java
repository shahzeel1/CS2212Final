package strategies.publisher;

import java.util.ArrayList;
import java.util.List;

import events.AbstractEvent;
import events.EventFactory;
import events.EventMessage;
import events.EventType;
import pubSubServer.ChannelEventDispatcher;
import publishers.AbstractPublisher;


public class DefaultStrategy implements IStrategy{

	public void doPublish(int publisherId) {
		List<String> listOfChannels = new ArrayList<>();
		
		// adding arbitrary channels to the channelList to post
		listOfChannels.add("DefaultStrategy NE1");
		
		ChannelEventDispatcher channelDispatch = ChannelEventDispatcher.getInstance();
		
		// random message
		EventMessage message = new EventMessage("default strategy", "message");
		
		AbstractEvent event = EventFactory.createEvent(EventType.values()[0], publisherId, message);
		
		System.out.println("Publisher " + publisherId + " publishes event " + event.getEventID());

		channelDispatch.postEvent(event, listOfChannels);
		
	}
	
	public void doPublish(AbstractEvent event, int publisherId) {
		
		List<String> listOfChannels = new ArrayList<>();
		
		// adding arbitrary channels to the channelList to post
		listOfChannels.add("DefaultStrategy E1");
		listOfChannels.add("DefaultStrategy E2");
		
		System.out.println("Publisher " + publisherId + " publishes event " + event.getEventID());
				
		ChannelEventDispatcher channelDispatch = ChannelEventDispatcher.getInstance();
		channelDispatch.postEvent(event, listOfChannels);
				
	}
}

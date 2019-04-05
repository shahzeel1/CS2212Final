package strategies.publisher;
import events.AbstractEvent;
import events.EventFactory;
public class AbstractStrategy {

	private int publisherId;
	private AbstractEvent event;
	
	public AbstractStrategy(int publisherId)
	{
	this.publisherId=publisherId;
	//create event
	this.event = EventFactory.createEvent(null, publisherId, null);
	}
	
	public AbstractStrategy (int publisherId, AbstractEvent event)
	{
		this.publisherId=publisherId;
		this.event=event;
	}
	
	public void doPublish(int publisherId) {};
	
	public void doPublish(AbstractEvent event, int publisherId) {};
	
}

	
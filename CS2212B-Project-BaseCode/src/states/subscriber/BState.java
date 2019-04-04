package states.subscriber;
import states.subscriber.AbstractState;
import events.AbstractEvent;

public class BState extends AbstractState{
	
	protected BState(AbstractEvent event, String channelName)
	{
		super(event,channelName);
	}

}

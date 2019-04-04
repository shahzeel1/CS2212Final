package states.subscriber;
import states.subscriber.AbstractState;
import events.AbstractEvent;

public class AState extends AbstractState{
	
	protected AState(AbstractEvent event, String channelName)
	{
		super(event,channelName);
	}

}



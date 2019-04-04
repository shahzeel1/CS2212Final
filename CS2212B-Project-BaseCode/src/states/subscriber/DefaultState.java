package states.subscriber;
import states.subscriber.AbstractState;
import events.AbstractEvent;

public class DefaultState extends AbstractState{

	protected DefaultState(AbstractEvent event, String channelName)
	{
		super(event,channelName);
	}

}

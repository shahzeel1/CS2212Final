package states.subscriber;
import events.AbstractEvent;

public class AbstractState {
	private AbstractEvent event;
	private String channelName;

	public AbstractState(AbstractEvent event, String channelName)
	{
		this.event= event;
		this.channelName=channelName;
	}
	protected AbstractEvent getEvent()
	{
		return event;

	}
	protected String getchannelName()
	{
		return channelName;
	}

}


package strategies.publisher;
import events.AbstractEvent;

public class AStrategy extends AbstractStrategy{
	protected AStrategy (int publisherId)
	{
		super(publisherId);
	}
	
	protected AStrategy(int publisherId, AbstractEvent event)
	{
		super(publisherId, event);
	}

}

package strategies.publisher;

import events.AbstractEvent;

public class BStrategy extends AbstractStrategy {
	protected BStrategy (int publisherId)
	{
		super(publisherId);
	}
	
	protected BStrategy(int publisherId, AbstractEvent event)
	{
		super(publisherId, event);
	}
	
	
}


 package com.codemonkey.push;

import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.EventPullSource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;


@Service
public class SimplePushlet {
	
	
	public static class TaskEventPullSource extends EventPullSource {
		@Override
		protected Event pullEvent(){
			Event event = Event.createDataEvent("/event");
			JSONObject jo = new JSONObject();
			event.setField("aa",jo.toString());
			return event;
		}

		@Override
		protected long getSleepTime() {
			return 10 * 1000;
		}
	}
	
}

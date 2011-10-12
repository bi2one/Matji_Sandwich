package com.matji.sandwich.session;

import android.content.Context;
import android.util.Log;

import com.google.android.maps.GeoPoint;

import com.matji.sandwich.data.LocationSearchToken;
import com.matji.sandwich.data.provider.PreferenceProvider;

import java.util.LinkedList;
import java.util.ArrayList;
import java.io.NotSerializableException;
import java.lang.ref.WeakReference;

public class SessionRecentLocationUtil {
    private static final int CAPACITY = 7;
    private Session session;
    private PreferenceProvider preferenceProvider;
    private WeakReference<Context> contextRef;
    
    public SessionRecentLocationUtil(Context context) {
	contextRef = new WeakReference(context);
	session = Session.getInstance(context);
	preferenceProvider = session.getPreferenceProvider();
    }

    public ArrayList<LocationSearchToken> getRecentList() {
	return new ArrayList<LocationSearchToken>(getRecentQueue());
    }

    public ArrayList<LocationSearchToken> push(String seed, int latitude, int longitude) {
	return push(new LocationSearchToken(seed, latitude, longitude));
    }

    public ArrayList<LocationSearchToken> push(LocationSearchToken searchToken) {
	LinkedList<LocationSearchToken> queue = getRecentQueue();
	
	for (LocationSearchToken token : queue) {
	    if (token.equals(searchToken)) {
		queue.remove(token);
		queue.addFirst(searchToken);
		return new ArrayList<LocationSearchToken>(queue);
	    }
	}
	queue.addFirst(searchToken);

	if (isOverCapacity(queue)) {
	    queue.removeLast();
	}

	saveRecentQueue(queue);
	return new ArrayList<LocationSearchToken>(queue);
    }

    private LinkedList<LocationSearchToken> getRecentQueue() {
	LinkedList<LocationSearchToken> queue = (LinkedList<LocationSearchToken>)preferenceProvider.getObject(SessionIndex.RECENT_CHANGED_LOCATION);
	
	if (queue == null) {
	    return new LinkedList<LocationSearchToken>();
	} else {
	    return queue;
	}
    }

    private void saveRecentQueue(LinkedList<LocationSearchToken> queue) {
	try {
	    preferenceProvider.setObject(SessionIndex.RECENT_CHANGED_LOCATION, queue);
	    preferenceProvider.commit(contextRef.get());
	} catch(NotSerializableException e) {
	    Log.d("Matji", "SessionRecentLocationUtil: not serializable exception");
	    e.printStackTrace();
	}
    }

    private boolean isOverCapacity(LinkedList<LocationSearchToken> recentQueue) {
	return recentQueue.size() > CAPACITY;
    }
}
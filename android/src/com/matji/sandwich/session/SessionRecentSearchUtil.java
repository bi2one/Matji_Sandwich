package com.matji.sandwich.session;

import java.io.NotSerializableException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;

import com.matji.sandwich.data.provider.PreferenceProvider;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.SessionIndex;
import com.matji.sandwich.data.SearchToken;

import android.content.Context;
import android.util.Log;

public class SessionRecentSearchUtil {
	private static final int CAPACITY = 5;
	private Session session;
	private PreferenceProvider preferenceProvider;
	private WeakReference<Context> contextRef;
	
	public SessionRecentSearchUtil(Context context) {
		contextRef = new WeakReference<Context>(context);
		session = Session.getInstance(context);
		preferenceProvider = session.getPreferenceProvider();
	}
	
	public ArrayList<SearchToken> getRecentList() {
		return new ArrayList<SearchToken>(getRecentQueue());
	}
	
	public ArrayList<SearchToken> push(String seed) {
		return push(new SearchToken(seed));
	}

	public ArrayList<SearchToken> push(SearchToken searchToken) {
		LinkedList<SearchToken> queue = getRecentQueue();
		
		for (SearchToken token : queue) {
			if (token.equals(searchToken)) {
				queue.remove(token);
				queue.addFirst(searchToken);
				return new ArrayList<SearchToken>(queue);
			}
		}
		queue.addFirst(searchToken);
		
		if (isOverCapacity(queue))
			queue.removeLast();
		
		saveRecentQueue(queue);
		return new ArrayList<SearchToken>(queue);
	}
	
	private void saveRecentQueue(LinkedList<SearchToken> queue) {
		try {
			preferenceProvider.setObject(SessionIndex.RECENT_STORE_SEARCH, queue);
			preferenceProvider.commit(contextRef.get());
		} catch (NotSerializableException e) {
			Log.d("Matji", "SessionRecentLocationUtil: not serializable exception");
			e.printStackTrace();
		}
	}

	private LinkedList<SearchToken> getRecentQueue() {
		@SuppressWarnings("unchecked")
		LinkedList<SearchToken> queue = (LinkedList<SearchToken>) preferenceProvider.getObject(SessionIndex.RECENT_STORE_SEARCH);
		
		if (queue == null) {
			return new LinkedList<SearchToken>();
		} else {
			return queue;
		}
	}

	private boolean isOverCapacity(LinkedList<SearchToken> recentQueue) {
		return recentQueue.size() > CAPACITY;
	}
	
}

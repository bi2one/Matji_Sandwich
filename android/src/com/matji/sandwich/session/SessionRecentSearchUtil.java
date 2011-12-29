package com.matji.sandwich.session;

import java.io.NotSerializableException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;

import com.matji.sandwich.data.provider.PreferenceProvider;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.SessionIndex;
import com.matji.sandwich.data.RecentSearchToken;

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
	
	public ArrayList<RecentSearchToken> getRecentList() {
		return new ArrayList<RecentSearchToken>(getRecentQueue());
	}
	
	public ArrayList<RecentSearchToken> push(String seed) {
		return push(new RecentSearchToken(seed));
	}

	public ArrayList<RecentSearchToken> push(RecentSearchToken searchToken) {
		LinkedList<RecentSearchToken> queue = getRecentQueue();
		
		for (RecentSearchToken token : queue) {
			if (token.equals(searchToken)) {
				queue.remove(token);
				queue.addFirst(searchToken);
				return new ArrayList<RecentSearchToken>(queue);
			}
		}
		queue.addFirst(searchToken);
		
		if (isOverCapacity(queue))
			queue.removeLast();
		
		saveRecentQueue(queue);
		return new ArrayList<RecentSearchToken>(queue);
	}
	
	private void saveRecentQueue(LinkedList<RecentSearchToken> queue) {
		try {
			preferenceProvider.setObject(SessionIndex.RECENT_STORE_SEARCH, queue);
			preferenceProvider.commit(contextRef.get());
		} catch (NotSerializableException e) {
			Log.d("Matji", "SessionRecentLocationUtil: not serializable exception");
			e.printStackTrace();
		}
	}

	private LinkedList<RecentSearchToken> getRecentQueue() {
		@SuppressWarnings("unchecked")
		LinkedList<RecentSearchToken> queue = (LinkedList<RecentSearchToken>) preferenceProvider.getObject(SessionIndex.RECENT_STORE_SEARCH);
		
		if (queue == null) {
			return new LinkedList<RecentSearchToken>();
		} else {
			return queue;
		}
	}

	private boolean isOverCapacity(LinkedList<RecentSearchToken> recentQueue) {
		return recentQueue.size() > CAPACITY;
	}
	
}

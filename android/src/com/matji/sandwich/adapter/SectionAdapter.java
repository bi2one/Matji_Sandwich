package com.matji.sandwich.adapter;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.util.TimeUtil;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * Adapter for sections.
 * 아직 PostForSeparator 클래스를 사용하는 것에 대해 고려해 봐야 할 듯.
 * 개선 할 수 있다면 개선.
 * 
 * @author mozziluv
 *
 */
public class SectionAdapter extends MBaseAdapter implements OnItemClickListener {
    private final DataSetObserver dataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            updateSessionCache();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            updateSessionCache();
        };
    };

    private final Map<Integer, String> sectionPositions = new LinkedHashMap<Integer, String>();
    private final Map<Integer, Integer> itemPositions = new LinkedHashMap<Integer, Integer>();
    private final Map<View, String> currentViewSections = new HashMap<View, String>();

    private OnItemClickListener linkedListener;
    
    private static final int TYPE_POST = 0;
    private static final int TYPE_SECTION = 1;
    private static final int VIEW_TYPE_COUNT = TYPE_SECTION + 1;

    public SectionAdapter(Context context) {
    	super(context);
        registerDataSetObserver(dataSetObserver);
//        updateSessionCache();
    }

    private boolean isTheSame(final String previousSection, final String newSection) {
        if (previousSection == null) {
            return newSection == null;
        } else {
            return previousSection.equals(newSection);
        }
    }

    private synchronized void updateSessionCache() {
        int currentPosition = 0;
        sectionPositions.clear();
        itemPositions.clear();
        String currentSection = null;
        final int count = data.size();
        for (int i = 0; i < count; i++) {
            final Post post = (Post) data.get(i);
            Date date = TimeUtil.getDateFromCreatedAt(post.getCreatedAt());
            String time = TimeUtil.parseString(date);
            if (!isTheSame(currentSection, time)) {
                sectionPositions.put(currentPosition, time);
                currentSection = time;
                currentPosition++;
            }
            itemPositions.put(currentPosition, i);
            currentPosition++;
        }
    }

    @Override
    public synchronized int getCount() {
        return sectionPositions.size() + itemPositions.size();
    }

    @Override
    public synchronized Object getItem(final int position) {
        if (isSection(position)) {
            return sectionPositions.get(position);
        } else {
            final int linkedItemPosition = getLinkedPosition(position);
            return data.get(linkedItemPosition);
        }
    }

    public synchronized boolean isSection(final int position) {
        return sectionPositions.containsKey(position);
    }

    public synchronized String getSectionName(final int position) {
        if (isSection(position)) {
            return sectionPositions.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(final int position) {
        if (isSection(position)) {
            return sectionPositions.get(position).hashCode();
        } else {
            return super.getItemId(getLinkedPosition(position));
        }
    }

    protected Integer getLinkedPosition(final int position) {
        return itemPositions.get(position);
    }

    @Override
    public int getItemViewType(final int position) {
        if (isSection(position)) {
            return VIEW_TYPE_COUNT - 1;
        }
        return super.getItemViewType(getLinkedPosition(position));
    }

    private View getSectionView(final View convertView, final String section) {
        View theView = convertView;
        if (theView == null) {
            theView = createNewSectionView();
        }
        setSectionText(section, theView);
        replaceSectionViewsInMaps(section, theView);
        return theView;
    }

    protected void setSectionText(final String section, final View sectionView) {
        final TextView textView = (TextView) sectionView.findViewById(R.id.date_section);
        if (TimeUtil.isToday(section)) {
        	textView.setText(context.getResources().getString(R.string.today));
        	textView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.section_today));
        } else {
        	textView.setText(section);
        }
    }

    protected synchronized void replaceSectionViewsInMaps(final String section, final View theView) {
        if (currentViewSections.containsKey(theView)) {
            currentViewSections.remove(theView);
        }
        currentViewSections.put(theView, section);
    }

    protected View createNewSectionView() {
        return inflater.inflate(R.layout.date_section, null);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        if (isSection(position)) {
            return getSectionView(convertView, sectionPositions.get(position));
        }
        return getViews(getLinkedPosition(position), convertView, parent);
    }

    public View getViews(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            final LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.example_list_view, null);
        }
        final Post currentPost = (Post) data.get(position);
        if (currentPost != null) {
            final TextView textView = (TextView) view.findViewById(R.id.example_text_view);
            if (textView != null) {
                textView.setText(currentPost.getPost());
            }
        }
        return view;
    }
    
    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public boolean isEnabled(final int position) {
        if (isSection(position)) {
            return true;
        }
        return super.isEnabled(getLinkedPosition(position));
    }
    
    protected void sectionClicked(final String section) {
        // do nothing
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        if (isSection(position)) {
            sectionClicked(getSectionName(position));
        } else if (linkedListener != null) {
            linkedListener.onItemClick(parent, view,
                    getLinkedPosition(position), id);
        }
    }

    public void setOnItemClickListener(final OnItemClickListener linkedListener) {
        this.linkedListener = linkedListener;
    }
}
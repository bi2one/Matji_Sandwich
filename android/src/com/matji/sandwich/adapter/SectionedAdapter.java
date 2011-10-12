package com.matji.sandwich.adapter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Adapter for sections.
 * 
 * @author ...
 * 
 * @modifier bizone
 *
 */
public abstract class SectionedAdapter extends MBaseAdapter {
    public abstract View getSectionView(View convertView, String section);
    public abstract View getItemView(int position, View convertView, ViewGroup parent);
    public abstract String putSectionName(int position);

    private final Map<Integer, String> sectionPositions = new LinkedHashMap<Integer, String>();
    private final Map<Integer, Integer> itemPositions = new LinkedHashMap<Integer, Integer>();
    private final Map<View, String> currentViewSections = new HashMap<View, String>();

    private static final int TYPE_SECTION = 1;
    private static final int VIEW_TYPE_COUNT = TYPE_SECTION + 1;

    public SectionedAdapter(Context context) {
        super(context);
    }

    public void notifyDataSetChanged() {
        int currentPosition = 0;
        String prevSectionName = "";
        sectionPositions.clear();
        itemPositions.clear();

        for (int i = 0; i < data.size(); i++) {
            String sectionName = putSectionName(i);
            if (sectionName != null && !sectionName.equals(prevSectionName)) {
                prevSectionName = sectionName;
                sectionPositions.put(currentPosition, sectionName);
                currentPosition++;
            }
            itemPositions.put(currentPosition, i);
            currentPosition++;
        }
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return sectionPositions.size() + itemPositions.size();
    }

    @Override
    public Object getItem(final int position) {
        if (isSection(position)) {
            return sectionPositions.get(position);
        } else {
            final int linkedItemPosition = getLinkedPosition(position);
            return data.get(linkedItemPosition);
        }
    }

    public boolean isSection(final int position) {
        return sectionPositions.containsKey(position);
    }

    public String getSectionName(final int position) {
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


    @Override
    public int getItemViewType(final int position) {
        if (isSection(position)) {
            return VIEW_TYPE_COUNT - 1;
        }
        return super.getItemViewType(getLinkedPosition(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (isSection(position)) {
            View sectionView = getSectionView(convertView, sectionPositions.get(position));
            replaceSectionViewsInMaps(sectionPositions.get(position), sectionView);
            return sectionView;
        }
        return getItemView(getLinkedPosition(position), convertView, parent);
    }

    protected void replaceSectionViewsInMaps(final String section, final View theView) {
        if (currentViewSections.containsKey(theView)) {
            currentViewSections.remove(theView);
        }
        currentViewSections.put(theView, section);
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public boolean isEnabled(final int position) {
        if (isSection(position)) {
            return false;
        }
        return super.isEnabled(getLinkedPosition(position));
    }

    @Override
    public void clear() {
        super.clear();
        sectionPositions.clear();
        itemPositions.clear();
        currentViewSections.clear();
    }

    protected Integer getLinkedPosition(final int position) {
        return itemPositions.get(position);
    }
}
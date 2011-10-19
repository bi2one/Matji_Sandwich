package com.matji.sandwich.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.UserHttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;

public class CountryAdapter extends BaseAdapter implements Requestable {

    private Session session;
    private HttpRequest request;
    private HttpRequestManager manager;

    private Context context;
    private String[] names = MatjiConstants.stringArray(R.array.country_names);
    private String[] codes = MatjiConstants.stringArray(R.array.country_codes);

    public CountryAdapter(Context context) {
        this.context = context;
        manager = HttpRequestManager.getInstance();
        session = Session.getInstance(context);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final CountryElement countryElement;
        if (convertView == null) {
            countryElement = new CountryElement();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row_country, null);

            countryElement.country= (TextView) convertView.findViewById(R.id.row_country_name);
            countryElement.radio = (RadioButton) convertView.findViewById(R.id.row_country_radio);

            convertView.setTag(countryElement);
        } else {
            countryElement = (CountryElement) convertView.getTag();
        }

        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!manager.isRunning()) {
                    manager.request(
                            context,
                            updateCountryRequest(codes[position]),
                            HttpRequestManager.USER_UPDATE_REQUEST,
                            CountryAdapter.this);
                }
                session.getCurrentUser().setCountryCode(codes[position]);
                notifyDataSetChanged();
            }
        });
        
        countryElement.country.setText(codes[position]+", "+names[position]);
        if (session.getCurrentUser().getCountryCode().equals(codes[position])) {
            countryElement.radio.setChecked(true);
        } else {
            countryElement.radio.setChecked(false);
        }

        return convertView;
    }

    private class CountryElement {
        TextView country;
        RadioButton radio;
    }

    public HttpRequest updateCountryRequest(String country_code) {
        if (request == null || !(request instanceof UserHttpRequest)) {
            request = new UserHttpRequest(context);
        }

        ((UserHttpRequest) request).actionUpdateCountry(country_code);

        return request;
    }

    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch (tag) {
        case HttpRequestManager.USER_UPDATE_REQUEST:
            User user = (User) data.get(0);
            if (user != null);
            Session.getInstance(context).getCurrentUser().setCountryCode(user.getCountryCode());
            break;
        }

        notifyDataSetChanged();
    }

    @Override
    public void requestExceptionCallBack(int tag, MatjiException e) {
        e.performExceptionHandling(context);
    }

    @Override
    public int getCount() {
        return codes.length;
    }

    @Override
    public Object getItem(int position) {
        return codes[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
}
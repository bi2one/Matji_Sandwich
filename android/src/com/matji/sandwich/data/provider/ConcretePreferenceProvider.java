package com.matji.sandwich.data.provider;

import android.content.Context;

public class ConcretePreferenceProvider extends PreferenceProvider {
    public ConcretePreferenceProvider(Context context) {
        super(context);
    }

    @Override
    protected String getFileName() {
        return "app_concerete_object_preference";
    }
}



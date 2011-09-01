package com.matji.sandwich;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.ViewGroup;

import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;

public class LoginAsyncTask extends AsyncTask<Object, Integer, Boolean> {

    private ProgressDialog dialog;

    private Context context;
    private Loginable loginable;
    private ViewGroup spinnerContainer;
    private String userid;
    private String password;
    private boolean hasLogin;

    public LoginAsyncTask(Context context, Loginable loginable, ViewGroup spinnerContainer, String userid, String password) {
        this.context = context;
        this.loginable = loginable;
        this.spinnerContainer = spinnerContainer;
        this.userid = userid;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();

        dialog = new ProgressDialog(context);
        dialog.setMessage(MatjiConstants.string(R.string.login_loading));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected Boolean doInBackground(Object... arg0) {
        // TODO Auto-generated method stub

        Session session = Session.getInstance(context);
        hasLogin = session.login(spinnerContainer, userid, password);

        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        dialog.dismiss();
        
        if (hasLogin) {
            loginable.loginCompleted();
        } else {
            loginable.loginFailed();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }
}

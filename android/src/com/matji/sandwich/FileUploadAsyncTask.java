package com.matji.sandwich;

import java.io.File;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;

public class FileUploadAsyncTask extends AsyncTask<File, Integer, Boolean> {

    private ProgressDialog dialog;

    private Context context;
    private FileUploader uploader;

    public FileUploadAsyncTask(Context context, FileUploader uploader) {
        this.context = context;
        this.uploader = uploader;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Session.getInstance(context).preLogin();

        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMessage(MatjiConstants.string(R.string.progress_upload));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected Boolean doInBackground(File... files) {
        for (File file : files) {
            uploader.upload(file);
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        // TODO 파일 업로드 실패할 경우....
        super.onPostExecute(result);
        dialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }

    public interface FileUploader {
        public void upload(File file);
    }
}
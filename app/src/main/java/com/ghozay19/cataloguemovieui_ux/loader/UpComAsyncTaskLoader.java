package com.ghozay19.cataloguemovieui_ux.loader;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.ghozay19.cataloguemovieui_ux.Movieitem;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.ghozay19.cataloguemovieui_ux.BuildConfig.MOVIE_API_KEY;

public class UpComAsyncTaskLoader extends AsyncTaskLoader<ArrayList<Movieitem>> {

    private ArrayList<Movieitem> mData;
    private boolean mHasResult = false;


    public UpComAsyncTaskLoader(final Context context, ArrayList<Movieitem> mData) {
        super(context);
        onForceLoad();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<Movieitem> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResource(mData);
            mData = null;
            mHasResult = false;
        }
    }


    private void onReleaseResource(ArrayList<Movieitem> mData) {
    }


    @Override
    public ArrayList<Movieitem> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<Movieitem> movie_items = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + MOVIE_API_KEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {

                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject film = list.getJSONObject(i);
                        Movieitem movieItems = new Movieitem(film);
                        movie_items.add(movieItems);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movie_items;
    }
}


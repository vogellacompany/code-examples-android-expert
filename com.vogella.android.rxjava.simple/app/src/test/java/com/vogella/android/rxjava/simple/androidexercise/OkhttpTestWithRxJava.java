package com.vogella.android.rxjava.simple.androidexercise;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import io.reactivex.Observable;


public class OkhttpTestWithRxJava {
    Request request;
    OkHttpClient client;

    @Before
    public void setup() {
        client = new OkHttpClient();

        request = new Request.Builder()
                .url("http://www.vogella.com/index.html")
                .build();
    }

    // Simple subscription to a fix value
    @Test
    @Ignore
    public void useOkHttp() {
        // TODO 1 create observable with  Observable.fromCallable
        // handle the error case as well as the success case

        // TODO 3
        // Afterwards subscribe to your observable  with a new DisposableObserver on
        // implement onNext, onError and onComplete

        // TODO 3
        // Use observable.blockingFirst() to get the data
    }

    /**
     * Demonstrates cancellation handling
     */
    @Test
    public void useOkHttpAsync() {
        Observable<String> observer = Observable.create(emitter ->
        {
            emitter.setCancellable(() -> {
                // for example update the UI remove listener, etlc
            });
            client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        emitter.onError(e);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        emitter.onNext(response.body().string());
                        emitter.onComplete();
                    }
                }); });
        // TODO subscribe to your observable
    }
}

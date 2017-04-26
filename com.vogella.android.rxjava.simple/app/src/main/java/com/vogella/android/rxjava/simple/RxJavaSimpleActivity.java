package com.vogella.android.rxjava.simple;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;


public class RxJavaSimpleActivity extends AppCompatActivity {

    RecyclerView colorListView;
    SimpleStringAdapter simpleStringAdapter;
    CompositeDisposable disposable = new CompositeDisposable();
    public int value =0;

    final Observable<Integer> serverDownloadObservable = Observable.create(emitter -> {
        SystemClock.sleep(10000); // simulate delay
        emitter.onNext(5);
        emitter.onComplete();
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjavasimple);
        View view = findViewById(R.id.button);
        // TODO build observable
    }

    private void updateTheUserInterface(int integer) {
        TextView view = (TextView) findViewById(R.id.resultView);
        view.setText(String.valueOf(integer));
    }

    @Override
    protected void onStop() {
        super.onStop();
        // TODO dispose subscription
    }

    public void onClick(View view) {
        Toast.makeText(this, "Still active " + value++, Toast.LENGTH_SHORT).show();
    }
}

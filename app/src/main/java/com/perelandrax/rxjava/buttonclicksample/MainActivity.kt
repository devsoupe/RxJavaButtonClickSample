package com.perelandrax.rxjava.buttonclicksample

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

class MainActivity : AppCompatActivity() {

    companion object {
        private var TAG = MainActivity::class.java.simpleName
    }

    private val disposables = CompositeDisposable()
    private lateinit var helloButtonClickStream: Observable<Unit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        helloButtonClickStream = createHelloButtonClickStream()
        helloButtonClickStream
            .subscribe { Log.d(TAG, "helloButtonClicked") }
            .addTo(disposables)
    }

    private fun createHelloButtonClickStream() = Observable.create<Unit> { emitter ->
        findViewById<TextView>(R.id.bt_hello).setOnClickListener {
            if (emitter.isDisposed) return@setOnClickListener
            emitter.onNext(Unit)
        }
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }
}
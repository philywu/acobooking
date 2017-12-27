package com.acob.blc.acobooking.ui

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.acob.blc.acobooking.R
import com.acob.blc.acobooking.presenter.MainPresenter
import com.acob.blc.acobooking.presenter.MainViewEvent
import dagger.android.AndroidInjection


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(),MainViewEvent {
    @Inject lateinit var presenter: MainPresenter
    var evtBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        presenter.onCreate(this)

        //evtBtn = findViewById(R.id.btn_event_show) as Button
       // evtBtn = btn_event_show
        btn_event_show.setOnClickListener({
            val intent = Intent(this, EventsActivity::class.java)
            startActivity(intent);
        })
        btn_event_new.setOnClickListener({
            val intent = Intent(this, EventDetailActivity::class.java)
            startActivity(intent);
        })

        /*
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent);
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onStart() {
        super.onStart()
        presenter.onConnect("From Main Start")

    }
    override fun onDestroy() {
        presenter.onDestroy();
        super.onDestroy()

    }

    override fun notifMainView(value :String) {
        Toast.makeText(this, value, Toast.LENGTH_LONG).show()
        //presenter.mqttSubscribe("phily/test",Integer(1))
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

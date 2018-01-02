package com.acob.blc.acobooking.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.acob.blc.acobooking.R

import com.acob.blc.acobooking.data.model.OBEventWithRegister
import com.acob.blc.acobooking.presenter.EventRegisterPresenter
import com.acob.blc.acobooking.presenter.EventRegisterViewEvent
import dagger.android.AndroidInjection

import kotlinx.android.synthetic.main.activity_events.*
import kotlinx.android.synthetic.main.content_event_register.*
import kotlinx.android.synthetic.main.content_events.*
import javax.inject.Inject

class EventRegisterActivity : AppCompatActivity(), EventRegisterViewEvent {

    val TAG = "event register"
    @Inject lateinit var presenter: EventRegisterPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_register)
        setSupportActionBar(toolbar)
        presenter.onCreate(this)


        event_register_list.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
       // event_list.hasFixedSize()
       // event_list.setHasFixedSize(true);
       // event_list.adapter = EventListAdapter(this, ArrayList<OBEvent>())
       // event_list.adapter = EventListAdapter(this, getLists())

    /*
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        */
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
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun showEvents(events: List<OBEventWithRegister>) {

        event_register_list.hasFixedSize()
        event_register_list.adapter = EventRegisterListAdapter(this,events)
       // nHandler.createSimpleNotification(this.applicationContext)
    }



}

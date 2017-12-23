package com.acob.blc.acobooking.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListAdapter
import com.acob.blc.acobooking.R
import com.acob.blc.acobooking.data.model.OBEvent
import com.acob.blc.acobooking.presenter.EventsPresenter
import com.acob.blc.acobooking.presenter.EventsViewEvent
import com.acob.blc.acobooking.ui.EventListAdapter.OnItemClicked
import dagger.android.AndroidInjection

import kotlinx.android.synthetic.main.activity_events.*
import kotlinx.android.synthetic.main.content_events.*
import javax.inject.Inject

class EventsActivity : AppCompatActivity(), EventsViewEvent , OnItemClicked {



    val TAG = "event list"
    @Inject lateinit var presenter: EventsPresenter
    @Inject lateinit var nHandler: NotificationHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)
        setSupportActionBar(toolbar)
        presenter.onCreate(this)


        event_list.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
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
    override fun showEvents(events: List<OBEvent>) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        event_list.hasFixedSize()
        event_list.adapter = EventListAdapter(this,events)
       // nHandler.createSimpleNotification(this.applicationContext)
    }


    override fun taskAddedAt(position: Int) {
        event_list.adapter?.notifyItemInserted(position)
    }

    override fun scrollTo(position: Int) {
        event_list.smoothScrollToPosition(position)
    }
    /*override fun onClick(v: View?) {
        Log.d(TAG,"--> ID: " + v?.id)

    }*/
    override fun onItemClick(position: Int) {
        Log.d(TAG,"--> ID: " + position)
    }
}

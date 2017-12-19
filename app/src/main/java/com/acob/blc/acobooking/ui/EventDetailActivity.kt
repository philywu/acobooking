package com.acob.blc.acobooking.ui

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.acob.blc.acobooking.R
import com.acob.blc.acobooking.presenter.EventDetailPresenter
import com.acob.blc.acobooking.presenter.EventDetailViewEvent
import dagger.android.AndroidInjection

import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.content_event_detail.*
import javax.inject.Inject

class EventDetailActivity : AppCompatActivity(),EventDetailViewEvent {
    val TAG = "event detail"
    @Inject lateinit var presenter: EventDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        presenter.onCreate(this)
        setContentView(R.layout.activity_event_detail)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            run {

                val eventMap = hashMapOf(
                    keyEventName to et_name.text.toString().trim(),
                        keyEventDesc     to et_desc.text.toString().trim(),
                        keyStartD to et_start_d.text.toString().trim(),
                        keyStartT to et_start_t.text.toString().trim(),
                        keyDeadlineD to et_deadline_d.text.toString().trim(),
                        keyDeadlineT to et_deadline_t.text.toString().trim()
                )
                presenter.doAdd(eventMap)
                Log.d(TAG, "clicked")
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()

            }
        }
    }

}

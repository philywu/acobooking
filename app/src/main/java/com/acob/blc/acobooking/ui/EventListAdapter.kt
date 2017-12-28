package com.acob.blc.acobooking.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.eventlist_layout.view.*
import android.content.Context
import android.view.View
import com.acob.blc.acobooking.R
import com.acob.blc.acobooking.data.model.OBEvent
import com.acob.blc.acobooking.presenter.asString
import kotlinx.android.synthetic.main.eventlist_layout.view.*
import android.R.attr.onClick
import android.content.ContentValues.TAG
import android.util.Log
import com.acob.blc.acobooking.presenter.EventsViewEvent


/**
 * Created by wugang00 on 11/12/2017.
 */
class EventListAdapter(var eventsViewEvent: EventsViewEvent, var lists: List<OBEvent>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        var v = LayoutInflater.from(parent?.context).inflate(R.layout.eventlist_layout, parent, false)
        return Item(v)

    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var item = holder as Item
        var itemListener = View.OnClickListener {
            Log.d(TAG, "come on " +" pos : " + item.itemView.list_event_id.text)
            item.itemView.bar_event_item.visibility = if ( item.itemView.bar_event_item.visibility == View.GONE) View.VISIBLE else View.GONE

        }
        item.bindData(lists[position])
        item.itemView.list_event_name.setOnClickListener (itemListener)
        item.itemView.list_event_desc.setOnClickListener (itemListener)

        item.itemView.btn_event_register.setOnClickListener (
                {
                    eventsViewEvent.eventRegister(item.itemView.list_event_id.text.toString(),item.itemView.list_event_owner.text.toString())
                }
        )
        item.itemView.btn_event_delete.setOnClickListener (
                {
                    eventsViewEvent.eventDelete(item.itemView.list_event_id.text.toString())
                }
        )


    }


    class Item(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(event: OBEvent) {
            itemView.list_event_id.text = event.evtId
            itemView.list_event_name.text = event.name
            itemView.list_event_desc.text = event.description
            itemView.list_event_owner.text = event.owner
            itemView.list_event_start_dt.text = "Start: " + event.startTime?.asString("dd.MM HH:mm")
            itemView.list_event_end_dt.text = "Start: " + event.endTime?.asString("dd.MM HH:mm")
            itemView.list_event_deadline.text = "Deadline: " + event.deadline?.asString("dd.MM HH:mm")

        }
    }



}
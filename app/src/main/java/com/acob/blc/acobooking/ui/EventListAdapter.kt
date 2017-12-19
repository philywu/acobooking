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

/**
 * Created by wugang00 on 11/12/2017.
 */
class EventListAdapter(var c: Context, var lists: List<OBEvent>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        var v = LayoutInflater.from(parent?.context).inflate(R.layout.eventlist_layout, parent, false)
        return Item(v)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as Item).bindData(lists[position])
    }

    class Item(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(event: OBEvent) {
            itemView.list_event_name.text = event.name
            itemView.list_event_desc.text = event.description
            itemView.list_event_owner.text = event.owner
            itemView.list_event_deadline.text = "Start: " + event.startTime?.asString("dd.MM HH:mm")
            itemView.list_event_start_dt.text = "Deadline: " + event.deadline?.asString("dd.MM HH:mm")
        }
    }
}
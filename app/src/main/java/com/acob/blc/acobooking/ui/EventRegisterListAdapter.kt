package com.acob.blc.acobooking.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.eventlist_layout.view.*
import android.view.View
import com.acob.blc.acobooking.R
import com.acob.blc.acobooking.presenter.asString
import android.content.ContentValues.TAG
import android.util.Log
import com.acob.blc.acobooking.data.model.OBEventWithRegister
import com.acob.blc.acobooking.presenter.EventRegisterViewEvent
import kotlinx.android.synthetic.main.event_register_list_layout.view.*


/**
 * Created by wugang00 on 11/12/2017.
 */
class EventRegisterListAdapter(var viewEvent: EventRegisterViewEvent, var lists: List<OBEventWithRegister>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        var v = LayoutInflater.from(parent?.context).inflate(R.layout.event_register_list_layout, parent, false)
        return Item(v)

    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var item = holder as Item
        var itemListener = View.OnClickListener {
            Log.d(TAG, "come on " +" pos : " + item.itemView.list_reg_event_id.text)
            item.itemView.bar_event_item.visibility = if ( item.itemView.bar_event_item.visibility == View.GONE) View.VISIBLE else View.GONE

        }
        item.bindData(lists[position])
        item.itemView.list_reg_event_name.setOnClickListener (itemListener)
        item.itemView.list_reg_event_desc.setOnClickListener (itemListener)




    }


    class Item(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(ewr: OBEventWithRegister) {
            itemView.list_reg_event_id.text = ewr.event?.evtId
            itemView.list_reg_event_name.text = ewr.event?.name
            itemView.list_reg_event_desc.text = ewr.event?.description
            itemView.list_reg_event_owner.text = ewr.event?.owner
            itemView.list_reg_event_start_dt.text = "Start: " + ewr.event?.startTime?.asString("dd.MM HH:mm")
            itemView.list_reg_event_end_dt.text = "Start: " + ewr.event?.endTime?.asString("dd.MM HH:mm")
            itemView.list_reg_event_deadline.text = "Deadline: " + ewr.event?.deadline?.asString("dd.MM HH:mm")
            itemView.list_reg_registerCount.text = ewr.register.size.toString()

        }
    }



}
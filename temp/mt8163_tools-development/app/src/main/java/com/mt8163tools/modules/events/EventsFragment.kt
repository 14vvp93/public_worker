package com.mt8163tools.modules.events

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mt8163tools.R
import com.mt8163tools.core.common.EventsLog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EventsFragment : Fragment() {
    private var eventsText: TextView? = null
    private var receiverRegistered = false

    private val eventReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            renderEvents()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_events, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventsText = view.findViewById(R.id.events_entries)
        renderEvents()
    }

    override fun onStart() {
        super.onStart()
        val context = requireContext()
        context.registerReceiver(
            eventReceiver,
            IntentFilter(EventsLog.ACTION_EVENT_RECORDED)
        )
        receiverRegistered = true
        renderEvents()
    }

    override fun onStop() {
        if (receiverRegistered) {
            requireContext().unregisterReceiver(eventReceiver)
            receiverRegistered = false
        }
        super.onStop()
    }

    private fun renderEvents() {
        val target = eventsText ?: return
        val events = EventsLog.getEvents(requireContext())
        if (events.isEmpty()) {
            target.setText(R.string.events_empty)
            return
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        target.text = events.joinToString("\n\n") { event ->
            val timestamp = dateFormat.format(Date(event.timestamp))
            "[$timestamp] [${event.moduleId}] ${event.type}\n${event.message}"
        }
    }

    override fun onDestroyView() {
        eventsText = null
        super.onDestroyView()
    }
}

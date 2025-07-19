package com.kgignatyev.fss.service.automation.impl.common

import io.temporal.activity.ActivityInterface


enum class EventType {
    LEAD_ACQUISITION_SUCCESS,
    LEAD_ACQUISITION_FAILURE,
}

@ActivityInterface
interface NotificationActivity {

    fun notifyAboutEvent( eventType: EventType, message: String )

    companion object {
        const val QUEUE_NOTIFICATIONS = "notifications"
    }
}

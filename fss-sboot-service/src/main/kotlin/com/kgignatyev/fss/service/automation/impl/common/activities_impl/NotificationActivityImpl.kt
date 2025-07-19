package com.kgignatyev.fss.service.automation.impl.common.activities_impl

import com.kgignatyev.fss.service.automation.impl.common.EventType
import com.kgignatyev.fss.service.automation.impl.common.NotificationActivity
import com.kgignatyev.fss.service.automation.impl.common.NotificationActivity.Companion.QUEUE_NOTIFICATIONS
import io.temporal.spring.boot.ActivityImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@ActivityImpl(taskQueues = [QUEUE_NOTIFICATIONS])
class NotificationActivityImpl : NotificationActivity {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun notifyAboutEvent(
        eventType: EventType,
        message: String
    ) {
        logger.warn("Simulating: Notification of event type: $eventType message: $message")
    }
}

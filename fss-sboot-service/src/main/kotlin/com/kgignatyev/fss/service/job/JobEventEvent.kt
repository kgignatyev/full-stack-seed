package com.kgignatyev.fss.service.job

import com.kgignatyev.fss.service.common.events.CrudEvent
import com.kgignatyev.fss.service.common.events.CrudEventType


class JobEventEvent(val data:JobEvent,  eventType: CrudEventType): CrudEvent(eventType) {
}

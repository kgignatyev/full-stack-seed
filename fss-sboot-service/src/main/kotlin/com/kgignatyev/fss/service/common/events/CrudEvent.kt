package com.kgignatyev.fss.service.common.events

import org.jmolecules.event.types.DomainEvent


open class CrudEvent(val eventType: CrudEventType): DomainEvent{
}

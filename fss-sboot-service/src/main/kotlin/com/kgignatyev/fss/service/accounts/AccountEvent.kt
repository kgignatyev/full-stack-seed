package com.kgignatyev.fss.service.accounts

import com.kgignatyev.fss.service.common.events.CrudEvent
import com.kgignatyev.fss.service.common.events.CrudEventType


class AccountEvent( val data: Account, eventType: CrudEventType): CrudEvent(eventType) {
}

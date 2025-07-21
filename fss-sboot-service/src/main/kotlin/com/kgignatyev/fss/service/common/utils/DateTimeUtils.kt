package com.kgignatyev.fss.service.common.utils

import com.google.protobuf.Timestamp
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset

fun Timestamp.toOffsetDateTime(): OffsetDateTime {
    val instant = Instant.ofEpochSecond(this.seconds, this.nanos.toLong())
    return OffsetDateTime.ofInstant(instant, ZoneOffset.UTC)
}

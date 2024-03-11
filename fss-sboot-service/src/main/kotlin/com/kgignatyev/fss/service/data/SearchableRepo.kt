package com.kgignatyev.fss.service.data

import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1ListSummary
import org.springframework.transaction.annotation.Transactional

data class SearchSummary(val offset: Long = 0, val count:Int = 0, val total:Long = 0) {
    fun toListSummary():  V1ListSummary {
        val r = V1ListSummary()
        r.offset = offset
        r.total = total
        r.size = count
        return r
    }
}

data class SearchResult<T>(val items:List<T>, val summary:SearchSummary)


interface SearchableRepo<T> {

    fun search(expression:String, offset:Long = 0, limit:Int = 20):SearchResult<T>
}

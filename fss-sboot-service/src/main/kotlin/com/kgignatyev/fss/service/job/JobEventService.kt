package com.kgignatyev.fss.service.job

import com.kgignatyev.fss.service.common.data.SearchableRepo
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface JobEventService: SearchableRepo<JobEvent>, CrudRepository<JobEvent, String> {
    fun deleteByJobId(id: String)
    fun findByJobId(id: String): List<JobEvent>
}

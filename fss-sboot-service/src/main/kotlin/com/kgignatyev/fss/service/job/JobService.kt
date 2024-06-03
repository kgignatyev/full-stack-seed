package com.kgignatyev.fss.service.job

import com.kgignatyev.fss.service.common.data.SearchableRepo
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface JobService: SearchableRepo<Job>,CrudRepository<Job, String>{

}

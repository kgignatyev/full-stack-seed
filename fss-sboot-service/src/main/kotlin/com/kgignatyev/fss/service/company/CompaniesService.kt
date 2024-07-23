package com.kgignatyev.fss.service.company

import com.kgignatyev.fss.service.common.data.SearchableRepo
import com.kgignatyev.fss.service.job.Job
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface CompaniesService : SearchableRepo<Company>, CrudRepository<Company, String>{
}




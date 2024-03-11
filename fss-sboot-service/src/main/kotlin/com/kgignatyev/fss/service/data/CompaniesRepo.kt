package com.kgignatyev.fss.service.data

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CompaniesRepo:CrudRepository<Company, String>{
}

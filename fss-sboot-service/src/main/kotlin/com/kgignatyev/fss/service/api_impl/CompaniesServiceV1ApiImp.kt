package com.kgignatyev.fss.service.api_impl

import com.kgignatyev.fss.service.data.CompaniesRepo
import com.kgignatyev.fss_svc.api.fsssvc.CompaniesServiceV1Api
import com.kgignatyev.fss_svc.api.fsssvc.v1.model.*
import jakarta.annotation.Resource
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RequestMapping(path = ["/api"])
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"], methods = [RequestMethod.PATCH, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.PUT])
@RestController
class CompaniesServiceV1ApiImp: CompaniesServiceV1Api {


    @Resource
    lateinit var companiesRepo: CompaniesRepo
    @Resource
    lateinit var convSvc:ConversionService

    override fun getAllCompanies(): ResponseEntity<List<V1Company>> {
       return ok( companiesRepo.findAll().map { convSvc.convert(it, V1Company::class.java)!! })
    }

    override fun createCompany(v1Company: V1Company?): ResponseEntity<V1Company> {
        val company = companiesRepo.save(convSvc.convert(v1Company, com.kgignatyev.fss.service.data.Company::class.java)!!)
        return ok( convSvc.convert(company, V1Company::class.java)!! )
    }

    override fun getCompanyById(id: String?): ResponseEntity<V1Company> {
        val company = companiesRepo.findById(id!!).get()
        return ok( convSvc.convert(company, V1Company::class.java)!! )
    }
}

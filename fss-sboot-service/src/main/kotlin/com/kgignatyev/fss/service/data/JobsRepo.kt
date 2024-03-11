package com.kgignatyev.fss.service.data

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import jakarta.persistence.criteria.Predicate

interface SearchableJobsRepo : SearchableRepo<Job>

@Repository
@Transactional
interface JobsRepo : CrudRepository<Job, String>, JpaSpecificationExecutor<Job> {


}

@Transactional
@Service
class SearchableJobsRepoImpl(val jobsRepo: JobsRepo) : SearchableJobsRepo {


    override fun search(expression: String, offset: Long, limit: Int): SearchResult<Job> {
        var parts = expression.split(" ").map { it.trim() }
        val spec = if (parts.size >= 3) {
            Specification.where<Job> { root, query, builder ->
                val predicates = mutableListOf<Predicate>()
                while (parts.size >= 3) {
                    val (f, op, criteria) = parts.take(3)
                    val noQuotesCriteria = criteria.replace("'","")
                        .replace("\"","")
                    parts = parts.drop(3)
                    val getF = root.get<String>(f)
                    predicates.add(builder.like(getF, noQuotesCriteria))

                    if (parts.isNotEmpty()) {
                        val op = parts.take(1)
                        parts = parts.drop(1)
                    }
                }
                builder.or( *predicates.toTypedArray())
            }
        } else {
            Specification.where { root, query, builder ->
                builder.like(root.get("companyName"), "%i%")
            }
        }
        val pageN = (offset / limit).toInt()
        val page = PageRequest.of(pageN, limit).withSort(Sort.Direction.ASC, "title")
        val itemsPage = jobsRepo.findAll(spec, page)
        val total = itemsPage.totalElements
        val summary = SearchSummary(offset, itemsPage.size, total)

        val r = SearchResult<Job>(itemsPage.toList(), summary)
        return r
    }

}

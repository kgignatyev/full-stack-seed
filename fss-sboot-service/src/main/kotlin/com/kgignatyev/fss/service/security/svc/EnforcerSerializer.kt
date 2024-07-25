package com.kgignatyev.fss.service.security.svc

import com.hazelcast.nio.serialization.compact.CompactReader
import com.hazelcast.nio.serialization.compact.CompactSerializer
import com.hazelcast.nio.serialization.compact.CompactWriter
import com.kgignatyev.fss.service.FssService
import com.kgignatyev.fss.service.security.AuthorizationSvc
import org.casbin.jcasbin.main.Enforcer
import org.casbin.jcasbin.model.Model
import org.casbin.jcasbin.persist.Helper
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class EnforcerSerializer:CompactSerializer<Enforcer> {
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    private lateinit var authzSvcV: AuthorizationSvc

    fun authzSvc(): AuthorizationSvc {
        if( !this::authzSvcV.isInitialized ) {
            authzSvcV = FssService.cxt.getBean(AuthorizationSvc::class.java)
        }
        return authzSvcV
    }

    override fun read(reader: CompactReader): Enforcer {

        val modelText = reader.readString("model")!!
        val policyText = reader.readString("policy")!!
        logger.info("""deserializing enforcer:
            |modelText: 
            |$modelText 
            |policyText: 
            |$policyText""".trimMargin())
        val model = Model()
        model.loadModelFromText(modelText)
        policyText.split("\n").forEach {
            Helper.loadPolicyLine(it, model)
        }
        val e = Enforcer(model)
        authzSvc().postConfigureEnforcer(e)
        return e
    }

    override fun getTypeName(): String {
        return "Enforcer"
    }

    override fun getCompactClass(): Class<Enforcer> {
        return Enforcer::class.java
    }

    override fun write(writer: CompactWriter, v: Enforcer) {

        val modelText = v.model.saveModelToText()
        val policyText = v.model.savePolicyToText()
        logger.info("""serializing enforcer:${v.hashCode()} 
            |modelText: 
            |$modelText 
            |policyText: 
            |$policyText""".trimMargin())
        writer.writeString("model", modelText)
        writer.writeString("policy", policyText)
    }
}

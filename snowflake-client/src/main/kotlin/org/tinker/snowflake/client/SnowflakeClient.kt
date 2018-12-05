package org.tinker.snowflake.client

import jodd.http.HttpRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import org.tinker.snowflake.spring.SnowflakeDomain
import javax.annotation.PostConstruct

@Component
class SnowflakeClient {
    @Autowired
    lateinit var applicationContext: ApplicationContext

    @PostConstruct
    private fun init() {
        environment = applicationContext.environment
    }

    companion object {
        lateinit var environment: Environment

        fun next(): Long {
            var host = environment.getProperty("snowflake.server.host") ?: "localhost"
            return HttpRequest.get("$host:1010/sf/next-small")
                    .send()
                    .body()
                    .toLong()
        }

        fun resolver(sid: Long): SnowflakeDomain {
            return SnowflakeDomain.resolverLarge(sid)
        }
    }
}
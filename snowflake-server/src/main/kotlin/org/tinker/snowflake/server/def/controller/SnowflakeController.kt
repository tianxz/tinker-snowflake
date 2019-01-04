package org.tinker.sfserver.def.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.tinker.snowflake.spring.SnowflakeComponent
import org.tinker.snowflake.spring.SnowflakeDomain
import java.time.format.DateTimeFormatter
import javax.websocket.server.PathParam

@RestController
@RequestMapping("/sf")
open class SnowflakeController {
    private var logger = LoggerFactory.getLogger(SnowflakeController::class.java)

    @GetMapping("/next-large-info")
    fun nextLargeInfo(): SnowflakeDomain {
        var sf = SnowflakeDomain.resolverLarge(SnowflakeComponent.nextLarge())
        logger.debug("sid={}, dateTime={}, timestamp={}, node={}, sn={}", sf.sid, sf.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), sf.timestamp, sf.node, sf.seq)
        return sf
    }

    @GetMapping("/next-large")
    fun nextLarge(): String {
        return SnowflakeComponent.nextLarge().toString()
    }

    @GetMapping("/next-small")
    fun nextSmall(): String {
        return SnowflakeComponent.nextSmall().toString()
    }

    @GetMapping("/next-small-info")
    fun nextSmallInfo(): SnowflakeDomain {
        var sf = SnowflakeDomain.resolverSmall(SnowflakeComponent.nextSmall())
        logger.debug("sid={}, dateTime={}, timestamp={}, node={}, sn={}", sf.sid, sf.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), sf.timestamp, sf.node, sf.seq)
        return sf
    }

    @GetMapping("/resolver/{sid}")
    fun resolver(@PathVariable("sid") sid: Long): SnowflakeDomain {
        var sf = SnowflakeDomain.resolverSmall(sid)
        logger.debug("sid={}, dateTime={}, timestamp={}, node={}, sn={}", sf.sid, sf.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), sf.timestamp, sf.node, sf.seq)
        return sf
    }
}
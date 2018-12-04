package org.tinker.sfserver.def.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.tinker.snowflake.spring.SnowflakeComponent
import org.tinker.snowflake.spring.SnowflakeDomain
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/sf")
open class SnowflakeController {
    private var logger = LoggerFactory.getLogger(SnowflakeController::class.java)

    @GetMapping("/next-large-info")
    fun nextLargeInfo(): String {
        var sf = SnowflakeDomain.resolverLarge(SnowflakeComponent.nextLarge())
        logger.info("sid={}, dateTime={}, timestamp={}, node={}, sn={}", sf.sid, sf.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), sf.timestamp, sf.node, sf.seq)
        return "sid=${sf.sid}, dateTime=${sf.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))}, timestamp=${sf.timestamp}, node=${sf.node}, sn=${sf.seq}"
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
    fun nextSmallInfo(): String {
        var sf = SnowflakeDomain.resolverSmall(SnowflakeComponent.nextSmall())
        logger.info("sid={}, dateTime={}, timestamp={}, node={}, sn={}", sf.sid, sf.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), sf.timestamp, sf.node, sf.seq)
        return "sid=${sf.sid}, dateTime=${sf.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))}, timestamp=${sf.timestamp}, node=${sf.node}, sn=${sf.seq}"
    }
}
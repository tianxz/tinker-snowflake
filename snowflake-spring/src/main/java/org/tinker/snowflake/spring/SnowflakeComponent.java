package org.tinker.snowflake.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.tinker.snowflake.core.LargeSnowflake;
import org.tinker.snowflake.core.SmallSnowflake;

import javax.annotation.PostConstruct;

@Component
public class SnowflakeComponent {
    private final static Object           largeSnowflakeLock = new Object();
    private static       LargeSnowflake[] largeSnowflakeArray;
    private final static Object           smallSnowflakeLock = new Object();
    private static       SmallSnowflake[] smallSnowflakeArray;
    private static       Environment      environment;
    private static       int              largeStart;
    private static       int              largeEnd;
    private static       int              largeCurrentIndex;
    private static       int              smallStart;
    private static       int              smallEnd;
    private static       int              smallCurrentIndex;

    @Autowired
    ApplicationContext applicationContext;

    @PostConstruct
    private void init() {
        this.environment = applicationContext.getEnvironment();
        this.largeStart = Integer.valueOf(environment.getProperty("snowflake.node.large").split("-")[0]);
        this.largeEnd = Integer.valueOf(environment.getProperty("snowflake.node.large").split("-")[1]);
        this.largeCurrentIndex = this.largeStart;
        this.smallStart = Integer.valueOf(environment.getProperty("snowflake.node.small").split("-")[0]);
        this.smallEnd = Integer.valueOf(environment.getProperty("snowflake.node.small").split("-")[1]);
        this.smallCurrentIndex = this.smallStart;
    }

    public static long nextLarge() {
        if (largeSnowflakeArray == null) {
            synchronized (largeSnowflakeLock) {
                if (largeSnowflakeArray == null) {
                    largeSnowflakeArray = new LargeSnowflake[largeEnd - largeStart + 1];
                    for (int i = largeStart; i <= largeEnd; i++) {
                        largeSnowflakeArray[i] = new LargeSnowflake(i);
                    }
                }
            }
        }
        long sid = largeSnowflakeArray[largeCurrentIndex].next();
        if (largeCurrentIndex > largeEnd) {
            largeCurrentIndex = largeStart;
        } else {
            largeCurrentIndex++;
        }
        return sid;
    }

    public static long nextSmall() {
        if (smallSnowflakeArray == null) {
            synchronized (smallSnowflakeLock) {
                if (smallSnowflakeArray == null) {
                    smallSnowflakeArray = new SmallSnowflake[smallEnd - smallStart + 1];
                    for (int i = smallStart; i <= smallEnd; i++) {
                        smallSnowflakeArray[i] = new SmallSnowflake(i);
                    }
                }
            }
        }

        if (smallCurrentIndex > smallEnd) {
            smallCurrentIndex = smallStart;
        }
        return smallSnowflakeArray[smallCurrentIndex++].next();
    }
}

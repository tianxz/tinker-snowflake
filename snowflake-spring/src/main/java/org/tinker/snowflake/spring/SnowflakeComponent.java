package org.tinker.snowflake.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.tinker.snowflake.core.LargeSnowflake;
import org.tinker.snowflake.core.SmallSnowflake;
import sun.misc.Lock;

import javax.annotation.PostConstruct;

@Component
public class SnowflakeComponent {
    private final static Lock               largeSnowflakeLock = new Lock();
    private static       LargeSnowflake     largeSnowflake;
    private final static Lock               smallSnowflakeLock = new Lock();
    private static       SmallSnowflake     smallSnowflake;
    private static       ApplicationContext context;
    private static       Environment        environment;

    @Autowired
    ApplicationContext applicationContext;

    @PostConstruct
    private void init() {
        this.context = applicationContext;
        this.environment = applicationContext.getEnvironment();
    }

    public static long nextLarge() {
        if (largeSnowflake == null) {
            synchronized (largeSnowflakeLock) {
                if (largeSnowflake == null) {
                    largeSnowflake = new LargeSnowflake(Integer.valueOf(environment.getProperty("snowflake.node.large")));
                }
            }
        }

        return largeSnowflake.next();
    }

    public static long nextSmall() {
        if (smallSnowflake == null) {
            synchronized (smallSnowflakeLock) {
                if (smallSnowflake == null) {
                    smallSnowflake = new SmallSnowflake(Integer.valueOf(environment.getProperty("snowflake.node.small")));
                }
            }
        }

        return smallSnowflake.next();
    }
}

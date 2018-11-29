package org.tinker.snowflake;

/**
 * A snowflake is a source of k-ordered unique 64-bit integers.
 */
public class LargeSnowflake extends SmallSnowflake {
    /**
     * A snowflake is designed to operate as a singleton instance within the context of a node.
     * If you deploy different nodes, supplying a unique node id will guarantee the uniqueness
     * of ids generated concurrently on different nodes.
     *
     * @param node This is an id you use to differentiate different nodes.
     */
    public LargeSnowflake(int node) {
        super(node);
    }

    protected long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
}

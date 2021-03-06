/**
 * Copyright (c) 2017 Dell Inc., or its subsidiaries. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package io.pravega.segmentstore.server;

import io.pravega.segmentstore.storage.ThrottleSourceListener;

/**
 * Defines an object that can provide information about the Cache utilization.
 */
public interface CacheUtilizationProvider {
    /**
     * Gets a value representing the current cache utilization, as a ratio of cache used to cache max size.
     * * A value of 0 indicates that the cache is empty (or almost empty).
     * * A value in the interval (0,1) indicates the cache is used, but not full.
     * * A value of 1 indicates that the cache is used at 100% capacity.
     * * A value greater than 1 indicates the cache exceeds it allocated capacity.
     *
     * @return The cache utilization.
     */
    double getCacheUtilization();

    /**
     * Gets a value representing the target utilization of the cache, as a ratio of cache used to cache max size.
     * The cache should be kept at or below this level. Any utilization above this limit should cause throttling and/or
     * cache eviction to occur.
     *
     * See {@link #getCacheUtilization()} for more details.
     *
     * @return The maximum cache utilization.
     */
    double getCacheTargetUtilization();

    /**
     * Gets a value representing the maximum allowed utilization of the cache, as a ratio of cache used to cache max size.
     * Any utilization above this limit should cause both full throttling and cache eviction to occur.
     *
     * See {@link #getCacheUtilization()} for more details.
     *
     * @return The maximum cache utilization.
     */
    double getCacheMaxUtilization();

    /**
     * Registers the given {@link ThrottleSourceListener}, which will be notified of all subsequent Cache Cleanup events that
     * result in at least one entry being evicted from the cache.
     *
     * @param listener The {@link ThrottleSourceListener} to register. This will be auto-unregistered on the first Cache Cleanup
     *                 run that detects {@link ThrottleSourceListener#isClosed()} to be true.
     */
    void registerCleanupListener(ThrottleSourceListener listener);
}

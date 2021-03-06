/**
 * Copyright (c) 2017 Dell Inc., or its subsidiaries. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package io.pravega.segmentstore.storage.noop;

import com.google.common.base.Preconditions;
import io.pravega.segmentstore.storage.AsyncStorageWrapper;
import io.pravega.segmentstore.storage.Storage;
import io.pravega.segmentstore.storage.StorageFactory;
import io.pravega.segmentstore.storage.rolling.RollingStorage;

import java.util.concurrent.Executor;

/**
 * Factory for No-Op mode Storage adapters.
 */
public class NoOpStorageFactory implements StorageFactory {
    private final StorageExtraConfig config;
    private final Executor executor;
    private final StorageFactory systemStorageFactory;
    private final StorageFactory userStorageFactory;

    public NoOpStorageFactory(StorageExtraConfig config, Executor executor, StorageFactory systemStorageFactory, StorageFactory userStorageFactory) {
        this.config = Preconditions.checkNotNull(config, "config");
        this.executor = Preconditions.checkNotNull(executor, "executor");
        this.systemStorageFactory = Preconditions.checkNotNull(systemStorageFactory, "systemStorageFactory");
        this.userStorageFactory = userStorageFactory;
    }

    @Override
    public Storage createStorageAdapter() {
        NoOpStorage s = new NoOpStorage(this.config, this.systemStorageFactory.createSyncStorage(),
                userStorageFactory == null ? null : userStorageFactory.createSyncStorage());
        return new AsyncStorageWrapper(new RollingStorage(s), this.executor);
    }
}

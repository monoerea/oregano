package com.mono.oregano.data.repository;

import com.mono.oregano.data.remote.DataSources;

public abstract class baseRepository {
    public abstract baseRepository getInstance(DataSources dataSources);
}

package com.mono.oregano.data.repository;

import com.mono.oregano.data.network.DataSources;
import com.mono.oregano.data.repository.user.AuthRepository;

public abstract class baseRepository {
    public abstract baseRepository getInstance(DataSources dataSources);
}

package com.mono.oregano.data.repository;

import com.mono.oregano.data.network.DataSources;
import com.mono.oregano.data.repository.user.AuthRepository;

public interface baseRepository {
    Result result = null;

    static baseRepository getInstance(DataSources dataSources) {
        return (baseRepository) new Object();
    }
}

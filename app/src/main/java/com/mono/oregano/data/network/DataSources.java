package com.mono.oregano.data.network;

public interface DataSources {

    DataSources getInstance(DataSources sources);

    FirebaseDBInstance getInstance(FirebaseDBInstance source);

    FirebaseAuthInstance getInstance(FirebaseAuthInstance sources);
}

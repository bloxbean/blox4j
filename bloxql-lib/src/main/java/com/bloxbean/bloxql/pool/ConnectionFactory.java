package com.bloxbean.bloxql.pool;

import org.springframework.stereotype.Service;

@Service
public interface ConnectionFactory {

    public ChainConnection createConnection();
}

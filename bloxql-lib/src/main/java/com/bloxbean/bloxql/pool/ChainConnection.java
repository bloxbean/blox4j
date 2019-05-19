package com.bloxbean.bloxql.pool;

public interface ChainConnection {

    public void destroy();

    public boolean validate();
}

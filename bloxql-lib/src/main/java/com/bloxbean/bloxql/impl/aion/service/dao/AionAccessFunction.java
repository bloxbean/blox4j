package com.bloxbean.bloxql.impl.aion.service.dao;

import org.aion.api.IAionAPI;
import org.aion.api.type.ApiMsg;

@FunctionalInterface
public interface AionAccessFunction<T> {

    public T invoke(ApiMsg apiMsg, IAionAPI api);
}

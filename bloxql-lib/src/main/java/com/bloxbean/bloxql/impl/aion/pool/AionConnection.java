package com.bloxbean.bloxql.impl.aion.pool;

import com.bloxbean.bloxql.pool.ChainConnection;
import org.aion.api.IAionAPI;
import org.aion.api.type.ApiMsg;

public class AionConnection implements ChainConnection {

    private IAionAPI api;
    private ApiMsg apiMsg;

    public AionConnection(IAionAPI api, ApiMsg apiMsg) {
        this.api = api;
        this.apiMsg = apiMsg;
    }

    public IAionAPI getApi() {
        return api;
    }

    public void setApi(IAionAPI api) {
        this.api = api;
    }

    public ApiMsg getApiMsg() {
        return apiMsg;
    }

    public void setApiMsg(ApiMsg apiMsg) {
        this.apiMsg = apiMsg;
    }

    @Override
    public boolean validate() {
        if(api != null)
            return api.isConnected();
        else
            return false;
    }

    @Override
    public void destroy() {
        if(api != null) {
            api.destroyApi();
        }

        api = null;
        apiMsg = null;
    }
}

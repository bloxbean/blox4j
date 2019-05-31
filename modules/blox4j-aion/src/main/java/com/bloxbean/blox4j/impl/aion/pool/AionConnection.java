/*
 * MIT License
 *
 * Copyright (c) 2019 BloxBean Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bloxbean.blox4j.impl.aion.pool;

import com.bloxbean.blox4j.pool.ChainConnection;
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

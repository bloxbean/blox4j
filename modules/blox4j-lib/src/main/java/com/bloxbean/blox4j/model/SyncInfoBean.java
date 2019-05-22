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

package com.bloxbean.blox4j.model;

public class SyncInfoBean {

    private long chainBestBlock;
    private long maxImportBlocks;
    private long networkBestBlock;
    private long startingBlock;
    private boolean isSyncing;

    public long getChainBestBlock() {
        return chainBestBlock;
    }

    public void setChainBestBlock(long chainBestBlock) {
        this.chainBestBlock = chainBestBlock;
    }

    public long getMaxImportBlocks() {
        return maxImportBlocks;
    }

    public void setMaxImportBlocks(long maxImportBlocks) {
        this.maxImportBlocks = maxImportBlocks;
    }

    public long getNetworkBestBlock() {
        return networkBestBlock;
    }

    public void setNetworkBestBlock(long networkBestBlock) {
        this.networkBestBlock = networkBestBlock;
    }

    public long getStartingBlock() {
        return startingBlock;
    }

    public void setStartingBlock(long startingBlock) {
        this.startingBlock = startingBlock;
    }

    public boolean isSyncing() {
        return isSyncing;
    }

    public void setSyncing(boolean syncing) {
        isSyncing = syncing;
    }
}

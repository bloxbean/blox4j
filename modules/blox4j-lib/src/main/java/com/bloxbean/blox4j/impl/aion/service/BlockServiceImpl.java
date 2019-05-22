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

package com.bloxbean.blox4j.impl.aion.service;

import com.bloxbean.blox4j.exception.BlockChainAcessException;
import com.bloxbean.blox4j.model.Block;
import com.bloxbean.blox4j.service.AdminService;
import com.bloxbean.blox4j.service.BlockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlockServiceImpl implements BlockService {

    private static final Logger logger = LoggerFactory.getLogger(BlockServiceImpl.class);

    private AdminService adminService;

    public BlockServiceImpl(AdminService adminService) {
        this.adminService = adminService;
    }

    public List<Block> getBlocks(Long first, long offset) {
        return adminService.getBlocks(first, offset);
    }

    public Block getBlock(long number) {

        return adminService.getBlockDetailsByNumber(number);
    }

    public Block getLatestBlock() {

        List<Block> blocks = adminService.getBlockDetailsByLatest(1L);

        if(blocks != null && blocks.size() > 0)
            return blocks.get(0);
        else
            throw new BlockChainAcessException("Latet block not found");
    }

}

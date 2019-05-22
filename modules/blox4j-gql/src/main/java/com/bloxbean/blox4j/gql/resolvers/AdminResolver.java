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

package com.bloxbean.blox4j.gql.resolvers;

import com.bloxbean.blox4j.exception.DataFetchingException;
import com.bloxbean.blox4j.model.Account;
import com.bloxbean.blox4j.model.Block;
import com.bloxbean.blox4j.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminResolver {

    private Logger logger = LoggerFactory.getLogger(AdminResolver.class);

    private AdminService adminService;

    public AdminResolver(AdminService adminService) {
        this.adminService = adminService;
    }

    //comma separated addresses
    public List<Account> accountsByAddressString(String addresses) {
        try {
            return adminService.getAccountDetailsByAddressList(addresses);
        } catch (Exception e) {
            logger.error("Excption while fetching accounts ", e);
            throw new DataFetchingException(e.getMessage());
        }
    }

    public List<Account> accounts(List<String> addresses) {
        try {
            return adminService.getAccountDetailsByAddressList​(addresses);
        } catch (Exception e) {
            logger.error("Excption while fetching accounts ", e);
            throw new DataFetchingException(e.getMessage());
        }
    }

    public Block blockByHash(String hash) {
        try {
            return adminService.getBlockDetailsByHash(hash);
        } catch (Exception e) {
            logger.error("error getting block by hash", e);
            throw new DataFetchingException(e.getMessage());
        }
    }

    public List<Block> blocksByLatest(long count) {
        return adminService.getBlockDetailsByLatest(count);
    }

    public Block blockByNumber(long blockNumber) {
        return adminService.getBlockDetailsByNumber(blockNumber);
    }

    public List<Block> blocksByNumber(List<Long> blockNumbers) {
        return adminService.getBlockDetailsByNumber(blockNumbers);
    }

    public List<Block> blocksByRange(long blockStart, long blockEnd) {
        return adminService.getBlockDetailsByRange(blockStart, blockEnd);
    }

    public List<Block> blocks(Long first, Long before) {
        return adminService.getBlocks(first, before);
    }



}

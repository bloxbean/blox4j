package com.bloxbean.bloxql.service;

import com.bloxbean.bloxql.model.Account;
import com.bloxbean.bloxql.model.Block;

import java.util.List;

public interface AdminService {

    public List<Account> getAccountDetailsByAddressList(String addresses);

    public List<Account> getAccountDetailsByAddressList​(List<String> addresses);

    public Block getBlockDetailsByHash(String hash);

    public List<Block> getBlockDetailsByLatest(long count);

    public Block getBlockDetailsByNumber(long blockNumber);

    public List<Block> getBlockDetailsByNumber(List<Long> blockNumbers);

    public List<Block> getBlockDetailsByRange(long blockStart, long blockEnd);

    //The following two methods are redundant. So will not be implemented.

//    public List<Block> getBlocksByLatest(long count);

//    getBlockSqlByRange​(java.lang.Long blkStart, java.lang.Long blkEnd)

    public List<Block> getBlocks(Long first, long before);
}

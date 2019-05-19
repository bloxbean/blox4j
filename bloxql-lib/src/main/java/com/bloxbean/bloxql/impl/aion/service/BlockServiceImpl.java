package com.bloxbean.bloxql.impl.aion.service;

import com.bloxbean.bloxql.exception.BlockChainAcessException;
import com.bloxbean.bloxql.model.Block;
import com.bloxbean.bloxql.service.AdminService;
import com.bloxbean.bloxql.service.BlockService;
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

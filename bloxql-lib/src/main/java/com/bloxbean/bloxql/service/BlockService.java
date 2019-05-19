package com.bloxbean.bloxql.service;

import com.bloxbean.bloxql.model.Block;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlockService {

    public List<Block> getBlocks(Long first, long offset);

    public Block getBlock(long number);

    public Block getLatestBlock();
}

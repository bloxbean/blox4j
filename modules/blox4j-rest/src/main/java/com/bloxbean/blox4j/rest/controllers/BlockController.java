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

package com.bloxbean.blox4j.rest.controllers;

import com.bloxbean.blox4j.model.Block;
import com.bloxbean.blox4j.model.TxDetails;
import com.bloxbean.blox4j.rest.common.RestConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.bloxbean.blox4j.rest.exception.RestResourceNotFoundException;
import com.bloxbean.blox4j.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(RestConstants.VERSION1_BASE_PATH + "blocks")
@ConditionalOnProperty(name = "rest.enable", havingValue = "true")
@Api(value="blocks", description="Block specific apis")
public class BlockController {

  @Autowired
  private BlockService service;

  @GetMapping(produces = "application/hal+json")
  @ApiOperation(value = "Get blocks starting from the block number passed through \"before\" parameter")
  public ResponseEntity<Resources<Block>> getBlocks(
      @RequestParam(value = "first", required = false, defaultValue = "20") @ApiParam(value = "No of blocks to return") Long first,
      @RequestParam(value = "before", required = false, defaultValue = "-1") @ApiParam(value = "Start block number") Long before) {

    List<Block> blocks = service.getBlocks(first, before);

    if(blocks == null)
      return ResponseEntity.ok(null);

    for(Block block: blocks) {
      block.add(linkTo(methodOn(BlockController.class).getBlock(block.getNumber())).withSelfRel());

      block.getTxDetails().stream().forEach(txDetails -> {
        addTxnDetailsLink(txDetails);
      });
    }

    final Resources<Block> blockResources = new Resources<>(blocks);

    blockResources.add(
       linkTo(methodOn(BlockController.class).getBlocks(first, before))
           .withSelfRel()
    );
    return ResponseEntity.ok(blockResources);
  }

  @GetMapping(value = "/{number}", produces = "application/json")
  @ApiOperation(value = "Get block details for the given block number")
  public HttpEntity<Block> getBlock(@PathVariable("number") @ApiParam("Block number") Long number) {

    Block block = service.getBlock(number);

    if(block != null) {
      block.add(linkTo(methodOn(BlockController.class).getBlock(number)).withSelfRel());

      block.getTxDetails().stream().forEach(txDetails -> {
        addTxnDetailsLink(txDetails);
      });

      return new ResponseEntity<>(block, HttpStatus.OK);
    } else {
      throw new RestResourceNotFoundException("Block not found");
    }
  }

  private void addTxnDetailsLink(TxDetails txDetails) {
    txDetails.add(linkTo(methodOn(TransactionController.class).transaction(txDetails.getTxHash())).withSelfRel());

    txDetails.add(
        linkTo(methodOn(AccountController.class).getAccount(txDetails.getFrom()))
            .withRel("from"));

    txDetails.add(
        linkTo(methodOn(AccountController.class).getAccount(txDetails.getTo()))
            .withRel("to"));
  }

}

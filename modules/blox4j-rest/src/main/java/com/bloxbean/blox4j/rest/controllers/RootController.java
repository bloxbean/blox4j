package com.bloxbean.blox4j.rest.controllers;

import com.bloxbean.blox4j.rest.common.RestConstants;
import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(RestConstants.VERSION1_BASE_PATH)
@ConditionalOnProperty(name = "rest.enable", havingValue = "true")
@Api(value="root", description="List all api endpoints")
public class RootController {

    @GetMapping
    public ResponseEntity<RootResource> getAllApis() {

        RootResource rootResource = new RootResource();

        rootResource.add(
            linkTo(methodOn(BlockController.class).getBlocks(Long.valueOf(20), Long.valueOf(-1)))
                .withRel("blocks")
        );

        rootResource.add(
            linkTo(methodOn(TransactionController.class).transactions(Long.valueOf(-1), Long.valueOf(20)))
                .withRel("transactions")
        );

        rootResource.add(
            linkTo(methodOn(NetController.class).getNetworkInfo())
                .withRel("network")
        );

        rootResource.add(
            linkTo(methodOn(AccountController.class).getAccount("{enter_address}"))
                .withRel("accounts")
        );

        return ResponseEntity.ok(rootResource);
    }
}

class RootResource extends ResourceSupport {

}

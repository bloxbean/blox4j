package com.bloxbean.bloxql.impl.aion.util;

import com.bloxbean.bloxql.exception.DataConversionException;
import com.bloxbean.bloxql.model.*;
import com.bloxbean.bloxql.model.input.TxArgsInput;
import com.google.gson.Gson;
import org.aion.api.IContract;
import org.aion.api.type.*;
import org.aion.api.type.Block;
import org.aion.api.type.TxDetails;
import org.aion.base.type.AionAddress;
import org.aion.base.util.ByteArrayWrapper;
import org.aion.base.util.ByteUtil;
import com.bloxbean.bloxql.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ModelConverter {

    private final static Logger logger = LoggerFactory.getLogger(ModelConverter.class);

    public static com.bloxbean.bloxql.model.Block convert(BlockDetails blockDetails) {
        com.bloxbean.bloxql.model.Block b = new com.bloxbean.bloxql.model.Block();

        BeanUtils.copyProperties(blockDetails, b);

        b.setBloom(String.valueOf(blockDetails.getBloom()));
        b.setExtraData(String.valueOf(blockDetails.getExtraData()));
        b.setSolution(String.valueOf(blockDetails.getSolution()));
        b.setHash(String.valueOf(blockDetails.getHash()));
        b.setParentHash(String.valueOf(blockDetails.getParentHash()));
        b.setMinerAddress(String.valueOf(blockDetails.getMinerAddress()));
        b.setStateRoot(String.valueOf(blockDetails.getStateRoot()));
        b.setTxTrieRoot(String.valueOf(blockDetails.getTxTrieRoot()));
        b.setSize(blockDetails.getSize());

        if(blockDetails.getTxDetails() != null) {
            b.setTxDetails(blockDetails.getTxDetails().stream()
                    .map(txDetails -> {
                        com.bloxbean.bloxql.model.TxDetails txD = ModelConverter.convert(txDetails);
                        txD.setBlockHash(b.getHash());
                        txD.setBlockNumber(b.getNumber());
                        if(txD.getTimestamp() == 0)
                            txD.setTimestamp(b.getTimestamp());

                        return txD;
                    }).collect(Collectors.toList()));
        }

        return b;
    }

    public static com.bloxbean.bloxql.model.Block convert(Block aionBlock) {
        com.bloxbean.bloxql.model.Block block = new com.bloxbean.bloxql.model.Block();

        BeanUtils.copyProperties(aionBlock, block);

        block.setBloom(String.valueOf(aionBlock.getBloom()));
        block.setExtraData(String.valueOf(aionBlock.getExtraData()));
        block.setSolution(String.valueOf(aionBlock.getSolution()));

        block.setHash(String.valueOf(aionBlock.getHash()));
        block.setParentHash(String.valueOf(aionBlock.getParentHash()));
        block.setMinerAddress(String.valueOf(aionBlock.getMinerAddress()));
        block.setStateRoot(String.valueOf(aionBlock.getStateRoot()));
        block.setTxTrieRoot(String.valueOf(aionBlock.getTxTrieRoot()));
        block.setSize(aionBlock.getSize());

        return block;
    }

    public static com.bloxbean.bloxql.model.TxDetails convert(TxDetails aionTxDetails) {
        com.bloxbean.bloxql.model.TxDetails tx = new com.bloxbean.bloxql.model.TxDetails();

        BeanUtils.copyProperties(aionTxDetails, tx);

        tx.setTo(String.valueOf(aionTxDetails.getTo()));
        tx.setFrom(String.valueOf(aionTxDetails.getFrom()));
        tx.setContract(String.valueOf(aionTxDetails.getContract()));

        tx.setTxHash(String.valueOf(aionTxDetails.getTxHash()));
        tx.setData(String.valueOf(aionTxDetails.getData()));

        return  tx;
    }

    public static com.bloxbean.bloxql.model.TxDetails convert(Transaction aionTx) {
        com.bloxbean.bloxql.model.TxDetails tx = new com.bloxbean.bloxql.model.TxDetails();

        BeanUtils.copyProperties(aionTx, tx);

        tx.setTo(String.valueOf(aionTx.getTo()));
        tx.setFrom(String.valueOf(aionTx.getFrom()));
        tx.setTxHash(String.valueOf(aionTx.getTxHash()));
        tx.setData(String.valueOf(aionTx.getData()));

        tx.setBlockNumber(aionTx.getBlockNumber());
        tx.setBlockHash(String.valueOf(aionTx.getBlockHash()));

        //Set timestamp manually as field names are different in DTO and original object
        tx.setTimestamp(aionTx.getTimeStamp());

        return  tx;
    }

    public static ProtocolInfo convert(Protocol aionProtocol) {
        ProtocolInfo protocolInfo = new ProtocolInfo();

        BeanUtils.copyProperties(aionProtocol, protocolInfo);

        return protocolInfo;
    }

    public static MsgRespBean convert(MsgRsp aMsgResp) {
        MsgRespBean resp = new MsgRespBean();

        resp.setError(aMsgResp.getError());
        resp.setMsgHash(String.valueOf(aMsgResp.getMsgHash()));
        resp.setStatus(String.valueOf(aMsgResp.getStatus()));
        resp.setTxDeploy(String.valueOf(aMsgResp.getTxDeploy()));
        resp.setTxHash(String.valueOf(aMsgResp.getTxHash()));
        resp.setTxResult(String.valueOf(aMsgResp.getTxResult()));

        return resp;
    }

    public static CompileResponseBean convert(CompileResponse aionResponse) {

            Gson gson = new Gson();
            String resJson = gson.toJson(aionResponse);

            CompileResponseBean bean = gson.fromJson(resJson, CompileResponseBean.class);

//TODO        bean.setUserDoc(String.valueOf(aionResponse.getUserDoc()));
//            bean.setDeveloperDoc(String.valueOf(aionResponse.getDeveloperDoc()));

            return bean;
    }

    public static DeployResponseBean convert(DeployResponse deployResponse) {
        DeployResponseBean drb = new DeployResponseBean();

        BeanUtils.copyProperties(deployResponse, drb);

        if(deployResponse.getAddress() != null)
            drb.setAddress(deployResponse.getAddress().toString());

        if(deployResponse.getTxid() != null)
            drb.setTxId(deployResponse.getTxid().toString());

        return drb;
    }

    public static TxArgs convert(TxArgsInput gqlInput) {
        TxArgs.TxArgsBuilder builder = new TxArgs.TxArgsBuilder();

        try {
            if (gqlInput.getData() != null) {
                if ("hex".equals(gqlInput.getEncoding()))
                    builder
                        .data(ByteArrayWrapper.wrap(ByteUtil.hexStringToBytes(gqlInput.getData())));
                else {
                    byte[] bytes;
                    if(gqlInput.getEncoding() != null)
                        bytes = gqlInput.getData().getBytes(gqlInput.getEncoding());
                    else
                        bytes = gqlInput.getData().getBytes();

                    builder.data(ByteArrayWrapper.wrap(bytes));
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("Unable to decode the string", e);
            throw new DataConversionException("Unable to convert the data with the specified encoding "
                + gqlInput.getEncoding());
        }

        builder.nrgPrice(gqlInput.getNrgPrice());
        builder.nrgLimit(gqlInput.getNrgLimit());
        builder.nonce(gqlInput.getNonce());
        builder.value(gqlInput.getValue());

        if(gqlInput.getFrom() != null)
            builder.from(AionAddress.wrap(gqlInput.getFrom()));

        if(gqlInput.getTo() != null)
            builder.to(AionAddress.wrap(gqlInput.getTo()));

        return builder.createTxArgs();
    }

    public static ContractEventFilter convert(ContractEventFilterBean gqlBean) {

        ContractEventFilter.ContractEventFilterBuilder builder = new ContractEventFilter.ContractEventFilterBuilder();

        builder.expireTime(gqlBean.getExpireTime());
        builder.fromBlock(gqlBean.getFromBlock());
        builder.toBlock(gqlBean.getToBlock());
        builder.topics(gqlBean.getTopics());

        if(gqlBean.getAddresses() != null) {
            builder.addresses(gqlBean.getAddresses()
                                    .stream()
                                    .map(as -> AionAddress.wrap(as))
                                    .collect(Collectors.toList()));
        }

        return builder.createContractEventFilter();
    }

    public static TxReceiptBean convert(TxReceipt aionTxnRecipt) {
        TxReceiptBean bean = new TxReceiptBean();

        if(aionTxnRecipt == null)
            return null;

        bean.setBlockHash(aionTxnRecipt.getBlockHash().toString());
        bean.setBlockNumber(aionTxnRecipt.getBlockNumber());

        if(aionTxnRecipt.getContractAddress() !=  null)
            bean.setContractAddress(aionTxnRecipt.getContractAddress().toString());

        bean.setCumulativeNrgUsed(aionTxnRecipt.getCumulativeNrgUsed());
        bean.setNrgConsumed(aionTxnRecipt.getNrgConsumed());

        if(aionTxnRecipt.getFrom() != null)
            bean.setFrom(aionTxnRecipt.getFrom().toString());
        if(aionTxnRecipt.getTo() != null)
            bean.setTo(aionTxnRecipt.getTo().toString());

        bean.setTxHash(aionTxnRecipt.getTxHash().toString());
        bean.setTxIndex(aionTxnRecipt.getTxIndex());

        if(aionTxnRecipt.getTxLogs() != null) {
            bean.setTxLogs(aionTxnRecipt.getTxLogs()
                    .stream()
                    .map(tl -> {
                        TxReceiptBean.TxLogBean txLogBean = new TxReceiptBean.TxLogBean();

                        txLogBean.setAddress(tl.getAddress().toString());
                        txLogBean.setData(tl.getData().toString());
                        txLogBean.setTopic(txLogBean.getTopic());

                        return txLogBean;
                    })
                    .collect(Collectors.toList()));
        }

        return bean;
    }

    public static ContractBean convert(IContract contract) {

        ContractBean bean = new ContractBean();

        bean.setContractName(contract.getContractName());

        if(contract.getContractAddress() != null)
            bean.setContractAddress(contract.getContractAddress().toString());

        if(contract.getFrom() != null)
            bean.setFrom(contract.getFrom().toString());

        if(contract.getDeployTxId() != null)
            bean.setDeployTxId(contract.getDeployTxId().toString());

       //if(contract.getEncodedData() != null)
       //     bean.setEncodedData(contract.getEncodedData().toString()); //TODO

        bean.setAbiDefToString(contract.getAbiDefToString());
        bean.setSource(contract.getSource());
        bean.setCode(contract.getCode());
        bean.setCompilerOptions(contract.getCompilerOptions());
        bean.setCompilerVersion(contract.getCompilerVersion());

        if(contract.getDeveloperDoc() != null)
            bean.setDeveloperDoc(contract.getDeveloperDoc().toString());

        bean.setLanguageVersion(contract.getLanguageVersion());

        if(contract.getUserDoc() != null)
            bean.setUserDoc(contract.getUserDoc().toString());

        //set Abi info
        bean.setAbiDefinition(
                contract.getAbiDefinition()
                .stream()
                .map(cabi -> {
                    ContractAbiEntryBean abiEntryBean = new ContractAbiEntryBean();
                    abiEntryBean.setAnonymous(cabi.anonymous);
                    abiEntryBean.setConstant(cabi.constant);
                    abiEntryBean.setName(cabi.name);
                    abiEntryBean.setPayable(cabi.payable);
                    abiEntryBean.setType(cabi.type);

                    if(cabi.inputs != null) {
                        abiEntryBean.setInputs(
                            cabi.inputs
                                    .stream()
                                    .map(in -> {
                                        ContractAbiIOParamBean ioBean = new ContractAbiIOParamBean(
                                                in.getName(),
                                                in.getParamLengths(),
                                                in.getType(),
                                                in.isIndexed()
                                        );

                                        return ioBean;
                                    }).collect(Collectors.toList())
                        );
                    }

                    if(cabi.outputs != null) {
                        abiEntryBean.setOutputs(
                                cabi.outputs
                                        .stream()
                                        .map(out -> {
                                            ContractAbiIOParamBean ioBean = new ContractAbiIOParamBean(
                                                    out.getName(),
                                                    out.getParamLengths(),
                                                    out.getType(),
                                                    out.isIndexed()
                                            );

                                            return ioBean;
                                        }).collect(Collectors.toList())
                        );
                    }

                    abiEntryBean.setEvent(cabi.isEvent());
                    abiEntryBean.setConstructor(cabi.isConstructor());
                    abiEntryBean.setHashed(cabi.getHashed());
                    abiEntryBean.setFallback(cabi.isFallback());

                    return abiEntryBean;
                }).collect(Collectors.toList())
        );

        return bean;
    }

    public static ContractResponseBean convert(ContractResponse res) {
        ContractResponseBean bean = new ContractResponseBean();

        bean.setConstant(res.isConstant());

//        if(res.getData() != null)
//            bean.setData(res.getData()
//                            .stream()
//                            .map(o -> String.valueOf(o))
//                            .collect(Collectors.toList()));

        bean.setError(res.getError());

        if(res.getMsgHash() != null)
            bean.setMsgHash(res.getMsgHash().toString());

        if(res.getTxHash() != null)
            bean.setTxHash(res.getTxHash().toString());

        bean.setStatus(res.getStatus());

        return bean;
    }


    public static List convert(List<Node> nodes) {

        if(nodes == null)
            return Collections.EMPTY_LIST;

        return nodes.stream()
                .map(node -> {
                            NodeInfo nodeInfo = new NodeInfo();
                            nodeInfo.setBlockNumber(node.getBlockNumber());
                            nodeInfo.setLatency(node.getLatency());
                            nodeInfo.setNodeId(node.getNodeId());
                            nodeInfo.setP2pId(node.getP2pIP());
                            nodeInfo.setP2pPort(node.getP2pPort());

                            return nodeInfo;
                        }
                )
                .collect(Collectors.toList());
    }

    public static ContractEventBean convert(ContractEvent contractEvent) {
        ContractEventBean bean = new ContractEventBean();

        if(contractEvent.getAddress() != null)
            bean.setAddress(contractEvent.getAddress().toString());

        if(contractEvent.getBlockHash() != null)
            bean.setBlockHash(contractEvent.getBlockHash().toString());

        bean.setBlockNumber(contractEvent.getBlockNumber());

        if(contractEvent.getData() != null) //TODO
            bean.setData(String.valueOf(contractEvent.getData()));

        bean.setEventName(contractEvent.getEventName());
        bean.setLogIndex(contractEvent.getLogIndex());
        bean.setRemoved(contractEvent.isRemoved());

        if(bean.getTxHash() != null)
            bean.setTxHash(contractEvent.getTxHash().toString());
        bean.setTxIndex(contractEvent.getTxIndex());

        bean.setResults(contractEvent.getResults());

        return bean;

    }
}

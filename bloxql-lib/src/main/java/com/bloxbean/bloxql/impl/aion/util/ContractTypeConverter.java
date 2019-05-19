package com.bloxbean.bloxql.impl.aion.util;

import com.bloxbean.bloxql.exception.DataConversionException;
import com.bloxbean.bloxql.model.ContractResponseBean;
import com.bloxbean.bloxql.model.Output;
import com.bloxbean.bloxql.model.input.ContractFunction;
import com.bloxbean.bloxql.model.input.Param;
import org.aion.api.IContract;
import org.aion.api.IUtils;
import org.aion.api.sol.*;
import org.aion.api.type.ContractResponse;
import org.aion.base.type.AionAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class ContractTypeConverter {

    private static Logger logger = LoggerFactory.getLogger(ContractTypeConverter.class);

    public static void populateInputParams(ContractFunction contractFunction, IContract contract) {

        if(contractFunction.getParams() == null)
            return;

        //set params
        for(Param param: contractFunction.getParams()) {
            ISolidityArg solValue = convertParamToSolidityType(param);
            contract.setParam(solValue);
        }
    }

    public static List<ISolidityArg> convertParamsToSolValues(List<Param> params) {

        if(params == null || params.size() == 0)
            return Collections.EMPTY_LIST;

        return params.stream()
                .map(param -> convertParamToSolidityType(param))
                .collect(Collectors.toList());
    }

    public static ISolidityArg convertParamToSolidityType(Param param) {
        boolean isArray = false;

        if(param.getValues() != null && param.getValues().size() > 0)
            isArray = true;

        ISolidityArg solValue = null;

        if(param.getType().startsWith("address")) {

            if(isArray)
                solValue = IAddress.copyFrom(TypeUtil.toStringList(param.getValues()));
            else
                solValue = IAddress.copyFrom(String.valueOf(param.getValue()));

        } else if(param.getType().startsWith("bool")) {

            if(isArray)
                solValue = IBool.copyFrom(TypeUtil.toBooleanList(param.getValues()));
            else
                solValue = IBool.copyFrom(TypeUtil.toBoolean(param.getValue()));

        } else if(param.getType().startsWith("bytes")) {

            if(isArray)
                solValue = IBytes.copyFrom(TypeUtil.toBytesList(param.getValues(), param.getEncoding()));
            else
                solValue = IBytes.copyFrom(TypeUtil.toBytes(String.valueOf(param.getValue()), param.getEncoding()));

        } else if(param.getType().startsWith("int")) {

            if(isArray)
                solValue = TypeUtil.toIInt(param.getValues());
            else
                solValue = TypeUtil.toIInt(param.getValue());

        } else if(param.getType().startsWith("uint")) {

            if(isArray)
                solValue = TypeUtil.toIUint(param.getValues());
            else
                solValue = TypeUtil.toIUint(param.getValue());

        } else if(param.getType().startsWith("string")) {
            solValue = ISString.copyFrom(String.valueOf(param.getValue()));
        } else
            throw new DataConversionException("Unable to convert input parameters : " + param);

        return solValue;
    }

    public static void populateOutputs(ContractFunction contractFunction,
                                       ContractResponse contractResponse, ContractResponseBean responseBean) {

        List<Output> outputParams = contractFunction.getOutputs();

        if(outputParams == null) {
            if(logger.isDebugEnabled()) {
                logger.debug("No output param defined. Please define output attribute in graphql query");
            }
            return;
        }

        List<Object> rawResData = contractResponse.getData();
        List<Object> resultData = convertSolidityObjectToJavaObject(outputParams, rawResData);

        responseBean.setData(resultData);

    }

    public static List<Object> convertSolidityObjectToJavaObject(List<Output> outputParams, List<Object> rawResData) {
        List<Object> resultData = new ArrayList<>();

        if(logger.isDebugEnabled())
            logger.debug("** Print output **");

        for(int i = 0; i< rawResData.size(); i++) {

            Object outputData = rawResData.get(i);

            if(logger.isDebugEnabled())
                logger.debug(outputData.getClass().toString());

            if(outputParams.size() <= i) {
                //no coversion
                resultData.add(outputData);
                continue;
            }

            Output outputParam = outputParams.get(i);

            if(outputData instanceof Collection) {
                Collection<Object> dataColl = ((Collection)outputData);

                Collection dataList =  dataColl.stream()
                        .map(obj -> convertOutput(outputParam, obj))
                        .collect(Collectors.toList());

                resultData.add(dataList);
            } else if(outputData instanceof Map) {

                Map rMap = new HashMap();

                ((Map)(outputData))
                        .forEach( (k,v) -> {
                             rMap.put(convertOutput(outputParam, k), convertOutput(outputParam, v));
                        } );

                resultData.add(rMap);

            } else {
                Object value = convertOutput(outputParam, outputData);
                resultData.add(value);
            }
        }
        return resultData;
    }

    public static Object convertOutput(Output outputParam, Object outputData) {
//
//        boolean isArray = outputParam.isArray();

        if(outputData !=  null) {
            if (logger.isDebugEnabled())
                logger.debug("Output type : " + outputData.getClass());
        } else
            return null;
//
        if(outputParam.getType().startsWith("address")) {
            return AionAddress.wrap((byte [])outputData);

        } else if(outputParam.getType().startsWith("bool")) {

            return (Boolean)outputData;

        } else if(outputParam.getType().startsWith("bytes")) {

            return byteToString((byte[])outputData, outputParam.getEncoding());

        } else if(outputParam.getType().startsWith("int")) {

            //No need to convert
            return outputData;//(long)outputData;

        } else if(outputParam.getType().startsWith("uint")) {
            //no conversion required
            return outputData;
            //return (long)outputData;

        } else if(outputParam.getType().startsWith("string")) {

            return String.valueOf(outputData);

        } else {
            return outputData;
        }

    }

    public static String byteToString(byte[] bytes, String enc) {
        if(bytes == null)
            return null;

        if(StringUtils.isEmpty(enc)) {
            return new String(bytes);
        } if(Output.HEX_TYPE.equalsIgnoreCase(enc)) {
            return IUtils.bytes2Hex(bytes);
        } else if(Output.BASE64_TYPE.equalsIgnoreCase(enc)) {
            return Base64.getEncoder().encodeToString(bytes);
        } else {
            try {
                return new String(bytes, enc);
            } catch (Exception e) {
                logger.error("Unable to encode {} from byte to string with encoding {}", bytes, enc);
                throw  new DataConversionException("Unable to encode {} from byte to string with encoding : " + enc );
//                return new String(bytes); //try to encode with default utf-8
            }
        }
    }

}

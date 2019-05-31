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

package com.bloxbean.blox4j.impl.aion.util;

import org.aion.solidity.Abi.Entry.Param;
import org.aion.solidity.Abi.Function;
import org.aion.solidity.SolidityType;
import com.bloxbean.blox4j.model.input.ContractFunction;

import java.util.List;

public class AbiUtil {

    public static boolean isSameFunction(Function function, ContractFunction inputFunction) {

        String fnName = function.name;

        if(inputFunction.getName() == null || !inputFunction.getName().equals(fnName)) {
            return false;
        }

        List<Param> abiParams = function.inputs;

        List<com.bloxbean.blox4j.model.input.Param> inputParams = inputFunction.getParams();

        if(abiParams.size() != inputParams.size())
            return false;

        if(abiParams.size() == 0) {
            if(inputParams.size() == 0)
                return true;
            else
                return false;
        }

        for(int i=0; i<abiParams.size(); i++) {

            SolidityType abiType = abiParams.get(i).type;

            SolidityType inputType = SolidityType.getType(inputParams.get(i).getType());

            if(!abiType.getName().equals(inputType.getName())) {
                return false;
            }
        }

        return true;

    }

}

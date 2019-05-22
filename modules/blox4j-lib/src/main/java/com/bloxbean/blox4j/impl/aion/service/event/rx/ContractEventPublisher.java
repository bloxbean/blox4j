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

package com.bloxbean.blox4j.impl.aion.service.event.rx;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.observables.ConnectableObservable;
import org.aion.api.IContract;
import org.aion.api.type.ContractEvent;
import com.bloxbean.blox4j.impl.aion.pool.AionConnection;
import com.bloxbean.blox4j.impl.aion.util.ContractTypeConverter;
import com.bloxbean.blox4j.impl.aion.util.ModelConverter;
import com.bloxbean.blox4j.model.ContractEventBean;
import com.bloxbean.blox4j.model.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ContractEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(ContractEventPublisher.class);

    private final long POLL_TIME_IN_MILLIS = 10;

    private boolean destroyed;

    private final Flowable<ContractEventBean> publisher;
    private ScheduledExecutorService executorService;

    private AionConnection connection;
    private IContract contract;
    private List<Output> outputTypes;

    public ContractEventPublisher(AionConnection connection, IContract contract, List<Output> outputTypes) {

        this.connection = connection;
        this.contract = contract;
        this.outputTypes = outputTypes;

        Observable<ContractEventBean> contractEventObservable = Observable.create(emitter -> {

            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            executorService.scheduleAtFixedRate(newContractEvent(emitter), 0, POLL_TIME_IN_MILLIS, TimeUnit.SECONDS);

        });

        ConnectableObservable<ContractEventBean> connectableObservable = contractEventObservable.share().publish();
        connectableObservable.connect();

        publisher = connectableObservable.toFlowable(BackpressureStrategy.BUFFER);
    }


    private Runnable newContractEvent(ObservableEmitter<ContractEventBean> emitter) {
        return () -> {
            List<ContractEvent> contractEvents = contract.getEvents();

            if(logger.isDebugEnabled()) {
                if (contractEvents != null)
                    logger.debug("Emitting events : size {}" + contractEvents.size());
                else
                    logger.debug("No events to emit");
            }

            if (contractEvents != null && contractEvents.size() != 0) {

                List<ContractEventBean> beans = contractEvents.stream()
                        .map(cb -> {
                            ContractEventBean ceb = ModelConverter.convert(cb);

                            List<Object> convertedOutputData = ContractTypeConverter.convertSolidityObjectToJavaObject(outputTypes, cb.getResults());
                            ceb.setResults(convertedOutputData);

                            return ceb;
                        })
                        .collect(Collectors.toList());

                emitEvents(emitter, beans);
            }
        };
    }

    private void emitEvents(ObservableEmitter<ContractEventBean> emitter, List<ContractEventBean> stockPriceUpdates) {
        for (ContractEventBean contractEventBean : stockPriceUpdates) {
            try {
                emitter.onNext(contractEventBean);
            } catch (RuntimeException e) {
                logger.error("Cannot send contract event", e);
            }
        }
    }

    public Flowable<ContractEventBean> getPublisher() {
        return publisher;
    }

    public void destroy() {

        destroyed = true;

        try {
            if (connection != null) {
                connection.destroy();
            }
        } catch (Exception e) {
            logger.error("error", e);
        }

        try {
            if (executorService != null) {
                executorService.shutdownNow();
            }
        } catch (Exception e) {
            logger.error("error", e);
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

}


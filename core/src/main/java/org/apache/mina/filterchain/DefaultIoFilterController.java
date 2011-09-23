/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.apache.mina.filterchain;

import org.apache.mina.api.IoFilter;
import org.apache.mina.api.IoService;
import org.apache.mina.api.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultIoFilterController implements IoFilterController, ReadFilterChainController,
        WriteFilterChainController {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultIoFilterController.class);

    /**
     * The list of {@link IoFilter} implementing this chain.
     */
    private final IoFilter[] chain;

    /**
     * The instance of {@link DefaultIoFilterController} with the {@link IoService} chain.
     */
    public DefaultIoFilterController(IoFilter[] chain) {
        if (chain == null) {
            throw new IllegalArgumentException("chain");
        }
        this.chain = chain;
    }

    @Override
    public void processSessionCreated(IoSession session) {
        LOG.debug("processing session created event");
        for (IoFilter filter : chain) {
            filter.sessionCreated(session);
        }
    }

    @Override
    public void processSessionOpened(IoSession session) {
        LOG.debug("processing session open event");
        for (IoFilter filter : chain) {
            filter.sessionOpened(session);
        }
    }

    @Override
    public void processSessionClosed(IoSession session) {
        LOG.debug("processing session closed event");
        for (IoFilter filter : chain) {
            filter.sessionClosed(session);
        }
    }

    private static final ThreadLocal<Integer> readChainPosition = new ThreadLocal<Integer>();

    @Override
    public void processMessageReceived(IoSession session, Object message) {
        LOG.debug("processing message '{}' received event ", message);
        if (chain.length < 1) {
            LOG.debug("Nothing to do, the chain is empty");
        } else {
            readChainPosition.set(0);
            // we call the first filter, it's supposed to call the next ones using the filter chain controller
            chain[readChainPosition.get()].messageReceived(session, message, this);
        }
    }

    private static final ThreadLocal<Integer> writeChainPosition = new ThreadLocal<Integer>();

    @Override
    public void processMessageWriting(IoSession session, Object message) {
        LOG.debug("processing message '{}' writing event ", message);
        if (chain.length < 1) {
            LOG.debug("Nothing to do, the chain is empty, we just enqueue the message");
            session.enqueueWriteRequest(message);
        } else {

            writeChainPosition.set(chain.length - 1);
            // we call the first filter, it's supposed to call the next ones using the filter chain controller
            chain[writeChainPosition.get()].messageWriting(session, message, this);
        }
    }

    @Override
    public void callWriteNextFilter(IoSession session, Object message) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("calling next filter for writing for message '{}' position : {}", message,
                    writeChainPosition.get());
        }
        writeChainPosition.set(writeChainPosition.get() - 1);
        if (writeChainPosition.get() < 0 || chain.length == 0) {
            // end of chain processing
            LOG.debug("end of write chan we enqueue the message in the session : {}", message);
            session.enqueueWriteRequest(message);
        } else {
            chain[writeChainPosition.get()].messageWriting(session, message, this);
        }
        writeChainPosition.set(writeChainPosition.get() + 1);
    }

    @Override
    public void callReadNextFilter(IoSession session, Object message) {
        readChainPosition.set(readChainPosition.get() + 1);
        if (readChainPosition.get() >= chain.length) {
            // end of chain processing
        } else {
            chain[readChainPosition.get()].messageReceived(session, message, this);
        }
        readChainPosition.set(readChainPosition.get() - 1);
    }

    @Override
    public String toString() {
        StringBuilder bldr = new StringBuilder("IoFilterChain {");
        int index = 0;
        for (IoFilter filter : chain) {
            bldr.append(index).append(":").append(filter).append(", ");
        }
        return bldr.append("}").toString();
    }
}
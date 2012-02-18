/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.mina.transport.tcp;

import org.apache.mina.session.AbstractIoSessionConfig;

/**
 * Implementation for the socket session configuration.
 * 
 * Will hold the values for the service in change of configuring this session (before the session opening).
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class DefaultSocketSessionConfig extends AbstractIoSessionConfig implements SocketSessionConfig {

    //=====================
    // buffers
    //=====================

    private Integer receiveBufferSize = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getReceiveBufferSize() {
        return receiveBufferSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setReceiveBufferSize(int receiveBufferSize) {
        this.receiveBufferSize = receiveBufferSize;
    }

    private Integer sendBufferSize = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getSendBufferSize() {
        return sendBufferSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSendBufferSize(int sendBufferSize) {
        this.sendBufferSize = sendBufferSize;
    }

    //=====================
    // socket options
    //=====================

    private Boolean tcpNoDelay = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    private Boolean reuseAddress = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isReuseAddress() {
        return reuseAddress;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setReuseAddress(boolean reuseAddress) {
        this.reuseAddress = reuseAddress;
    }

    private Integer trafficClass;

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getTrafficClass() {
        return trafficClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTrafficClass(int trafficClass) {
        this.trafficClass = trafficClass;
    }

    private Boolean keepAlive = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isKeepAlive() {
        return keepAlive;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    private Boolean oobInline = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isOobInline() {
        return oobInline;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOobInline(boolean oobInline) {
        this.oobInline = oobInline;

    }

    private Integer soLinger;

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getSoLinger() {
        return soLinger;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSoLinger(int soLinger) {
        this.soLinger = soLinger;
    }
}
/*
 * Copyright 2021 berni3.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.huberb.socketconnectivity;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Simply try to connect to a server socket.
 *
 * @author berni3
 */
public class SocketConnector {

    /**
     * Socket connection result wrapper.
     */
    static class ConnectionResult {

        private String host;
        private int port;
        private int connectionTimeout;

        private boolean success;
        private Throwable t;

        @Override
        public String toString() {
            return String.format("host %s, port %d, connectionTimout %d"
                    + " success %s, throwable %s",
                    this.host,
                    this.port,
                    this.connectionTimeout,
                    this.success,
                    this.t);
        }

        boolean isSuccess() {
            return success;
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }

        public int getConnectionTimeout() {
            return connectionTimeout;
        }

        public Throwable getT() {
            return t;
        }

    }

    /**
     * Try to connect to given host, and port.
     *
     * @param host
     * @param port
     * @return
     */
    ConnectionResult connectTo(String host, int port) {
        final int connectionTimeout = 1000;
        final InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
        final ConnectionResult connectionResult = connectTo(inetSocketAddress, connectionTimeout);
        return connectionResult;
    }

    /**
     * Try to connect to given connectionResult.
     *
     * @param connectionResult
     * @return
     */
    ConnectionResult connectTo(InetSocketAddress inetSocketAddress, int connectionTimeout) {
        final ConnectionResult connectionResult = new ConnectionResult();
        connectionResult.host = inetSocketAddress.getHostString();
        connectionResult.port = inetSocketAddress.getPort();
        connectionResult.connectionTimeout = connectionTimeout;
        try (Socket s = new Socket()) {
            s.connect(inetSocketAddress, connectionTimeout);
            connectionResult.success = true;
        } catch (java.net.ConnectException conex) {
            connectionResult.success = false;
            connectionResult.t = conex;
        } catch (IOException ioex) {
            connectionResult.success = false;
            connectionResult.t = ioex;
        }
        return connectionResult;
    }
}

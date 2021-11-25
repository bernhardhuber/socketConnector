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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.huberb.socketconnectivity.SocketConnector.ConnectionResult;
import org.junit.Assume;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class SocketConnectorTest {

    int serverSocketPort = 65401;
    ServerSocketRunnable serverSocketThread;
    CountDownLatch serverSocketCreatedLatch;

    @BeforeEach
    public void setUp() throws InterruptedException {
        serverSocketCreatedLatch = new CountDownLatch(1);
        serverSocketThread = new ServerSocketRunnable(serverSocketPort, serverSocketCreatedLatch);
        new Thread(serverSocketThread).start();
    }

    @AfterEach
    public void tearDown() throws IOException {
    }

    /**
     * Test of connectTo method, of class SocketConnector.
     */
    @Test
    public void testConnectTo_localhost_65401() throws InterruptedException {
        Assume.assumeTrue(serverSocketCreatedLatch.await(250, TimeUnit.MILLISECONDS));
        final String host = "localhost";
        final int port = serverSocketPort;
        final SocketConnector instance = new SocketConnector();
        final ConnectionResult result = instance.connectTo(host, port);
        final String m = "" + result;
        assertTrue(result.isSuccess(), m);
    }

    /**
     * Test of connectTo method, of class SocketConnector.
     */
    @Test
    public void testConnectTo_localhost_65402() throws InterruptedException {
        Assume.assumeTrue(serverSocketCreatedLatch.await(250, TimeUnit.MILLISECONDS));
        final String host = "localhost";
        final int port = serverSocketPort + 1;
        final SocketConnector instance = new SocketConnector();
        final ConnectionResult result = instance.connectTo(host, port);
        final String m = "" + result;
        assertFalse(result.isSuccess(), m);
    }

    public static class ServerSocketRunnable implements Runnable {

        private final int serverSocketPort;
        private final CountDownLatch serverSocketCreatedLatch;

        private ServerSocketRunnable(int serverSocketPort, CountDownLatch serverSocketCreatedLatch) {
            this.serverSocketPort = serverSocketPort;
            this.serverSocketCreatedLatch = serverSocketCreatedLatch;
        }

        @Override
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(serverSocketPort)) {
                serverSocketCreatedLatch.countDown();
                final Socket connectedSocket = serverSocket.accept();

                connectedSocket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}

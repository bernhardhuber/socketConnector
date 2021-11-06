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
import org.huberb.socketconnectivity.SocketConnector.ConnectionResult;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class H2SocketConnectionTest {

    int serverSocketPort = 65401;
    ServerSocketRunnable serverSocketThread;

    @BeforeEach
    public void setUp() throws InterruptedException {
        serverSocketThread = new ServerSocketRunnable(serverSocketPort);
        new Thread(serverSocketThread).start();
        Thread.sleep(500);
    }

    @AfterEach
    public void tearDown() throws IOException {
    }

    /**
     * Test of connectTo method, of class SocketConnector.
     */
    @Test
    public void testConnectTo_localhost_65401() {
        String host = "localhost";
        int port = 65401;
        SocketConnector instance = new SocketConnector();
        ConnectionResult result = instance.connectTo(host, port);
        String m = "" + result;
        assertTrue(result.isSuccess(), m);
    }

    /**
     * Test of connectTo method, of class SocketConnector.
     */
    @Test
    public void testConnectTo_localhost_65402() {
        String host = "localhost";
        int port = 65402;
        SocketConnector instance = new SocketConnector();
        ConnectionResult result = instance.connectTo(host, port);
        String m = "" + result;
        assertFalse(result.isSuccess(), m);
    }

    public static class ServerSocketRunnable implements Runnable {

        final int serverSocketPort;

        private ServerSocketRunnable(int serverSocketPort) {
            this.serverSocketPort = serverSocketPort;
        }

        @Override
        public void run() {
            ServerSocket serverSocket;
            try {
                serverSocket = new ServerSocket(serverSocketPort);
                Socket connectedSocket = serverSocket.accept();
                connectedSocket.close();
                serverSocket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}

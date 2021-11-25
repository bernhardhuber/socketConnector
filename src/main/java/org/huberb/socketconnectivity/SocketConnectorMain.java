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

import java.util.concurrent.Callable;
import org.huberb.socketconnectivity.SocketConnector.ConnectionResult;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 *
 * @author berni3
 */
@Command(name = "socketConnectorMain",
        mixinStandardHelpOptions = true,
        version = "socketConnectorMain 1.0-SNAPSHOT",
        description = "test socket connectivity")
public class SocketConnectorMain implements Callable<Integer> {

    @Option(names = {"--host"},
            defaultValue = "localhost",
            description = "connect to this host")
    private String host;

    @Option(names = {"--port"},
            required = true,
            description = "connect to this port")
    private int port;
    @Option(names = {"--connection-timeout"},
            required = false,
            defaultValue = "1000",
            description = "connect connection timeout, default value: ${DEFAULT-VALUE}")
    private int connectionTimeout;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new SocketConnectorMain()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        final int rc;
        final SocketConnector testSocketConnection = new SocketConnector();
        final ConnectionResult connectionResult = testSocketConnection.connectTo(host, port, connectionTimeout);
        System_out_printf("connection result %s%n", connectionResult);
        rc = connectionResult.isSuccess() ? 0 : 1;
        return rc;
    }

    private void System_out_printf(String fmt, Object... args) {
        System.out.printf(fmt, args);
    }
}

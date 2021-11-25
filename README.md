# SocketConnector
 A simple application testing socket connectivity.
You can use this to test if at a given port a socket is listening.

This application is quite simple, if need more features try to
use telnet, or curl with telnet protocol.

## Build Status 

[![Java CI with Maven](https://github.com/bernhardhuber/socketConnector/actions/workflows/maven.yml/badge.svg)](https://github.com/bernhardhuber/socketConnector/actions/workflows/maven.yml)

## Usage

The commandline options of this application:

```
$ java -jar ./target/socketconnector-1.0-SNAPSHOT-main.jar -h
Usage: socketConnectorMain [-hV] [--connection-timeout=<connectionTimeout>]
                           [--host=<host>] --port=<port>
test socket connectivity
      --connection-timeout=<connectionTimeout>
                      connect connection timeout, default value: 1000
  -h, --help          Show this help message and exit.
      --host=<host>   connect to this host
      --port=<port>   connect to this port
  -V, --version       Print version information and exit.
```

On Unix-like systems you may use `./target/socketconnector-1.0-SNAPSHOT-executable` instead 
of `java -jar ./target/socketconnector-1.0-SNAPSHOT-main.jar` for launching
the application.

In this case there should by `java` available in the `PATH` environment.

## Example A

Running against an listening server-port at localhost:9093

```
$ java -jar ./target/socketconnector-1.0-SNAPSHOT-main.jar --host=localhost --port=9093
connection result host localhost, port 9093, connectionTimout 1000 success true, throwable null
```
## Example B

Running against an non-listening server-port at localhost:9094

```
$ java -jar ./target/socketconnector-1.0-SNAPSHOT-main.jar --host=localhost --port=9094
connection result host localhost, port 9094, connectionTimout 1000 success false, throwable java.net.SocketTimeoutException: connect timed out
```


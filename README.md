# SocketConnector
 A simple application testing socket connectivity.
You can use this to test if at a given port a socket is listening.

This application is quite simple, if need more features try to
use telnet, or curl with telnet protocol.

## Usage

The commandline options of this application:

```
$ java -jar ./target/socketconnector-1.0-SNAPSHOT-main.jar -h
Usage: socketConnectorMain [-hV] [--host=<host>] --port=<port>
test socket connectivity
  -h, --help          Show this help message and exit.
      --host=<host>   connect to this host
      --port=<port>   connect to this port
  -V, --version       Print version information and exit.
```

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


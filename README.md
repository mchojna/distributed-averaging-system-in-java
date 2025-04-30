# Distributed Averaging System in Java

This project implements a distributed system in Java that calculates the average of numbers sent by multiple clients. The system uses UDP for communication between clients and a server, and it supports broadcasting messages and acknowledgment mechanisms.

## Features

- **Client-Server Architecture**: The system consists of a server that receives messages from clients and calculates averages.
- **UDP Communication**: Clients and the server communicate using UDP sockets.
- **Message Serialization**: Messages are serialized into byte arrays for efficient transmission.
- **Acknowledgment Mechanism**: Clients wait for acknowledgment from the server to ensure message delivery.
- **Broadcasting**: The server can broadcast messages to all clients.
- **Logging**: The system includes a logging mechanism to track events during execution.


### Key Classes

1. **`DAS`**: The main class that initializes the server or client based on the input arguments.
2. **`Server`**: Listens for incoming messages, calculates averages, and broadcasts results.
3. **`Client`**: Sends numbers to the server and waits for acknowledgment.
4. **`Message`**: Handles message serialization and deserialization.
5. **`Accumulator`**: Stores received numbers and calculates their average.
6. **`Logger`**: Provides optional logging for debugging and monitoring.

## How It Works

1. **Server Initialization**:
   - The server starts on a specified port and waits for incoming messages.
   - It stores received numbers and calculates their average when requested.

2. **Client Interaction**:
   - Clients send numbers to the server using UDP.
   - Clients wait for acknowledgment from the server to confirm message delivery.
   - If acknowledgment is not received, the client retries up to three times.

3. **Broadcasting**:
   - The server can broadcast messages (e.g., the calculated average) to all clients.

## Usage

### Prerequisites

- Java Development Kit (JDK) 8 or higher.

### Compilation

To compile the project, navigate to the project directory and run:

    ```bash
    javac src/*.java
    ```

### Running the Server

To start the server, run:

    ```bash
    java src.DAS <PORT> <NUMBER>
    ```

- \<PORT\>: The port number the server will listen on.
- \<NUMBER\>: The initial number to include in the average calculation.

### Running the Client

To start a client, run:

- \<PORT\>: The port number of the server to connect to.
- \<NUMBER\>: The number to send to the server.

### Example

Start the server:

    ```bash
    java src.DAS 12345 10
    ```

Start a client:

    ```bash
    java src.DAS 12345 20
    ```

Start another client:

    ```bash
    java src.DAS 12345 30
    ```

The server will calculate the average of the numbers (10, 20, 30) and broadcast the result.

### Logging

To enable logging, set the Logger.state variable to true in the DAS class:

    ```java
    Logger.state = true;
    ```

### Limitations

- The system assumes all messages are sent and received within the same local network.
- The maximum message size is limited to 11 bytes.

### Future Improvements

- Add support for handling larger messages.
- Implement a more robust error-handling mechanism.
- Extend the system to support multiple servers for scalability.

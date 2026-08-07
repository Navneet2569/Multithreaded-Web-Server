# Multithreaded Web Server in Java

A Java-based TCP server project demonstrating three different approaches to handling client connections:

- Single-Threaded Server
- Multithreaded Server
- Thread Pool Server using `ExecutorService`

The project compares these implementations under the same workload using Apache JMeter to understand the impact of different concurrency models on server performance.

---

## Server Implementations

### 1. Single-Threaded Server

The Single-Threaded implementation processes client connections sequentially.

A connection is accepted, processed, and completed before the server accepts the next connection.

```text
Client 1 ──► Server ──► Process ──► Response
                                      │
Client 2 ─────────────────────────────┘
                                      │
Client 3 ─────────────────────────────┘
```

### Characteristics

- Sequential request processing
- One request handled at a time
- Simple implementation
- Minimal concurrency overhead
- Useful for understanding TCP socket programming
- Limited performance under concurrent workloads

---

## 2. Multithreaded Server

The Multithreaded implementation creates a new Java thread for each incoming client connection.

This allows multiple client connections to be processed concurrently.

```text
                         ┌──► Thread 1 ──► Client 1
                         │
Server ──► accept() ─────┼──► Thread 2 ──► Client 2
                         │
                         └──► Thread 3 ──► Client 3
```

### Characteristics

- One thread per client connection
- Concurrent request processing
- Improved response time
- Increased throughput
- Higher thread creation overhead
- Increased resource consumption as concurrency grows

---

## 3. Thread Pool Server

The Thread Pool implementation uses Java's `ExecutorService` to manage a reusable pool of worker threads.

Instead of creating a new thread for every incoming connection, client-handling tasks are submitted to the thread pool.

```text
                         ┌──► Worker 1 ──► Request
                         │
Incoming Requests ─────► ExecutorService
                         │
                         ├──► Worker 2 ──► Request
                         │
                         └──► Worker 3 ──► Request
```

### Characteristics

- Reusable worker threads
- Controlled concurrency
- Reduced thread creation overhead
- Better thread management
- Improved throughput
- Lower average response time in the benchmark
- Better behavior under high concurrent workloads

---

# Thread Management Comparison

| Implementation  | Thread Management              |
| --------------- | ------------------------------ |
| Single-Threaded | Single execution path          |
| Multithreaded   | New thread for each connection |
| Thread Pool     | Reusable worker threads        |

The main difference between the Multithreaded and Thread Pool implementations is how worker threads are managed.

The Multithreaded implementation continuously creates new threads, while the Thread Pool implementation reuses a controlled number of worker threads.

---

# Client-Server Communication

The project also contains a simple terminal-based client-server communication implementation using Java TCP sockets.

The client and server communicate through the terminal.

```text
┌──────────────┐                  ┌──────────────┐
│    Client    │                  │    Server    │
│              │                  │              │
│   Terminal   │◄────── TCP ─────►│   Terminal   │
│              │                  │              │
└──────────────┘                  └──────────────┘
```

This implementation demonstrates the fundamentals of:

- TCP communication
- `Socket`
- `ServerSocket`
- Input streams
- Output streams
- Client-server architecture

---

# Performance Testing

All three server implementations were tested using **Apache JMeter** under the same general testing conditions.

The objective was to compare:

- Average response time
- Maximum response time
- Throughput
- Error percentage

## Test Configuration

| Parameter     | Value                            |
| ------------- | -------------------------------- |
| Testing Tool  | Apache JMeter                    |
| Total Samples | 600,000                          |
| Protocol      | TCP                              |
| Environment   | Local Machine                    |
| JVM           | Same JVM                         |
| Hardware      | Same Machine                     |
| Workload      | Identical across implementations |

> The benchmark results are specific to the local test environment and workload. They are intended to compare the relative behavior of the three implementations rather than represent production capacity.

---

# Performance Results

The following results were obtained after testing each implementation with **600,000 samples**.

| Metric                | Single-Threaded | Multithreaded |       Thread Pool |
| --------------------- | --------------: | ------------: | ----------------: |
| Samples               |         600,000 |       600,000 |           600,000 |
| Average Response Time |          218 ms |         45 ms |         **34 ms** |
| Maximum Response Time |        2,869 ms |        769 ms |            840 ms |
| Throughput            |   2,536 req/sec | 3,422 req/sec | **3,916 req/sec** |
| Error Percentage      |          42.72% |        19.25% |         **1.76%** |

---

# Response Time

```text
Single-Threaded    218 ms
Multithreaded       45 ms
Thread Pool         34 ms
```

The Thread Pool implementation produced the lowest average response time in the benchmark.

Compared with the Single-Threaded implementation:

**Average response time was reduced by approximately 84.4%.**

---

# Throughput

```text
Single-Threaded    2,536 req/sec
Multithreaded      3,422 req/sec
Thread Pool        3,916 req/sec
```

The Thread Pool implementation achieved the highest throughput.

Compared with the Single-Threaded implementation:

**Throughput increased by approximately 54.4%.**

---

# Error Percentage

```text
Single-Threaded    42.72%
Multithreaded      19.25%
Thread Pool         1.76%
```

The Thread Pool implementation produced the lowest observed error percentage.

Compared with the Single-Threaded implementation:

**The observed error rate was reduced by approximately 95.9%.**

---

# Performance Improvement

| Metric                | Single-Threaded |   Thread Pool |      Improvement |
| --------------------- | --------------: | ------------: | ---------------: |
| Average Response Time |          218 ms |         34 ms |  **84.4% lower** |
| Throughput            |   2,536 req/sec | 3,916 req/sec | **54.4% higher** |
| Error Percentage      |          42.72% |         1.76% |  **95.9% lower** |

---

# Overall Comparison

| Rank | Implementation  | Performance                         |
| ---- | --------------- | ----------------------------------- |
| 1    | Thread Pool     | Best overall benchmark performance  |
| 2    | Multithreaded   | Significant concurrency improvement |
| 3    | Single-Threaded | Baseline implementation             |

---

# Technical Analysis

## Single-Threaded

The Single-Threaded implementation processes requests sequentially.

```text
Request 1 ──► Processing
Request 2 ──► Waiting
Request 3 ──► Waiting
Request 4 ──► Waiting
```

This creates a bottleneck when multiple clients send requests concurrently.

---

## Multithreaded

The Multithreaded implementation allows multiple requests to execute simultaneously.

```text
Request 1 ──► Thread 1
Request 2 ──► Thread 2
Request 3 ──► Thread 3
Request 4 ──► Thread 4
```

This removes the single-thread execution bottleneck.

However, creating a new thread for every connection introduces additional overhead related to:

- Thread creation
- Memory allocation
- Thread scheduling
- Context switching
- Thread lifecycle management

---

## Thread Pool

The Thread Pool implementation uses reusable worker threads.

```text
Request
   │
   ▼
ExecutorService
   │
   ▼
Available Worker
   │
   ▼
Process Request
   │
   ▼
Worker Reused
```

This allows the number of concurrently executing tasks to be controlled while avoiding continuous thread creation.

The benchmark showed that this approach produced the best overall performance among the three implementations.

---

# Key Observations

### Single-Threaded Server

Provides the simplest implementation but becomes a bottleneck under concurrent workloads.

### Multithreaded Server

Introduces concurrent processing and significantly improves performance, but creating a new thread for every connection introduces additional overhead.

### Thread Pool Server

Provides controlled concurrency through reusable worker threads and achieved the best results in the benchmark.

---

# Technologies Used

- Java
- Java Socket API
- `Socket`
- `ServerSocket`
- `Thread`
- `ExecutorService`
- Thread Pool
- TCP
- Apache JMeter

---

# Project Architecture

```text
                    TCP Server
                        │
          ┌─────────────┼─────────────┐
          │             │             │
          ▼             ▼             ▼
    Single Thread   Multithreaded   Thread Pool
          │             │             │
          │             │             │
          ▼             ▼             ▼
    Sequential      New Thread     ExecutorService
    Processing      per Client     Worker Pool
          │             │             │
          └─────────────┼─────────────┘
                        │
                        ▼
                 Apache JMeter
                        │
                        ▼
              Performance Analysis
```

---

# Benchmark Summary

The test was performed using **600,000 samples** for each server implementation.

The Thread Pool implementation achieved:

- **34 ms average response time**
- **3,916 requests/sec throughput**
- **1.76% error percentage**

Compared with the Single-Threaded implementation:

- **84.4% lower average response time**
- **54.4% higher throughput**
- **95.9% lower observed error rate**

The results demonstrate the impact of moving from sequential processing to concurrent processing and finally to controlled concurrency using a thread pool.

---

# Important Note

These results are benchmark results from a specific local environment.

Actual performance can vary based on:

- CPU
- Memory
- JVM configuration
- Operating system
- Number of worker threads
- JMeter configuration
- Network conditions
- Request complexity
- Number of concurrent connections

The primary purpose of this benchmark is to compare the behavior of the three concurrency models under the same workload.

---

# Conclusion

The project demonstrates the progression from:

```text
Single Thread
      │
      ▼
Multiple Threads
      │
      ▼
Thread Pool
```

The Single-Threaded implementation establishes the basic server architecture.

The Multithreaded implementation introduces concurrent request processing.

The Thread Pool implementation introduces controlled concurrency and worker-thread reuse through `ExecutorService`.

The **600,000-request JMeter benchmark** showed that the Thread Pool implementation provided the best overall results among the three implementations tested.

```

```

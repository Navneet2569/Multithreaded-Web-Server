## 🚀 Server Implementations

This project demonstrates the evolution of a Java TCP server through three different concurrency models, highlighting how each approach affects performance, scalability, and resource utilization.

### 🔴 Single-Threaded Server

The initial implementation processes **one client connection at a time**. While simple and easy to understand, it quickly becomes a bottleneck when multiple clients attempt to connect simultaneously.

**Key Characteristics**

- Sequential request processing
- Simple implementation
- No concurrency support
- Suitable for learning socket programming fundamentals

---

### 🟡 Multithreaded Server

The second implementation creates **a new thread for every incoming client connection**, allowing multiple requests to be processed concurrently.

**Key Characteristics**

- One thread per client
- Concurrent request processing
- Improved response time
- Higher throughput
- Increased thread creation overhead under heavy load

---

### 🟢 Thread Pool Server

The final implementation uses Java's **ExecutorService** to maintain a reusable pool of worker threads instead of creating a new thread for every request.

**Key Characteristics**

- Thread reuse using ExecutorService
- Better CPU and memory utilization
- Improved scalability
- Lower response time
- Highest throughput
- Lowest error percentage
- Production-oriented concurrency model

---

# 🧪 Performance Testing

All three implementations were benchmarked using **Apache JMeter** under identical testing conditions to compare their performance under concurrent client load.

### Test Configuration

| Parameter | Value |
|-----------|-------|
| Testing Tool | Apache JMeter |
| Total Requests | 600,000 |
| Protocol | TCP |
| Environment | Local Machine |
| JVM | Same JVM |
| Hardware | Same Machine |
| Workload | Identical for all implementations |

---

# 📊 Performance Comparison

| Metric | 🔴 Single Threaded | 🟡 Multithreaded | 🟢 Thread Pool |
|--------|-------------------:|----------------:|---------------:|
| Average Response Time | 218 ms | 45 ms | **34 ms** |
| Maximum Response Time | 2869 ms | 769 ms | 840 ms |
| Throughput | 2536 req/sec | 3422 req/sec | **3916 req/sec** |
| Error Percentage | **42.72%** | 19.25% | **1.76%** |
| Scalability | Low | Medium | High |
| Resource Utilization | Poor | Moderate | Excellent |
| Thread Management | None | One Thread per Request | Reusable Thread Pool |

---

# 📈 Performance Improvements

Compared to the **Single-Threaded Server**, the **Thread Pool Server** achieved:

| Improvement | Result |
|------------|--------|
| Response Time Reduction | **84% Faster** |
| Throughput Increase | **54% Higher** |
| Error Rate Reduction | **95.9% Lower** |
| Scalability | Significantly Improved |
| Thread Management | Efficient Thread Reuse |

---

# 🏆 Overall Ranking

| Rank | Implementation | Overall Performance |
|------|----------------|---------------------|
| 🥇 1 | Thread Pool Server | Best |
| 🥈 2 | Multithreaded Server | Good |
| 🥉 3 | Single-Threaded Server | Baseline |

---

## 🎯 Key Takeaways

- The **Single-Threaded Server** provides a strong foundation for understanding socket programming but struggles under concurrent workloads.
- The **Multithreaded Server** improves concurrency by allowing multiple client requests to execute simultaneously, though it incurs the overhead of creating a new thread for each request.
- The **Thread Pool Server** delivers the best overall performance by reusing worker threads, reducing latency, increasing throughput, and minimizing errors.
- Apache JMeter benchmarking clearly demonstrates how efficient thread management significantly improves server scalability and reliability.

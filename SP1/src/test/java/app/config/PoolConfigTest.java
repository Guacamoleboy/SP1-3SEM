package app.config;

import org.junit.jupiter.api.*;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import static org.junit.jupiter.api.Assertions.*;

public class PoolConfigTest {

    // Attributes
    private ThreadPoolExecutor executor;

    // ____________________________________________________________________

    @BeforeEach
    public void setUp() {
        this.executor = (ThreadPoolExecutor) PoolConfig.getExecutor();
        executor.getQueue().clear();
    }

    // ____________________________________________________________________

    @AfterAll
    public static void closeAll() {
        PoolConfig.shutdown();
    }

    // ____________________________________________________________________

    @Test
    @DisplayName("Should have exactly 4 threads and 100 queue capacity")
    public void threadPoolLimits() {
        // Assert
        assertEquals(4, executor.getCorePoolSize());
        assertEquals(4, executor.getMaximumPoolSize());
        assertEquals(100, executor.getQueue().remainingCapacity() + executor.getQueue().size());
    }

    // ____________________________________________________________________

    @Test
    @DisplayName("Should throw exception when heated")
    public void poolOverload() {
        // Arrange
        Runnable runnable = () -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        // Act & Assert
        assertThrows(RejectedExecutionException.class, () -> {
            for (int i = 0; i < 110; i++) {
                executor.execute(runnable);
            }
        });
    }

    // ____________________________________________________________________

    @Test
    @DisplayName("Should trigger pressure logging")
    public void shouldLogPressure() throws InterruptedException {
        // Act
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        Thread.sleep(150);
        // Assert
        assertTrue(executor.getActiveCount() > 0);
        assertTrue(executor.getQueue().size() > 0 || executor.getActiveCount() >= executor.getMaximumPoolSize());
    }

    // ____________________________________________________________________

    @Test
    @DisplayName("Should trigger critical logging")
    public void shouldLogCritical() throws InterruptedException {
        for (int i = 0; i < 105; i++) {
            try {
                executor.execute(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            } catch (RejectedExecutionException ignored) { }
        }
        Thread.sleep(100);
        int remaining = executor.getQueue().remainingCapacity();
        int size = executor.getQueue().size();
        System.out.println("Remaining capacity in test: " + remaining);
        System.out.println("Current queue size: " + size);
        assertTrue(remaining < 10);
    }

    // ____________________________________________________________________

    @Test
    @DisplayName("Should log failure without crashing the Thread")
    public void shouldLogFailure() throws InterruptedException {
        Thread.sleep(500);
        // Act
        executor.submit(() -> {
            throw new RuntimeException("Test Failure");
        });
        Thread.sleep(100);
        // Assert
        assertEquals(0, executor.getActiveCount());
    }

}
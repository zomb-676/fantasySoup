import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.junit.Test

class LoggerTest {
    @Test
    fun wrappedLoggerTest() {
        LogManager.getLogger().atDebug().log("")

    }
}
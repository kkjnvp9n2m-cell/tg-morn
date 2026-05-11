import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:diary_bot.db";

    // Метод для создания таблицы, если её ещё нет
    public static void initDatabase() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS events (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "user_id LONG," +
                         "event_type TEXT," + // 'WAKEUP' или 'FOOD'
                         "event_time TEXT," +
                         "description TEXT" +
                         ");";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для сохранения времени пробуждения
    public static void saveWakeUp(long userId) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String sql = "INSERT INTO events(user_id, event_type, event_time) VALUES(?, 'WAKEUP', ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setString(2, now);
            pstmt.executeUpdate();
            System.out.println("Время пробуждения сохранено в БД!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

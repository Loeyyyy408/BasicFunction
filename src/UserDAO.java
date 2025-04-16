import java.sql.*;

public class UserDAO {

    // 数据库连接信息
    private static final String URL = "jdbc:mysql://localhost:3306/java";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    // 加载驱动（如果使用的是 JDBC 4.0+ 可以省略显式加载）
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 插入用户
    public void insertUser(User user) {
        String sql = "INSERT INTO user (name, gender) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getGender());
            stmt.executeUpdate();
            System.out.println("用户插入成功！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除用户
    public void deleteUser(long id) {
        String sql = "DELETE FROM user WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("用户删除成功！");
            } else {
                System.out.println("未找到指定ID的用户！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 更新用户
    public void updateUser(User user) {
        String sql = "UPDATE user SET name = ?, gender = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getGender());
            stmt.setLong(3, user.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("用户更新成功！");
            } else {
                System.out.println("未找到指定ID的用户！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询单个用户
    public User getUserById(long id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setGender(rs.getString("gender"));
                return user;
            } else {
                System.out.println("未找到指定ID的用户！");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 查询所有用户
    public void getAllUsers() {
        String sql = "SELECT * FROM user";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                System.out.println("ID: " + id + ", Name: " + name + ", Gender: " + gender);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();

        // 插入用户
        User newUser = new User();
        newUser.setName("Alice");
        newUser.setGender("Female");
        dao.insertUser(newUser);

        // 查询所有用户
        dao.getAllUsers();

        // 查询单个用户
        User user = dao.getUserById(1L);
        if (user != null) {
            System.out.println("查询到的用户: " + user.getName());
        }

        // 更新用户
        if (user != null) {
            user.setName("Bob");
            dao.updateUser(user);
        }

        // 删除用户
        dao.deleteUser(1L);

        // 再次查询所有用户
        dao.getAllUsers();
    }
}
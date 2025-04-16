package crud;

public class UserDaoTester
{
    public static void main(String[] args)
    {
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
        if (user != null)
        {
            System.out.println("查询到的用户: " + user.getName());
        }

        // 更新用户
        if (user != null)
        {
            user.setName("Bob");
            dao.updateUserById(user);
        }

        // 删除用户
        dao.deleteUserById(1L);

        // 再次查询所有用户
        dao.getAllUsers();
    }
}

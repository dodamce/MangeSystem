import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sql.User;
import sql.UserDAO;

import java.util.List;

import static org.junit.Assert.*;

public class TestUserDAO {

    private UserDAO userDao;

    @Before
    public void setUp() throws Exception {
        userDao = new UserDAO();
    }

    @After
    public void tearDown() throws Exception {
        userDao = null;
    }

    @Test
    public void testSelectByName() {
        User user = userDao.SelectByName("John");
        assertNotNull(user);
        assertEquals("John", user.getName());
    }

    @Test
    public void testSelectUserById() {
        User user = userDao.selectUserById(1);
        assertNotNull(user);
        assertEquals(1, user.getId());
    }

    @Test
    public void testGetAllUser() {
        List<User> users = userDao.getAllUser();
        assertNotNull(users);
        assertEquals(3, users.size());
    }

    @Test
    public void testDropUser() {
        userDao.dropUser(3);
        User user = userDao.selectUserById(3);
        assertNull(user);
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("Jane");
        user.setPassword("password");
        userDao.insert(user);
        User insertedUser = userDao.SelectByName("Jane");
        assertNotNull(insertedUser);
        assertEquals("password", insertedUser.getPassword());
    }
}

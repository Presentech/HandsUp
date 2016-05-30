package com.presentech.handsup;
import static android.support.test.InstrumentationRegistry.getTargetContext;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Lewis
 */


@RunWith(AndroidJUnit4.class)
public class UserDBHandlerTest {

    UserDBHandler handler;

    @Before
    public void setUp() {
        getTargetContext().deleteDatabase("userDB");
        handler = new UserDBHandler(getTargetContext(), "name", null, 1, "FP");
    }

    @After
    public void tearDown() throws Exception {
        handler.close();
    }

    //Method to test adding user to UserDB
    @Test
    public void testAddingUser(){
        //Create test user
        User testUser = new User("test@testuser.com", "testpassword", "testname", "testlockout");
        //Add test user to database using handler
        handler.addUser(testUser);
        //Retrieve user details from database
        User receivedUser = handler.findUser("test@testuser.com");

        //Check retrieved user details match details for newly added user
        assertEquals("test@testuser.com", receivedUser.getEmail());
        assertEquals("testpassword", receivedUser.getPassword());
        assertEquals("testname", receivedUser.getName());
        assertEquals("testlockout", receivedUser.getLockout());
    }

    //Method to test updating lockout state of user in database
    @Test
    public void testUpdatingUser(){
        String userEmail = "lockedout@test.com";
        //New lockout state for user
        String lockoutState = "lock";
        //Update lockout state for user
        handler.updateUser(userEmail, lockoutState);
        User user = handler.findUser(userEmail);

        //Check retrieved Lockout State For User matches expected value
        assertEquals(lockoutState, user.getLockout());
    }

    //Method to test database querying
    @Test
    public void testFindUser(){
        //Expected details for retrieved user
        int expectedID = 3;
        String expectedEmail = "test@test.com";
        String expectedPassword = "test";
        String expectedName = "test";
        String expectedLockout = "0";
        //Query terms
        String userEmail = "test@test.com";
        //Search for user in database and return user
        User user = handler.findUser(userEmail);
        //Query results
        int returnedID = user.getID();
        String returnedEmail = user.getEmail();
        String returnedPassword = user.getPassword();
        String returnedName = user.getName();
        String returnedLockout =user.getLockout();

        //Check query results
        assertEquals(expectedID, returnedID);
        assertEquals(expectedEmail, returnedEmail);
        assertEquals(expectedPassword, returnedPassword);
        assertEquals(expectedName, returnedName);
        assertEquals(expectedLockout, returnedLockout);
    }

    //Method to test deleting user from database
    @Test
    public void testDeleteUser(){
        //Create test user
        String userEmail = "testing@presentech.com";
        User testUser = new User("testing@presentech.com", "presentech", "testaccount", "0");
        //Add test user to database using handler
        handler.addUser(testUser);
        User addedUser = handler.findUser(userEmail);
        assertNotNull(addedUser);
        //Delete added test user from database
        handler.deleteUser(userEmail);
        //Check user is not found in DB
        assertFalse(handler.deleteUser(userEmail));
    }

    //Method to test database presence checking
    @Test
    public void testCheckUserDB(){
        //Check that database file exists on device
        String DATABASE_PATH = "/data/data/com.presentech.handsup/databases/";
        String DATABASE_NAME = "userDB.db";
        File file = new File(DATABASE_PATH + DATABASE_NAME);
        assertEquals(file.exists(), handler.checkUserDB());
    }

    //Method to test database file copying
    @Test
    public void testDBCopy() throws IOException {
        String DATABASE_PATH = "/data/data/com.presentech.handsup/databases/";
        String DATABASE_NAME = "userDB.db";
        handler.copyDataBase();
        //Check that database file exists in correct location
        File file = new File(DATABASE_PATH + DATABASE_NAME);
        assertTrue(file.exists());
    }
}
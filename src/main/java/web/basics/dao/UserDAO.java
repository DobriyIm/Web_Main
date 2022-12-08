package web.basics.dao;

import web.basics.entities.User;
import web.basics.services.DataService;
import web.basics.services.HashServices.HashService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Singleton
public class UserDAO {
    private final Connection connection ;
    private final HashService hashService ;
    private final DataService dataService;

    @Inject
    public UserDAO(DataService dataService, HashService hashService ) {
        this.hashService = hashService ;
        this.dataService = dataService;
        this.connection = dataService.getConnection() ;
    }

    /**
     * Inserts User to database
     * @param user data to insert
     * @return user ID in table
     */
    public String add(User user ) {
        // генеруемо id
        user.setId( UUID.randomUUID().toString() ) ;
        // генеруємо сіль
        user.setSalt( hashService.hash( UUID.randomUUID().toString() ) ) ;
        // формуємо запит
        String sql = "INSERT INTO users(`id`,`login`,`pass`,`name`,`salt`) VALUES(?,?,?,?,?)" ;
        try( PreparedStatement prep = connection.prepareStatement( sql ) ) {
            prep.setString( 1, user.getId()    ) ;
            prep.setString( 2, user.getLogin() ) ;
            prep.setString( 3, this.makePasswordHash( user.getPass(), user.getSalt() ) ) ;
            prep.setString( 4, user.getName()  ) ;
            prep.setString( 5, user.getSalt()  ) ;
            prep.executeUpdate() ;
        }
        catch( SQLException ex ) {
            System.out.println( ex.getMessage() ) ;
            return null ;
        }
        return user.getId() ;
    }

    /**
     * Checks if login is out from DB table
     * @param login string to test
     * @return true if login NOT in DB
     */
    public boolean isLoginFree( String login ) {
        String sql = "SELECT COUNT(u.id) FROM users u WHERE u.`login` = ? " ;
        try( PreparedStatement prep = connection.prepareStatement( sql ) ) {
            prep.setString( 1, login ) ;
            ResultSet res = prep.executeQuery() ;
            if( res.next() ) {
                return res.getInt(1) == 0 ;
            }
        }
        catch( SQLException ex ) {
            System.out.println( ex.getMessage() ) ;
            System.out.println( sql + "; " + login ) ;
        }
        return false ;
    }

    /**
     * Looks for user in DB
     * @param login Credentials: login
     * @param password Credentials: password
     * @return entities.User or null if not found
     */
    public User getUserByCredentials( String login, String password ) {
        String sql = "SELECT * FROM users u WHERE u.`login` = ?" ;
        try( PreparedStatement prep = connection.prepareStatement( sql ) ) {
            prep.setString( 1, login ) ;
            ResultSet res = prep.executeQuery() ;
            if( res.next() ) {
                User user = new User( res ) ;
                String expectedHash = this.makePasswordHash( password, user.getSalt() ) ;
                if( expectedHash.equals( user.getPass() ) ) {
                    return user ;
                }
            }
        }
        catch( SQLException ex ) {
            System.out.println( ex.getMessage() ) ;
            System.out.println( sql + "; " + login + " " + password ) ;
        }
        return null ;
    }

    /**
     * Search user by ID
     * @param userId user's id in DB
     * @return User entity or null
     */
    public User getUserById(String userId){
        String sql = "SELECT * FROM users u WHERE u.id = ? ";
        try(PreparedStatement prep = dataService.getConnection().prepareStatement(sql)){
            prep.setString(1, userId);
            ResultSet res = prep.executeQuery();
            if(res.next()) return new User(res);
        }
        catch (Exception ex){
            System.out.println("UserDAO::getUserById() error: " + ex.getMessage() + "\n" + sql + " --- " + userId);
        }
        return null;
    }
    public User getUserByCredentialsOld( String login, String password ) {
        String sql = "SELECT * FROM Users u WHERE u.`login` = ? AND u.`pass` = ?" ;
        try( PreparedStatement prep = connection.prepareStatement( sql ) ) {
            prep.setString( 1, login ) ;
            prep.setString( 2, this.makePasswordHash( password, "" ) ) ;
            ResultSet res = prep.executeQuery() ;
            if( res.next() ) {
                return new User( res ) ;
            }
        }
        catch( SQLException ex ) {
            System.out.println( ex.getMessage() ) ;
            System.out.println( sql + "; " + login + " " + password ) ;
        }
        return null ;
    }

    private String makePasswordHash( String password, String salt ) {
        return hashService.hash( salt + password + salt ) ;
    }
}
/*
Криптографічна сіль - дані, що додадються до основних даних перед перетвореннями.
Навіщо?
 геш від однакових даних дає однакові результати, це дозволяє впізнати однакові паролі
  за їх однаковими гешами, а також це стосується загальної ситуації (між базами даних)
Як?
 1. Константна сіль - дозволяє уникнути глобальної ситуації, не уникає локальної
    рівності. Проста для реалізації
 2. Випадкова сіль - робить всі ситуації винятковими, але ускладнює реалізацію,
    оскільки потребує збереження.
 */
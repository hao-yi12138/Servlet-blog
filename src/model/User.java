package model;

import util.DBUtil;

import java.sql.*;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: HAOYI
 * @date:2020-11-04 15:33
 **/
public class User {
        public int id;
        public String username;
        public static User register(String username,String password) throws SQLException {
              try(Connection connection = DBUtil.getConnection()) {
                     String sql = "insert into users(username,password) values(?,?)";
                     try(PreparedStatement s = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                             s.setString(1,username);
                             s.setString(2,password);
                             s.executeUpdate();
                        try(ResultSet resultSet = s.getGeneratedKeys()){
                                if(!resultSet.next()){
                                        return null;
                                }
                                User user = new User();
                                user.id = resultSet.getInt(1);
                                user.username = username;
                                return user;
                        }
                     }
              }
        }
        public static User login(String username,String password) throws SQLException {
                try(Connection connection = DBUtil.getConnection()){
                       String sql = "select id from users where username = ? and password = ?";
                       try(PreparedStatement statement = connection.prepareStatement(sql)){
                               statement.setString(1,username);
                               statement.setString(2,password);
                               try(ResultSet resultSet = statement.executeQuery()){
                                       if(!resultSet.next()){
                                               return null;
                                       }
                                       User user = new User();
                                       user.id = resultSet.getInt(1);
                                       user.username = username;
                                       return user;
                               }
                       }
                }
        }
}

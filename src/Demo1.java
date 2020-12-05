import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * JDBC
 * 1.本质：SUN公司制定的一套接口，java.sql.*包
 * 2.优点：面向接口编程，解耦合
 * 3.背景：每一个数据库的底层实现原理都不一样
 * 4.驱动：以jar包的形式存在，其中的class文件是对JDBC接口的实现，要去官网下载驱动
 * 5.编程六部：注册驱动、获取连接（进程之间的连接）、获取数据库操作对象、执行SQL语句、处理查询结果、释放资源
 * 6.URL：统一资源定位符，包括协议、IP地址、端口号、资源名
 * 7.案例表中的字段：oa(string)\technicalLever(string)\airport(string)\time(date)
 */
public class Demo1 {
    public static void main(String[] args) {
        //获取资源绑定器
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        String driver = bundle.getString("driver");
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");
        Connection connectionection = null;
        //Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String oa = "zhangsan";

        try {
            //1.注册驱动（反射机制，执行类的静态代码块）
            Class.forName(driver);

            //2.获取连接(使用资源绑定器）
            connectionection = DriverManager.getConnection(url, user, password);

            //3.写带有占位符的SQL语句
            String sql = "select * from airport where oa=?";

            //4.获取预编译的数据库操作对象
            preparedStatement = connectionection.prepareStatement(sql);

            //5.传值,第一个参数是占位符的下标，第二个是要传的值
            preparedStatement.setString(1, oa);

            //6.处理查询结果集
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String oaname = resultSet.getString(1);
                String technicalLever = resultSet.getString(2);
                String airport = resultSet.getString(3);
                Date date = resultSet.getDate(4);
                Date time = resultSet.getTime(4);
                System.out.println(oaname + "," + technicalLever + "," + airport + "," + date + " " + time);
            }

            /*3.获取数据库操作对象
            解决SQL注入问题:让用户提供的信息不参与SQL语句的编译过程
            statement = connectionection.createStatement();

            4.执行sql语句（jdbc中sql语句不需要写分号）
            String sql = "select * from airport where oa='zhangsan'";

            5.处理查询结果集
              （1）statement.executeUpdate();处理增删改sql语句；返回int
              （2）statement.executeQuery();处理查询sql语句；返回ResultSet结果集
              （3）JDBC中所有的下标从1开始，不是从0开始
              （4）获取数据的方式：下标 OR 列名
              （5）列名称不是表中的列名称，而是查询结果集的列名称，有别名应该写别名

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String oa = resultSet.getString(1);
                String technicalLever = resultSet.getString(2);
                String airport = resultSet.getString(3);
                Date date = resultSet.getDate(4);
                Date time = resultSet.getTime(4);
                System.out.println(oa + "," + technicalLever + "," + airport + "," + date + " " + time);
            }*/

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            //6.关闭资源
            if (connectionection != null) {
                try {
                    connectionection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
            if (resultSet != null) {
                        try {
                            resultSet.close();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}

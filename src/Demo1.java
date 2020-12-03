/**
 * JDBC
 * 1.本质：SUN公司制定的一套接口，java.sql.*包
 * 2.优点：面向接口编程，解耦合
 * 3.背景：每一个数据库的底层实现原理都不一样
 * 4.驱动：以jar包的形式存在，其中的class文件是对JDBC接口的实现，要去官网下载驱动
 * 5.编程六部：注册驱动、获取连接（进程之间的连接）、获取数据库操作对象、执行SQL语句、处理查询结果、释放资源
 *
 */
public class Demo1 {
    public static void main(String[] args) {

        try {
            Class.forName("Demo1.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

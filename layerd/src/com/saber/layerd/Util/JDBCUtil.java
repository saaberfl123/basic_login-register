package com.saber.layerd.Util;

import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUtil
{
    private static final String url="jdbc:mysql://localhost:3306/exercise?serverTimeZone=Asia/Shanghai";
    private static final String userName="root";
    private static final String password="saber520";
    static
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("驱动程序未加载");
        }
    }
    //创建一个预处理器并填充补全sql
    private static PreparedStatement createPreparedStatement(Connection connection,String sql,Object... params) throws SQLException
    {
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        //补全sql语句
        if(params==null|| params.length == 0)return preparedStatement;
        for(int i=0;i<params.length;i++)
        {
            preparedStatement.setObject(i+1,params[i]);
        }
        return preparedStatement;
    }

    //创建一个对应实例(从sql的结果集中读取)
    private static<T> T createNewInstance(Class<T> c,ResultSet resultSet) throws Exception
    {
        Field[] fields=c.getDeclaredFields();
        Constructor<T> constructor=c.getDeclaredConstructor();//获取空构造函数
        //读取结果集信息
        T t = constructor.newInstance();
        for (Field f : fields)
        {
            String fieldName = f.getName();
            //结果集中 查找对应数据
            try
            {
                Object obj = resultSet.getObject(fieldName, f.getType());//顺手转化了
                //setId = set+I+d;
                //获取方法名称
                String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                //查找方法(名称+参数名)
                Method method = c.getDeclaredMethod(methodName, f.getType());
                //调用方法
                method.setAccessible(true);
                method.invoke(t, obj);
            }
            catch (SQLException e) {continue;}
        }
        return t;
    }

    //关闭对应资源  AutoCloseable
    private static void close(AutoCloseable... closeables)
    {
        for(AutoCloseable c:closeables)
        {
            try {
                c.close();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 万能查询
     * @param sql sql语句
     * @param c 查询对象的class类型
     * @param params 查询填充sql的参数
     * @return 查询表的list对象类型
     * @param <T> 查询对象的类型
     */
    public static<T> List<T> Query(String sql,Class<T> c,Object...params)
    {
        List<T> dataList=new ArrayList<>();
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        Connection connection=null;
        try
        {
            connection=DriverManager.getConnection(url,userName,password);
            preparedStatement=createPreparedStatement(connection,sql,params);
            //执行查询 注意null错误
            if(preparedStatement==null)return null;
            resultSet = preparedStatement.executeQuery();
            if(resultSet==null)return null;
            while(resultSet.next())
            {
                dataList.add(createNewInstance(c,resultSet));
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            close(resultSet,preparedStatement,connection);
        }
        return dataList;
    }

    /**
     * 万能更新
     * @param sql sql语句
     * @param params 填充问号内容
     * @return result变更行数
     */
    public static int UpDate(String sql,Object...params)
    {
        int result=0;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try
        {
            connection=DriverManager.getConnection(url,userName,password);
            preparedStatement=createPreparedStatement(connection,sql,params);
            if(preparedStatement == null)return -1;
            result=preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("出现异常");
        }
        finally {
            close(preparedStatement,connection);
        }
        return result;
    }

}

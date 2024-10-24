/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.dao;

import emart.dbutil.DBConnection;
import emart.pojo.EmployeesPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jain's
 */
public class EmployeesDao {
    public static String getNextEmpId()throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select max(empid)from employees");
        rs.next();
        String empid=rs.getString(1);
        int empno=Integer.parseInt(empid.substring(1));
        empno=empno+1;
        return "E"+empno;
    }
    public static boolean addEmployee(EmployeesPojo e)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Insert into employees values(?,?,?,?)");
        ps.setString(1, e.getEmpid());
        ps.setString(2, e.getEmpname());
        ps.setString(3, e.getJob());
        ps.setDouble(4, e.getSalary());
        int result=ps.executeUpdate();
        return result==1;
    }
    public static List<EmployeesPojo> getAllEmployee() throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select * from employees order by empid");
        ArrayList<EmployeesPojo>empList=new ArrayList<>();
        while(rs.next()){
            EmployeesPojo emp=new EmployeesPojo();
            emp.setEmpid(rs.getString(1));
            emp.setEmpname(rs.getString(2));
            emp.setJob(rs.getString(3));
            emp.setSalary(rs.getDouble(4));
            empList.add(emp);
        }
        return empList;
    }
    public static List<String>getAllId()throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select empid from employees order by empid");
        ArrayList<String>ls=new ArrayList<String>();
        while(rs.next()){
            ls.add(rs.getString(1));
        }
        return ls;
    }
    public static EmployeesPojo findEmpById(String empid)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Select * from employees where empid=?");
        ps.setString(1, empid);
        ResultSet rs=ps.executeQuery();
        rs.next();
        EmployeesPojo emp=new EmployeesPojo();
        emp.setEmpid(rs.getString(1));
        emp.setEmpname(rs.getString(2));
        emp.setJob(rs.getString(3));
        emp.setSalary(rs.getDouble(4));
        return emp;
    }
    public static boolean updateEmployee(EmployeesPojo e)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Update employees set empname=?,job=?,salary=? where empid=?");
        ps.setString(1, e.getEmpname());
        ps.setString(2, e.getJob());
        ps.setDouble(3, e.getSalary());
        ps.setString(4, e.getEmpid());
        
        int result=ps.executeUpdate();
        if(result==0){
            return false;
        }
        else{
            boolean x=Userdao.isUserPresent(e.getEmpid());
            if(x==false){
               return true; 
            }
           
            ps=conn.prepareStatement("Update users set username=?,usertype=? where empid=?");
            ps.setString(1, e.getEmpname());
            ps.setString(2, e.getJob());
            ps.setString(3, e.getEmpid());
            int y=ps.executeUpdate();
            return y==1;
            
        
        }
        
    }
    public static boolean deleteEmployee(String empid)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Delete from employees where empid=?");
        ps.setString(1, empid);
        
        int result=ps.executeUpdate();
        return result==1;
    }
}

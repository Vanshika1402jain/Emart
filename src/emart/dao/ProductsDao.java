/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.dao;

import emart.dbutil.DBConnection;
import emart.pojo.EmployeesPojo;
import emart.pojo.Productspojo;
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
public class ProductsDao {
    public static String getNextProductId()throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select max(pid)from products");
        rs.next();
        String pid=rs.getString(1);
        if(pid==null){
            return "P101";
        }
        int pno=Integer.parseInt(pid.substring(1));
        pno=pno+1;
        return "P"+pno;
    }
    public static boolean addProducts(Productspojo p)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Insert into products values(?,?,?,?,?,?,?,'Y')");
        ps.setString(1, p.getProductId());
        ps.setString(2, p.getProductName());
        ps.setString(3, p.getProductCompany());
        ps.setDouble(4, p.getProductPrice());
        ps.setDouble(5, p.getOurPrice());
        ps.setInt(6, p.getTax());
        ps.setInt(7, p.getQuantity());
        return ps.executeUpdate()==1;
    }
    public static List<Productspojo> getProductsDetails() throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select * from products where status='Y' order by pid");
        ArrayList<Productspojo>pl=new ArrayList<>();
        while(rs.next()){
            Productspojo p=new Productspojo();
            p.setProductId(rs.getString(1));
            p.setProductName(rs.getString(2));
            p.setProductCompany(rs.getString(3));
            p.setProductPrice(rs.getDouble(4));
            p.setOurPrice(rs.getDouble(5));
            p.setTax(rs.getInt(6));
            p.setQuantity(rs.getInt(7));
            pl.add(p);
        }
        return pl;
    }
    public static boolean deleteProduct(String productId)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("update products set status='N' where pid=?");
        ps.setString(1, productId);
        return ps.executeUpdate()==1;
    }
    public static boolean updateProduct(Productspojo p)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("update products set pname=?,pcompanyname=?,pprice=?,ourprice=?,ptax=?,quantity=? where pid=?");
        ps.setString(1, p.getProductName());
        ps.setString(2, p.getProductCompany());
        ps.setDouble(3, p.getProductPrice());
        ps.setDouble(4, p.getOurPrice());
        ps.setInt(5, p.getTax());
        ps.setInt(6, p.getQuantity());
        ps.setString(7, p.getProductId());
        return ps.executeUpdate()==1;
    }
    public static Productspojo getProductDetailsById(String pid)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Select * from products where pid=? and status='Y'");
        ps.setString(1, pid);
        ResultSet rs=ps.executeQuery();
        Productspojo p=new Productspojo();
        if(rs.next()){
            
            p.setProductId(rs.getString(1));
            p.setProductName(rs.getString(2));
            p.setProductCompany(rs.getString(3));
            p.setProductPrice(rs.getDouble(4));
            p.setOurPrice(rs.getDouble(5));
            p.setTax(rs.getInt(6));
            p.setQuantity(rs.getInt(7));
        }
        return p;
    }
    public static boolean updateQuantity(List<Productspojo>pl)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("update products set quantity=quantity-? where pid=?");
        int x=0;
        for(Productspojo p:pl){
            ps.setInt(1, p.getQuantity());
            ps.setString(2, p.getProductId());
            int rows=ps.executeUpdate();
            if(rows!=0){
                x++;
            }
        }
        return x==pl.size();
    }
}

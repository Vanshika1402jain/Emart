/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.dao;

import emart.dbutil.DBConnection;
import emart.pojo.Productspojo;
import emart.pojo.Userprofile;
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
public class OrderDao {
    public static String getNextOrderId()throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select max(orderid)from orders");
        rs.next();
        String oid=rs.getString(1);
        if(oid==null){
            return "O101";
        }
        int ono=Integer.parseInt(oid.substring(1));
        ono=ono+1;
        return "O"+ono;
    }
    public static boolean addOrder(List<Productspojo>al,String orid)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Insert into orders values(?,?,?,?)");
        int cnt=0;
        for(Productspojo p:al){
            ps.setString(1, orid);
            ps.setString(2,p.getProductId());
            ps.setInt(3, p.getQuantity());
            ps.setString(4, Userprofile.getUserid());
            cnt=cnt+ps.executeUpdate();
            
        }
        return cnt==al.size();
    }
    public static List<String>getAllId()throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select orderid from orders order by orderid");
        ArrayList<String>ls=new ArrayList<String>();
        while(rs.next()){
            ls.add(rs.getString(1));
        }
        return ls;
    }
    public static List<Productspojo> getAllOrder(String id) throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("SELECT * FROM products WHERE pid = (SELECT pid FROM orders WHERE orderid = ?");
        ps.setString(1,id);
        ResultSet rs=ps.executeQuery();
        
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
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hitesh Gupta
 */
package case_sync;
import java.sql.*;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;
import java.io.IOException;

public class list_supply {
    static String url = "jdbc:mysql://localhost:3306/";
    static String dbName = "cs";
    static String driver = "com.mysql.jdbc.Driver";
    static String userName = "root"; 
    static String password = "30121993";

  
    
    static void dateentry(String j,String d,String m,String y)
    {
         Statement s;
        
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url+dbName,userName,password);
            s=conn.createStatement();
            
            String sql_stmnt;
            sql_stmnt= "UPDATE `sync_record` SET `sync_date`='"+y+"-"+m+"-"+d+"' WHERE `file_type`='"+j+"';";
            int i=s.executeUpdate(sql_stmnt);
            
            
            s.close();
            
            conn.close();
            }catch(Exception e) {}
        
    }

    static void crno(String content,String k,int start,int end)
    { 
        String CRNO,CRMAT;
        int first_index = content.indexOf("CR NO ");
        for(int i=0;i<100;i++)
            {    
            int second_index = content.indexOf("CR NO ", (first_index+5));
            if(first_index==-1&&second_index==-1)
            {}
            else if(first_index==-1)
            {}
            else if(second_index==-1)
            {
                CRMAT = content.substring(first_index+8);
                CRNO = content.substring(first_index,first_index+8);
                crmat(CRNO,CRMAT,i,k,start,end);
                break;
            }
            else if(first_index>second_index)
            {}
            else
            {
                CRMAT = content.substring(first_index+8,second_index-45);
                CRNO = content.substring(first_index,first_index+8);
                crmat(CRNO,CRMAT,i,k,start,end);
            }
            first_index=second_index;
            }
    }
    
    static void crmat(String CRNO,String CRMAT,int crid,String k,int start,int end)
    {   
      
        for(int i=start;i<end;i++)
       {     
        String serial_string="\n"+Integer.toString(i)+" ";
        int serial_index = CRMAT.indexOf(serial_string);
        if(serial_index!=-1)
        {
            String CRMAT_NOTE=CRMAT.substring(0, serial_index);
            String CRMAT_SERIAL=CRMAT.substring(serial_index-4);
            note(CRNO,CRMAT_NOTE,crid,k);
            srno(CRNO,CRMAT_SERIAL,crid,k,start,end);
            break;
        }
        if(i==end-1&&serial_index==-1)
        {
            note(CRNO,CRMAT,crid,k);
        }
       }
        
    }
    
    
    
    static void srno(String CRNO,String CRMAT,int crid,String k,int start,int end)
    {
        int second_index,first_index;
        String string_index,SRMAT,SRNO;
        first_index = CRMAT.indexOf("\n"+Integer.toString(start)+" ");
        for(int i=start+1;i<=end;i++)
            {
            string_index ="\n"+Integer.toString(i)+" ";
            second_index = CRMAT.indexOf(string_index,(first_index+5));
            if(first_index==-1)
            {}
            else
            {
                if(second_index==-1)
                {
                    SRNO=Integer.toString(i-1);
                    SRMAT=CRMAT.substring(first_index+1); 
                    caseno(CRNO,SRNO,SRMAT,crid,k);
                    break;
                }
                else if(second_index<=first_index)
                {}
                else
                {   
                    SRNO=Integer.toString(i-1);
                    SRMAT=CRMAT.substring(first_index+1,second_index); 
                    caseno(CRNO,SRNO,SRMAT,crid,k);
                }
            }
            first_index=second_index;
           
            }
    }
    
    static void note(String CRNO,String CRMAT,int crid,String k)
    {
        int first_index;
        String first_part,second_part;
        for(int i=0;i<200;i++)
            {    
            first_index=CRMAT.indexOf("'");
            if(first_index==-1)
            {
                Statement s;
                try{Class.forName(driver);
                Connection conn = DriverManager.getConnection(url+dbName,userName,password);
                s=conn.createStatement();
                String sql_stmnt1;
                sql_stmnt1="insert into "+k+"_court (crid,crno,note)values("+crid+",'"+CRNO+"','"+CRMAT+"')";
                int u=s.executeUpdate(sql_stmnt1);
                s.close();
                conn.close();
                }catch(Exception e) { System.out.println(e);}
            break;
            }
            else
            {
                first_part=CRMAT.substring(0, first_index);
                second_part=CRMAT.substring(first_index+1);
                CRMAT=first_part+"`"+second_part;
            }
            }    
    }
    
    
    static void srmat(String CRNO,String SRNO,String SRMAT,int crid,String k)
    {
        int first_index=0;
        String SRMAT1;
        for(int i=0;i<10000;i++)
        {    
            int second_index = SRMAT.indexOf("WITH", (first_index+5));
            if(first_index==-1&&second_index==-1)
            {}
            else if(first_index==-1)
            {}
            else if(second_index==-1)
            {
                SRMAT1 = SRMAT.substring(first_index);
                Statement s;
                try{Class.forName(driver);
                Connection conn = DriverManager.getConnection(url+dbName,userName,password);
                s=conn.createStatement();
                String sql_stmnt;
                sql_stmnt="insert into "+k+"_sync (crno,srno,csno,crid)values('"+CRNO+"','"+SRNO+"','"+SRMAT1+"',"+crid+")";
                int n=s.executeUpdate(sql_stmnt);
                s.close();
                conn.close();
                }catch(Exception e) { System.out.println(e);}
                break;
            }
            else if(first_index>second_index)
            {}
            else
            {
                SRMAT1 = SRMAT.substring(first_index,second_index);
                Statement s;
                try{Class.forName(driver);
                Connection conn = DriverManager.getConnection(url+dbName,userName,password);
                s=conn.createStatement();
                String sql_stmnt;
                sql_stmnt="insert into "+k+"_sync (crno,srno,csno,crid)values('"+CRNO+"','"+SRNO+"','"+SRMAT1+"',"+crid+")";
                int n=s.executeUpdate(sql_stmnt);
                s.close();
                conn.close();
                }catch(Exception e) { System.out.println(e);}
            }
        first_index=second_index;
        }
    }
    
   

    static void caseno(String CRNO,String SRNO,String SRMAT,int crid,String k)
    {
        int error_index;
        String first_part,second_part;
        for(int i=0;i<200;i++)
            {    
            error_index=SRMAT.indexOf("'");
            if(error_index==-1)
            {   
                Statement s;
                try{Class.forName(driver);
                Connection conn = DriverManager.getConnection(url+dbName,userName,password);
                s=conn.createStatement();
                String sql_stmnt;
                sql_stmnt="insert into "+k+"_sync (crno,srno,csno,crid)values('"+CRNO+"','"+SRNO+"','"+SRMAT+"',"+crid+")";
                int n=s.executeUpdate(sql_stmnt);
                s.close();
                conn.close();
                }catch(Exception e) {srmat(CRNO,SRNO,SRMAT,crid,k);}
                break;
            }
            else
            {
                first_part=SRMAT.substring(0, error_index);
                second_part=SRMAT.substring(error_index+1);
                SRMAT=first_part+"`"+second_part;
            }
            }
    }  
        
    static void deleterecord(String k)
    {
        Statement s;
        Statement t;
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url+dbName,userName,password);
            s=conn.createStatement();
            t=conn.createStatement();
            String sql_stmnt;
            sql_stmnt= "DROP TABLE "+k+"_sync";
            int i=s.executeUpdate(sql_stmnt);
            String sql_stmnt1;
            sql_stmnt1= "DROP TABLE "+k+"_court";
            int j=t.executeUpdate(sql_stmnt1);
            s.close();
            t.close();
            conn.close();
            }catch(Exception e) {}
    }
     
    static void createtable(String k)
    {
        Statement s2;
        Statement s1;
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url+dbName,userName,password);
            s1=conn.createStatement();
            s2=conn.createStatement();
            String sql_stmnt1;
            sql_stmnt1="CREATE TABLE `"+k+"_sync` (\n" +
                           "  `id` INT(40) NOT NULL AUTO_INCREMENT,\n" +
                           "  `crno` VARCHAR(10) NOT NULL,\n" +
                           "  `srno` VARCHAR(10) NOT NULL,\n" +
                           "  `csno` VARCHAR(20000) NOT NULL,\n" +
                           "  `crid` INT(5) NOT NULL,\n" +
                           "  PRIMARY KEY (`id`))";
            int n=s1.executeUpdate(sql_stmnt1);
            String sql_stmnt;
            sql_stmnt="CREATE TABLE `"+k+"_court` (\n" +
                        "  `crid` INT(5) NOT NULL,\n" +
                        "  `crno` VARCHAR(10) NOT NULL,\n" +
                        "  `note` VARCHAR(20000) NOT NULL,\n" +
                        "  PRIMARY KEY (`crid`))";
            int m=s2.executeUpdate(sql_stmnt);
            s2.close();
            s1.close();
            conn.close();
            }catch(Exception e) { System.out.println(e);}
    }
}


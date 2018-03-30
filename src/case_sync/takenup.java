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

public class takenup {
    static String url = "jdbc:mysql://localhost:3306/";
    static String dbName = "cs";
    static String driver = "com.mysql.jdbc.Driver";
    static String userName = "root"; 
    static String password = "30121993";

    public int main_method(String filename,String j,String d,String m,String y) throws IOException 
    {
         PDDocument doc = PDDocument.load(filename);
         String content = new PDFTextStripper().getText(doc);
         content=takenup.cleancontent(content);
         if(new String(j).equals("t_m")||new String(j).equals("t_s"))
         {  takenup.dateentry(j, d, m, y);
             takenup.deleterecord(j);
            takenup.createtable(j);
            takenup.crno(content,j);
            return 1;
         }
         
         
         
         doc.close();
         return 0;
    }
    
    static String cleancontent(String content)
    {
        for(int i=1;i<300;i++)
        {
            int first_index=content.indexOf("\n"+Integer.toString(i)+" of");
            if(first_index!=-1)
            {
            String a=content.substring(0, first_index);
            String b=content.substring(first_index+12);
            content=a+b;
            }
        }
        return content;
    }
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

    static void crno(String content,String k)
    { 
        String CRNO,CRMAT;
        int first_index = content.indexOf("COURT ROOM NO");
        for(int i=0;i<100;i++)
            {    
            int second_index = content.indexOf("COURT ROOM NO", (first_index+5));
            if(first_index==-1&&second_index==-1)
            {}
            else if(first_index==-1)
            {}
            else if(second_index==-1)
            {
                CRMAT = content.substring(first_index+17);
                CRNO = content.substring(first_index,first_index+17);
                crmat(CRNO,CRMAT,i,k);
                break;
            }
            else if(first_index>second_index)
            {}
            else
            {
                CRMAT = content.substring(first_index+17,second_index-45);
                CRNO = content.substring(first_index,first_index+17);
                crmat(CRNO,CRMAT,i,k);
            }
            first_index=second_index;
            }
    }
    
    static void crmat(String CRNO,String CRMAT,int crid,String k)
    {   
      for(int i=301;i<2000;i++)
         {
         String index_string=Integer.toString(i)+" ";
         int first_index = CRMAT.indexOf(index_string);
           
            if(first_index==-1)
            {
            }
             else
                {
                    int second_index =CRMAT.indexOf("\n"+index_string,first_index+3);
                    if(second_index!=-1)
                    {
            
            String CRMAT_serial_no=CRMAT.substring(first_index, second_index);
            String CRMAT_note=CRMAT.substring(0, second_index);
            String CRMAT_serial=CRMAT.substring(second_index-2);
            note(CRNO,CRMAT_note,crid,k);
            srno(CRNO,CRMAT_serial,CRMAT_serial_no,crid,k);
            break;
                 }
            
                }
         }
        
    }
    
    static int srno_sync(String CRMAT,int f)
    {
        for(int i=301;i<5000;i++)
        {
            int index =CRMAT.indexOf("\n"+Integer.toString(i)+" ",f+3);
            if(index!=-1)
            {
                //System.out.println(index);
                return index;
            }
        }
        return -1;
    }
    static int srno_sync(String CRMAT)
    {
        for(int i=301;i<5000;i++)
        {
            int index =CRMAT.indexOf("\n"+Integer.toString(i)+" ");
            if(index!=-1)
            {
                //System.out.println(index);
                return index;
            }
        }
        return -1;
    }
    
    static void srno(String CRNO,String CRMAT,String SERIAL,int crid,String k)
    {
        int second_index,first_index;
        String string_index,SRMAT,SRNO;
        first_index = srno_sync(CRMAT);
        for(int i=1;i<=5000;i++)
            {
            second_index = srno_sync(CRMAT,first_index);
            if(first_index==-1)
            {}
            else
            {
                if(second_index==-1)
                {
                    SRNO=CRMAT.substring(first_index+1,first_index+5); 
                    SRMAT=CRMAT.substring(first_index+1); 
                    
                    caseno(CRNO,SRNO,SRMAT,crid,k);
                    break;
                }
                else if(second_index<=first_index)
                {}
                else
                {   
                    SRNO=CRMAT.substring(first_index+1,first_index+5); 
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
                           "  `crno` VARCHAR(20) NOT NULL,\n" +
                           "  `srno` VARCHAR(10) NOT NULL,\n" +
                           "  `csno` VARCHAR(20000) NOT NULL,\n" +
                           "  `crid` INT(5) NOT NULL,\n" +
                           "  PRIMARY KEY (`id`))";
            int n=s1.executeUpdate(sql_stmnt1);
            String sql_stmnt;
            sql_stmnt="CREATE TABLE `"+k+"_court` (\n" +
                        "  `crid` INT(5) NOT NULL,\n" +
                        "  `crno` VARCHAR(20) NOT NULL,\n" +
                        "  `note` VARCHAR(20000) NOT NULL,\n" +
                        "  PRIMARY KEY (`crid`))";
            int m=s2.executeUpdate(sql_stmnt);
            s2.close();
            s1.close();
            conn.close();
            }catch(Exception e) { System.out.println(e);}
    }
}


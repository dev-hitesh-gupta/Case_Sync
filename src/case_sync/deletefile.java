/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package case_sync;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Hitesh Gupta
 */
public class deletefile {
 
public static void delte(String list_name)throws IOException {    
    try{
File f = new File(list_name);
f.delete();
    }catch(Exception e){System.out.println(e+"delete");}
}
}

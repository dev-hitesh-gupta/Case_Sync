/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package case_sync;

/**
 *
 * @author Hitesh Gupta
 */
import java.io.*;
import java.net.*;

public class DownloadFile {
   

public static void download(String address, String localFileName) throws IOException {
    URL url1 = new URL(address);

    byte[] ba1 = new byte[1024];
    int baLength;
    FileOutputStream fos1 = new FileOutputStream(localFileName);

    try {
        // Contacting the URL
        System.out.print("Connecting to " + url1.toString() + " ... ");
        URLConnection urlConn = url1.openConnection();

        // Checking whether the URL contains a PDF
        if (!urlConn.getContentType().equalsIgnoreCase("application/pdf")) {
            System.out.println("FAILED.\n[Sorry. This is not a PDF.]");
            
        } else {
            try {

                // Read the PDF from the URL and save to a local file
                InputStream is1 = url1.openStream();
                while ((baLength = is1.read(ba1)) != -1) {
                    fos1.write(ba1, 0, baLength);
                }
                fos1.flush();
                fos1.close();
                is1.close();


            } catch (ConnectException ce) {
                System.out.println("FAILED.\n[" + ce.getMessage() + "]\n");
            }
        }

    } catch (NullPointerException npe) {
        System.out.println("FAILED.\n[" + npe.getMessage() + "]\n");
    }
}
}

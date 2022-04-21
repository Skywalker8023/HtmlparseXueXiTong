import okhttp3.*;
import okhttp3.Headers.Builder;

import okhttp3.internal.http2.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class OkkHttpTry {

public String[] stringtoheader(String path) throws FileNotFoundException {
    File f=new File(path);
    Scanner scanner=new Scanner(f);
    ArrayList<String> stes=new ArrayList<>();
    while (scanner.hasNextLine()){
        String[] s= scanner.nextLine().split(": ");
        stes.addAll(Arrays.asList(s));
    }
//    String [] sts=stes.toArray(new String[0]);

  return stes.toArray(new String[0]);

}



    public static void main(String[] args) throws Exception {
     OkkHttpTry okkHttpTry=new OkkHttpTry();
     String[] s= okkHttpTry.stringtoheader("src/main/resources/Header.txt");

     for (String o:s){
         System.out.println(o);
     }
//
//        System.out.print(Headers.of("Content","text"));


    }


}

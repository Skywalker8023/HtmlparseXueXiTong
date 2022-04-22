import okhttp3.*;
import okhttp3.Headers.Builder;
import okhttp3.internal.http2.Header;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class OkkHttpTry {
    private final OkHttpClient client = new OkHttpClient();

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
    public void run() throws Exception {
        Headers.Builder builder=new Headers.Builder();
       Headers headers= builder.addAll(Headers.of(stringtoheader("src/main/resources/Header.txt"))).build();
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/square/okhttp/issues")
                .headers(headers)
                .build();
//        System.out.println(request.headers().value(2));
        System.out.println(request.headers());
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
////            System.out.println("Server: " + response.header("Server"));
////            System.out.println("Date: " + response.header("Date"));
////            System.out.println("Vary: " + response.headers("Vary"));
//            System.out.println(response.body());
//        }
    }

    public void parsebin() throws IOException {

        FileInputStream fileInputStream=new FileInputStream(new File("src/main/resources/request_body.bin"));
    int value;
    while ((value=fileInputStream.read())!=-1){
        System.out.println(value);

    }


    }


    public static void main(String[] args) throws Exception {
           new OkkHttpTry().parsebin();
//        System.out.println("++++++++++++++++++++=");
//                String s="孔";
//                byte[] bt=s.getBytes();
//
//        for (byte b : bt) {
//            String temp = Integer.toHexString(b);
//
//            System.out.println(temp.substring(6).toUpperCase(Locale.ROOT));
//
//        }



    }


}

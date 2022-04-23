import okhttp3.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

//请求体 分为 辅字段1 + 自定义参数 +辅字段2    //必须按照这个格式 服务器才接收请求
public class OkkHttpTry {
    private final OkHttpClient client = new OkHttpClient();
    protected   ArrayList<Integer> fu1 =new ArrayList<>();
    protected   ArrayList<Integer> fu2=new ArrayList<>();
    protected   String s;
    protected   ArrayList<Integer> allpostparameter=new ArrayList<>();
    protected   byte[] bis;
    public OkkHttpTry(String p) {
        this.s=p;
    }


private String[] stringtoheader() throws FileNotFoundException {//把头文件键 值 键 值 的字符数组
    File f=new File("src/main/resources/Header.txt");
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
        parsebin();//生成f1 f2
        ;//生成 自定义参数
         allpostparameter.addAll(fu1);
         allpostparameter.addAll(stringtobinsparameter(s));
         allpostparameter.addAll(fu2);
         System.out.println(allpostparameter);

         bis= new byte[allpostparameter.size()]; //二进制请求体
      int i=0;
      for (int s:allpostparameter) bis[i++] = (byte) s;
        for (byte b:bis)
        {
            System.out.println(b);
        }
//        write();
//        System.out.println("ok");
        // 开始请求
       Headers.Builder builder=new Headers.Builder();
       Headers headers= builder.addAll(Headers.of(stringtoheader())).build();//请求头
       Request request = new Request.Builder()
                .url("https://api.github.com/repos/square/okhttp/issues")
                .headers(headers)
                .build();
//        System.out.println(request.headers().value(2));
        System.out.println(request.headers());
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//            System.out.println("Server: " + response.header("Server"));
//            System.out.println("Date: " + response.header("Date"));
//            System.out.println("Vary: " + response.headers("Vary"));
            System.out.println(response.body());
        }
    }

    private void parsebin() throws IOException {//生成请求体辅字段1和2

        FileInputStream fileInputStream=new FileInputStream(new File("src/main/resources/request_body.bin"));
    int value;
    ArrayList<Integer> bi=new ArrayList<>();//bi存储 二进制 数
    while ((value=fileInputStream.read())!=-1){
        bi.add(value);
    }

    for(int i=0;i<4;i++){
        fu1.add(bi.get(i));
    }
    for (int j=10;j<bi.size();j++){
        fu2.add(bi.get(j));
    }
//        System.out.println(fu1);
//        System.out.println("\n");
//        System.out.println(fu2);

    //bi的


//    FileOutputStream fileOutputStream=new FileOutputStream(new File("src/main/resources/Binary"));
//    for(int x : bi){
//fileOutputStream.write(x);
//    }
//fileOutputStream.close();

    }

//    public void write() throws IOException {
//
//            FileOutputStream fileOutputStream=new FileOutputStream(new File("src/main/resources/Binary"));
//    for(int x : bis){
//fileOutputStream.write(x);
//    }
//fileOutputStream.close();
//    }

    private ArrayList<Integer> stringtobinsparameter(String s){//返回自定义参数
         ArrayList<Integer> ints=new ArrayList<>();
    byte[] bt=s.getBytes();
        for (byte b : bt) {
            String temp = Integer.toHexString(b);
            String ss=temp.substring(6).toUpperCase(Locale.ROOT);
            ints.add(Integer.valueOf(ss,16));
        }
    return ints;
    }

    public static void main(String[] args) throws Exception {
     new OkkHttpTry("孔子").run();


    }


}
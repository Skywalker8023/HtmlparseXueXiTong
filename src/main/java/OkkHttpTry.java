import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.google.protobuf.TextFormat;
import com.google.protobuf.*;
import com.google.protobuf.util.JsonFormat;
import okhttp3.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

//请求体 分为 辅字段1 + 自定义参数 +辅字段2    //必须按照这个格式 服务器才接收请求
public class OkkHttpTry {
    private final OkHttpClient client = new OkHttpClient();
    private final ArrayList<Integer> fu1 =new ArrayList<>();
    private final ArrayList<Integer> fu2=new ArrayList<>();
    private final ArrayList<Integer> allpostparameter=new ArrayList<>();



private String[] stringtoheader(String ps) throws FileNotFoundException {//把头文件键 值 键 值 的字符数组
    File f=new File(ps);
    Scanner scanner=new Scanner(f);
    ArrayList<String> stes=new ArrayList<>();
    while (scanner.hasNextLine()){
        String[] s= scanner.nextLine().split(": ");
        stes.addAll(Arrays.asList(s));
    }
//    String [] sts=stes.toArray(new String[0]);
  return stes.toArray(new String[0]);

}

public  Map<String,String> jsonstringtoamp(String ps) throws FileNotFoundException { //文件路径
    Scanner scanner=new Scanner(new File(ps));
    StringBuilder jsonstring= new StringBuilder();
    while (scanner.hasNextLine()){
        jsonstring.append(scanner.nextLine());
    }
    Gson gson=new Gson();
    Type stringtype=new TypeToken<Map<String,Object>>(){}.getType();
    return gson.fromJson(jsonstring.toString(),stringtype);
}


public String parserespose(byte[] bs) throws IOException { //解析返回体 返回字符串

    Xuexiaoyi.RespOfSearch xue=Xuexiaoyi.RespOfSearch.parseFrom(bs);

    return JsonFormat.printer().print(xue);
//    return TextFormat.printer().escapingNonAscii(false).printToString(xue);
}
@Deprecated
private  byte[] setbinpostbody(String ps) throws IOException { //获取二进制请求体
      parsebin();//生成f1 f2
      //生成 自定义参数
      allpostparameter.addAll(fu1);
      allpostparameter.addAll(stringtobinsparameter(ps));
      allpostparameter.addAll(fu2);
//      System.out.println(allpostparameter); //allpostparameter为请求体
      byte[] bis = new byte[allpostparameter.size()]; //二进制请求体
      int i=0;
      for (int bs:allpostparameter) bis[i++] = (byte) bs;
      return  bis;
}



    public String run(String s) throws Exception {//s为请求参数 //run为主方法
       byte[] postbody=getparameterfromproto(s);
       Headers.Builder builder=new Headers.Builder();
       Headers headers= builder.addAll(Headers.of(jsonstringtoamp("src/main/resources/request.json"))).build();//生成请求头
       Request request = new Request.Builder() //开始请求
                .url("https://xxy.51xuexiaoyi.com/el/v0/sou/search")
                .headers(headers)
                .post(RequestBody.create(postbody))
                .build();
        try (Response response = client.newCall(request).execute()) {
            return  parserespose(Objects.requireNonNull(response.body()).bytes());
        }
}


    public  static byte[] binfiletobyte(String fp) throws IOException {//把二进制文件转 byte数组
    FileInputStream fi=new FileInputStream(new File(fp));
    ArrayList<Integer> ints=new ArrayList<>();
    int value;
    while ((value=fi.read())!=-1){
        ints.add(value);
    }
        byte[] bys=new byte[ints.size()];
        int i=0;
        for (int s:ints) bys[i++] = (byte) s;
   return  bys;
    }

@Deprecated
    private void parsebin() throws IOException {//生成请求体辅字段 f1 和 f2
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
    }

public  void write(byte [] bb) throws IOException {
    FileOutputStream fileOutputStream=new FileOutputStream(new File("src/main/resources/Try"));
    fileOutputStream.write(bb);
    fileOutputStream.close();
}

 private ArrayList<Integer> stringtobinsparameter(String s){//返回 接收请求参数（ 题目） 自定义参数的二进制 形式  //将字符串转化为二进制形式
         ArrayList<Integer> ints=new ArrayList<>();
    byte[] bt=s.getBytes();
        for (byte b : bt) {
            String temp = Integer.toHexString(b);
            if(temp.length()<=2){
                ints.add(Integer.valueOf(temp,16));
                continue;
            }
            String ss=temp.substring(6).toUpperCase(Locale.ROOT);
            ints.add(Integer.valueOf(ss,16));
        }
    return ints;
    }


     public  byte[] getparameterfromproto(String para){
         Xuexiaoyi.ReqOfSearch xue=Xuexiaoyi.ReqOfSearch.newBuilder()
                 .setChannel(1)
                 .setQuery(para)
                 .setSearchType(3)
                 .setTraceId("1249509491345470-"+gettraceid()).build();
         return xue.toByteArray();
     }

private long gettraceid(){
     long id= (int) (Math.random() * 58952);
    return  id + 1650940846249L;
}

public String  parserespose(String item ){


    return "";
}

    public static void main(String[] args) throws Exception {
           OkkHttpTry os=new OkkHttpTry();
       Gson gson=new Gson();
     String my=  gson.fromJson(os.run("44. (多选题, 3.0分)蝙蝠体内携带的病毒包括()。\n" +
               "A SARS病毒 B 冠状病毒 C 尼帕病毒 D 埃博拉病毒"),String.class);


    }
}

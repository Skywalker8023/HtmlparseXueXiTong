import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

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
                .url("https://xxy.51xuexiaoyi.com/el/v0/sou/search?iid\u003d2309412502376109\u0026device_id\u003d" +
                        "655737355241264\u0026ac\u003dwifi\u0026channel\u003dxiaomi_199563_64\u0026aid\u003d199563\u0026app_name\u003dxxy\u0026" +
                        "version_code\u003d10502\u0026version_name\u003d1.5.2\u0026device_platform\u003dandroid\u0026os\u003dandroid\u0026" +
                        "ssmix\u003da\u0026device_type\u003dM2004J7AC\u0026device_brand\u003dRedmi\u0026language\u003dzh\u0026os_api\u003d29\u0026os_version" +
                        "\u003d10\u0026openudid\u003ddafd35b356fd35c0\u0026manifest_version_code\u003d10502\u0026resolution\u003d1080*2201\u0026dpi\u003d440" +
                        "\u0026update_version_code\u003d1050201\u0026_rticket\u003d1650544955800\u0026cdid\u003d597f3d54-b015-4c7a-b758-f694fcb92812" +
                        "\u0026uuid\u003d655737355241264\u0026oaid\u003d476f5f89aa723343\u0026el_app_version\u003d10502")
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

public  String getjsonparse(String repath) throws FileNotFoundException {

    File f=new File(repath);
    Scanner scanner=new Scanner(f);
    StringBuilder s= new StringBuilder();
    while (scanner.hasNextLine()){
        s.append(scanner.nextLine());
    }
    return s.toString();
}
private String getresultstring(String respoString){
    JsonObject je= JsonParser.parseString(respoString).getAsJsonObject().getAsJsonObject("result");
    StringBuilder s= new StringBuilder();
    try {
        for (JsonElement j : je.getAsJsonArray("items")) {
            s.append(j.getAsJsonObject().getAsJsonObject("questionAnswer").getAsJsonPrimitive("answerPlainText"));
            s.append("\n");
        }
    }catch (NullPointerException e){
        System.out.println(respoString);
    }
    return s.toString();
}
    public  void start(String questionandoption) throws Exception {
        System.out.println(getresultstring(run(questionandoption)));

    }

    public static void main(String[] args) throws Exception {
            OkkHttpTry okkHttpTry=new OkkHttpTry();
            System.out.println(okkHttpTry.run("孔子"));




    }
}

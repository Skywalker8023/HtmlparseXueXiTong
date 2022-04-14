import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;

///答案页 抽取题目和选项
public class Parsetopic {


    public static void main(String[] args) {
        try {
            Document document= Jsoup.parse(new File("src/main/resources/整卷预览.html"),"UTF-8");
            for (Element e : document.body().getElementsByClass("questionLi")) {
                System.out.println("+++++++++++++++++++++++++++++");
                System.out.println(e.getElementsByTag("h3").text());
                System.out.println(e.getElementsByClass("stem_answer").text());
                System.out.println("+++++++++++++++++++++++++++++");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

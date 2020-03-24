import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @ClassName: test
 * @Description: TODO
 * @author: Doge_fang
 * @date: 2020/3/23  9:21
 */
public class test {
    public static void main(String[] args) {
        HashMap<String, String> pamas = new HashMap<>();
        pamas.put("name","fangfang");
        pamas.put("phone","15895806533");
        pamas.put("age","19");
        Set<String> key = pamas.keySet();
        System.out.println(key);
        ArrayList<BasicNameValuePair> list = new ArrayList<>();
        for (String keyresult:key
             ) {
            String s = pamas.get(keyresult);
            list.add(new BasicNameValuePair(keyresult, s));
            System.out.println(list);

        }
    }
}

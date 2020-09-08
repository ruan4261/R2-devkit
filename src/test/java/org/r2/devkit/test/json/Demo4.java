package org.r2.devkit.test.json;

import org.r2.devkit.json.JSONArray;
import org.r2.devkit.json.JSONObject;
import org.r2.devkit.serialize.CustomSerializer;
import org.r2.devkit.test.TestCore;
import org.r2.devkit.test.mod.People;
import org.r2.devkit.test.mod.User;

import java.util.ArrayList;
import java.util.List;

public class Demo4 extends TestCore {

    public static void main(String[] args) {
        User user = new User(1, "一号东大街");
        People p = new People("紫sfmladdfqroqpfmasdamdqwrfdsnjwwdamasvc", 1700);
        List list = new ArrayList();
        list.add(user);
        List list2 = new ArrayList();
        list2.add(p);

        JSONObject object = new JSONObject();
        object.put("user", "tom");
        object.put("userList", new JSONArray(list));
        object.put("userList3", new JSONArray(list2));

        CustomSerializer customSerializer = new CustomSerializer();
        customSerializer.register(People.class, people -> "序列化个锤锤");// level -1 only own class serializer
        object.setCustomSerializer(customSerializer);

        print(object);
    }

}

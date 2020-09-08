package org.r2.devkit.test.json;

import org.r2.devkit.json.JSONArray;
import org.r2.devkit.serialize.CustomSerializer;
import org.r2.devkit.test.TestCore;
import org.r2.devkit.test.mod.People;
import org.r2.devkit.test.mod.User;

import java.io.Serializable;

public class Demo2 extends TestCore {

    public static void main(String[] args) {
        JSONArray array = new JSONArray();
        User user = new User();
        user.name = "tom";
        user.age = 12;
        user.address = "煤锅炉";
        user.code = 10086;
        array.add(user);

        CustomSerializer customSerializer = new CustomSerializer();
        customSerializer.register(People.class, 100, people -> "name=" + people.name + ";age=" + people.age);
        customSerializer.register(User.class, 101, u -> "address=" + u.address + ";code=" + u.code);
        customSerializer.register(Serializable.class, 20, o -> "不序了，不序了");

        array.setCustomSerializer(customSerializer);
        print(array.toJSONString());
    }

}

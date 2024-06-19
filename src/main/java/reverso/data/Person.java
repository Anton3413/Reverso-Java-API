package reverso.data;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Person {

    String name;

    int age;

    String gender;

    public Person() {
    }

    public Person(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}

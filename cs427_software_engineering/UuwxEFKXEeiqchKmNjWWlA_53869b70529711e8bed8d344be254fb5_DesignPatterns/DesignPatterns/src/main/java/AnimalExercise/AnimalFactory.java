package AnimalExercise;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AnimalFactory {

    public static Animal create(AnimalType type) {
        if (type != null) {
            //default age value.
            int age = Integer.parseInt((new SimpleDateFormat("dd")).format(new Date()));
            if (AnimalType.Cat.equals(type)) {
                Cat cat = new Cat("Salmon");
                cat.setAge(age);
                return cat;
            } else if (AnimalType.Dog.equals(type)) {
                Dog dog = new Dog("Bone");
                dog.setAge(age);
                return dog;
            } else if (AnimalType.Duck.equals(type)) {
                Duck duck = new Duck("Rice");
                duck.setAge(age);
                return duck;
            } else if (AnimalType.Cow.equals(type)) {
                Cow cow = new Cow("Grass");
                cow.setAge(age);
                return cow;
            }
        }
        return null;
    }
}

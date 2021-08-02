package ru.appline.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {

    private static final PetModel petModel = PetModel.getInstance();
    private static final AtomicInteger counter = new AtomicInteger(1);

    @PostMapping(value = "/createPet", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createPet(@RequestBody Pet pet) {
        petModel.add(pet, counter.getAndIncrement());
        return petModel.getAll().size() <= 1 ? "Поздравляем! Ваш первый домашний питомец создан!" : "Ваш питомец успешно создан!";
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Integer, Pet> getAll() {
        return petModel.getAll();
    }

    @GetMapping(value = "/getPet", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Pet getPet(@RequestBody Map<String, Integer> id) {
        return petModel.getFromList(id.get("id"));
    }

    @DeleteMapping(value = "/deletePet", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String deletePet(@RequestBody Map<String, Integer> id) {
        if (0 >= id.get("id") || petModel.getAll().get(id.get("id")) == null) {
            return "Такого питомца не существует!";
        }
        petModel.getAll().remove(id.get("id"));
        return "Вы удалили питомца:( Как вы могли!!!";
    }

    @PutMapping(value = "/putPet", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String putPet(@RequestBody Map<String, Object> newPet) {
        String response = null;
        Integer id = (Integer) newPet.get("id");
        if (0 >= id) return "ID питомца не может быть меньше 0";

        String name = (String) newPet.get("name");
        String type = (String) newPet.get("type");
        Integer age = (Integer) newPet.get("age");

        Pet pet = new Pet(name, type, age);

        if (petModel.getAll().get(id) != null) {
            petModel.add(pet, id);
            response = "Питомец был обновлен!";
        } else {
            boolean isCreate = false;
            Integer i = 1;
            for (Map.Entry u: petModel.getAll().entrySet()) {
                if (!i.equals(u.getKey())) {
                    petModel.add(pet, i);
                    isCreate = true;
                    response = "Был создан новый питомец! ID питомца: " + i;
                    break;
                } else {
                    i++;
                }
            }
            if (!isCreate) {
                petModel.add(pet, i);
                response = "Был создан новый питомец! ID питомца: " + i;
            }
        }

        return response;
    }

}

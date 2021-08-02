package ru.appline.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.appline.logic.Side;
import ru.appline.logic.SideModel;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/compass")
public class CompassController {

    private final SideModel sides = SideModel.getInstance();

    /*
        {
            "North": "316-0",
            "North-East": "1-45",
            "East":  "46-90",
            "South-East": "91-135",
            "South": "136-180",
            "South-West": "181-225",
            "West": "226-270",
            "North-West": "271-315"
        }
     */

    @PostMapping(value = "/setCoordinates", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String setCoordinates(@RequestBody Map<String, String> coordinates) {
        for (Map.Entry<String, String> setSide : coordinates.entrySet()) {
            String[] rangeOf = setSide.getValue().split("-");

            if (Integer.parseInt(rangeOf[0]) > 360 || Integer.parseInt(rangeOf[1]) > 360 ||
            Integer.parseInt(rangeOf[0]) < 0 || Integer.parseInt(rangeOf[1]) < 0)  {
                return "В \'" + setSide.getKey() + "\' неверно выставленны координаты!";
            }

            Side side = new Side(setSide.getKey(), Integer.parseInt(rangeOf[0]), Integer.parseInt(rangeOf[1]));
            sides.addCoordinates(side);
        }
        return "Координаты успешно установлены!";
    }

    @GetMapping(value = "/getSide", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getSide(@RequestBody Map<String, Integer> degree) {
        Map<String, String> response = new HashMap<>();

        if (0 > degree.get("Degree") || 360 < degree.get("Degree")) {
            response.put("Error", "Неверно указаны координаты, допустимое значение от 0 до 359");
            return response;
        }

        for (Side s : sides.getSides()) {
            if (s.getFrom() < s.getTo()) {
                if (degree.get("Degree") >= s.getFrom() && degree.get("Degree") <= s.getTo()) {
                    response.put("Side", s.getSide());
                    break;
                }
            } else {
                if (degree.get("Degree") >= s.getFrom() || degree.get("Degree") <= s.getTo()) {
                    response.put("Side", s.getSide());
                    break;
                }
            }
        }
        return response;
    }


}

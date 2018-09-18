package Marshmallow.Robot.controller;

import Marshmallow.Robot.domain.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
class OilCleanerControllerAdapter {

    OilCleaner adaptToOilCleaner(OilCleanerRequest request) {

        Coordinate areaSize = createCoordinate(request.getAreaSize(), "Area size");

        Coordinate startingPosition = createCoordinate(request.getStartingPosition(), "Starting position");

        Set<Coordinate> patches = request.getPatches()
                .stream()
                .map(coordinates -> createCoordinate(coordinates, "Oil patch"))
                .collect(Collectors.toSet());

        //Maybe worth wrapping into a try/catch for a better handled error message
        List<CardinalDirection> directions = request.getInstructions().chars()
                .mapToObj(item ->  CardinalDirection.valueOf(String.valueOf((char)item)))
                .collect(Collectors.toList());

        return new OilCleaner(areaSize, startingPosition, patches, directions);
    }

    OilCleanerResponse adaptToOliCleanerResponse(OilCleanerSolution oilCleanerSolution) {

        return new OilCleanerResponse(oilCleanerSolution.getPosition(), oilCleanerSolution.getOilPatchesCleaned().size());
    }

    private Coordinate createCoordinate(List<Integer> coordinateInList, String field) {

        if(coordinateInList.size() != 2) {
            throw new IllegalArgumentException(field + " must have 2 coordinates");
        }
        return new Coordinate(coordinateInList.get(0), coordinateInList.get(1));
    }
}
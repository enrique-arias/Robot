package Marshmallow.Robot.service;

import Marshmallow.Robot.domain.CardinalDirection;
import Marshmallow.Robot.domain.Coordinate;
import Marshmallow.Robot.domain.OilCleaner;
import Marshmallow.Robot.domain.OilCleanerSolution;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class OilCleanerService {

    private final static Integer MINIMUM_POSITION = 0;

    public OilCleanerSolution cleanOil(OilCleaner oilCleaner) {

        checkAreaSizeIsValid(oilCleaner.getAreaSize());
        checkInitialPosition(oilCleaner.getAreaSize(), oilCleaner.getStartingPosition());

        OilCleanerSolution oilCleanerSolution = initialPosition(oilCleaner);

        for(CardinalDirection direction : oilCleaner.getInstructions()) {
            oilCleanerSolution = moveToNextPosition(oilCleaner, oilCleanerSolution, direction);
        }

        return oilCleanerSolution;
    }

    private OilCleanerSolution initialPosition(OilCleaner oilCleaner) {

        Set<Coordinate> patchesCleaned = new HashSet<>();
        Coordinate currentPosition = oilCleaner.getStartingPosition();

        if(isOnTopOfOilPatch(oilCleaner.getPatches(), currentPosition)) {
            patchesCleaned.add(currentPosition);
        }

        return new OilCleanerSolution(currentPosition, patchesCleaned);
    }
    private OilCleanerSolution moveToNextPosition(OilCleaner oilCleaner, OilCleanerSolution oilCleanerSolution, CardinalDirection direction) {

        Coordinate newPosition = movePosition(oilCleanerSolution.getPosition(), direction);
        Set<Coordinate> currentPatchesCleaned = oilCleanerSolution.getOilPatchesCleaned();

        if (isOutsideTheArea(oilCleaner.getAreaSize(), newPosition)) {
            throw new IllegalArgumentException("Robot has move outside the designated area: " + newPosition);
        }

        if(isOnTopOfOilPatch(oilCleaner.getPatches(), newPosition)) {
            currentPatchesCleaned.add(newPosition);
        }
        return new OilCleanerSolution(newPosition, oilCleanerSolution.getOilPatchesCleaned());
    }

    private void checkAreaSizeIsValid(Coordinate area) {

        if(area.getX() < MINIMUM_POSITION || area.getY() < MINIMUM_POSITION) {
            throw new IllegalArgumentException("Area must be positive numbers: " + area);
        }
    }

    private void checkInitialPosition(Coordinate area, Coordinate currentPosition) {

        if(isOutsideTheArea(area, currentPosition)) {
            throw new IllegalArgumentException("Robot starts outside area: " + currentPosition);
        }
    }

    private boolean isOutsideTheArea(Coordinate area, Coordinate currentPosition) {

        boolean biggerThanArea = currentPosition.getX() > area.getX() || currentPosition.getY() > area.getY();
        boolean smallerThanArea = currentPosition.getX() < MINIMUM_POSITION || currentPosition.getY() < MINIMUM_POSITION;

        return biggerThanArea || smallerThanArea;
    }

    private Coordinate movePosition(Coordinate currentPosition, CardinalDirection direction) {

        switch (direction) {
            case E:
                return new Coordinate(currentPosition.getX() + 1, currentPosition.getY());
            case W:
                return new Coordinate(currentPosition.getX() - 1, currentPosition.getY());
            case N:
                return new Coordinate(currentPosition.getX(), currentPosition.getY() + 1);
            default:
                return new Coordinate(currentPosition.getX(), currentPosition.getY() - 1);
        }
    }

    private boolean isOnTopOfOilPatch(Set<Coordinate> patches, Coordinate currentPosition) {

        return patches.contains(currentPosition);
    }
}

package Marshmallow.Robot.service;

import Marshmallow.Robot.domain.CardinalDirection;
import Marshmallow.Robot.domain.Coordinate;
import Marshmallow.Robot.domain.OilCleaner;
import Marshmallow.Robot.domain.OilCleanerSolution;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static Marshmallow.Robot.domain.CardinalDirection.*;
import static org.assertj.core.api.Assertions.assertThat;

public class OilCleanerServiceTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private OilCleanerService oilCleanerService;

    @Before
    public void setUp() {

        oilCleanerService = new OilCleanerService();
    }

    @Test
    public void areaXCoordinateIsNegative() {

        Coordinate areaSize = new Coordinate(-5, 5);
        Coordinate staringPosition = new Coordinate(1, 2);
        Set<Coordinate> oilPatches = new HashSet<>(
                Arrays.asList(new Coordinate(1, 0), new Coordinate(2, 2), new Coordinate(2, 3)));
        List<CardinalDirection> directions = Arrays.asList(N, N, E, S, E, E, S, W, N, W, W);

        OilCleaner oilCleaner = new OilCleaner(areaSize, staringPosition, oilPatches, directions);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Area must be positive numbers: " + areaSize);
        oilCleanerService.cleanOil(oilCleaner);
    }

    @Test
    public void areaYCoordinateIsNegative() {

        Coordinate areaSize = new Coordinate(5, -1);
        Coordinate staringPosition = new Coordinate(1, 2);
        Set<Coordinate> oilPatches = new HashSet<>(
                Arrays.asList(new Coordinate(1, 0), new Coordinate(2, 2), new Coordinate(2, 3)));
        List<CardinalDirection> directions = Arrays.asList(N, N, E, S, E, E, S, W, N, W, W);

        OilCleaner oilCleaner = new OilCleaner(areaSize, staringPosition, oilPatches, directions);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Area must be positive numbers: " + areaSize);
        oilCleanerService.cleanOil(oilCleaner);
    }

    @Test
    public void initialPositionOutsideAreaXCoordinate() {

        Coordinate areaSize = new Coordinate(5, 5);
        Coordinate staringPosition = new Coordinate(10, 2);
        Set<Coordinate> oilPatches = new HashSet<>(
                Arrays.asList(new Coordinate(1, 0), new Coordinate(2, 2), new Coordinate(2, 3)));
        List<CardinalDirection> directions = Arrays.asList(N, N, E, S, E, E, S, W, N, W, W);

        OilCleaner oilCleaner = new OilCleaner(areaSize, staringPosition, oilPatches, directions);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Robot starts outside area: " + staringPosition);
        oilCleanerService.cleanOil(oilCleaner);
    }

    @Test
    public void initialPositionOutsideAreaYCoordinate() {

        Coordinate areaSize = new Coordinate(5, 5);
        Coordinate staringPosition = new Coordinate(2, 6);
        Set<Coordinate> oilPatches = new HashSet<>(
                Arrays.asList(new Coordinate(1, 0), new Coordinate(2, 2), new Coordinate(2, 3)));
        List<CardinalDirection> directions = Arrays.asList(N, N, E, S, E, E, S, W, N, W, W);

        OilCleaner oilCleaner = new OilCleaner(areaSize, staringPosition, oilPatches, directions);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Robot starts outside area: " + staringPosition);
        oilCleanerService.cleanOil(oilCleaner);
    }

    @Test
    public void moveOutsideEastCoordinate() {

        Coordinate areaSize = new Coordinate(5, 5);
        Coordinate staringPosition = new Coordinate(2, 3);
        Set<Coordinate> oilPatches = new HashSet<>(
                Arrays.asList(new Coordinate(1, 0), new Coordinate(2, 2), new Coordinate(2, 3)));
        List<CardinalDirection> directions = Arrays.asList(E, E, E, E);

        OilCleaner oilCleaner = new OilCleaner(areaSize, staringPosition, oilPatches, directions);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Robot has move outside the designated area: " + new Coordinate(6, 3));
        oilCleanerService.cleanOil(oilCleaner);
    }

    @Test
    public void moveOutsideWestCoordinate() {

        Coordinate areaSize = new Coordinate(5, 5);
        Coordinate staringPosition = new Coordinate(2, 3);
        Set<Coordinate> oilPatches = new HashSet<>(
                Arrays.asList(new Coordinate(1, 0), new Coordinate(2, 2), new Coordinate(2, 3)));
        List<CardinalDirection> directions = Arrays.asList(W, W, W);

        OilCleaner oilCleaner = new OilCleaner(areaSize, staringPosition, oilPatches, directions);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Robot has move outside the designated area: " + new Coordinate(-1, 3));
        oilCleanerService.cleanOil(oilCleaner);
    }

    @Test
    public void moveOutsideNorthCoordinate() {

        Coordinate areaSize = new Coordinate(5, 5);
        Coordinate staringPosition = new Coordinate(2, 3);
        Set<Coordinate> oilPatches = new HashSet<>(
                Arrays.asList(new Coordinate(1, 0), new Coordinate(2, 2), new Coordinate(2, 3)));
        List<CardinalDirection> directions = Arrays.asList(N, N, N);

        OilCleaner oilCleaner = new OilCleaner(areaSize, staringPosition, oilPatches, directions);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Robot has move outside the designated area: " + new Coordinate(2, 6));
        oilCleanerService.cleanOil(oilCleaner);
    }

    @Test
    public void moveOutsideSouthCoordinate() {

        Coordinate areaSize = new Coordinate(5, 5);
        Coordinate staringPosition = new Coordinate(2, 3);
        Set<Coordinate> oilPatches = new HashSet<>(
                Arrays.asList(new Coordinate(1, 0), new Coordinate(2, 2), new Coordinate(2, 3)));
        List<CardinalDirection> directions = Arrays.asList(S, S, S, S);

        OilCleaner oilCleaner = new OilCleaner(areaSize, staringPosition, oilPatches, directions);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Robot has move outside the designated area: " + new Coordinate(2, -1));
        oilCleanerService.cleanOil(oilCleaner);
    }

    @Test
    public void staysInTheAreaNoOilPatchesToClean() {

        Coordinate areaSize = new Coordinate(5, 5);
        Coordinate staringPosition = new Coordinate(1, 2);
        Set<Coordinate> oilPatches = new HashSet<>();
        List<CardinalDirection> directions = Arrays.asList(N, N, E, S, E, E, S, W, N, W, W);

        OilCleaner oilCleaner = new OilCleaner(areaSize, staringPosition, oilPatches, directions);

        OilCleanerSolution oilCleanerSolution = oilCleanerService.cleanOil(oilCleaner);

        assertThat(oilCleanerSolution.getPosition()).isEqualTo(new Coordinate(1, 3));
        assertThat(oilCleanerSolution.getOilPatchesCleaned()).isEmpty();
    }

    @Test
    public void staysInTheAreaDoesNotCleanAnything() {

        Coordinate areaSize = new Coordinate(5, 5);
        Coordinate staringPosition = new Coordinate(1, 2);
        Set<Coordinate> oilPatches = new HashSet<>(
                Arrays.asList(new Coordinate(1, 0), new Coordinate(2, 2), new Coordinate(2, 5)));
        List<CardinalDirection> directions = Arrays.asList(N, N, E, S, E, E, S, W, N, W, W);

        OilCleaner oilCleaner = new OilCleaner(areaSize, staringPosition, oilPatches, directions);

        OilCleanerSolution oilCleanerSolution = oilCleanerService.cleanOil(oilCleaner);

        assertThat(oilCleanerSolution.getPosition()).isEqualTo(new Coordinate(1, 3));
        assertThat(oilCleanerSolution.getOilPatchesCleaned()).isEmpty();
    }

    @Test
    public void staysInTheAreaCleanTheSamePatchTwice() {

        Coordinate areaSize = new Coordinate(5, 5);
        Coordinate staringPosition = new Coordinate(1, 2);
        Set<Coordinate> oilPatches = new HashSet<>(
                Arrays.asList(new Coordinate(1, 0), new Coordinate(2, 2), new Coordinate(2, 3)));
        List<CardinalDirection> directions = Arrays.asList(N, N, E, S, E, E, S, W, N, W, W);

        OilCleaner oilCleaner = new OilCleaner(areaSize, staringPosition, oilPatches, directions);

        OilCleanerSolution oilCleanerSolution = oilCleanerService.cleanOil(oilCleaner);

        assertThat(oilCleanerSolution.getPosition()).isEqualTo(new Coordinate(1, 3));
        assertThat(oilCleanerSolution.getOilPatchesCleaned()).containsExactly(new Coordinate(2, 3));
    }

    @Test
    public void staysInTheAreaCleanDifferentPatches() {

        Coordinate areaSize = new Coordinate(5, 5);
        Coordinate staringPosition = new Coordinate(1, 2);
        Set<Coordinate> oilPatches = new HashSet<>(
                Arrays.asList(new Coordinate(1, 2), new Coordinate(1, 3)));
        List<CardinalDirection> directions = Collections.singletonList(N);

        OilCleaner oilCleaner = new OilCleaner(areaSize, staringPosition, oilPatches, directions);

        OilCleanerSolution oilCleanerSolution = oilCleanerService.cleanOil(oilCleaner);

        assertThat(oilCleanerSolution.getPosition()).isEqualTo(new Coordinate(1, 3));
        assertThat(oilCleanerSolution.getOilPatchesCleaned()).containsExactly(new Coordinate(1, 2), new Coordinate(1, 3));
    }

    @Test
    public void emptyDirections() {

        Coordinate areaSize = new Coordinate(5, 5);
        Coordinate staringPosition = new Coordinate(1, 2);
        Set<Coordinate> oilPatches = new HashSet<>(
                Arrays.asList(new Coordinate(1, 0), new Coordinate(2, 2), new Coordinate(2, 3)));
        List<CardinalDirection> directions = Collections.emptyList();

        OilCleaner oilCleaner = new OilCleaner(areaSize, staringPosition, oilPatches, directions);

        OilCleanerSolution oilCleanerSolution = oilCleanerService.cleanOil(oilCleaner);

        assertThat(oilCleanerSolution.getPosition()).isEqualTo(staringPosition);
        assertThat(oilCleanerSolution.getOilPatchesCleaned()).isEmpty();
    }

}

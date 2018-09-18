package Marshmallow.Robot.controller;

import Marshmallow.Robot.domain.Coordinate;
import Marshmallow.Robot.domain.OilCleaner;
import Marshmallow.Robot.domain.OilCleanerSolution;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static Marshmallow.Robot.domain.CardinalDirection.*;
import static org.assertj.core.api.Assertions.assertThat;

public class OilCleanerControllerAdapterTest {

    private static final List<Integer> AREA_SIZE = Arrays.asList(4, 5);
    private static final List<Integer> STARTING_POSITION = Arrays.asList(2, 2);
    private static final List<List<Integer>> OIL_PATCHES = Arrays.asList(Arrays.asList(2, 2), Arrays.asList(1, 4));
    private static final String INSTRUCTIONS = "NSEWW";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private OilCleanerControllerAdapter oilCleanerControllerAdapter;

    @Before
    public void setUp() {
        oilCleanerControllerAdapter = new OilCleanerControllerAdapter();
    }

    @Test
    public void adaptToOilCleanerOnlyOneCoordinateInAreaSize() {

        OilCleanerRequest oilCleanerRequest = new OilCleanerRequest(Collections.singletonList(2), STARTING_POSITION, OIL_PATCHES, INSTRUCTIONS);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Area size must have 2 coordinates");
        oilCleanerControllerAdapter.adaptToOilCleaner(oilCleanerRequest);
    }

    @Test
    public void adaptToOilCleaner4CoordinateInAreaSize() {

        OilCleanerRequest oilCleanerRequest = new OilCleanerRequest(Arrays.asList(2, 4, 6, 7), STARTING_POSITION, OIL_PATCHES, INSTRUCTIONS);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Area size must have 2 coordinates");
        oilCleanerControllerAdapter.adaptToOilCleaner(oilCleanerRequest);
    }

    @Test
    public void adaptToOilCleanerOnlyOneCoordinateInStartingPosition() {

        OilCleanerRequest oilCleanerRequest = new OilCleanerRequest(AREA_SIZE, Collections.singletonList(2), OIL_PATCHES, INSTRUCTIONS);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Starting position must have 2 coordinates");
        oilCleanerControllerAdapter.adaptToOilCleaner(oilCleanerRequest);
    }

    @Test
    public void adaptToOilCleaner4CoordinateInStartingPosition() {

        OilCleanerRequest oilCleanerRequest = new OilCleanerRequest(AREA_SIZE, Arrays.asList(2, 4, 6, 7), OIL_PATCHES, INSTRUCTIONS);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Starting position must have 2 coordinates");
        oilCleanerControllerAdapter.adaptToOilCleaner(oilCleanerRequest);
    }

    @Test
    public void adaptToOilCleanerOnlyOneCoordinateInOneOfTheOilPatches() {

        OilCleanerRequest oilCleanerRequest = new OilCleanerRequest(AREA_SIZE, STARTING_POSITION,
                Arrays.asList(Collections.singletonList(2), Arrays.asList(1, 4)), INSTRUCTIONS);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Oil patch must have 2 coordinates");
        oilCleanerControllerAdapter.adaptToOilCleaner(oilCleanerRequest);
    }

    @Test
    public void adaptToOilCleanerInstructionsNotTheRightFormat() {

        OilCleanerRequest oilCleanerRequest = new OilCleanerRequest(AREA_SIZE, STARTING_POSITION, OIL_PATCHES, "NWETS");

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("No enum constant Marshmallow.Robot.domain.CardinalDirection.T");
        oilCleanerControllerAdapter.adaptToOilCleaner(oilCleanerRequest);
    }

    @Test
    public void adaptToOilCleaner() {

        OilCleanerRequest oilCleanerRequest = new OilCleanerRequest(AREA_SIZE, STARTING_POSITION, OIL_PATCHES, INSTRUCTIONS);

        OilCleaner oilCleaner = oilCleanerControllerAdapter.adaptToOilCleaner(oilCleanerRequest);

        assertThat(oilCleaner.getAreaSize()).isEqualTo(new Coordinate(4, 5));
        assertThat(oilCleaner.getStartingPosition()).isEqualTo(new Coordinate(2, 2));
        assertThat(oilCleaner.getPatches()).containsExactly(new Coordinate(2, 2), new Coordinate(1, 4));
        assertThat(oilCleaner.getInstructions()).containsExactly(N, S, E, W, W);
    }

    @Test
    public void adaptToOilCleanerResponse() {

        OilCleanerSolution oilCleanerSolution = new OilCleanerSolution(new Coordinate(1, 4),
                new HashSet<>(Arrays.asList(new Coordinate(2, 3), new Coordinate(4, 4))));

        OilCleanerResponse oilCleanerResponse = oilCleanerControllerAdapter.adaptToOliCleanerResponse(oilCleanerSolution);

        assertThat(oilCleanerResponse.getFinalPosition()).isEqualTo(new Coordinate(1, 4));
        assertThat(oilCleanerResponse.getOilPatchesCleaned()).isEqualTo(2);
    }
}

package Marshmallow.Robot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public class OilCleaner {

    private final Coordinate areaSize;
    private final Coordinate startingPosition;
    private final Set<Coordinate> patches;
    private final List<CardinalDirection> instructions;
}

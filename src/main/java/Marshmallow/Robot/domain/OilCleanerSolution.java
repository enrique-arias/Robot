package Marshmallow.Robot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class OilCleanerSolution {

    private final Coordinate position;
    private final Set<Coordinate> oilPatchesCleaned;
}

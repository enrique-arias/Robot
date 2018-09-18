package Marshmallow.Robot.controller;

import Marshmallow.Robot.domain.Coordinate;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
class OilCleanerResponse {

    private final Coordinate finalPosition;
    private final Integer oilPatchesCleaned;

    @JsonCreator
    OilCleanerResponse(@JsonProperty(value = "finalPosition") Coordinate finalPosition,
                       @JsonProperty(value = "oilPatchesCleaned") Integer oilPatchesCleaned) {
        this.finalPosition = finalPosition;
        this.oilPatchesCleaned = oilPatchesCleaned;
    }

}

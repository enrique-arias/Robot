package Marshmallow.Robot.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
class OilCleanerRequest {

    private final List<Integer> areaSize;
    private final List<Integer> startingPosition;
    private final List<List<Integer>> patches;
    private final String instructions;

    @JsonCreator
    OilCleanerRequest(@JsonProperty(value = "areaSize", required = true) List<Integer> areaSize,
                      @JsonProperty(value = "startingPosition", required = true) List<Integer> startingPosition,
                      @JsonProperty(value = "oilPatches", required = true) List<List<Integer>> patches,
                      @JsonProperty(value = "navigationInstructions", required = true) String instructions) {
        this.areaSize = areaSize;
        this.startingPosition = startingPosition;
        this.patches = patches;
        this.instructions = instructions;
    }
}

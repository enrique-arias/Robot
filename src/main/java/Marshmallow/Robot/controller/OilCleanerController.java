package Marshmallow.Robot.controller;

import Marshmallow.Robot.domain.OilCleaner;
import Marshmallow.Robot.domain.OilCleanerSolution;
import Marshmallow.Robot.service.OilCleanerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OilCleanerController {

    private OilCleanerService oilCleanerService;
    private OilCleanerControllerAdapter oilCleanerControllerAdapter;

    @Autowired
    public OilCleanerController(OilCleanerService oilCleanerService,
                                OilCleanerControllerAdapter oilCleanerControllerAdapter) {
        this.oilCleanerService = oilCleanerService;
        this.oilCleanerControllerAdapter = oilCleanerControllerAdapter;
    }

    @ApiOperation(value = "Use a robot to clean oil patches in a determined area", response = OilCleanerResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully cleaned the area"),
            @ApiResponse(code = 400, message = "Some of the input parameters are wrong")
    }
    )
    @PostMapping(value = "/oilCleaner")
    public ResponseEntity cleanOilPatches(@RequestBody OilCleanerRequest oilCleanerRequest) {

        try {
            OilCleaner oilCleaner = oilCleanerControllerAdapter.adaptToOilCleaner(oilCleanerRequest);
            OilCleanerSolution oilCleanerSolution = oilCleanerService.cleanOil(oilCleaner);
            return ResponseEntity.ok(oilCleanerControllerAdapter.adaptToOliCleanerResponse(oilCleanerSolution));
        }
        catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}

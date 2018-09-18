package Marshmallow.Robot.controller;

import Marshmallow.Robot.domain.OilCleaner;
import Marshmallow.Robot.domain.OilCleanerSolution;
import Marshmallow.Robot.service.OilCleanerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OilCleanerControllerTest {

    private static final List<Integer> AREA_SIZE = Arrays.asList(4, 5);
    private static final List<Integer> STARTING_POSITION = Arrays.asList(2, 2);
    private static final List<List<Integer>> OIL_PATCHES = Arrays.asList(Arrays.asList(2, 2), Arrays.asList(1, 4));
    private static final String INSTRUCTIONS = "NSEWW";

    private static final String ERROR_MESSAGE = "something went wrong";

    @Mock
    private OilCleanerService oilCleanerService;

    @Mock
    private OilCleanerControllerAdapter oilCleanerControllerAdapter;

    @Mock
    private OilCleaner oilCleaner;

    @Mock
    private OilCleanerSolution oilCleanerSolution;

    @Mock
    private OilCleanerResponse oilCleanerResponse;

    @InjectMocks
    private OilCleanerController oilCleanerController;

    @Test
    public void cleanOilPatches() {

        OilCleanerRequest oilCleanerRequest = new OilCleanerRequest(AREA_SIZE, STARTING_POSITION, OIL_PATCHES, INSTRUCTIONS);

        when(oilCleanerControllerAdapter.adaptToOilCleaner(oilCleanerRequest)).thenReturn(oilCleaner);
        when(oilCleanerService.cleanOil(oilCleaner)).thenReturn(oilCleanerSolution);
        when(oilCleanerControllerAdapter.adaptToOliCleanerResponse(oilCleanerSolution)).thenReturn(oilCleanerResponse);

        ResponseEntity responseEntity = oilCleanerController.cleanOilPatches(oilCleanerRequest);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        OilCleanerResponse oilCleanerResponseReturned = (OilCleanerResponse) responseEntity.getBody();
        assertThat(oilCleanerResponseReturned).isEqualTo(oilCleanerResponse);
    }

    @Test
    public void cleanOilPatchesExceptionThrownAdaptingTheRequest() {

        OilCleanerRequest oilCleanerRequest = new OilCleanerRequest(AREA_SIZE, STARTING_POSITION, OIL_PATCHES, INSTRUCTIONS);

        when(oilCleanerControllerAdapter.adaptToOilCleaner(oilCleanerRequest)).thenThrow(new IllegalArgumentException(ERROR_MESSAGE));

        ResponseEntity responseEntity = oilCleanerController.cleanOilPatches(oilCleanerRequest);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        String responseReturned = (String) responseEntity.getBody();
        assertThat(responseReturned).isEqualTo(ERROR_MESSAGE);
    }

    @Test
    public void cleanOilPatchesExceptionThrownCalculatingTheSolution() {

        OilCleanerRequest oilCleanerRequest = new OilCleanerRequest(AREA_SIZE, STARTING_POSITION, OIL_PATCHES, INSTRUCTIONS);

        when(oilCleanerControllerAdapter.adaptToOilCleaner(oilCleanerRequest)).thenReturn(oilCleaner);
        when(oilCleanerService.cleanOil(oilCleaner)).thenThrow(new IllegalArgumentException(ERROR_MESSAGE));

        ResponseEntity responseEntity = oilCleanerController.cleanOilPatches(oilCleanerRequest);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        String responseReturned = (String) responseEntity.getBody();
        assertThat(responseReturned).isEqualTo(ERROR_MESSAGE);
    }

    @Test
    public void cleanOilPatchesExceptionThrownAdaptingTheSolution() {

        OilCleanerRequest oilCleanerRequest = new OilCleanerRequest(AREA_SIZE, STARTING_POSITION, OIL_PATCHES, INSTRUCTIONS);

        when(oilCleanerControllerAdapter.adaptToOilCleaner(oilCleanerRequest)).thenReturn(oilCleaner);
        when(oilCleanerService.cleanOil(oilCleaner)).thenReturn(oilCleanerSolution);
        when(oilCleanerControllerAdapter.adaptToOliCleanerResponse(oilCleanerSolution)).thenThrow(new IllegalArgumentException(ERROR_MESSAGE));

        ResponseEntity responseEntity = oilCleanerController.cleanOilPatches(oilCleanerRequest);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        String responseReturned = (String) responseEntity.getBody();
        assertThat(responseReturned).isEqualTo(ERROR_MESSAGE);
    }
}

package Marshmallow.Robot.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CoordinateTest {

    @Test
    public void equalsTest() {

        Coordinate coordinate = new Coordinate(1,2);
        Coordinate differentYCoordinate = new Coordinate(1,3);
        Coordinate differentXCoordinate = new Coordinate(2,2);
        Coordinate sameCoordinate = new Coordinate(1,2);

        assertThat(coordinate).isNotEqualTo(differentYCoordinate);
        assertThat(coordinate).isNotEqualTo(differentXCoordinate);
        assertThat(coordinate).isEqualTo(sameCoordinate);
    }
}

package telran.range;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class BrokenFloorTest {

private int getMinimalBrokenFloor(BallBrokenFloor bbf) {
//Using only method checkFloor to find out minimal broken floor by applying 
//binary search algorithm

int left = 1;
int right = Integer.MAX_VALUE;
int res = -1;

while (left <= right) {
    int mid = left + (right - left) / 2;
    try {
        bbf.checkFloor(mid);
        left = mid + 1;
    } catch (Exception e) {
        res = mid;
        right = mid - 1;
    }
}

return res;
}
@Test
void minimalBrokenFloorTest() {
    int [] floors = {200, 17, 1001, 2000};
    for(int i = 0; i < floors.length; i++) {
        BallBrokenFloor bbf = new BallBrokenFloor(floors[i]);
        assertEquals(bbf.getMinBrokenFloor(), getMinimalBrokenFloor(bbf));
    }
}
}

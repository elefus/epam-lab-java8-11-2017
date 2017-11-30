package streams.part1.example;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("ConstantConditions")
public class Example3 {

    @Test
    public void getIvansLastNames() {
        String[] ivansLastNames = null;

        assertArrayEquals(new String[]{"Мельников", "Александров"}, ivansLastNames);
    }

    @Test
    public void check25AgedIvansDevExpirience() {
        Boolean all25IvansHasDevExpirience = null;

        assertTrue(all25IvansHasDevExpirience);
    }
}

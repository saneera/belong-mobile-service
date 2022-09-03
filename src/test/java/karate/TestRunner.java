package karate;

import com.intuit.karate.junit5.Karate;

public class TestRunner {

    @Karate.Test
    Karate testEverything() {
        return Karate.run().relativeTo(getClass());
    }
}

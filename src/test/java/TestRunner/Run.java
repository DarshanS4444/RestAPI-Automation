package TestRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/Features/getAPI.feature", // Path to your feature files
        glue = "steps", // Package where your step definitions are located
        dryRun = true,
        tags = "@createUserPOST"
)

public class Run {
    // This class will not have any code inside it. It serves as the entry point for running Cucumber tests.

}


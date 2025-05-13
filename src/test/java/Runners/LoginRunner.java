package Runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features={"Features/myLogin.feature"},
glue={"stepDefinitions"},

plugin = {
        "pretty",
        "html:target/cucumber-report.html",
        "json:target/cucumber-report.json"
    },
		
	//dryRun=true
		
	tags="@datatable"	
		
		
		
		)





public class LoginRunner {

}

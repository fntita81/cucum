Feature: MyAccount-Login Feature
  Description: This feature will test a Login feature

  @test
	Scenario: Log-in with valid username and password 
	Given Open the browser
	When Enter the URL "http://practice.automationtesting.in/"
	And Click on My Account Menu
	And Enter registered username and password
	And Click on login button
	Then User must successfully login to the web page
	
	
	@smoke
	Scenario Outline: Log-in with invalid multiple credentials
	Given Open the browser
	When Enter the URL "http://practice.automationtesting.in/"
	And Click on My Account Menu
	When Enter invalid credentials "<username>" and password "<password>"
	And Click on login button
	Then User must not login succesfully 
	
		Examples:
	| username         | password         |
	| pavanoltraining  | Test@selenium |
	| pavanoltrainings | Test@selenium123 |

	
	@datadriven
	Scenario Outline: Log-in with multiple valid credentials
	Given Open the browser
	When Enter the URL "http://practice.automationtesting.in/"
	And Click on My Account Menu
	And Enter valid credentials "<username>" and password "<password>"
	And Click on login button
	Then User must successfully login to the web page
	
	Examples:
	| username        | password         |
	| pavanoltraining | Test@selenium123 |
	| pavanoltraining | Test@selenium123 |

	
	  @datatable
  Scenario: Login with multiple credentials using data table
    Given Open the browser
    When Enter the URL "http://practice.automationtesting.in/"
    And Click on My Account Menu
    And Enter the following credentials:
      | username         | password         |
      | pavanoltraining  | Test@selenium123 |
      | pavanoltraining  | Test@selenium123 |
    And Click on login button
    #Then User must successfully login to the web page
    #And I click on logout


	
	
	
	
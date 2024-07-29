This Repo has Test scripts for Rest Assured Automation for APIs

    We have used a demo Website of bookstore : https://bookstore.toolsqa.com/swagger/

This Repo Contains API automation for below API calls :

User Modification APIs -

    1. POST call to create a new user
    2. DELETE call to delete the user
    3. GET call to retrieve user data
    4. PUT call to update user data
    
Example Books APIs automated -
    
    1. GET call to get a list of books in bookstore
    2. POST call to add a book to user's cart
    3. DELETE call to clear user's cart
    4. PUT call to update user's book data 
    
Additional features used :

    * POJO class is used to send request and validate response
    * RequestSpecification is used to set headers,path params, querry params
    * Test Data class is used to store static data (i.e URLs, credentials, Error Messages)
    * ThreadLocalContext is used to set and get the values reused in the test case
    * Hooks class is used to set auth token before execution of certain TCs
    

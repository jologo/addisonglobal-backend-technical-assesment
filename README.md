This project consists of Akka Http with Scala.

A Rest API(Http Method - POST) i.e. http://<host>:<port>/issuetoken is being exposed in order to generate 
a random user token which consists of the user name provided by the user followed by the current
time stamp in a specific format.

The api take username and password as input, validate the credentials and if the validation is 
successful generates the user token as mentioned above.

The api is also able to handle error scenarios like:
1. Invalid Http Method
2. Invalid url
3. Resource not found
4. Invalid input
5. Empty input(s) etc.

Test cases including both positive and negative scenarios are being taken care.
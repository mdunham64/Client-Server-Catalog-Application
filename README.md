# Domainlogic #
``This package handles all functions that manipulate products within the catalog.``
<br />
<br />
*The domain logic can be found in the domainLogic package. All of that logic works as it's
supposed to. 

# Store #
``This package handles the catalog manipulation both from an Admin side and User side.``
<br />
<br />
*There is a lot of logic in the Store package. All of the logic is correct but some of the functions
should have been made to handle strings. As time ran down, we opted to handle a couple of the "easier" 
requests in the clientWorker class, specifically in the processClientRequest method, in lieu of rewriting
functions. 


# Server #
``This package handles all connections between server and client.``
<br />
<br />
*The server and client connect perfectly. An you play in the app, you can leave the serverApplication
on your console to watch it handle requests. 

*There is on one glitch that we could not resolve. I cannot get to the bottom of why line 253 does not
update the user email in  the client worker class. It's a smallish issue but it creates a problem because
the user is never logged in as a unique user. All new orders are created for the same 'default' user.  


# Application #
``This package handles the UI for admin and users.``
<br />
<br />
All of this works as it should besides the issue mentioned above. 

To use our app, follow these steps: 
1) Launch the serverApplication. Press 1 to load from the .ser file in the src folder. 
2) Login the server with localhost at port 10001. 
3) only one Admin login is saved in the .ser file. email Oleksiy@bestta.com / password is password
4) If you make new products or users, be sure to safely exit the app. 
5) To launch the customer app, us the same server logins and you can use jake@mac.com password: 1234 or
    you can make a user in thee admin app. Safely exit on the last tab to save. 
    
    Programmed by: 
            Jake Bush
            Ronald Jenkins
            MacGyverDunham
            Savannah Murphy
#Client Server Protocol
##The System
The client-server protocol that Space Shooter use for it's TCP communication with the remote server was developed specifically for this project in mind. The system could really be used for any high-score game however. The client and server exchange simple Strings that contain the information needed. There are two queries that can be sent by the client and handled by the server:


##Insert Highscore Query
This query starts with the word ***insert*** and is followed by two additional parameters, ***name*** and ***score***. All parts of the query are separated by *:*. The query looks like follows when sent to the server:

***insert:[name]:[score]***

The server receives this query and checks the first parameter. Since it's ***insert*** in this case, the server breaks down the String into it's parts and stores the name and score in a local text file. It then shut downs the connection. This text file is also sorted with every insert to make it easier to generate the second type of query response. Namely the select query.

##Select Query
The Select query is used by the Client to acquire the global highscore list from the server. It is a very simple query:

***select:***

When the server receives the select query it creates the response query as follows:

1. Create a String called response with the content ***table***
2. add to this String all entries in the highscore text file, one by one. Separate each entry with ***:*** and separate name and score with ***%***

A full response containing the list of scores might look like:

***table:Simon%1326:Jonas%872:Anders%674:Josef%431:***

When the client receives this String it is first split between the ***:***. Each entry is then split into name and score. There is a function for this in the Client that does this splitting and returns an array of entries, ready for printing in the high-score list.

# LotrTopTrumps
Lord Of The Rings Themed Top Trumps Game

# How to run
1. Clone the repo using Git.
2. View the GameServer class. The constructor for the Game object takes an integer which sets the number of players in the game. Adjust this value accordingly.
3. Make a telnet connection using [PuTTY](https://putty.org/) with the following configuration.
![Connecting1](/connect1.JPG)
Alternatively use netcat (unix) from the terminal `nc localhost 8888`
4. Enter a username. Once all players are connected, the game will start and instructions will be provided.
![Connecting2](/connect2.JPG)

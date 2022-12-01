# Marble Solitaire

There are three board types: English, European, Triangular\
Here are the starting views for the default board types:\
<img width="98" alt="english" src="https://user-images.githubusercontent.com/94412143/204557547-792e891a-5187-471c-845c-e33070a47987.png"> <img width="98" alt="european" src="https://user-images.githubusercontent.com/94412143/204557607-f2514d4e-5268-4272-b844-f167468cf078.png"> <img width="77" alt="triangular" src="https://user-images.githubusercontent.com/94412143/204557620-6a60a4dc-4945-4175-8db9-0f2ada691e25.png">


Through command-line arguments, you must specify which board type you would like to use and, optionally, you may specify a different size and starting position for the board.\
Command-line arguments should be formatted as follows: "[boardType] (-size [size]) (-hole [row col])"\
• Note: the order of the size and hole specifications does not matter

Please familiarize yourself with the structure and size specifications of the board types: [MarbleSolitaireBoards.xlsx](https://github.com/ben-lischin/MarbleSolitaire/files/10133878/MarbleSolitaireBoards.xlsx)


### Gameplay

#### Moves
Under the board, you can input a move you would like to make\
Moves are of the form: "[from-row] [from-col] [to-row] [to-col]"\
Note that the positions on the board are 1-indexed\
After you enter, an updated board will appear in the terminal, and you can make a new move

A valid move is an orthogonal jump of 2 slots, starting from a marble, jumping over a marble, and landing in an empty space on the board\
• Note: for the Triangular board, you may additionally make diagonal moves

#### End game
• At any point, you may enter a move containing "q" to quit the game\
• You win when there is only one marble remaining on the board\
• If you run out of moves before then, the game is over

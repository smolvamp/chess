# Chess Game

This is a chess game developed using Java, currently available as a desktop version with offline player-versus-player (PvP) functionality.

## Features

### Current Features:
- **Offline PvP**: Play locally against another player on the same device, with no need for an internet connection.
- **Classic Chess Rules**: The game follows the official rules of chess, including special moves like castling, en passant, and pawn promotion.
- **User Interface**: A clean, minimalistic, and easy-to-use interface for smooth gameplay. It includes a simple chessboard and clearly labeled pieces.
- **Drag and Drop Pieces**: Move your chess pieces effortlessly with the drag-and-drop functionality.
- **Piece Movement Validations**: Ensures that each move follows the rules of chess (such as only moving within the piece's allowed pattern).

### Planned Features:
- **Single Player Mode**: Play against an AI opponent.
- **Online PvP**: Connect with other players over the internet.
- **Move History**: Keep track of your moves during the game.

## Getting Started

### Prerequisites
To run the desktop version, ensure you have the following installed:
- **Java 8 or later**: Ensure that Java is installed on your system. You can download it from [here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
- **IDE**: Use an IDE like IntelliJ IDEA, Eclipse, or NetBeans for running or modifying the code.

### Running the Game
1. Clone or download the repository.
   ```bash
   git clone https://github.com/smolvamp/chess.git

2. Build the Project (optional): If you'd like to build the game from source, navigate to the project folder and run the following commands:
```
   cd chess
   javac -d bin src/*.java
   ```
3. Run the Game: Alternatively, if you're just looking to play the game, simply navigate to the folder containing the .jar file and run:
```
   java -jar chess/chess.jar
   ```


## Troubleshooting

   Java Version Issues: Ensure that you have Java 8 or later installed on your machine.(If you encounter errors, check your Java version by running java -version in the terminal or command prompt).
   
   Missing .jar File: If you're building from source, ensure that the build process was successful and the .jar file exists in the project folder.
   
## Contributing

We welcome contributions! If you'd like to improve the game, fix bugs, or add features, follow these steps:

   Fork the repository.
   Clone your fork to your local machine.
   Make your changes.
   Commit your changes and push to your fork.
   Open a pull request to the main repository.

## License

This project is licensed under the MIT License. See the [LICENSE file](https://github.com/smolvamp/chess/blob/main/LICENSE) for details.

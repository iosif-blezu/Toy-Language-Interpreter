#ToyLanguageInterpreter
Welcome to the ToyLanguageInterpreter project! This project is a custom-built "toy" language interpreter implemented in Java, featuring a rich set of functionalities aimed at demonstrating various programming concepts and design patterns. It's a great tool for educational purposes, helping to understand the inner workings of programming languages and interpreters.

#Features
ToyLanguageInterpreter supports a wide array of features, making it a versatile tool for learning and experimentation:

Execution of Logical, Arithmetical, and Relational Expressions: Enables complex computations and logical operations.
Variable Declaration & Assignment: Supports basic programming constructs for variable management.
Printing Operations: Allows outputting results and messages, aiding in debugging and program interaction.
Control Structures: Includes if conditions and while loops for flow control.
File Operations: Supports opening, closing, and reading from files, integrating file handling into the language.
Fork (Multi-Threading): Introduces concurrency, allowing the language to execute multiple threads.
Heap Allocation, Reading, and Writing: Manages dynamic memory allocation, enabling complex data manipulation.
Garbage Collection: Automatic memory management to free up unused resources.

#Variables Types
The interpreter supports several variable types to accommodate various data handling needs:

Bool: For logical true/false values.
Int: Handles integer numbers.
String: For text manipulation and storage.
Reference: Enables the creation and manipulation of reference types, aiding in complex data structures.

#Core Functionalities
The architecture of the ToyLanguageInterpreter includes several key components designed to mimic the functionality of real-world programming languages:

Execution Stacks: Stores instructions for each program state, managing the flow of execution.
Symbol Tables: Holds local variables for each program state, allowing for variable tracking and scope management.
File Table: Manages BufferedReader objects for file operations, centralizing file access and manipulation.
Shared Heap: A global memory space for all states, facilitating dynamic data allocation and access.
Output Table: Collects printing outputs, centralizing messages and results from the program execution.
Unique Program States: Each state is identified by a unique ID and contains its own execution stack and symbol table, supporting concurrency through forking.

#Design Patterns and Concepts
The project is built on a foundation of solid software engineering principles and patterns:

Layered Architecture: Separates concerns into distinct layers, enhancing modularity and maintainability.
JavaFX for GUI: Provides a graphical user interface, making the interpreter more accessible and user-friendly.
Encapsulation and Interfaces: Encourages clean code practices and robust design.
MVC Pattern: Adopts the Model-View-Controller pattern, organizing code into logical components for easier management and development.
Streams: Utilizes Java streams for efficient data manipulation and processing.

# Task #1
## Solution requirements

Please solve the following task. The solution should be structured as a small sbt or scala-cli project using Scala 3. The code should compile, be runnable and produce results. You are encouraged to use as many Scala 3 features as you like. Please create a GitHub (or any other online code hosting platform) repository and share the link in your internship application. Please refrain from using AI and code generation tools while solving this task, as well as during the interview. We hope you have fun with the task.

## 3-bit computer

The task is to simulate a 3-bit computer given the following requirements.

The computer executes programs which are described as a list of 3-bit numbers (0, 1, 2, 3, 4, 5, 6 and 7).

Additionally, the computer also has three registers named X, Y and Z. These registers are not limited to 3-bit numbers, they can hold any integer value instead.

The computer recognizes eight different instructions, each identified by a 3-bit number called the instruction’s opcode. Following each instruction is another 3-bit number, called the instruction’s operand, serving as the instruction’s input.

For example, the program 0,1,2,3 would run the instruction with opcode 0 and pass 1 as its operand. Then, it would run the instruction with opcode 2 and pass 3 as its operand.

The computer has a number called the instruction pointer, which identifies the position in the program from which the next opcode will be read. The instruction pointer starts with the value 0, pointing to the first 3-bit number in the program. Except for jump instructions (explained later), the instruction pointer increases by 2 after each instruction is processed (to move past the instruction’s opcode and its operand). If the computer tries to read past the end of the program, it halts.

In the 0,1,2,3 program example, after running the instruction with opcode 2 and passing 3 as its operand, the computer halts.

Operands can have one of two types (the type of the operand is specified by each instruction later). Operands can be literal operands and combo operands.

The value of the literal operand is the operand itself. For example, the value of the literal operand 5 is the number 5.

The value of the combo operand is defined as follows:

- Combo operands 0, 1, 2 and 3 represent literal values 0, 1, 2 and 3 respectively.
- Combo operand 4 represents the value of register X.
- Combo operand 5 represents the value of register Y.
- Combo operand 6 represents the value of register Z.
- 7 is not a valid value for combo operands and does not appear in valid programs.


The eight instructions are defined as follows:

- The xdv instruction (opcode 0) performs division. The numerator is the value in the X register. The denominator is found by raising 2 to the power of the instruction’s combo operand. (So, an operand of 2 would divide X by 4 (2^2); an operand of 5 would divide X by 2^Y.) The result of the division operation is truncated to an integer and written to the X register.
- The yxl instruction (opcode 1) calculates the bitwise XOR of register Y and the instruction’s literal operand, then stores the result in register Y (overwrites the previous value).
- The yst instruction (opcode 2) calculates the value of its combo operand modulo 8 (thereby keeping only its lowest 3 bits), then writes that value to the Y register.
- The jnz instruction (opcode 3) does nothing if the X register is 0. However, if the X register is not zero, it jumps by setting the instruction pointer to the value of its literal operand. If this instruction jumps, the instruction pointer is not increased by 2 after this instruction.
- The yxz instruction (opcode 4) calculates the bitwise XOR of the register Y and register Z, then stores the result in register Y. For preserving consistency between all instructions, this instruction reads an operand but ignores it, i.e. the instruction pointer still needs to be increased by 2 after the instruction is executed.
- The out instruction (opcode 5) calculates the value of its combo operand modulo 8, then outputs that value.
- The ydv instruction (opcode 6) works exactly like the xdv instruction except that the result is stored in the Y register. The numerator is still read from the X register. As is the case for the xdv instruction, the operand of the ydv instruction is also a combo operand.
- The zdv instruction (opcode 7) works exactly like the xdv instruction except that the result is stored in the Z register. The numerator is still read from the X register. As is the case for the xdv instruction, the operand of the zdv instruction is also a combo operand.

Here are some examples of instruction operations:

- If register Z contains 9, the program 2,6 would set register Y to 1.

- If register X contains 10, the program 5,0,5,1,5,4 would output 0,1,2.

- If register X contains 2024, the program 0,1,5,4,3,0 would output 4,2,5,6,7,7,7,7,3,1,0 and leave 0 in register X.

- If register Y contains 29, the program 1,7 would set register Y to 26.

- If register Y contains 2024 and register Z contains 43690, the program 4,0 would set register Y to 44354.

## Tests

The following two starting states are provided. Your task is to initialize the registers to the given values and run the given program. Once the program halts, you should join all values produced by the out instructions into a single string to obtain the expected output.

### Starting state 1

Register X: 3729

Register Y: 0

Register Z: 0

Program: 0,1,5,4,3,0

Expected output: 0,4,2,1,4,2,5,6,7,3,1,0


### Starting state 2

Register X: 8642024

Register Y: 0

Register Z: 0

Program: 0,3,5,4,3,0

Expected output: 5,7,6,5,7,0,4,0

After the task has been submitted, we will check the outputs on additional examples to verify that the simulated computer functions correctly.

During the interview, we will analyze the solution code, discuss the solution and possible improvements and solve another problem connected to this one.
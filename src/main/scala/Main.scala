
import scala.math.pow
import scala.annotation.tailrec
import scala.annotation.init

/** our 3-bit computer */
object Computer:

	/** the state of our computer, with registers and instruction pointer */
	case class State(
		x: Int, y: Int, z: Int,
		ip: Int
	)

	/** opcodes for the instructions */
	enum OpCode:
		case XDV
		case YXL
		case YST
		case JNZ
		case YXZ
		case OUT
		case YDV
		case ZDV
	import OpCode.*

	/** initial state with preset memory */
	def initState(x: Int, y: Int, z: Int): State =
		State(
			x, y, z,
			0	// always start at 0
		)

	/** interpret an operand as literal */
	def asLit(operand: Int): Int =
		operand	

	/** interpret an operand as a combo */
	def asCombo(operand: Int, state: State): Int =
		operand match
			case 0 | 1 | 2 | 3 => operand
			case 4 => state.x
			case 5 => state.y 
			case 6 => state.z
			case _ => throw IllegalArgumentException("operand 7 as combo :(")

	/** execute one instruction of the program */
	def step(state: State, program: List[Int], outputAcc: List[Int]): (Option[State], List[Int]) =
		if state.ip + 1 >= program.length then ( None, outputAcc )	// end of the program
		else	// read and execute instruction
			val opcode = OpCode.fromOrdinal(program(state.ip))
			val operand = program(state.ip + 1)
			val nextState = state.copy(ip = state.ip + 2)	// pre-copy since it's done in every case

			opcode match
				case XDV =>	// 0
					val combOp = asCombo(operand, state)
					val x = (state.x >> combOp).toInt
					( Some(nextState.copy(x = x)), outputAcc )

				case YXL =>	// 1
					val litOp = asLit(operand)
					val y = state.y ^ litOp
					( Some(nextState.copy(y = y)), outputAcc )

				case YST =>	// 2
					val combOp = asCombo(operand, state)
					val y = combOp % 8
					( Some(nextState.copy(y = y)), outputAcc )

				case JNZ =>	// 3
						if state.x == 0 then ( Some(nextState), outputAcc )
						else 
							val litOp = asLit(operand)
							( Some(state.copy(ip = litOp)), outputAcc )

				case YXZ =>	// 4
					val y = state.y ^ state.z
					( Some(nextState.copy(y = y)), outputAcc )

				case OUT =>	// 5
					val combOp = asCombo(operand, state)
					val out = combOp % 8
					( Some(nextState), out :: outputAcc)

				case YDV =>	// 6
					val combOp = asCombo(operand, state)
					val y = (state.x >> combOp).toInt
					( Some(nextState.copy(y = y)), outputAcc )

				case ZDV =>	// 7
					val combOp = asCombo(operand, state)
					val z = (state.x >> combOp).toInt
					( Some(nextState.copy(z = z)), outputAcc )

	/** run the program step by step, stop when arrived at the end */
	@tailrec
	def run(state: State, program: List[Int], output : List[Int] = Nil): List[Int] =
		step(state, program, output) match
			case ( None, out ) => out.reverse
			case ( Some(next), out ) => run(next, program, out)

	@main 
	def main(args: String*) = 

		args match
			case "--tests" :: Nil => 
				// format: (state, input program, expected output)
				val tests = List(
					(initState(3729, 0, 0), 
						List(0, 1, 5, 4, 3, 0), 
						List(0, 4, 2, 1, 4, 2, 5, 6, 7, 3, 1, 0)),
					(initState(8642024, 0, 0), 
						List(0, 3, 5, 4, 3, 0), 
						List(5, 7, 6, 5, 7, 0, 4, 0))
				).foreach { (state, in, out) => 
					println(s"starting test")
					val res = run(state, in)	
					println(s"	initial state: X: ${state.x}, Y: ${state.y}, Z: ${state.z}")
					println(s"	program: ${in}")
					println(s"	expected output: ${out}")
					println(s"	actual output: ${res}")
					println(s"--------------------------------------------")
				}

			case x :: y :: z :: prog :: Nil =>
				val parsed = prog.split(',').toList.map(s => s.trim().toInt)
				val out = run(initState(x.toInt, y.toInt, z.toInt), parsed)
				println(s"starting test")
				println(s"	initial state: X: ${x}, Y: ${y}, Z: ${z}")
				println(s"	program: ${parsed}")
				println(s"	actual output: ${out}")
				println(s"--------------------------------------------")

			case "-h" :: Nil | "--help" :: Nil | _ =>
				println(
					"""|usage:
					|  run <X> <Y> <Z> <program>  execute with given registers (program is a list of comma-separated ints)  
					|  run --tests                run built-in tests
					|  run --help, run -h         print this 
					|""".stripMargin
				)
				
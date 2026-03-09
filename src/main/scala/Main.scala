
import scala.math.pow
import scala.annotation.tailrec

/** our 3-bit computer */
object Computer:

	/** the state of our computer, with registers and intruction pointer */
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

	/** initial state, assuming empty memory */
	def initState(): State =
		State(
			0, 0, 0,
			0
		)

	/** interprets an operand as literal */
	def asLit(operand: Int): Int =
		operand	

	/** interprets an operand as a combo */
	def asCombo(operand: Int, state: State): Int =
		operand match
			case 0 | 1 | 2 | 3 => operand
			case 4 => state.x
			case 5 => state.y 
			case 6 => state.z
			case _ => throw IllegalArgumentException("operand 7 as combo :(")

	/** execute one instruction of the program */
	def step(state: State, program: List[Int], output: List[Int]): (Option[State], List[Int]) =
		if state.ip + 2 >= program.length then ( None, output )	// end of the program
		else	// read instructions
			val opcode = OpCode.fromOrdinal(program(state.ip))
			val operand = program(state.ip + 1)
			val nextState = state.copy(ip = state.ip + 2)	// pre-copy since it's done everywhere

			opcode match
				case XDV =>	// 0
					val combOp = asCombo(operand, state)
					val x = (state.x >> combOp).toInt
					( Some(state.copy(x = x)), output )
				
				case YXL =>	// 1
					val litOp = asLit(operand)
					val y = state.y ^ litOp
					( Some(state.copy(y = y)), output )
				
				case YST =>	// 2
					val combOp = asCombo(operand, state)
					val y = combOp % 8
					( Some(state.copy(y = y)), output )
				
				case JNZ =>	// 3
						if state.x == 0 then ( Some(state), output )
						else 
							val litOp = asLit(operand)
							( Some(state.copy(ip = litOp)), output )
				
				case YXZ =>	// 4
					val y = state.y ^ state.z
					( Some(state.copy(y = y)), output )
				
				case OUT =>	// 5
					val combOp = asCombo(operand, state)
					val out = operand % 8
					( Some(state), output :+ out)
				
				case YDV =>	// 6
					val combOp = asCombo(operand, state)
					val y = (state.x >> combOp).toInt
					( Some(state.copy(y = y)), output )
				
				case ZDV =>	// 7
					val combOp = asCombo(operand, state)
					val z = (state.x >> combOp).toInt
					( Some(state.copy(z = z)), output )

	/** runs the program step by step, stops when arrived at the end */
	@tailrec
	def run(state: State, program: List[Int], output: List[Int]): List[Int] =
		step(state, program, Nil) match
			case ( None, out ) => out
			case ( Some(next), out ) => run(next, program, out)

	@main 
	def main() = 
		// parse args
		// init state
		// run
		//! output -- check requirements

		???	// todo duh




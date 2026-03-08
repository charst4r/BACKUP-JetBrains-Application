
import scala.math.pow

object Computer:

	case class State(
		x: Int, y: Int, z: Int,	// regs
		ip: Int,	// instr pointer
	)

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

	def initState(p: List[Int]): State =
		State(
			0, 0, 0,	// assuming empty regs
			0, 
			// p
		)

	def asLit(operand: Int): Int =
		operand	

	def asCombo(operand: Int, state: State): Int =
		operand match
			case 0 | 1 | 2 | 3 => operand
			case 4 => state.x
			case 5 => state.y 
			case 6 => state.z
			case _ => throw IllegalArgumentException("operand 7 as combo :(")

	def step(state: State, program: List[(OpCode, Int)], output: List[Int]): Option[State] =
		program match
			case Nil | _ :: Nil => None
			case (opcode, operand) :: rem =>
				opcode match
					case XDV =>	// 0
						val combOp = asCombo(operand, state)
						val x = (state.x / pow(2, combOp)).toInt
						Some(State(x, state.y, state.z, state.ip + 2))
					case YXL =>	// 1
						val litOp = asLit(operand)
						val y = state.y ^ litOp
						Some(State(state.x, y, state.z, state.ip + 2))
					case YST =>	// 2
						val combOp = asCombo(operand, state)
						val y = combOp % 8
						Some(State(state.x, y, state.z, state.ip + 2))
					case JNZ =>	// 3
						Some(
							if state.x == 0 then state
							else State(state.x, state.y, state.z, asLit(operand))
						)
					case YXZ =>	// 4
						val y = state.y ^ state.z
						Some(State(state.x, y, state.z, state.ip + 2))
					case OUT =>	// 5
						val combOp = asCombo(operand, state)
						val output = operand % 8
						???	// todo output
					case YDV =>	// 6
						val combOp = asCombo(operand, state)
						val y = (state.x / pow(2, combOp)).toInt
						Some(State(state.x, y, state.z, state.ip + 2))
					case ZDV =>	// 7
						val combOp = asCombo(operand, state)
						val z = (state.x / pow(2, combOp)).toInt
						Some(State(state.x, state.y, z, state.ip + 2))

	def run(state: State, program: List[(OpCode, Int)]): State =
		step(state, program, Nil) match
			case Some(s) => 
				???
			case None => ???



	@main 
	def main() = 
		// parse args
		// init state
		// run
		//! output -- check requirements

		???	// todo duh




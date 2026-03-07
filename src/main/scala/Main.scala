
object Computer:

	case class State(
		x: Int, y: Int, z: Int,	// regs
		ip: Int,	// instr pointer
		program: List[Int]
	)

	def initState(p: List[Int]): State =
		State(
			0, 0, 0,	// assuming empty regs
			0, 
			p
		)

	//? could be a case class for modularity? no need here tho?
	def asLit(operand: Int): Int =
		operand	

	//? same
	def asCombo(operand: Int, state: State): Int =
		operand match
			case 0 | 1 | 2 | 3 => operand
			case 4 => state.x
			case 5 => state.y 
			case 6 => state.z
			case _ => throw IllegalArgumentException("operand 7 as combo :(")

	def step(state: State): State =
		???	// todo
		// also make this tailrec
		//? check how to do in scala3 
		//? also make this return opt[state] for halt maybe?

	def run(state: State): State =
		step(state)	// todo: refine this for halting

	@main 
	def main() = 
		// parse args
		// init state
		// run
		//! output -- check requirements

		???	// todo duh




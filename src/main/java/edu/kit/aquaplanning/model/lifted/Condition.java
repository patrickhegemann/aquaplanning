package edu.kit.aquaplanning.model.lifted;

import java.util.ArrayList;
import java.util.List;

public class Condition extends AbstractCondition {

	private Predicate predicate;
	private List<Argument> arguments;
	private boolean negated;
	
	public Condition(Predicate predicate) {
		super(ConditionType.atomic);
		this.predicate = predicate;
		this.arguments = new ArrayList<>();
	}
	
	public Condition(Predicate predicate, boolean negated) {
		super(ConditionType.atomic);
		this.predicate = predicate;
		this.negated = negated;
		this.arguments = new ArrayList<>();
	}
	
	public void addArgument(Argument arg) {
		this.arguments.add(arg);
	}
		
	public Predicate getPredicate() {
		return predicate;
	}

	public int getNumArgs() {
		return arguments.size();
	}
	
	public List<Argument> getArguments() {
		return arguments;
	}

	public boolean isNegated() {
		return negated;
	}
	
	public Condition withoutNegation() {
		
		Condition c = new Condition(predicate);
		arguments.forEach(arg -> c.addArgument(arg));
		return c;
	}
	
	@Override
	public Condition getConditionBoundToArguments(List<Argument> refArgs, List<Argument> argValues) {
		
		Condition newCondition = new Condition(predicate);
		newCondition.negated = negated;
		for (int condArgIdx = 0; condArgIdx < arguments.size(); condArgIdx++) {
			Argument condArg = arguments.get(condArgIdx);
			if (!condArg.isConstant()) {
				boolean isBound = false;
				for (int refArgIdx = 0; refArgIdx < refArgs.size(); refArgIdx++) {
					Argument refArg = refArgs.get(refArgIdx);
					if (refArg.getName().equals(condArg.getName())) {
						newCondition.addArgument(argValues.get(refArgIdx));
						isBound = true;
						break;
					}
				}
				if (!isBound) {
					// The variable is not bound by the provided arguments;
					// it must be part of a quantification
					newCondition.addArgument(condArg);
				}
			} else {
				newCondition.addArgument(condArg);
			}
		}
		return newCondition;
	}
	
	public Condition copy() {
		
		Condition c = new Condition(predicate);
		arguments.forEach(arg -> c.addArgument(arg.copy()));
		c.negated = negated;
		return c;
	}
	
	
	@Override
	public String toString() {
		String out = "";
		if (negated)
			out += "¬";
		out += predicate.getName() + "( ";
		for (Argument arg : arguments) {
			out += arg.getName() + " ";
		}
		out += ")";
		return out;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arguments == null) ? 0 : arguments.hashCode());
		result = prime * result + (negated ? 1231 : 1237);
		result = prime * result + ((predicate == null) ? 0 : predicate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Condition other = (Condition) obj;
		if (arguments == null) {
			if (other.arguments != null)
				return false;
		} else if (!arguments.equals(other.arguments))
			return false;
		if (negated != other.negated)
			return false;
		if (predicate == null) {
			if (other.predicate != null)
				return false;
		} else if (!predicate.equals(other.predicate))
			return false;
		return true;
	}
	
	
}
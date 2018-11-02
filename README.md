# Aquaplanning – QUick Automated Planning

This is a Java framework for Automated Planning, developed by Tomáš Balyo and Dominik Schreiber for the lecture [Automated Planning and Scheduling](https://baldur.iti.kit.edu/plan/) at Karlsruhe Institute of Technology (KIT). It is meant as a simple, but extensible and reasonably powerful planning environment for PDDL problems, for educational and any other means.

## Features

Aquaplanning supports PDDL (Planning Domain Description Language) files as an input for planning domains and problems. The following features are implemented as of now (i.e. problems with these features can be parsed and grounded):

* Basic STRIPS planning with typing
* Negative goals
* Equality (as a universal predicate `(= obj1 obj2)`)
* Conditional effects (`when (...) (...)`)
* Universal quantifications (inside preconditions, effects, initial state and goal)
* Action costs (in its basic form `(:function total-cost - number)`, with constant positive cost per operator)

These features (minus any mentioned restrictions) are more or less equivalent with the following [PDDL 3.1](https://helios.hud.ac.uk/scommv/IPC-14/repository/kovacs-pddl-3.1-2011.pdf) requirements:
	
	:strips :typing :negative-preconditions :conditional-effects :equality :universal-preconditions :action-cost

For planning problems using these features (or any subset), a full representation of the read problem is available in the form of Java objects after parsing, as well as a separate representation after grounding the problem.

Currently, Aquaplanning features two grounding procedures: a naïve full enumeration of atoms and actions, and a traversal of the problem's (delete-)relaxed planning graph.

A trivial forward search is provided as a planner. (Better algorithms will be added in the future.) At the end of the planning pipeline, a tiny plan validator can be employed to ensure the planner's correctness.

## Building and installing

The framework is built using Maven. E.g. using Eclipse, the project can be directly imported as a Maven project and then built and/or installed. The framework is written from scratch and only depends on antlr4 (for the parsing of PDDL files) and JUnit for tests. Maven should take care of these dependencies.

## Usage

Aquaplanning can be used as an off-the-shelf planner; you can specify a domain file and a problem file as arguments (in that order), and it will attempt to parse, ground, and solve the problem. You can try the files provided in the `testfiles/` directory.

When you want to use your own planner, implement the Planner interface and take a look at the DefaultPlanner class as a point of reference.

If you find any bugs or you consider something to be missing, please let us know. We appreciate receiving issues and/or pull requests!

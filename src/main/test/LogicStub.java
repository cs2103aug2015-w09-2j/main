package main.test;

import java.text.ParseException;

import main.Logic;

public class LogicStub extends Logic{
	private LogicStub logic;

	public LogicStub() {
		super();
	}

	@Override
	public boolean processCommand(String input) throws NoSuchFieldException, ParseException {
		if (input.equals("true"))
			return true;
		else
			return false;
	}

	public LogicStub getInstance() {
		if (logic == null) {
			logic = new LogicStub();
		}
		return logic;
	}
}

package compiler;

import java.util.ArrayList;

public class Record {

	private String name,
			type, //the type of the record
			variableKind, //normal or parameter
			kind, // variable, function, class
			structure; //simple, array, class
	private Table local;
	private ArrayList<Integer> dimensions;
	private ArrayList<String> parameters;
	private int line; //the line at which the record is found
	private long address;

	private Table classLocal; //the table inherited from its type

	public Record(int line) {

		this.line = line;
		classLocal = new Table();
		dimensions = new ArrayList<>();
		parameters = new ArrayList<>();
		address = 0;
	}

	public Record(Table parentTable, int line) {

		this.line = line;
		local = new Table(parentTable);

	}

	public Table getClassLocal() {
		return classLocal;
	}

	public void setClassLocal(Table classLocal) {
		this.classLocal = classLocal;
	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setVariableKind(String variableKind) {
		this.variableKind = variableKind;
	}

	public Table getLocal() {
		return local;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public ArrayList<Integer> getDimensions() {
		return dimensions;
	}

	public ArrayList<String> getParameters() {
		return parameters;
	}

	public int getLine() {
		return line;
	}

	//ensures that two records are equal
	public boolean equalsRecord(Record record) {

		return kind.equals(record.kind)
				&& name.equals(record.name)
				&& (kind.equals("class")
					|| kind.equals("variable")
					|| (kind.equals("function")
						&& this.equalsFunction(record)));

	}

	// Verifies that the record is a reference to another function
	// At this level of complexity we can only verify that the number of parameters
	// are the same, as there is no way of fully resolving all types at runtime.
	// Therefore, two functions are identical if they have the same number of parameters.
	public boolean equalsFunction(Record function) {
		return parameters.size() == function.parameters.size();
	}

	//Return the Record as a string
	//The tabs are for formatting purposes, so that the local table can be indented under the record it belongs to
	public String toString(int numOftabs) {

		String tabs = "";

		for (int i = 0; i < numOftabs; ++i)
			tabs += "\t";

		numOftabs++;

		return tabs + "Record [name=" + name + ", type=" + type + ", variableKind="
				+ variableKind + ", kind=" + kind + ", structure="
				+ structure + ", dimensions="
				+ dimensions + ", parameters=" + parameters + ", address=" + address
				+ "]\n"
				+ local.toString(numOftabs) + "\n";

	}

}
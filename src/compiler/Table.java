package compiler;

import java.util.ArrayList;
import java.util.HashMap;

public class Table {

	private ArrayList<Record> records; //all defined records
	private HashMap<String, Table> validTypes; //a list of all valid types
	private Table parentTable;	// the parent table of this scope
	
	public Table() {
		
		records = new ArrayList<>();
		validTypes = new HashMap<>();
		validTypes.put("int", null); //int is a built-in type
		validTypes.put("float", null); //float is a built-in type
		
	}
	
	public Table(Table parentTable) {
		
		records = new ArrayList<>();

		validTypes = parentTable.validTypes; //propagate valid types downward
		this.parentTable = parentTable; //make the upper scope visible
		
	}

	//Inserts a new Record into the scope
	public void insert(Record record, boolean parsedComplete) {
		
		if(parsedComplete)
			return;

		if (record.getName().equals("program")) {

			//The record is the main program
			records.add(record);
			return;
			
		}

		//The record is not a class and the type is not defined yet
		if (!record.getKind().equals("class") && !isValidType(record))
			Outputter.errorStrings.append("Error: Undefined type ").append(record.getType()).append(" for ")
					.append(record.getKind()).append(" ").append(record.getName()).append(" at line ")
					.append(record.getLine()).append("\n");

		//The record is already defined
		else if (alreadyDefined(record)) {

			//The record is an already defined function
			//We must output the error with all its parameters
			if (record.getKind().equals("function")) {

				Outputter.errorStrings.append("Duplicate declaration of ").append(record.getKind()).append(" ")
						.append(record.getName()).append("(");

				for (int i = 0; i<record.getParameters().size(); i++) {

					if(i == record.getParameters().size() - 1)
						Outputter.errorStrings.append(record.getParameters().get(i));
					else
						Outputter.errorStrings.append(record.getParameters().get(i)).append(", ");

				}

				Outputter.errorStrings.append(") at line").append(record.getLine()).append("\n");

			}

			//The record is an already defined variable or class
			else
				Outputter.errorStrings.append("Duplicate declaration of ").append(record.getKind()).append(" ").append(record.getName()).append(" at line ").append(record.getLine()).append("\n");
		}

		//The record is unique and can be added to the scope
		else {

			records.add(record);

			//The record is a new type that must be added to the list of valid types
			if (record.getKind().equals("class"))
				validTypes.put(record.getName(), record.getLocal());

			//The record is a variable or function which should have its class's table assigned to it
			else
				record.setClassLocal(getClassScope(record.getType()));


		}
		
	}

	//Check if the record is found in the HashMap of valid types
	private boolean isValidType(Record record) {

		for (String validType : validTypes.keySet())
			if (record.getType().equals(validType))
				return true;

		return false;

	}

	public boolean alreadyDefined(Record record) {

		for (Record record1 : records)
			if (record.equalsRecord(record1))
				return true;
		
		return false;
		
	}

	public boolean search(Record record) {
		
		if(record.getKind().equals("variable")) {

			if (record.getStructure().equals("array")) {

				for (Record record1 : records)
					if (record.getName().equals(record1.getName()) && record.getDimensions().size() == record1.getDimensions().size())
						return true;

				if (parentTable!=null)
					return parentTable.search(record);

				Outputter.errorStrings.append("Array " + record.getName());

				for (int i = 0; i<record.getDimensions().size(); i++)
					Outputter.errorStrings.append("[]");

				Outputter.errorStrings.append(" is undefined").append("\n");

				return false;

			}

			else {

				for (Record record1 : records)
					if (record.getName().equals(record1.getName()))
						return true;

				if (parentTable!=null)
					return parentTable.search(record);

				Outputter.errorStrings.append("Error: Variable " + record.getName() + " is undefined").append("\n");

				return false;

			}

		}
		
		else if (record.getKind().equals("function")) {

			for (Record record1 : records)
				if (record.getName().equals(record1.getName()) && record.getParameters().size() == record1.getParameters().size())
					return true;

			if (parentTable!=null)
				return parentTable.search(record);

			Outputter.errorStrings.append("Error: Function " + record.getName() + "(");

			for (int i = 0; i<record.getParameters().size(); i++) {

				if (i == record.getParameters().size() - 1)
					Outputter.errorStrings.append("parameter");

				else
					Outputter.errorStrings.append("parameter, ");

			}

			Outputter.errorStrings.append(") is undefined").append("\n");

			return false;

		}
		
		return false;
		
	}

	//Returns the class scope of a particular type
	public Table getClassScope(String type) {

		for (String typeName : validTypes.keySet())
			if (typeName.equals(type))
				return validTypes.get(typeName);
		
		if (parentTable!=null)
			return parentTable.getClassScope(type);
		
		return null;
		
	}

	//Returns the called variable
	public Record getVariable(Record variable, boolean global) {

		for (Record record : records)
			if (record.getKind().equals("variable") && record.getName().equals(variable.getName()))
				return record;
		
		if (global && (parentTable!=null))
			return parentTable.getVariable(variable, true);
		
		return null;
		
	}

	//returns the called function
	public Record getFunction(Record functionCall, boolean global) {

		for (Record record : records)
			if (record.equalsFunction(functionCall))
				return record;
		
		if (global && parentTable!=null)
			return parentTable.getFunction(functionCall, true);

		return null;
		
	}
	
	public String toString(int numOfTabs) {
		
		String wholeTable = "";

		for (Record record : records)
			wholeTable += record.toString(numOfTabs);
		
		return wholeTable;
		
	}
	
}
package compiler;

import compiler.structures.*;
import compiler.structures.Class;
import compiler.structures.expressions.*;
import compiler.structures.statements.*;
import ui.Outputter;

import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
	
	//FIRST and FOLLOW sets
	private static final String[] 

			FIRST_prog = {"CLASS", "PROGRAM"},
			FOLLOW_prog = {"EOF"},

			FIRST_classDeclList = {"CLASS"},
			FOLLOW_classDeclList = {"PROGRAM"},

			FIRST_classMemberDeclPRIME = {"OBRACKET", "OPAREN", "SEMICOLON"},
				FIRST_classMemberDeclPRIME_RHS1 = {"OBRACKET", "SEMICOLON"},
				FIRST_classMemberDeclPRIME_RHS2 = {"OPAREN"},
			FOLLOW_classMemberDeclPRIME = {"ID", "INT", "FLOAT", "CBRACE"},

			FIRST_classMemberDeclList = {"ID", "INT", "FLOAT"},
			FOLLOW_classMemberDeclList = {"CBRACE"},

			FIRST_funcDefList = {"ID", "INT", "FLOAT"},
			FOLLOW_funcDefList = {"EOF"},

			FIRST_funcMember = {"IF", "FOR", "GET", "PUT", "RETURN", "ID", "FLOAT", "INT"},
				FIRST_funcMember_RHS1 = {"IF", "FOR", "GET", "PUT", "RETURN"},
				FIRST_funcMember_RHS2 = {"ID"},
				FIRST_funcMember_RHS3 = {"FLOAT", "INT"},
			FOLLOW_funcMember = {"SEMICOLON"},

			FIRST_funcMemberPRIME = {"OBRACKET", "DOT", "DEF", "ID"},
				FIRST_funcMemberPRIME_RHS1 = {"ID"},
				FIRST_funcMemberPRIME_RHS2 = {"OBRACKET", "DOT", "DEF"},
			FOLLOW_funcMemberPRIME = {"SEMICOLON"},

			FIRST_funcMemberList = {"INT", "FLOAT", "ID", "IF", "FOR", "GET", "PUT", "RETURN"},
			FOLLOW_funcMemberList = {"CBRACE"},

			FIRST_arraySizeList = {"OBRACKET"},
			FOLLOW_arraySizeList = {"SEMICOLON", "CPAREN", "COMMA"},

			FIRST_statement = {"IF", "FOR", "GET", "PUT", "RETURN", "ID"},
				FIRST_statement_RHS1 = {"ID"},
				FIRST_statement_RHS2 = {"IF", "FOR", "GET", "PUT", "RETURN"},
			FOLLOW_statement = {"SEMICOLON"},

			FIRST_statementPRIME = {"IF", "FOR", "GET", "PUT", "RETURN"},
				FIRST_statementPRIME_RHS1 = {"RETURN"},
				FIRST_statementPRIME_RHS2 = {"PUT"},
				FIRST_statementPRIME_RHS3 = {"GET"},
				FIRST_statementPRIME_RHS4 = {"IF"},
				FIRST_statementPRIME_RHS5 = {"FOR"},
			FOLLOW_statementPRIME = {"SEMICOLON"},

			FIRST_statBlock = {"IF", "FOR", "GET", "PUT", "RETURN", "ID", "OBRACE"},
				FIRST_statBlock_RHS1 = {"IF", "FOR", "GET", "PUT", "RETURN", "ID"},
				FIRST_statBlock_RHS2 = {"OBRACE"},
			FOLLOW_statBlock = {"ELSE", "SEMICOLON"},

			FIRST_assignStat = {"ID"},
			FOLLOW_assignStat = {"CPAREN", "SEMICOLON"},

			FIRST_statementList = {"ID", "IF", "FOR", "GET", "RETURN", "PUT"},
			FOLLOW_statementList = {"CBRACE"},

			FIRST_expr = {"OPAREN", "ID", "INTEGER", "FRACTION", "NOT", "PLUS", "MINUS"},
			FOLLOW_expr = {"SEMICOLON", "CPAREN", "COMMA"},

			FIRST_exprPRIME = {"LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ"},
			FOLLOW_exprPRIME = {"SEMICOLON", "CPAREN", "COMMA"},

			FIRST_arithExpr = {"OPAREN", "ID", "INTEGER", "FRACTION", "NOT", "PLUS", "MINUS"},
			FOLLOW_arithExpr = {"LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ", "CPAREN", "SEMICOLON", "COMMA", "CBRACKET"},

			FIRST_arithExprPRIME = {"PLUS", "MINUS", "OR"},
			FOLLOW_arithExprPRIME = {"SEMICOLON", "CPAREN", "COMMA", "LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ", "CBRACKET"},

			FIRST_sign = {"PLUS", "MINUS"},
				FIRST_sign_RHS1 = {"MINUS"},
				FIRST_sign_RHS2 = {"PLUS"},
			FOLLOW_sign = {"OPAREN", "ID", "FRACTION", "INTEGER", "NOT", "PLUS", "MINUS"},

			FIRST_term = {"OPAREN", "ID", "INTEGER", "FRACTION", "NOT", "PLUS", "MINUS"},
			FOLLOW_term = {"SEMICOLON", "CPAREN", "COMMA", "LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ", "CBRACKET", "PLUS", "MINUS", "OR"},

			FIRST_termPRIME = {"ASTERISK", "FWDSLASH", "AND"},
			FOLLOW_termPRIME = {"SEMICOLON", "CPAREN", "COMMA", "LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ", "CBRACKET", "PLUS", "MINUS", "OR"},

			FIRST_factor = {"OPAREN", "ID", "INTEGER", "FRACTION", "NOT", "PLUS", "MINUS"},
				FIRST_factor_RHS1 = {"PLUS", "MINUS"},
				FIRST_factor_RHS2 = {"FRACTION", "INTEGER"},
				FIRST_factor_RHS3 = {"ID"},
				FIRST_factor_RHS4 = {"NOT"},
				FIRST_factor_RHS5 = {"OPAREN"},
			FOLLOW_factor = {"ASTERISK", "FWDSLASH", "AND", "SEMICOLON", "CPAREN", "COMMA", "LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ", "CBRACKET", "PLUS", "MINUS", "OR"},

			FIRST_factorPRIME = {"OPAREN"},
			FOLLOW_factorPRIME = {"SEMICOLON", "CPAREN", "COMMA", "LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ", "CBRACKET", "PLUS", "MINUS", "OR", "ASTERISK", "FWDSLASH", "AND"},

			FIRST_variableNest = {"DOT"},
			FOLLOW_variableNest = {"DEF", "OPAREN", "CPAREN", "ASTERISK", "FWDSLASH", "AND", "OR", "PLUS", "MINUS", "CBRACKET", "EQ", "NEQ", "GEQ", "LEQ", "LESS", "GREATER", "SEMICOLON", "COMMA"},

			FIRST_variable = {"ID"},
			FOLLOW_variable = {"DEF", "OPAREN", "CPAREN", "ASTERISK", "FWDSLASH", "AND", "OR", "PLUS", "MINUS", "CBRACKET", "EQ", "NEQ", "GEQ", "LEQ", "LESS", "GREATER", "SEMICOLON", "COMMA"},

			FIRST_indiceList = {"OBRACKET"},
			FOLLOW_indiceList = {"DEF", "SEMICOLON", "CPAREN", "COMMA", "LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ", "CBRACKET", "PLUS", "MINUS", "OR", "ASTERISK", "FWDSLASH", "AND", "OPAREN", "DOT"},

			FIRST_fParams = {"ID", "INT", "FLOAT"},
			FOLLOW_fParams = {"CPAREN"},

			FIRST_fParamsTailList = {"COMMA"},
			FOLLOW_fParamsTailList = {"CPAREN"},

			FIRST_aParams = {"OPAREN", "ID", "FRACTION", "INTEGER", "NOT", "PLUS", "MINUS"},
			FOLLOW_aParams = {"CPAREN"},

			FIRST_aParamsTailList = {"COMMA"},
			FOLLOW_aParamsTailList = {"CPAREN"},

			FIRST_type = {"ID", "INT", "FLOAT"},
				FIRST_type_RHS1 = {"FLOAT", "INT"},
				FIRST_type_RHS2 = {"ID"},
			FOLLOW_type = {"ID"},

			FIRST_simpleType = {"INT", "FLOAT"},
				FIRST_simpleType_RHS1 = {"INT"},
				FIRST_simpleType_RHS2 = {"FLOAT"},
			FOLLOW_simpleType = {"ID"},

			FIRST_relOp = {"LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ"},
				FIRST_relOp_RHS1 = {"GEQ"},
				FIRST_relOp_RHS2 = {"GREATER"},
				FIRST_relOp_RHS3 = {"EQ"},
				FIRST_relOp_RHS4 = {"NEQ"},
				FIRST_relOp_RHS5 = {"LEQ"},
				FIRST_relOp_RHS6 = {"LESS"},
			FOLLOW_relOp = {"OPAREN", "ID", "INTEGER", "FRACTION", "NOT", "PLUS", "MINUS"},

			FIRST_addOp = {"OR", "MINUS", "PLUS"},
				FIRST_addOp_RHS1 = {"OR"},
				FIRST_addOp_RHS2 = {"MINUS"},
				FIRST_addOp_RHS3 = {"PLUS"},
			FOLLOW_addOp = {"OPAREN", "ID", "INTEGER", "FRACTION", "NOT", "PLUS", "MINUS"},

			FIRST_multOp = {"AND", "FWDSLASH", "ASTERISK"},
				FIRST_multOp_RHS1 = {"AND"},
				FIRST_multOp_RHS2 = {"FWDSLASH"},
				FIRST_multOp_RHS3 = {"ASTERISK"},
			FOLLOW_multOp = {"OPAREN", "ID", "INTEGER", "FRACTION", "NOT", "PLUS", "MINUS"},

			FIRST_number = {"INTEGER", "FRACTION"},
				FIRST_number_RHS1 = {"INTEGER"},
				FIRST_number_RHS2 = {"FRACTION"},
			FOLLOW_number = {"ASTERISK", "FWDSLASH", "AND", "OR", "PLUS", "MINUS", "CBRACKET", "CPAREN", "EQ", "NEQ", "LESS", "GREATER", "LEQ", "GEQ", "SEMICOLON", "COMMA"};

	private Lexer lexer;

	private Token lookahead;

	private String lastLexeme;

    private Function currentFunction;

    private Variable suspendedVariable;

	public Parser(Lexer lexer) {

		this.lexer = lexer;

	}

	//main parse function and head of recursive descent
	public void parse() {

		Global.clear();

		nextToken();

		boolean success = prog() & match("EOF");

		Global.print();

        if (success)
            Global.checkTypes();

		if (success && (Outputter.errorStrings.length() == 0)) {
			Outputter.errorStrings.append("Compiled successfully");
			Global.generateCode();
		}
		else
			Outputter.errorStrings.append("Compilation unsuccessful");

	}

	//a local version of nextToken that skips comments
	private void nextToken() {

		do
			lookahead = lexer.nextToken();
		while ((lookahead.getType().equals("BLOCKCOMMENT") || lookahead.getType().equals("COMMENT")));
			
	}

	//matches terminals
	private boolean match(String expectedToken) {

		if (lookahead.getType().equals(expectedToken)) {

			lastLexeme = lookahead.getLexeme();

			nextToken();
			return true;

		}
		
		else {

			Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", lookahead.getLine()))
					.append("Unexpected token: ").append(lookahead.getLexeme())
					.append(" | Expected: ").append(Token.toDescription(expectedToken)).append("\n");
			return false;

		}

	}

	//Skips errors and recovers the parser from valid detection
	private boolean skipErrors(String[] firstUfollow) {

		if (Arrays.asList(firstUfollow).contains(lookahead.getType()))
			return true;
		
		else {
			
			Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", lookahead.getLine()))
					.append("Unexpected token: ").append(lookahead.getLexeme()).append("\n");

			while (!Arrays.asList(firstUfollow).contains(lookahead.getType()) && !lookahead.getType().equals("EOF"))
				nextToken();

			return false;

		}

	}

	//Creates the union of FIRST and FOLLOW sets
	private String[] union(String[] first, String[] follow) {

		String[] union = new String[first.length + follow.length];

		System.arraycopy(first, 0, union, 0, first.length);

		System.arraycopy(follow, 0, union, first.length, follow.length);

		return union;

	}

	//Grammar Rules
//program
	private boolean prog() { // <prog> -> <classDeclList> program { <funcMemberList> } ; <funcDefList>

		// Define built-in types
		Class intClass = new Class(0);
		intClass.setName("int");
		Class floatClass = new Class(0);
		floatClass.setName("float");

		Global.insert(intClass);
		Global.insert(floatClass);

		boolean valid = skipErrors(union(FIRST_prog, FOLLOW_prog));

		if (lookahead.belongsTo(FIRST_prog)) {

			boolean c1 = classDeclList() & match("PROGRAM");

            Function program = new Function(lookahead.getLine());

            currentFunction = program;

			boolean c2 = c1 & match("OBRACE")
                    & funcMemberList(program)
                    & match("CBRACE")
                    & match("SEMICOLON");

			if (c2)
				Global.setProgram(program);

			if (c2 & funcDefList(null))
				Outputter.derivationStrings.append("<prog> -> <classDeclList> program { <funcMemberList> } ; <funcDefList>").append("\n");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean classDeclList() { // <classDeclList> -> class id { <classMemberDeclList> } ; <classDeclList> | EPSILON

		boolean valid = skipErrors(union(FIRST_classDeclList, FOLLOW_classDeclList));

		if (lookahead.belongsTo(FIRST_classDeclList)) {

			// Create a new class
			Class newClass = new Class(lookahead.getLine());

			boolean c1 = match("CLASS") & match("ID");
			newClass.setName(lastLexeme);
			boolean c2 = c1 & match("OBRACE")
                    & classMemberDeclList(newClass)
                    & match("CBRACE");

			if (c2)
				Global.insert(newClass);

			if (c2 & match("SEMICOLON")
					& classDeclList())
				Outputter.derivationStrings.append("<classDeclList> ->  class id { <classMemberDeclList> } ; <classDeclList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_classDeclList))
			Outputter.derivationStrings.append("<classDeclList> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean classMemberDeclPRIME(Class newClass, int line, String type, String name) { // <classMemberDeclPRIME> -> <arraySizeList> | ( <fParams> ) { <funcMemberList> }

		boolean valid = skipErrors(union(FIRST_classMemberDeclPRIME, FOLLOW_classMemberDeclPRIME));

		if (lookahead.belongsTo(FIRST_classMemberDeclPRIME_RHS1)) {

			// The record is a variable
			Variable variable = new Variable(line);
			variable.setName(name);
			variable.setType(type);

			boolean c1 = arraySizeList(variable);
			if (c1)
				newClass.insert(variable);

			if (c1 & match("SEMICOLON"))
				Outputter.derivationStrings.append("<classMemberDeclPRIME> -> <arraySizeList> ;").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_classMemberDeclPRIME_RHS2)) {

			// The record is a function
			Function function = new Function(line);
			function.setName(name);
			function.setType(type);

            currentFunction = function;
			function.setParent(newClass);

			boolean c1 = match("OPAREN")
					& fParams(function)
					& match("CPAREN")
					& match("OBRACE")
					& funcMemberList(function)
					& match("CBRACE");

			if (c1)
				newClass.insert(function);


			if (c1 & match("SEMICOLON"))
				Outputter.derivationStrings.append("<classMemberDeclPRIME> -> ( <fParams> ) { <funcMemberList> }").append("\n");
			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean classMemberDeclList(Class newClass) { // <classMemberDeclList> -> <type> id <classMemberDeclPRIME> ; <classMemberDeclList> | EPSILON

		boolean valid = skipErrors(union(FIRST_classMemberDeclList, FOLLOW_classMemberDeclList));

		if (lookahead.belongsTo(FIRST_classMemberDeclList)) {

			//Saving the line because we don't know what kind of record we have yet
			int line = lookahead.getLine();

			boolean c1 = type();

			// We must save it this way because we don't know what kind of member it is yet.
			String type = lastLexeme;
			boolean c2 = match("ID");

			// We must save it this way because we don't know what kind of member it is yet.
			String name = lastLexeme;

			if ((c1 && c2) & classMemberDeclPRIME(newClass, line, type, name)
					& classMemberDeclList(newClass))
				Outputter.derivationStrings.append("<classMemberDeclList> -> <type> id <classMemberDeclPRIME> <classMemberDeclList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_classMemberDeclList))
			Outputter.derivationStrings.append("<classMemberDeclList> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean funcDefList(Class newClass) { // <funcDefList> -> <type> id ( <fParams> ) { <funcMemberList> } ; <funcDefList> | EPSILON

		boolean valid = skipErrors(union(FIRST_funcDefList, FOLLOW_funcDefList));

		if (lookahead.belongsTo(FIRST_funcDefList)) {

			Function function = new Function(lookahead.getLine());
			boolean c1 = type();
			String type = lastLexeme;
			boolean c2 = match("ID");
			function.setName(lastLexeme);
			function.setType(type);

            currentFunction = function;

			if (newClass != null)
				function.setParent(newClass);

			boolean c3 = match("OPAREN")
			    & fParams(function)
			    & match("CPAREN")
			    & match("OBRACE")
			    & funcMemberList(function)
			    & match("CBRACE");

			boolean c13 = c1 && c2 && c3;

			// If newClass is null then this is a Global function
			// Otherwise it is a member function
			if (c13) {
				if (newClass == null)
					Global.insert(function);
				else
					newClass.insert(function);
			}

			if (c13 & match("SEMICOLON")
					& funcDefList(newClass))
				Outputter.derivationStrings.append("<funcDefList> -> <type> id ( <fParams> ) { <funcMemberList> } ; <funcDefList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_funcDefList))
			Outputter.derivationStrings.append("<funcDefList> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean funcMember(Function function) { // <funcMember> -> statementPRIME | id <funcMemberPRIME> | <simpleType> id <arraySizeList>

		boolean valid = skipErrors(union(FIRST_funcMember, FOLLOW_funcMember));

		if (lookahead.belongsTo(FIRST_funcMember_RHS1)) {

			// Statement

            currentFunction = function;

			if (statementPRIME(function.getStatements()))
				Outputter.derivationStrings.append("<funcMember> -> <statementPRIME>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_funcMember_RHS2)) {

			// Can be a variable declaration or a statement

			int line = lookahead.getLine();

			boolean c1 = match("ID");

			String id = lastLexeme;

			if (c1 & funcMemberPRIME(function, line, id))
				Outputter.derivationStrings.append("<funcMember> -> id <funcMemberPRIME>").append("\n");

			else
				valid = false;
			
		}

		else if (lookahead.belongsTo(FIRST_funcMember_RHS3)) {

			// It's a variable

			Variable variable = new Variable(lookahead.getLine());
			boolean c1 = simpleType();
			String type = lastLexeme;
			boolean c2 = match("ID");
			variable.setName(lastLexeme);
			variable.setType(type);

			if ((c1 && c2) & arraySizeList(variable)) {
				Outputter.derivationStrings.append("<funcMember> -> <simpleType> id <arraySizeList>").append("\n");
				function.insertVariable(variable);
			}
			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean funcMemberPRIME(Function function, int line, String id) { // <funcMemberPRIME> -> <indiceList> <funcMemberPRIMEPRIME> = <expr> | id <arraySizeList>

		boolean valid = skipErrors(union(FIRST_funcMemberPRIME, FOLLOW_funcMemberPRIME));

		if (lookahead.belongsTo(FIRST_funcMemberPRIME_RHS1)) {

			// It is a variable declaration

			Variable variable = new Variable(line);

			boolean c1 = match("ID");
			variable.setName(lastLexeme);
			variable.setType(id);

			if (c1 & arraySizeList(variable)) {
				Outputter.derivationStrings.append("<funcMemberPRIME> -> id <arraySizeList>").append("\n");
				function.insertVariable(variable);
			}
			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_funcMemberPRIME_RHS2)) {

            AssignmentStatement assignmentStatement = new AssignmentStatement(line);

            VariableCall variableCall = new VariableCall(currentFunction);
            variableCall.setLine(lookahead.getLine());

            Variable variable = new Variable(0);
            variable.setName(id);

            ArrayList<ArithmeticExpression> indiceList = new ArrayList<>();

            boolean c1 = indiceList(indiceList);

            if (c1) {
                variableCall.getIndiceLists().add(indiceList);
                for (ArithmeticExpression ignored : indiceList)
                    variable.getDimensions().add(0);
            }

            Expression expression = new Expression();

			if (c1 & variableNest(variableCall, variable, false)
					& match("DEF")
					& expr(expression)) {
                Outputter.derivationStrings.append("<funcMemberPRIME> -> <indiceList> <funcMemberPRIMEPRIME> = <expr>").append("\n");
                assignmentStatement.setVariableCall(variableCall);
                assignmentStatement.setExpression(expression);
                function.getStatements().add(assignmentStatement);
            }
			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean funcMemberList(Function function) { // <funcMemberList> -> <funcMember> ; <funcMemberList> | EPSILON

		boolean valid = skipErrors(union(FIRST_funcMemberList, FOLLOW_funcMemberList));
		
		if (lookahead.belongsTo(FIRST_funcMemberList)) {

			if (funcMember(function)
					& match("SEMICOLON")
					& funcMemberList(function))
				Outputter.derivationStrings.append("<funcMemberList> -> <funcMember> ; <funcMemberList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_funcMemberList))
			Outputter.derivationStrings.append("<funcMemberList> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean arraySizeList(Variable variable) { // <arraySizeList> -> [ integer ] <arraySizeList> | EPSILON

		boolean valid = skipErrors(union(FIRST_arraySizeList, FOLLOW_arraySizeList));

		if (lookahead.belongsTo(FIRST_arraySizeList)) {

			boolean c1 = match("OBRACKET") & match("INTEGER");

			try {

				variable.getDimensions().add(Integer.parseInt(lastLexeme));

			}
			catch (NumberFormatException numberFormatException) {

				Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", lookahead.getLine()))
						.append("Array dimension must be an integer").append("\n");

			}


			if (c1 & match("CBRACKET")
					& arraySizeList(variable))
				Outputter.derivationStrings.append("<arraySizeList> -> [ integer ] <arraySizeList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_arraySizeList))
			Outputter.derivationStrings.append("<arraySizeList> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean statement(ArrayList<Statement> statements) { // <statement> -> <assignStat> | <statementPRIME>

		boolean valid = skipErrors(union(FIRST_statement, FOLLOW_statement));

		int line = lookahead.getLine();

		if (lookahead.belongsTo(FIRST_statement_RHS1)) {

			if (assignStat(statements))
				Outputter.derivationStrings.append("<statement> -> <assignStat>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_statement_RHS2)) {

			if (statementPRIME(statements))
				Outputter.derivationStrings.append("<statement> -> <statementPRIME>").append("\n");

			else
				valid = false;

		}

		else
			valid = false;

		if (!valid)
			Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", line))
					.append("Invalid statement syntax").append("\n");

		return valid;

	}

	private boolean statementPRIME(ArrayList<Statement> statements) { // <statementPRIME> ->  if ( <expr> ) then <statBlock> else <statBlock> | for ( <type> id = <expr> ; <arithExpr> <relOp> <arithExpr> ; <assignStat> ) <statBlock> | get ( <variable> ) | put ( <variable> ) | return ( <expr> )

		boolean valid = skipErrors(union(FIRST_statementPRIME, FOLLOW_statementPRIME));

        int line = lookahead.getLine();

		if (lookahead.belongsTo(FIRST_statementPRIME_RHS1)) {

			ReturnStatement returnStatement = new ReturnStatement(line, currentFunction);

            Expression expression = new Expression();

			if (match("RETURN")
                    & match("OPAREN")
                    & expr(expression)
                    & match("CPAREN")) {
				Outputter.derivationStrings.append("<statementPRIME> -> return ( <expr> )").append("\n");
                returnStatement.setExpression(expression);
                statements.add(returnStatement);
			}
			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_statementPRIME_RHS2)) {

			PutStatement putStatement = new PutStatement(line);

            VariableCall variableCall = new VariableCall(currentFunction);

			if (match("PUT")
                    & match("OPAREN")
                    & variable(variableCall, false)
                    & match("CPAREN")) {
				Outputter.derivationStrings.append("<statementPRIME> -> put ( <variable> )").append("\n");
                putStatement.setVariableCall(variableCall);
                statements.add(putStatement);
			}
			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_statementPRIME_RHS3)) {

            GetStatement getStatement = new GetStatement(line);

            VariableCall variableCall = new VariableCall(currentFunction);

			if (match("GET")
                    & match("OPAREN")
                    & variable(variableCall, false)
                    & match("CPAREN")) {
                Outputter.derivationStrings.append("<statementPRIME> -> get ( <variable> )").append("\n");
                getStatement.setVariableCall(variableCall);
                statements.add(getStatement);
            }

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_statementPRIME_RHS4)) {

            IfStatement ifStatement = new IfStatement(line);

            Expression expression = new Expression();

			if (match("IF")
                    & match("OPAREN")
                    & expr(expression)
                    & match("CPAREN")
					& match("THEN")
					& statBlock(ifStatement.getThenStatementBlock())
					& match("ELSE")
					& statBlock(ifStatement.getElseStatementBlock())) {
                Outputter.derivationStrings.append("<statementPRIME> -> if ( <expr> ) then <statBlock> else <statBlock>").append("\n");
                ifStatement.setExpression(expression);
                statements.add(ifStatement);
            }
			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_statementPRIME_RHS5)) {

            ForStatement forStatement = new ForStatement(line);

            boolean c1 = match("FOR")
                    & match("OPAREN");

            int line2 = lookahead.getLine();

            boolean c2 = type();

            Variable variable = new Variable(lookahead.getLine());
            variable.setType(lastLexeme);

            boolean c3 = match("ID");
            variable.setName(lastLexeme);

            currentFunction.insertVariable(variable);
            forStatement.setVariable(variable);

            Expression expression = new Expression();

            boolean c4 = match("DEF") & expr(expression);

            AssignmentStatement assignmentStatement = new AssignmentStatement(line2);

            VariableCall variableCall = new VariableCall(currentFunction);
            variableCall.setLine(lookahead.getLine());
            variableCall.addVariable(variable);

            assignmentStatement.setVariableCall(variableCall);

            forStatement.setInitialAssignmentStatement(assignmentStatement);

            ArithmeticExpression lhs = new ArithmeticExpression();

            boolean c5 = match("SEMICOLON") & arithExpr(lhs);

            Expression condition = new Expression();
            condition.setLine(lookahead.getLine());

            boolean c6 = relOp();

            if (c6)
                condition.setOperator(lastLexeme);

            ArithmeticExpression rhs = new ArithmeticExpression();

            boolean c7 = arithExpr(rhs);

            ArrayList<Statement> temp = new ArrayList<>();

			if ((c1 && c2 && c3 && c4 && c5 && c6 && c7)
                    & match("SEMICOLON")
                    & assignStat(temp)
                    & match("CPAREN")
					& statBlock(forStatement.getLoopStatements()))
            {
                Outputter.derivationStrings.append("<statementPRIME> -> for ( <type> id = <expr> ; <arithExpr> <relOp> <arithExpr> ; <assignStat> ) <statBlock> ").append("\n");
                assignmentStatement.setExpression(expression);
                condition.setLeftOperand(lhs);
                condition.setRightOperand(rhs);
                forStatement.setExpression(condition);
                forStatement.setLoopAssignmentStatement((AssignmentStatement) temp.get(0));
                currentFunction.getVariables().remove(currentFunction.getVariables().size() - 1);
                statements.add(forStatement);
            }
			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean statBlock(ArrayList<Statement> statements) { // <statBlock> -> { <statementList> } | <statement> ; | EPSILON

		boolean valid = skipErrors(union(FIRST_statBlock, FOLLOW_statBlock));

		if (lookahead.belongsTo(FIRST_statBlock_RHS1)) {

			if (statement(statements)
					& match("SEMICOLON"))
				Outputter.derivationStrings.append("<statBlock> -> <statement> ;").append("\n");

			else
				valid = false;

		}

		if (lookahead.belongsTo(FIRST_statBlock_RHS2)) {

			if (match("OBRACE")
					& statementList(statements)
					& match("CBRACE"))
				Outputter.derivationStrings.append("<statBlock> -> { <statementList> }").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_statBlock))
			Outputter.derivationStrings.append("<statBlock> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean assignStat(ArrayList<Statement> statements) { // <assignStat> -> <variable> = <expr>

		boolean valid = skipErrors(union(FIRST_assignStat, FOLLOW_assignStat));

		if (lookahead.belongsTo(FIRST_assignStat)) {

            AssignmentStatement assignmentStatement = new AssignmentStatement(lookahead.getLine());

            VariableCall variableCall = new VariableCall(currentFunction);

			boolean c1 = variable(variableCall, false);

            if (c1)
                assignmentStatement.setVariableCall(variableCall);

            Expression expression = new Expression();

            if (c1 & match("DEF")
                    & expr(expression)) {

                Outputter.derivationStrings.append("<assignStat> -> <variable> = <expr>").append("\n");
                assignmentStatement.setExpression(expression);
                statements.add(assignmentStatement);

            }

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean statementList(ArrayList<Statement> statements) { // <statementList> -> <statement> ; <statementList> | EPSILON

		boolean valid = skipErrors(union(FIRST_statementList, FOLLOW_statementList));

		if (lookahead.belongsTo(FIRST_statementList)) {

			if (statement(statements)
					& match("SEMICOLON")
					& statementList(statements))
				Outputter.derivationStrings.append("<statementList> -> <statement> ; <statementList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_statementList))
			Outputter.derivationStrings.append("<statementList> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean expr(Expression expression) { // <expr> -> <arithExpr> <exprPRIME>

		boolean valid = skipErrors(union(FIRST_expr, FOLLOW_expr));

		int line = lookahead.getLine();

		if (lookahead.belongsTo(FIRST_expr)) {

            expression.setLine(line);

            ArithmeticExpression leftOperand = new ArithmeticExpression();

            boolean c1 = arithExpr(leftOperand);

            if (c1)
                expression.setLeftOperand(leftOperand);

			if (c1 & exprPRIME(expression))
                Outputter.derivationStrings.append("<expr> -> <arithExpr> <exprPRIME>").append("\n");
			else
				valid = false;

		}

		else
			valid = false;

		if (!valid)
			Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", line))
					.append("Invalid expression syntax").append("\n");

		return valid;

	}

	private boolean exprPRIME(Expression expression) { // <exprPRIME> -> <relOp> <arithExpr> | EPSILON

		boolean valid = skipErrors(union(FIRST_exprPRIME, FOLLOW_exprPRIME));

		if (lookahead.belongsTo(FIRST_exprPRIME)) {

            boolean c1 = relOp();

            if (c1)
                expression.setOperator(lastLexeme);

            ArithmeticExpression rightOperand = new ArithmeticExpression();

			if (c1 & arithExpr(rightOperand)) {
                Outputter.derivationStrings.append("<exprPRIME> -> <relOp> <arithExpr>").append("\n");
                expression.setRightOperand(rightOperand);
            }
			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_exprPRIME))
			Outputter.derivationStrings.append("<exprPRIME> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean arithExpr(ArithmeticExpression arithmeticExpression) { // <arithExpr> -> <term> <arithExprPRIME>

		boolean valid = skipErrors(union(FIRST_arithExpr, FOLLOW_arithExpr));

		if (lookahead.belongsTo(FIRST_arithExpr)) {

            arithmeticExpression.setLine(lookahead.getLine());

            Term term = new Term();

            boolean c1 = term(term);

            if (c1)
                arithmeticExpression.setTerm(term);

			if (c1 & arithExprPRIME(arithmeticExpression))
                Outputter.derivationStrings.append("<arithExpr> -> <term> <arithExprPRIME>").append("\n");
			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean arithExprPRIME(ArithmeticExpression arithmeticExpression) { // <arithExprPRIME> -> <addOp> <arithExpr> | EPSILON

		boolean valid = skipErrors(union(FIRST_arithExprPRIME, FOLLOW_arithExprPRIME));

		if (lookahead.belongsTo(FIRST_arithExprPRIME)) {

            boolean c1 = addOp();

            if (c1)
                arithmeticExpression.setOperator(lastLexeme);

            ArithmeticExpression arithmeticExpression1 = new ArithmeticExpression();

			if (c1 & arithExpr(arithmeticExpression1)) {
                Outputter.derivationStrings.append("<arithExprPRIME> -> <addOp> <arithExpr>").append("\n");
                arithmeticExpression.setArithmeticExpression(arithmeticExpression1);
            }
			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_arithExprPRIME))
			Outputter.derivationStrings.append("<arithExprPRIME> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean sign() { // <sign> -> + | -

		boolean valid = skipErrors(union(FIRST_sign, FOLLOW_sign));

		if (lookahead.belongsTo(FIRST_sign_RHS1)) {

			if (match("MINUS"))
				Outputter.derivationStrings.append("<sign> -> -").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_sign_RHS2)) {

			if (match("PLUS"))
				Outputter.derivationStrings.append("<sign> -> +").append("\n");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean term(Term term) { // <term> -> <factor> <termPRIME>

		boolean valid = skipErrors(union(FIRST_term, FOLLOW_term));

		if (lookahead.belongsTo(FIRST_term)) {

            term.setLine(lookahead.getLine());

            Factor factor = new Factor();

            boolean c1 = factor(factor);

            if (c1)
                term.setLeftOperand(factor);

			if (c1 & termPRIME(term))
                Outputter.derivationStrings.append("<term> -> <factor> <termPRIME>").append("\n");
			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean termPRIME(Term term) { // <termPRIME> -> <multOp> <term> | EPSILON

		boolean valid = skipErrors(union(FIRST_termPRIME, FOLLOW_termPRIME));

		if (lookahead.belongsTo(FIRST_termPRIME)) {

            boolean c1 = multOp();

            if (c1)
                term.setOperator(lastLexeme);

            Term term1 = new Term();

			if (c1 & term(term1)) {
                Outputter.derivationStrings.append("<termPRIME> -> <multOp> <term>").append("\n");
                term.setRightOperand(term1);
            }
			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_termPRIME))
			Outputter.derivationStrings.append("<termPRIME> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean factor(Factor factor) { // <factor> -> <sign> <factor> | not <factor> | <number> | <variable> <factorPrime> | ( <arithExpr> )

		boolean valid = skipErrors(union(FIRST_factor, FOLLOW_factor));

        factor.setLine(lookahead.getLine());

		if (lookahead.belongsTo(FIRST_factor_RHS1)) {

            boolean c1 = sign();

            if (c1)
                factor.setUnary(lastLexeme);

            Factor factor1 = new Factor();

			if (c1 & factor(factor1)) {
                Outputter.derivationStrings.append("<factor> -> <sign> <factor>").append("\n");
                factor.setFactor(factor1);
            }

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_factor_RHS2)) {

			if (number()) {
                Outputter.derivationStrings.append("<factor> -> <number>").append("\n");
                factor.setNumber(lastLexeme);
            }
			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_factor_RHS3)) {

            VariableCall variableCall = new VariableCall(currentFunction);

			if (variable(variableCall, true)
                    & factorPRIME(factor, variableCall)) {
                Outputter.derivationStrings.append("<factor> -> <variable> <factorPRIME>").append("\n");

            }

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_factor_RHS4)) {

            Factor factor1 = new Factor();

			if (match("NOT")
					& factor(factor1)) {
                Outputter.derivationStrings.append("<factor> -> not <factor>").append("\n");
                factor.setUnary("not");
                factor.setFactor(factor1);
            }
			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_factor_RHS5)) {

            ArithmeticExpression arithmeticExpression = new ArithmeticExpression();

			if (match("OPAREN")
					& arithExpr(arithmeticExpression)
					& match("CPAREN")) {
                Outputter.derivationStrings.append("<factor> -> ( <arithExpr> )").append("\n");
                factor.setArithmeticExpression(arithmeticExpression);
            }
			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean factorPRIME(Factor factor, VariableCall variableCall) { // <factorPRIME> -> ( <aParams> ) | EPSILON

		boolean valid = skipErrors(union(FIRST_factorPRIME, FOLLOW_factorPRIME));

		if (lookahead.belongsTo(FIRST_factorPRIME)) {

            FunctionCall functionCall = new FunctionCall(currentFunction);
            functionCall.setVariableNest(variableCall.getVariableNest());

            functionCall.setFunctionName(suspendedVariable.getName());

			if (match("OPAREN")
					& aParams(functionCall)
					& match("CPAREN")) {
                Outputter.derivationStrings.append("<factorPRIME> -> ( <aParams> )").append("\n");
                factor.setFunctionCall(functionCall);
            }
			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_factorPRIME)) {
            Outputter.derivationStrings.append("<factorPRIME> -> EPSILON").append("\n");
            variableCall.addVariable(suspendedVariable);
            factor.setVariableCall(variableCall);
        }

		else
			valid = false;

		return valid;

	}

	private boolean variableNest(VariableCall variableCall, Variable variable, boolean fromFactor) { // <idnestList> -> . <variable> | EPSILON

		boolean valid = skipErrors(union(FIRST_variableNest, FOLLOW_variableNest));

		if (lookahead.belongsTo(FIRST_variableNest)) {

            boolean c1 = match("DOT");

            if (c1)
                variableCall.addVariable(variable);

			if (c1
					& variable(variableCall, fromFactor))
				Outputter.derivationStrings.append("<variableNest> -> . variable").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_variableNest)) {
            Outputter.derivationStrings.append("<variableNest> -> EPSILON").append("\n");

            //could be a function call or a variable
            //don't add it yet
            if (fromFactor)
                suspendedVariable = variable;

            else
                variableCall.addVariable(variable);

        }
		else
			valid = false;

		return valid;

	}

	private boolean variable(VariableCall variableCall, boolean fromFactor) { // <variable> -> id <indiceList> <variableNest>

		boolean valid = skipErrors(union(FIRST_variable, FOLLOW_variable));

		if (lookahead.belongsTo(FIRST_variable)) {

            variableCall.setLine(lookahead.getLine());

            Variable variable = new Variable(0);

            boolean c1 = match("ID");

            if (c1)
                variable.setName(lastLexeme);

            ArrayList<ArithmeticExpression> indiceList = new ArrayList<>();

            boolean c2 = indiceList(indiceList);

            if (c2) {
                variableCall.getIndiceLists().add(indiceList);

                for (ArithmeticExpression ignored : indiceList)
                    variable.getDimensions().add(0);

            }

			if ((c1 && c2) & variableNest(variableCall, variable, fromFactor))
				Outputter.derivationStrings.append("<variable> -> id <indiceList> <variableNest>").append("\n");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean indiceList(ArrayList<ArithmeticExpression> indiceList) { // <indiceList> -> [ <arithExpr> ] <indiceList> | EPSILON

		boolean valid = skipErrors(union(FIRST_indiceList, FOLLOW_indiceList));

		if (lookahead.belongsTo(FIRST_indiceList)) {

            ArithmeticExpression arithmeticExpression = new ArithmeticExpression();

            boolean c1 = match("OBRACKET")
                    & arithExpr(arithmeticExpression)
                    & match("CBRACKET");

            if (c1)
                indiceList.add(arithmeticExpression);

			if (c1 & indiceList(indiceList))
				Outputter.derivationStrings.append("<indiceList> -> [ <arithExpr> ] <indiceList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_indiceList))
			Outputter.derivationStrings.append("<indiceList> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean fParams(Function function) { // <fParams> -> <type> id <arraySizeList> <fParamsTailList> | EPSILON

		boolean valid = skipErrors(union(FIRST_fParams, FOLLOW_fParams));

		if (lookahead.belongsTo(FIRST_fParams)) {

			Variable parameter = new Variable(lookahead.getLine());

			boolean c1 = type();
			String type = lastLexeme;
			boolean c2 = match("ID");
			parameter.setName(lastLexeme);
			parameter.setType(type);

			boolean c3 = arraySizeList(parameter);

			if (c3)
				function.insertParameter(parameter);

			if ((c1 && c2 && c3) & fParamsTailList(function))
				Outputter.derivationStrings.append("<fParams> -> <type> id <arraySizeList> <fParamsTailList>").append("\n");
			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_fParams))
			Outputter.derivationStrings.append("<fParams> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean fParamsTailList(Function function) { // <fParamsTailList> -> , <type> id <arraySizeList> <fParamsTailList> | EPSILON

		boolean valid = skipErrors(union(FIRST_fParamsTailList, FOLLOW_fParamsTailList));

		if (lookahead.belongsTo(FIRST_fParamsTailList)) {

			boolean c1 = match("COMMA");

			Variable parameter = new Variable(lookahead.getLine());

			boolean c2 = type();
			String type = lastLexeme;
			boolean c3 = match("ID");
			parameter.setName(lastLexeme);
			parameter.setType(type);

			boolean c4 = arraySizeList(parameter);

			if (c4)
				function.insertParameter(parameter);

			if ((c1 && c2 && c3 && c4) & fParamsTailList(function))
				Outputter.derivationStrings.append("<fParamsTailList> -> , <type> id <arraySizeList> <fParamsTailList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_fParamsTailList))
			Outputter.derivationStrings.append("<fParamsTailList> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean aParams(FunctionCall functionCall) { // <aParams> -> <expr> <aParamsTailList> | EPSILON

		boolean valid = skipErrors(union(FIRST_aParams, FOLLOW_aParams));

		if (lookahead.belongsTo(FIRST_aParams)) {

            Expression expression = new Expression();

            boolean c1 = expr(expression);

            if (c1)
                functionCall.getParameters().add(expression);

			if (c1 & aParamsTailList(functionCall))
                Outputter.derivationStrings.append("<aParams> -> <expr> <aParamsTailList>").append("\n");
			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_aParams))
			Outputter.derivationStrings.append("<aParams> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean aParamsTailList(FunctionCall functionCall) { // <aParamsTailList> -> , <aParams> | EPSILON

		boolean valid = skipErrors(union(FIRST_aParamsTailList, FOLLOW_aParamsTailList));

		if (lookahead.belongsTo(FIRST_aParamsTailList)) {

			if (match("COMMA") & aParams(functionCall))
				Outputter.derivationStrings.append("<aParamsTailList> -> , <aParams>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_aParamsTailList))
			Outputter.derivationStrings.append("<aParamsTailList> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean type() { // <type> -> <simpleType> | id

		boolean valid = skipErrors(union(FIRST_type, FOLLOW_type));

		if (lookahead.belongsTo(FIRST_type_RHS1)) {

			if (simpleType())
				Outputter.derivationStrings.append("<type> -> <simpleType>").append("\n");
			
			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_type_RHS2)) {
			
			if (match("ID"))
				Outputter.derivationStrings.append("<type> -> id").append("\n");

			else
				valid = false;

		}
		
		else
			valid = false;

		return valid;

	}

	private boolean simpleType() { // <simpleType> -> int | float

		boolean valid = skipErrors(union(FIRST_simpleType, FOLLOW_simpleType));

		if (lookahead.belongsTo(FIRST_simpleType_RHS1)) {

			if (match("INT"))
				Outputter.derivationStrings.append("<simpleType> -> int").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_simpleType_RHS2)) {

			if (match("FLOAT"))
				Outputter.derivationStrings.append("<simpleType> -> float").append("\n");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}
	
	private boolean relOp() { // <relOp> -> < | = | > | <> | <= | == | >=
		
		boolean valid = skipErrors(union(FIRST_relOp, FOLLOW_relOp));

		if (lookahead.belongsTo(FIRST_relOp_RHS1)) {
			
			boolean c1 = match("GEQ");
			
			if (c1)
				Outputter.derivationStrings.append("<relOp> -> >=").append("\n");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS2)) {
			
			if (match("GREATER"))
				Outputter.derivationStrings.append("<relOp> -> >").append("\n");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS3)) {
			
			if (match("EQ"))
				Outputter.derivationStrings.append("<relOp> -> ==").append("\n");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS4)) {
			
			if (match("NEQ"))
				Outputter.derivationStrings.append("<relOp> -> <>").append("\n");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS5)) {
			
			if (match("LEQ"))
				Outputter.derivationStrings.append("<relOp> -> <=").append("\n");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS6)) {
			
			if (match("LESS"))
				Outputter.derivationStrings.append("<relOp> -> <").append("\n");

			else
				valid = false;

		}
		
		else
			valid = false;

		return valid;
		
	}
	
	private boolean addOp() { // <addOp> -> + | - | or
		
		boolean valid = skipErrors(union(FIRST_addOp, FOLLOW_addOp));

		if (lookahead.belongsTo(FIRST_addOp_RHS1)) {

			if (match("OR"))
				Outputter.derivationStrings.append("<addOp> -> or").append("\n");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_addOp_RHS2)) {

			if (match("MINUS"))
				Outputter.derivationStrings.append("<addOp> -> -").append("\n");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_addOp_RHS3)) {
			
			if (match("PLUS"))
				Outputter.derivationStrings.append("<addOp> -> +").append("\n");

			else
				valid = false;

		}
		
		else
			valid = false;

		return valid;
		
	}
	
	private boolean multOp() { // <multOp> -> * | / | and
		
		boolean valid = skipErrors(union(FIRST_multOp, FOLLOW_multOp));

		if (lookahead.belongsTo(FIRST_multOp_RHS1)) {
			
			if (match("AND"))
				Outputter.derivationStrings.append("<multOp> -> and").append("\n");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_multOp_RHS2)) {
			
			if (match("FWDSLASH"))
				Outputter.derivationStrings.append("<multOp> -> /").append("\n");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_multOp_RHS3)) {
			
			if (match("ASTERISK"))
				Outputter.derivationStrings.append("<multOp> -> *").append("\n");

			else
				valid = false;

		}
		
		else
			valid = false;

		return valid;
		
	}

	private boolean number() { // <number> -> integer | fraction

		boolean valid = skipErrors(union(FIRST_number, FOLLOW_number));

		if (lookahead.belongsTo(FIRST_number_RHS1)) {

			if (match("INTEGER"))
				Outputter.derivationStrings.append("<number> -> integer").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_number_RHS2)) {

			if (match("FRACTION"))
				Outputter.derivationStrings.append("<number> -> fraction").append("\n");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}
	
}
package compiler;

import java.util.Arrays;

public class Parser {
	
	//FIRST and FOLLOW sets
	private static final String[] 

			FIRST_prog = {"CLASS", "PROGRAM"},
			FOLLOW_prog = {"EOF"},

			FIRST_classDeclList = {"CLASS"},
			FOLLOW_classDeclList = {"PROGRAM"},

			FIRST_classMemberDeclList = {"ID", "INT", "FLOAT"},
			FOLLOW_classMemberDeclList = {"CBRACE"},

			FIRST_type = {"ID", "INT", "FLOAT"},
			FIRST_type_RHS1 = {"FLOAT"},
			FIRST_type_RHS2 = {"INT"},
			FIRST_type_RHS3 = {"ID"},
			FOLLOW_type = {"ID"},

			FIRST_classMemberDeclPRIME = {"OBRACKET", "OPAREN"},
			FIRST_classMemberDeclPRIME_RHS1 = {"OBRACKET"},
			FIRST_classMemberDeclPRIME_RHS2 = {"OPAREN"},
			FOLLOW_classMemberDeclPRIME = {"ID", "INTEGER", "REAL", "CBRACE"},

			FIRST_arraySizeList = {"OBRACKET"},
			FOLLOW_arraySizeList = {"SEMICOLON", "CPAREN", "COMMA"},

			FIRST_fParams = {"ID", "INT", "FLOAT"},
			FOLLOW_fParams = {"CPAREN"},

			FIRST_fParamsTailList = {"COMMA"},
			FOLLOW_fParamsTailList = {"CPAREN"},

			FIRST_funcBody = {"OBRACE"},
			FOLLOW_funcBody = {"SEMICOLON"},

			FIRST_funcBodyMemberList = {"INT", "FLOAT", "ID", "IF", "FOR", "GET", "PUT", "RETURN"},
			FOLLOW_funcBodyMemberList = {"CBRACE"},

			FIRST_funcBodyMember = {"IF", "FOR", "GET", "PUT", "RETURN", "ID", "FLOAT", "INT"},
			FIRST_funcBodyMember_RHS1 = {"IF", "FOR", "GET", "PUT", "RETURN"},
			FIRST_funcBodyMember_RHS2 = {"ID"},
			FIRST_funcBodyMember_RHS3 = {"FLOAT"},
			FIRST_funcBodyMember_RHS4 = {"INT"},
			FOLLOW_funcBodyMember = {"INTEGER", "REAL", "ID", "IF", "WHILE", "READ", "WRITE", "RETURN", "CBRACE"},

			FIRST_funcBodyMemberPRIME = {"OBRACKET", "DOT", "DEF", "ID"},
			FIRST_funcBodyMemberPRIME_RHS1 = {"OBRACKET", "DOT", "DEF"},
			FIRST_funcBodyMemberPRIME_RHS2 = {"ID"},
			FOLLOW_funcBodyMemberPRIME = {"INTEGER", "REAL", "ID", "IF", "WHILE", "READ", "WRITE", "RETURN", "CBRACE"},

			FIRST_funcBodyMemberPRIMEPRIME = {"DOT"},
			FOLLOW_funcBodyMemberPRIMEPRIME = {"INTEGER", "REAL", "ID", "IF", "WHILE", "READ", "WRITE", "RETURN", "CBRACE"},

			FIRST_variable = {"ID"},
			FOLLOW_variable = {"DEF", "CPAREN"},

			FIRST_indiceList = {"OBRACKET"},
			FOLLOW_indiceList = {"DEF", "SEMICOLON", "CPAREN", "COMMA", "LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ", "CBRACKET", "PLUS", "MINUS", "OR", "ASTERISK", "FWDSLASH", "AND", "OPAREN", "DOT"},

			FIRST_idnestList = {"DOT"},
			FOLLOW_idnestList = {"DEF", "SEMICOLON", "CPAREN", "COMMA", "LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ", "CBRACKET", "PLUS", "MINUS", "OR", "ASTERISK", "FWDSLASH", "AND", "OPAREN"},

			FIRST_factor = {"OPAREN", "ID", "INTEGER", "FRACTION", "NOT", "PLUS", "MINUS"},
			FIRST_factor_RHS1 = {"OPAREN"},
			FIRST_factor_RHS2 = {"ID"},
			FIRST_factor_RHS3 = {"FRACTION", "INTEGER"},
			FIRST_factor_RHS4 = {"NOT"},
			FIRST_factor_RHS5 = {"PLUS", "MINUS"},
			FOLLOW_factor = {"ASTERISK", "FWDSLASH", "AND", "SEMICOLON", "CPAREN", "COMMA","LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ", "CBRACKET", "PLUS", "MINUS", "OR"},

			FIRST_factorPRIME = {"OPAREN"},
			FOLLOW_factorPRIME = {"SEMICOLON", "CPAREN", "COMMA", "LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ", "CBRACKET", "PLUS", "MINUS", "OR", "ASTERISK", "FWDSLASH", "AND"},

			FIRST_aParams = {"OPAREN", "ID", "FRACTION", "INTEGER", "NOT", "PLUS", "MINUS"},
			FOLLOW_aParams = {"CPAREN"},

			FIRST_aParamsTailList = {"COMMA"},
			FOLLOW_aParamsTailList = {"CPAREN"},

			FIRST_exprPRIME = {"LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ"},
			FOLLOW_exprPRIME = {"SEMICOLON", "CPAREN", "COMMA"},

			FIRST_relOp = {"LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ"},
			FIRST_relOp_RHS1 = {"LESS"},
			FIRST_relOp_RHS2 = {"GREATER"},
			FIRST_relOp_RHS3 = {"NEQ"},
			FIRST_relOp_RHS4 = {"LEQ"},
			FIRST_relOp_RHS5 = {"EQ"},
			FIRST_relOp_RHS6 = {"GEQ"},
			FOLLOW_relOp = {"OPAREN", "ID", "NUM", "NOT", "PLUS", "MINUS"},

			FIRST_number = {"INTEGER", "FRACTION"},
			FIRST_number_RHS1 = {"INTEGER"},
			FIRST_number_RHS2 = {"FRACTION"},

			FIRST_sign = {"PLUS", "MINUS"},
			FIRST_sign_RHS1 = {"PLUS"},
			FIRST_sign_RHS2 = {"MINUS"},
			FOLLOW_sign = {"OPAREN", "ID", "NUM", "NOT", "PLUS", "MINUS"},

			FIRST_termPRIME = {"ASTERISK", "FWDSLASH", "AND"},
			FOLLOW_termPRIME = {"SEMICOLON", "CPAREN", "COMMA", "LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ", "CBRACKET", "PLUS", "MINUS", "OR"},

			FIRST_multOp = {"AND", "FWDSLASH", "ASTERISK"},
			FIRST_multOp_RHS1 = {"AND"},
			FIRST_multOp_RHS2 = {"FWDSLASH"},
			FIRST_multOp_RHS3 = {"ASTERISK"},
			FOLLOW_multOp = {"OPAREN", "ID", "NUM", "NOT", "PLUS", "MINUS"},

			FIRST_arithExprPRIME = {"PLUS", "MINUS", "OR"},
			FOLLOW_arithExprPRIME = {"SEMICOLON", "CPAREN", "COMMA", "LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ", "CBRACKET"},

			FIRST_addOp = {"OR", "MINUS", "PLUS"},
			FIRST_addOp_RHS1 = {"OR"},
			FIRST_addOp_RHS2 = {"MINUS"},
			FIRST_addOp_RHS3 = {"PLUS"},
			FOLLOW_addOp = {"OPAREN", "ID", "NUM", "NOT", "PLUS", "MINUS"},

			FIRST_statementPRIME = {"IF", "FOR", "GET", "PUT", "RETURN"},
			FIRST_statementPRIME_RHS1 = {"IF"},
			FIRST_statementPRIME_RHS2 = {"FOR"},
			FIRST_statementPRIME_RHS3 = {"GET"},
			FIRST_statementPRIME_RHS4 = {"RETURN"},
			FIRST_statementPRIME_RHS5 = {"PUT"},
			FOLLOW_statementPRIME = {"INTEGER", "REAL", "ID", "IF", "WHILE", "READ", "WRITE", "RETURN", "CBRACE", "ELSE", "SEMICOLON", "ID", "IF", "WHILE", "READ", "RETURN", "WRITE", "CBRACE"},

			FIRST_statBlock = {"IF", "FOR", "GET", "PUT", "RETURN", "ID", "OBRACE"},
			FIRST_statBlock_RHS1 = {"IF", "WHILE", "READ", "WRITE", "RETURN", "ID"},
			FIRST_statBlock_RHS2 = {"OBRACE"},
			FOLLOW_statBlock = {"ELSE", "SEMICOLON"},

			FIRST_statementList = {"ID", "IF", "FOR", "GET", "RETURN", "PUT"},
			FOLLOW_statementList = {"CBRACE"},

			FIRST_funcDefList = {"ID", "INT", "FLOAT"},
			FOLLOW_funcDefList = {"EOF"},

			FIRST_assignStat = {"ID"},

			FIRST_statement = {"IF", "FOR", "GET", "PUT", "RETURN", "ID"},
			FIRST_statement_RHS1 = {"IF", "FOR", "GET", "PUT", "RETURN"},
			FIRST_statement_RHS2 = {"ID"},
			FOLLOW_statement = {"ELSE", "SEMICOLON", "ID", "IF", "WHILE", "READ", "RETURN", "WRITE", "CBRACE"},

			FIRST_term = {"OPAREN", "ID", "INTEGER", "FRACTION", "NOT", "PLUS", "MINUS"},
			FOLLOW_term = {"SEMICOLON", "CPAREN", "COMMA", "LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ", "CBRACKET", "PLUS", "MINUS", "OR"},

			FIRST_arithExpr = {"OPAREN", "ID", "INTEGER", "FRACTION", "NOT", "PLUS", "MINUS"},
			FOLLOW_arithExpr = {"LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ", "SEMICOLON", "CPAREN", "COMMA", "SEMICOLON", "CPAREN", "COMMA", "CBRACKET"},

			FIRST_expr = {"OPAREN", "ID", "INTEGER", "FRACTION", "NOT", "PLUS", "MINUS"},
			FOLLOW_expr = {"SEMICOLON", "CPAREN", "COMMA", "CPAREN"};



	private Lexer lexer;
	private StringBuilder derivationStrings;
	private StringBuilder errorStrings;

	private Token lookahead;
	private boolean error;
	
	public Parser(Lexer lexer, StringBuilder derivationStrings, StringBuilder errorStrings) {

		this.lexer = lexer;
		this.derivationStrings = derivationStrings;
		this.errorStrings = errorStrings;

	}

	//a local version of nextToken which skips comments
	public void nextToken() {
		
		lookahead = lexer.nextToken();
		
		while (lookahead.getType().equals("BLOCKCOMMENT") || lookahead.getType().equals("COMMENT"))
			lookahead = lexer.nextToken();
			
	}
	
	//main parse function and head of recursive descent
	public boolean parse() {
		
		nextToken();

		return prog() & match("EOF");

	}

	//matches terminals
	public boolean match(String token) {

		if (lookahead.getType().equals(token)) {
			
			nextToken();
			return true;

		}
		
		else {

			errorStrings.append("Syntax error at line ").append(lookahead.getLine()).append(". Expected: ").append(token);
			nextToken();
			return false;

		}

	}

	//Skips errors and recovers the parser from error detection
	public boolean skipErrors(String[] firstUfollow) {

		if (Arrays.asList(firstUfollow).contains(lookahead.getType()))
			return true;
		
		else {
			
			errorStrings.append("Syntax error at line ").append(lookahead.getLine());

			while (!Arrays.asList(firstUfollow).contains(lookahead.getType()) && !lookahead.getType().equals("EOF"))
				nextToken();

			errorStrings.append("Parsing resumed at line ").append(lookahead.getLine());
			return false;

		}

	}

	//Creates the union of FIRST and FOLLOW sets
	public String[] union(String[] first, String[] follow) {

		String[] union = new String[first.length + follow.length];

		System.arraycopy(first, 0, union, 0, first.length);

		System.arraycopy(follow, 0, union, first.length, follow.length);

		return union;

	}

	//Grammar Rules
	public boolean prog() { // <prog> -> <classDeclList> <progBody>

		error = skipErrors(union(FIRST_prog, FOLLOW_prog));

		if (lookahead.belongsTo(FIRST_prog)) {

			boolean c1 = classDeclList(), c2 = progBody();

			if (c1 && c2)
				derivationStrings.append("<prog> -> <classDeclList> <progBody>");

			else
				error = false;

		}

		else
			error = false;

		return error;

	}

	public boolean classDeclList() { // <classDeclList> -> <classDecl> <classDeclList> | EPSILON
		
		error = skipErrors(union(FIRST_classDeclList, FOLLOW_classDeclList));

		if (lookahead.belongsTo(FIRST_classDeclList)) {

			boolean c1 = classDecl(), c2 = classDeclList();
			
			if (c1 && c2)
				derivationStrings.append("<classDeclList> ->  <classDecl> <classDeclList>");
			
			else
				error = false;

		}

		else if (lookahead.belongsTo(FOLLOW_classDeclList))
			derivationStrings.append("<classDeclList> -> EPSILON");

		else
			error = false;

		return error;

	}


	public boolean classMemberDeclPRIME() { // <classMemberDeclPRIME -> <arraySizeList> ; | ( <fParams> ) <funcBody> ;

		error = skipErrors(union(FIRST_classMemberDeclPRIME, FOLLOW_classMemberDeclPRIME));

		if (lookahead.belongsTo(FIRST_classMemberDeclPRIME_RHS1)) {

			boolean c1 = arraySizeList(), c2 = match("SEMICOLON");
			
			if (c1 && c2)
				derivationStrings.append("<classMemberDeclPRIME> -> <arraySizeList> ;");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FIRST_classMemberDeclPRIME_RHS2)) {

			boolean c1 = match("OPAREN");
			boolean c2 = fParams();
			boolean c3 = match("CPAREN");
			boolean c4 = funcBody();
			boolean c5 = match("SEMICOLON");
			
			if (c1 && c2 && c3 && c4 && c5)
				derivationStrings.append("<classMemberDeclPRIME> -> ( <fParams> ) <funcBody> ;");

			else
				error = false;

		}

		else
			error = false;

		return error;		

	}

	public boolean classMemberDeclList() { // <classMemberDeclList> -> <classMemberDecl> <classMemberDeclList> | EPSILON

		error = skipErrors(union(FIRST_classMemberDeclList, FOLLOW_classMemberDeclList));

		if (lookahead.belongsTo(FIRST_classMemberDeclList)) {

			boolean c1 = classMemberDecl(), c2 = classMemberDeclList();
			
			if (c1 && c2)
				derivationStrings.append("<classMemberDeclList> -> <classMemberDecl> <classMemberDeclList>");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FOLLOW_classMemberDeclList))
			derivationStrings.append("<classDeclList> -> EPSILON");

		else
			error = false;

		return error;	

	}

	public boolean funcDefList() { // <funcDefList> -> <funcDef> <funcDefList> | EPSILON

		error = skipErrors(union(FIRST_funcDefList, FOLLOW_funcDefList));

		if (lookahead.belongsTo(FIRST_funcDefList)) {

			boolean c1 = funcDef(), c2 = funcDefList();
			
			if (c1 && c2)
				derivationStrings.append("<funcDefList> -> <funcDef> <funcDefList>");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FOLLOW_funcDefList))
			derivationStrings.append("<funcDefList> -> EPSILON");

		else
			error = false;

		return error;	

	}

	public boolean funcBody() { // <funcBody> -> { <funcBodyMemberList> }

		error = skipErrors(union(FIRST_funcBody, FOLLOW_funcBody));

		if (lookahead.belongsTo(FIRST_funcBody)) {

			boolean c1 = match("OBRACE"), c2 = funcBodyMemberList(), c3 = match("CBRACE");
			
			if (c1 && c2 && c3)
				derivationStrings.append("<funcBody> -> { <funcBodyMemberList> }");

			else
				error = false;

		}

		else
			error = false;

		return error;

	}

	public boolean funcBodyMember() { // <funcBodyMember> -> <statementPRIME> | id <funcBodyMemberPRIME> | real id <arraySizeList> ; | integer id <arraySizeList> ;

		error = skipErrors(union(FIRST_funcBodyMember, FOLLOW_funcBodyMember));
		
		if (lookahead.belongsTo(FIRST_funcBodyMember_RHS1)) {
			
			boolean c1 = statementPRIME();
			
			if (c1)
				derivationStrings.append("<funcBodyMember> -> <statementPRIME>");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FIRST_funcBodyMember_RHS2)) {

			boolean c1 = match("ID");
			
			boolean c2 = funcBodyMemberPRIME();
			
			if (c1 && c2) {
				derivationStrings.append("<funcBodyMember> -> id <funcBodyMemberPRIME>");
			}
			else
				error = false;
			
		}

		else if (lookahead.belongsTo(FIRST_funcBodyMember_RHS3)) {
			
			boolean c1 = match("REAL");
			
			boolean c2 = match("ID");
			
			boolean c3 = arraySizeList(), c4 = match("SEMICOLON");
			
			if (c1 && c2 && c3 && c4)
				derivationStrings.append("<funcBodyMember> -> real id <arraySizeList> ;");

			else
				error = false;
			
		}

		else if (lookahead.belongsTo(FIRST_funcBodyMember_RHS4)) {

			boolean c1 = match("INTEGER");
			
			boolean c2 = match("ID");
			
			boolean c3 = arraySizeList(), c4 = match("SEMICOLON");
			
			if (c1 && c2 && c3 && c4)
				derivationStrings.append("<funcBodyMember> -> integer id <arraySizeList> ;");

			else
				error = false;
		}

		else
			error = false;

		return error;

	}

	public boolean funcBodyMemberPRIME() { // <funcBodyMemberPRIME> -> <indiceList> <funcBodyMemberPRIMEPRIME> | id <arraySizeList> ;

		error = skipErrors(union(FIRST_funcBodyMemberPRIME, FOLLOW_funcBodyMemberPRIME));

		if (lookahead.belongsTo(FIRST_funcBodyMemberPRIME_RHS1)) {

			boolean c1 = indiceList();
			
			boolean c2 = funcBodyMemberPRIMEPRIME();
			
			if (c1 && c2)
				derivationStrings.append("<funcBodyMemberPRIME> -> <indiceList> <funcBodyMemberPRIMEPRIME>");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FIRST_funcBodyMemberPRIME_RHS2)) {

			boolean c1 = match("ID");
			
			boolean c2 = arraySizeList(), c3 = match("SEMICOLON");
			
			if (c1 && c2 && c3)
				derivationStrings.append("<funcBodyMemberPRIME> -> id <arraySizeList> ;");

			else
				error = false;

		}

		else
			error = false;

		return error;

	}

	public boolean funcBodyMemberPRIMEPRIME() { // <funcBodyMemberPRIMEPRIME> -> = <expr> ; | . <variable> = <expr> ;

		error = skipErrors(union(FIRST_funcBodyMemberPRIMEPRIME, FOLLOW_funcBodyMemberPRIMEPRIME));

		if (lookahead.belongsTo(FIRST_funcBodyMemberPRIMEPRIME)) {
			
			boolean c1 = match("DEF"), c2 = expr(), c3 = match("SEMICOLON");
			
			if (c1 && c2 && c3)
				derivationStrings.append("<funcBodyMemberPRIMEPRIME> -> = <expr> ;");

			else
				error = false;

		}

		else
			error = false;

		return error;

	}

	public boolean funcBodyMemberList() { // <funcBodyMemberList> -> <funcBodyMember> <funcBodyMemberList> | EPSILON

		error = skipErrors(union(FIRST_funcBodyMemberList, FOLLOW_funcBodyMemberList));
		
		if (lookahead.belongsTo(FIRST_funcBodyMemberList)) {

			boolean c1 = funcBodyMember(), c2 = funcBodyMemberList();
			
			if (c1 && c2)
				derivationStrings.append("<funcBodyMemberList> -> <funcBodyMember> <funcBodyMemberList>");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FOLLOW_funcBodyMemberList))
			derivationStrings.append("<funcBodyMemberList> -> EPSILON");

		else
			error = false;

		return error;

	}

	public boolean type() { // <type> -> real | integer | id

		error = skipErrors(union(FIRST_type, FOLLOW_type));

		if (lookahead.belongsTo(FIRST_type_RHS1)) {

			boolean c1 = match("REAL");
			
			if (c1)
				derivationStrings.append("<type> -> real");
			
			else
				error = false;

		}

		else if (lookahead.belongsTo(FIRST_type_RHS2)) {

			boolean c1 = match("INTEGER");
			
			if (c1)
				derivationStrings.append("<type> -> integer");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FIRST_type_RHS3)) {

			boolean c1 = match("ID");
			
			if (c1)
				derivationStrings.append("<type> -> id");

			else
				error = false;

		}
		
		else
			error = false;

		return error;

	}

	public boolean arraySizeList() { // <arraySizeList> -> <arraySize> <arraySizeList> | EPSILON
		
		error = skipErrors(union(FIRST_arraySizeList, FOLLOW_arraySizeList));

		if (lookahead.belongsTo(FIRST_arraySizeList)) {
			
			boolean c1 = arraySize(), c2 = arraySizeList();
			
			if (c1 && c2)
				derivationStrings.append("<arraySizeList> -> <arraySize> <arraySizeList>");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FOLLOW_arraySizeList))
			derivationStrings.append("<arraySizeList> -> EPSILON");

		else
			error = false;

		return error;
		
	}
	
	public boolean statement() { // <statement> -> <statementPRIME> | <variable> = <expr> ;
		
		error = skipErrors(union(FIRST_statement, FOLLOW_statement));

		if (lookahead.belongsTo(FIRST_statement_RHS1)) {

			boolean c1 = statementPRIME();
			
			if (c1)
				derivationStrings.append("<statement> -> <statementPRIME>");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FIRST_statement_RHS2)) {

			boolean c1 = variable(), c2 = match("DEF"), c3 = expr(), c4 = match("SEMICOLON");
			
			if (c1 && c2 && c3 && c4)
				derivationStrings.append("<statement> -> <variable> = <expr> ;");

			else
				error = false;
			
		}
		
		else
			error = false;

		return error;
		
	}
	
	public boolean statementPRIME() { // <statementPRIME> -> write ( <expr> ) ; | return ( <expr> ) ; | read ( <expr> ) ; | while ( <expr> ) do <statBlock> ; | if ( <expr> ) then <statBlock> else <statBlock> ;
		
		error = skipErrors(union(FIRST_statementPRIME, FOLLOW_statementPRIME));

		if (lookahead.belongsTo(FIRST_statementPRIME_RHS1)) {

			boolean c1 = match("IF"), c2 = match("OPAREN"), c3 = expr(), c4 = match("CPAREN"), c5 = match("THEN"), c6 = statBlock(), c7 = match("ELSE"), c8 = statBlock(), c9 = match("SEMICOLON");
			
			if (c1 && c2 && c3 && c4 && c5 && c6 && c7 && c8 && c9)
				derivationStrings.append("<statementPRIME> -> if ( <expr> ) then <statBlock> else <statBlock> ;");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FIRST_statementPRIME_RHS2)) {

			boolean c1 = match("WHILE"), c2 = match("OPAREN"), c3 = expr(), c4 = match("CPAREN"), c5 = match("DO"), c6 = statBlock(), c7 = match("SEMICOLON");
			
			if (c1 && c2 && c3 && c4 && c5 && c6 && c7)
				derivationStrings.append("<statementPRIME> -> while ( <expr> ) do <statBlock> ;");

			else
				error = false;
			
		}
		
		else if (lookahead.belongsTo(FIRST_statementPRIME_RHS3)) {

			boolean c1 = match("READ"), c2 = match("OPAREN"), c3 = variable(), c4 = match("CPAREN"), c5 = match("SEMICOLON");
			
			if (c1 && c2 && c3 && c4 && c5)
				derivationStrings.append("<statementPRIME> -> read ( <variable> ) ;");

			else
				error = false;
			
		}
		
		else if (lookahead.belongsTo(FIRST_statementPRIME_RHS4)) {

			boolean c1 = match("RETURN"), c2 = match("OPAREN"), c3 = expr(), c4 = match("CPAREN"), c5 = match("SEMICOLON");
			
			if (c1 && c2 && c3 && c4 && c5)
				derivationStrings.append("<statementPRIME> -> return ( <expr> ) ;");

			else
				error = false;
			
		}
		
		else if (lookahead.belongsTo(FIRST_statementPRIME_RHS5)) {

			boolean c1 = match("WRITE"), c2 = match("OPAREN"), c3 = expr(), c4 = match("CPAREN"), c5 = match("SEMICOLON");
			
			if (c1 && c2 && c3 && c4 && c5)
				derivationStrings.append("<statementPRIME> -> write ( <expr> ) ;");

			else
				error = false;
			
		}
		
		else
			error = false;

		return error;
		
	}
	
public boolean statBlock() { // <statBlock> -> { <statementList> } | <statement> | EPSILON
		
		error = skipErrors(union(FIRST_statBlock, FOLLOW_statBlock));

		if (lookahead.belongsTo(FIRST_statBlock_RHS1)) {

			boolean c1 = statement();
			
			if (c1)
				derivationStrings.append("<statBlock> -> <statement>");

			else
				error = false;

		}
		
		if (lookahead.belongsTo(FIRST_statBlock_RHS2)) {
			
			boolean c1 = match("OBRACE"), c2 = statementList(), c3 = match("CBRACE");
			
			if (c1 && c2 && c3)
				derivationStrings.append("<statBlock> -> { <statementList> }");

			else
				error = false;

		}
		
		else if (lookahead.belongsTo(FOLLOW_statBlock))
			derivationStrings.append("<statBlock> -> EPSILON");

		else
			error = false;

		return error;
		
	}
	
	public boolean statementList() { // <statementList> -> <statement> <statementList> | EPSILON
	
		error = skipErrors(union(FIRST_statementList, FOLLOW_statementList));

		if (lookahead.belongsTo(FIRST_statementList)) {

				boolean c1 = statement(), c2 = statementList();
				
			if (c1 && c2)
				derivationStrings.append("<statementList> -> <statement> <statementList>");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FOLLOW_statementList))
			derivationStrings.append("<statementList> -> EPSILON");

		else
			error = false;

		return error;
		
	}
	
	public boolean expr() { // <expr> -> <arithExpr> <exprPRIME>
		
		error = skipErrors(union(FIRST_expr, FOLLOW_expr));

		if (lookahead.belongsTo(FIRST_expr)) {

			boolean c1 = arithExpr(), c2 = exprPRIME();
			
			if (c1 && c2)
				derivationStrings.append("<expr> -> <arithExpr> <exprPRIME>");

			else
				error = false;

		}
		
		else
			error = false;

		return error;
		
	}
	
	public boolean exprPRIME() { // <exprPRIME> -> <relOp> <arithExpr> | EPSILON
	
		error = skipErrors(union(FIRST_exprPRIME, FOLLOW_exprPRIME));

		if (lookahead.belongsTo(FIRST_exprPRIME)) {

			boolean c1 = relOp(), c2 = arithExpr();
			
			if (c1 && c2)
				derivationStrings.append("<exprPRIME> -> <relOp> <arithExpr>");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FOLLOW_exprPRIME))
			derivationStrings.append("<exprPRIME> -> EPSILON");

		else
			error = false;

		return error;
		
	}
	
	public boolean arithExpr() { // <arithExpr> -> <term> <arithExprPRIME>
		
		error = skipErrors(union(FIRST_arithExpr, FOLLOW_arithExpr));

		if (lookahead.belongsTo(FIRST_arithExpr)) {

				boolean c1 = term(), c2 = arithExprPRIME();
				
			if (c1 && c2)
				derivationStrings.append("<arithExpr> -> <term> <arithExprPRIME>");

			else
				error = false;

		}
		
		else
			error = false;

		return error;
		
	}

	public boolean arithExprPRIME() { // <arithExprPRIME> -> <addOp> <term> <arithExprPRIME> | EPSILON
		
		error = skipErrors(union(FIRST_arithExprPRIME, FOLLOW_arithExprPRIME));

		if (lookahead.belongsTo(FIRST_arithExprPRIME)) {
			
			boolean c1 = addOp(), c2 = term(), c3 = arithExprPRIME();
			
			if (c1 && c2 && c3)
				derivationStrings.append("<arithExprPRIME> -> <addOp> <term> <arithExprPRIME>");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FOLLOW_arithExprPRIME))
			derivationStrings.append("<arithExprPRIME> -> EPSILON");

		else
			error = false;

		return error;
		
	}

	public boolean sign() { // <sign> -> + | -
		
		error = skipErrors(union(FIRST_sign, FOLLOW_sign));

		if (lookahead.belongsTo(FIRST_sign_RHS1)) {

			boolean c1 = match("PLUS");
			
			if (c1)
				derivationStrings.append("<sign> -> +");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FIRST_sign_RHS2)) {
			
			boolean c1 = match("MINUS");
			
			if (c1)
				derivationStrings.append("<sign> -> -");

			else
				error = false;
		
		}
		
		else
			error = false;
		
		return error;
		
	}
	
	public boolean term() { // <term> -> <factor> <termPRIME>
		
		error = skipErrors(union(FIRST_term, FOLLOW_term));

		if (lookahead.belongsTo(FIRST_term)) {

			boolean c1 = factor(), c2 = termPRIME();
			
			if (c1 && c2)
				derivationStrings.append("<term> -> <factor> <termPRIME>");

			else
				error = false;

		}
		
		else
			error = false;

		return error;
		
	}
	
	public boolean termPRIME() { // <termPRIME> -> <multOp> <factor> <termPRIME> | EPSILON
		
		error = skipErrors(union(FIRST_termPRIME, FOLLOW_termPRIME));

		if (lookahead.belongsTo(FIRST_termPRIME)) {

			boolean c1 = multOp(), c2 = factor(), c3 = termPRIME();
			
			if (c1 && c2 && c3)
				derivationStrings.append("<termPRIME> -> <multOp> <factor> <termPRIME>");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FOLLOW_termPRIME))
			derivationStrings.append("<termPRIME> -> EPSILON");

		else
			error = false;

		return error;
		
	}

	public boolean factor() { // <factor> -> <sign> <factor> | not <factor> | num | id <idnestList> <factorPrime>
		
		error = skipErrors(union(FIRST_factor, FOLLOW_factor));

		if (lookahead.belongsTo(FIRST_factor_RHS1)) {

			boolean c1 = match("OPAREN"), c2 = expr(), c3 = match("CPAREN");
		
			if (c1 && c2 && c3)
				derivationStrings.append("<factor> -> ( <expr> )");

			else
				error = false;

		}
		
		else if (lookahead.belongsTo(FIRST_factor_RHS2)) {
			
			boolean c1 = match("ID");
			
			boolean c2 = indiceList();
			
			boolean c3 = idnestList();
			
			boolean c4 = factorPRIME();
			
			if (c1 && c2 && c3 && c4)
				derivationStrings.append("<factor> -> id <indiceList> <idnestList> <factorPRIME>");

			else
				error = false;

		}
		
		else if (lookahead.belongsTo(FIRST_factor_RHS3)) {

			boolean c1 = match("NUM");
			
			if (c1)
				derivationStrings.append("<factor> -> num");

			else
				error = false;

		}
		
		else if (lookahead.belongsTo(FIRST_factor_RHS4)) {
			
			boolean c1 = match("NOT"), c2 = factor();
			
			if (c1 && c2)
				derivationStrings.append("<factor> -> not <factor>");

			else
				error = false;

		}
		
		else if (lookahead.belongsTo(FIRST_factor_RHS5)) {

			boolean c1 = sign(), c2 = factor();
			
			if (c1 && c2)
				derivationStrings.append("<factor> -> <sign> <factor>");

			else
				error = false;

		}
		
		else
			error = false;

		return error;
		
	}
	
	public boolean factorPRIME() { // <factorPRIME> -> ( <aParams> ) | EPSILON
		
		error = skipErrors(union(FIRST_factorPRIME, FOLLOW_factorPRIME));

		if (lookahead.belongsTo(FIRST_factorPRIME)) {
			
			boolean c1 = match("OPAREN"), c2 = aParams(), c3 = match("CPAREN");
			
			if (c1 && c2 && c3)
				derivationStrings.append("<factorPRIME> -> ( <aParams> )");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FOLLOW_factorPRIME))
			derivationStrings.append("<factorPRIME> -> EPSILON");

		
		else
			error = false;

		return error;
		
	}
	
	public boolean idnestList() { // <idnestList> -> . id <indiceList> <idnestList>
		
		error = skipErrors(union(FIRST_idnestList, FOLLOW_idnestList));

		if (lookahead.belongsTo(FIRST_idnestList)) {

			boolean c1 = match("DOT");
			
			boolean c2 = match("ID");

			boolean c3 = indiceList();
			
			boolean c4 = idnestList();
			
			if (c1 && c2 && c3 && c4)
				derivationStrings.append("<idnestList> -> . id <indiceList> <idnestList>");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FOLLOW_idnestList))
			derivationStrings.append("<idnestList> -> EPSILON");

		else
			error = false;

		return error;
		
	}
	
	//Same as idnestList but handles calls differently
	public boolean idnestListPRIME() { // <idnestList> -> . id <indiceList> <idnestList>
		
		error = skipErrors(union(FIRST_idnestList, FOLLOW_idnestList));

		if (lookahead.belongsTo(FIRST_idnestList)) {

			boolean c1 = match("DOT");
			
			boolean c2 = match("ID");
			
			boolean c3 = indiceList();

			boolean c4 = idnestListPRIME();
			
			if (c1 && c2 && c3 && c4)
				derivationStrings.append("<idnestListPRIME> -> . id <indiceList> <idnestListPRIME>");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FOLLOW_idnestList))
			derivationStrings.append("<idnestList> -> EPSILON");

		else
			error = false;

		return error;
		
	}
	
	public boolean variable() { // <variable> -> id <idnestListPRIME>
		
		error = skipErrors(union(FIRST_variable, FOLLOW_variable));

		if (lookahead.belongsTo(FIRST_variable)) {
			
			boolean c1 = match("ID");
			
			boolean c2 = idnestListPRIME();
			
			if (c1 && c2)
				derivationStrings.append("<variable> -> id <idnestListPRIME>");

			else
				error = false;

		}

		else
			error = false;

		return error;
		
	}
	
	public boolean indiceList() { // <indiceList> -> <indice> <indiceList> | EPSILON
		
		error = skipErrors(union(FIRST_indiceList, FOLLOW_indiceList));

		if (lookahead.belongsTo(FIRST_indiceList)) {
			
			boolean c1 = indice(), c2 = indiceList();
			
			if (c1 && c2)
				derivationStrings.append("<indiceList> -> <indice> <indiceList>");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FOLLOW_indiceList))
			derivationStrings.append("<indiceList> -> EPSILON");

		else
			error = false;

		return error;
		
	}
	
	public boolean fParams() { // <fParams> -> <type> id <arraySizeList> <fParamsTailList> | EPSILON
		
		error = skipErrors(union(FIRST_fParams, FOLLOW_fParams));

		if (lookahead.belongsTo(FIRST_fParams)) {
			
			boolean c1 = type(), c2 = match("ID");
			
			boolean c3 = arraySizeList(), c4 = fParamsTailList();
			
			if (c1 && c2 && c3 && c4)
				derivationStrings.append("<fParams> -> <type> id <arraySizeList> <fParamsTailList>");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FOLLOW_fParams))
			derivationStrings.append("<fParams> -> EPSILON");

		else
			error = false;

		return error;
		
	}
	
	public boolean fParamsTailList() { // <fParamsTailList> -> <fParamsTail> <fParamsTailList> | EPSILON
		
		error = skipErrors(union(FIRST_fParamsTailList, FOLLOW_fParamsTailList));

		if (lookahead.belongsTo(FIRST_fParamsTailList)) {

			boolean c1 = fParamsTail(), c2 = fParamsTailList();
			
			if (c1 && c2)
				derivationStrings.append("<fParamsTailList> -> <fParamsTail> <fParamsTailList>");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FOLLOW_fParamsTailList))
			derivationStrings.append("<fParamsTailList> -> EPSILON");

		else
			error = false;

		return error;
		
	}
	
	public boolean aParams() { // <aParams> -> <expr> <aParamsTailList> | EPSILON
		
		error = skipErrors(union(FIRST_aParams, FOLLOW_aParams));

		if (lookahead.belongsTo(FIRST_aParams)) {

			boolean c1 = expr();
			
			boolean c2 = aParamsTailList();
			
			if (c1 && c2)
				derivationStrings.append("<aParams> -> <expr> <aParamsTailList>");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FOLLOW_aParams))
			derivationStrings.append("<aParams> -> EPSILON");

		else
			error = false;

		return error;
		
	}
	
	public boolean aParamsTailList() { // <aParamsTailList> -> <aParamsTail> <aParamsTailList> | EPSILON
		
		error = skipErrors(union(FIRST_aParamsTailList, FOLLOW_aParamsTailList));

		if (lookahead.belongsTo(FIRST_aParamsTailList)) {

			boolean c1 = aParamsTail();
			
			boolean c2 = aParamsTailList();
			
			if (c1 && c2)
				derivationStrings.append("<aParamsTailList> -> <aParamsTail> <aParamsTailList>");

			else
				error = false;

		}

		else if (lookahead.belongsTo(FOLLOW_aParamsTailList))
			derivationStrings.append("<aParamsTailList> -> EPSILON");

		else
			error = false;

		return error;
		
	}
	
	public boolean relOp() { // <relOp> -> < | = | > | <> | <= | == | >=
		
		error = skipErrors(union(FIRST_relOp, FOLLOW_relOp));

		if (lookahead.belongsTo(FIRST_relOp_RHS1)) {
			
			boolean c1 = match("LESS");
			
			if (c1)
				derivationStrings.append("<relOp> -> < <relOpPRIMEPRIME");

			else
				error = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS2)) {

			boolean c1 = match(">");
			
			if (c1)
				derivationStrings.append("<relOp> -> >");

			else
				error = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS3)) {

			boolean c1 = match("NEQ");
			
			if (c1)
				derivationStrings.append("<relOp> -> <>");

			else
				error = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS4)) {

			boolean c1 = match("LEQ");
			
			if (c1)
				derivationStrings.append("<relOp> -> <=");

			else
				error = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS5)) {
			
			boolean c1 = match("EQ");
			
			if (c1)
				derivationStrings.append("<relOp> -> ==");

			else
				error = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS6)) {

			boolean c1 = match("GEQ");
			
			if (c1)
				derivationStrings.append("<relOp> -> >=");

			else
				error = false;

		}
		
		else
			error = false;

		return error;
		
	}
	
	public boolean addOp() { // <addOp> -> + | - | or
		
		error = skipErrors(union(FIRST_addOp, FOLLOW_addOp));

		if (lookahead.belongsTo(FIRST_addOp_RHS1)) {

			boolean c1 = match("OR");
			
			if (c1)
				derivationStrings.append("<addOp> -> or");

			else
				error = false;

		}
		
		else if (lookahead.belongsTo(FIRST_addOp_RHS2)) {

			boolean c1 = match("MINUS");
			
			if (c1)
				derivationStrings.append("<addOp> -> -");

			else
				error = false;

		}
		
		else if (lookahead.belongsTo(FIRST_addOp_RHS3)) {
			
			boolean c1 = match("PLUS");
			
			if (c1)
				derivationStrings.append("<addOp> -> +");

			else
				error = false;

		}
		
		else
			error = false;

		return error;
		
	}
	
	public boolean multOp() { // <multOp> -> * | / | and
		
		error = skipErrors(union(FIRST_multOp, FOLLOW_multOp));

		if (lookahead.belongsTo(FIRST_multOp_RHS1)) {

			boolean c1 = match("AND");
			
			if (c1)
				derivationStrings.append("<multOp> -> and");

			else
				error = false;

		}
		
		else if (lookahead.belongsTo(FIRST_multOp_RHS2)) {

			boolean c1 = match("FWDSLASH");
			
			if (c1)
				derivationStrings.append("<multOp> -> /");

			else
				error = false;

		}
		
		else if (lookahead.belongsTo(FIRST_multOp_RHS3)) {

			boolean c1 = match("ASTERISK");
			
			if (c1)
				derivationStrings.append("<multOp> -> *");

			else
				error = false;

		}
		
		else
			error = false;

		return error;
		
	}
	
}
package compiler;

import java.util.Arrays;

public class Parser {
	
	//FIRST and FOLLOW sets
	private static final String[] 

			FIRST_prog = {"CLASS", "PROGRAM"},
			FOLLOW_prog = {"EOF"},

			FIRST_classDeclList = {"CLASS"},
			FOLLOW_classDeclList = {"PROGRAM"},

			FIRST_classMemberDeclPRIME = {"OBRACKET", "OPAREN"},
				FIRST_classMemberDeclPRIME_RHS1 = {"OBRACKET"},
				FIRST_classMemberDeclPRIME_RHS2 = {"OPAREN"},
			FOLLOW_classMemberDeclPRIME = {"SEMICOLON"},

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

			FIRST_funcMemberPRIMEPRIME = {"DOT"},
			FOLLOW_funcMemberPRIMEPRIME = {"DEF"},

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
				FIRST_factor_RHS1 = {"NOT", "PLUS", "MINUS"},
				FIRST_factor_RHS2 = {"ID"},
				FIRST_factor_RHS3 = {"FRACTION", "INTEGER"},
				FIRST_factor_RHS4 = {"OPAREN"},
			FOLLOW_factor = {"ASTERISK", "FWDSLASH", "AND", "SEMICOLON", "CPAREN", "COMMA", "LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ", "CBRACKET", "PLUS", "MINUS", "OR"},

			FIRST_factorPRIME = {"OPAREN"},
			FOLLOW_factorPRIME = {"SEMICOLON", "CPAREN", "COMMA", "LESS", "EQ", "GREATER", "GEQ", "LEQ", "NEQ", "CBRACKET", "PLUS", "MINUS", "OR", "ASTERISK", "FWDSLASH", "AND"},

			FIRST_variableNestList = {"DOT"},
			FOLLOW_variableNestList = {"DEF", "OPAREN", "CPAREN", "ASTERISK", "FWDSLASH", "AND", "OR", "PLUS", "MINUS", "CBRACKET", "EQ", "NEQ", "GEQ", "LEQ", "LESS", "GREATER", "SEMICOLON", "COMMA"},

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
			FOLLOW_number = {"ASTERISK", "FWDSLASH", "AND", "OR", "PLUS", "MINUS", "CBRACKET", "CPAREN", "EQ", "NEQ", "LESS", "GREATER", "LEQ", "GEQ", "SEMICOLON", "COMMA"},

	private Lexer lexer;
	private StringBuilder derivationStrings;
	private StringBuilder errorStrings;

	private Token lookahead;
	private boolean valid;
	
	public Parser(Lexer lexer, StringBuilder derivationStrings, StringBuilder errorStrings) {

		this.lexer = lexer;
		this.derivationStrings = derivationStrings;
		this.errorStrings = errorStrings;

	}

	//a local version of nextToken which skips comments
	public void nextToken() {

		do
			lookahead = lexer.nextToken();
		while (lookahead.getType().equals("BLOCKCOMMENT") || lookahead.getType().equals("COMMENT"));
			
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

	//Skips errors and recovers the parser from valid detection
	public boolean skipErrors(String[] firstUfollow) {

		if (Arrays.asList(firstUfollow).contains(lookahead.getType()))
			return true;
		
		else {
			
			errorStrings.append("Syntax error at line ").append(lookahead.getLine()).append(" Misplaced token: ").append(lookahead.getType());

			while (!Arrays.asList(firstUfollow).contains(lookahead.getType()) && !lookahead.getType().equals("EOF"))
				nextToken();

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
	public boolean prog() { // <prog> -> <classDeclList> program <funcBody> ; <funcDefList>

		valid = skipErrors(union(FIRST_prog, FOLLOW_prog));

		if (lookahead.belongsTo(FIRST_prog)) {

			if (classDeclList()
					& match("PROGRAM")
					& funcBody()
					& match("SEMICOLON")
					& funcDefList())
				derivationStrings.append("<prog> -> <classDeclList> program <funcBody> ; <funcDefList>");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	public boolean classDeclList() { // <classDeclList> -> class id { classMemberDeclList } ; <classDeclList> | EPSILON
		
		valid = skipErrors(union(FIRST_classDeclList, FOLLOW_classDeclList));

		if (lookahead.belongsTo(FIRST_classDeclList)) {
			
			if (match("CLASS")
					& match("ID")
					& match("OBRACE")
					& classMemberDeclList()
					& match("CBRACE")
					& match("SEMICOLON")
					& classDeclList())
				derivationStrings.append("<classDeclList> ->  class id { classMemberDeclList } ; <classDeclList>");
			
			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_classDeclList))
			derivationStrings.append("<classDeclList> -> EPSILON");

		else
			valid = false;

		return valid;

	}

	public boolean classMemberDeclPRIME() { // <classMemberDeclPRIME -> <arraySizeList> | ( <fParams> ) <funcBody>

		valid = skipErrors(union(FIRST_classMemberDeclPRIME, FOLLOW_classMemberDeclPRIME));

		if (lookahead.belongsTo(FIRST_classMemberDeclPRIME_RHS1)) {
			
			if (arraySizeList())
				derivationStrings.append("<classMemberDeclPRIME> -> <arraySizeList>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_classMemberDeclPRIME_RHS2)) {

			if (match("OPAREN")
					& fParams()
					& match("CPAREN")
					& funcBody())
				derivationStrings.append("<classMemberDeclPRIME> -> ( <fParams> ) <funcBody>");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	public boolean classMemberDeclList() { // <classMemberDeclList> -> <typeId> <classMemberDeclPRIME> ; <classMemberDeclList> | EPSILON

		valid = skipErrors(union(FIRST_classMemberDeclList, FOLLOW_classMemberDeclList));

		if (lookahead.belongsTo(FIRST_classMemberDeclList)) {
			
			if (typeId()
					& classMemberDeclPRIME()
					& match("SEMICOLON")
					& classMemberDeclList())
				derivationStrings.append("<classMemberDeclList> -> <typeId> <classMemberDeclPRIME> ; <classMemberDeclList>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_classMemberDeclList))
			derivationStrings.append("<classMemberDeclList> -> EPSILON");

		else
			valid = false;

		return valid;

	}

	public boolean funcDefList() { // <funcDefList> -> <typeId> ( <fParams> ) <funcBody> ; <funcDefList> | EPSILON

		valid = skipErrors(union(FIRST_funcDefList, FOLLOW_funcDefList));

		if (lookahead.belongsTo(FIRST_funcDefList)) {
			
			if (typeId()
					& match("OPAREN")
					& fParams()
					& match("CPAREN")
					& funcBody()
					& match("SEMICOLON")
					& funcDefList())
				derivationStrings.append("<funcDefList> -> <typeId> ( <fParams> ) <funcBody> ; <funcDefList>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_funcDefList))
			derivationStrings.append("<funcDefList> -> EPSILON");

		else
			valid = false;

		return valid;

	}

	public boolean funcBody() { // <funcBody> -> { <funcBodyMemberList> }

		valid = skipErrors(union(FIRST_funcBody, FOLLOW_funcBody));

		if (lookahead.belongsTo(FIRST_funcBody)) {
			
			if (match("OBRACE")
					& funcBodyMemberList()
					& match("CBRACE"))
				derivationStrings.append("<funcBody> -> { <funcBodyMemberList> }");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	public boolean funcBodyMember() { // <funcBodyMember> -> statementPRIME | id <funcBodyMemberPRIME> | <simpleType> id <arraySizeList>

		valid = skipErrors(union(FIRST_funcMember, FOLLOW_funcMember));

		if (lookahead.belongsTo(FIRST_funcMember_RHS1)) {

			if (statementPRIME())
				derivationStrings.append("<funcBodyMember> -> <statementPRIME>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_funcMember_RHS2)) {
			
			if (match("ID")
					& funcBodyMemberPRIME())
				derivationStrings.append("<funcBodyMember> -> id <funcBodyMemberPRIME>");

			else
				valid = false;
			
		}

		else if (lookahead.belongsTo(FIRST_funcMember_RHS3)) {

			if (simpleType()
					& match("ID")
					& arraySizeList())
				derivationStrings.append("<funcBodyMember> -> <simpleType> id <arraySizeList>");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	public boolean funcBodyMemberPRIME() { // <funcBodyMemberPRIME> -> <indiceList> <funcBodyMemberPRIMEPRIME> = <expr> | id <arraySizeList>

		valid = skipErrors(union(FIRST_funcMemberPRIME, FOLLOW_funcMemberPRIME));

		if (lookahead.belongsTo(FIRST_funcMemberPRIME_RHS1)) {

			if (match("ID")
					& arraySizeList())
				derivationStrings.append("<funcBodyMemberPRIME> -> id <arraySizeList>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_funcMemberPRIME_RHS2)) {

			if (indiceList()
					& funcBodyMemberPRIMEPRIME()
					& match("DEF")
					& expr())
				derivationStrings.append("<funcBodyMemberPRIME> -> <indiceList> <funcBodyMemberPRIMEPRIME> = <expr>");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	public boolean funcBodyMemberPRIMEPRIME() { // <funcBodyMemberPRIMEPRIME> -> . <variable> | EPSILON

		valid = skipErrors(union(FIRST_funcMemberPRIMEPRIME, FOLLOW_funcMemberPRIMEPRIME));

		if (lookahead.belongsTo(FIRST_funcMemberPRIMEPRIME)) {

			if (match("DOT")
					& variable())
				derivationStrings.append("<funcBodyMemberPRIMEPRIME> -> . <variable>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_funcMemberPRIMEPRIME))
			derivationStrings.append("<funcBodyMemberPRIMEPRIME> -> EPSILON");

		else
			valid = false;

		return valid;

	}

	public boolean funcBodyMemberList() { // <funcBodyMemberList> -> <funcBodyMember> ; <funcBodyMemberList> | EPSILON

		valid = skipErrors(union(FIRST_funcMemberList, FOLLOW_funcMemberList));
		
		if (lookahead.belongsTo(FIRST_funcMemberList)) {

			if (funcBodyMember()
					& match("SEMICOLON")
					& funcBodyMemberList())
				derivationStrings.append("<funcBodyMemberList> -> <funcBodyMember> ; <funcBodyMemberList>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_funcMemberList))
			derivationStrings.append("<funcBodyMemberList> -> EPSILON");

		else
			valid = false;

		return valid;

	}

	public boolean arraySizeList() { // <arraySizeList> -> <arraySize> <arraySizeList> | EPSILON

		valid = skipErrors(union(FIRST_arraySizeList, FOLLOW_arraySizeList));

		if (lookahead.belongsTo(FIRST_arraySizeList)) {

			if (match("OBRACKET")
					& match("INTEGER")
					& match("CBRACKET")
					& arraySizeList())
				derivationStrings.append("<arraySizeList> -> [ integer ] <arraySizeList>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_arraySizeList))
			derivationStrings.append("<arraySizeList> -> EPSILON");

		else
			valid = false;

		return valid;

	}

	public boolean statement() { // <statement> -> <assignStat> | <statementPRIME>

		valid = skipErrors(union(FIRST_statement, FOLLOW_statement));

		if (lookahead.belongsTo(FIRST_statement_RHS1)) {

			if (assignStat())
				derivationStrings.append("<statement> -> <assignStat>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_statement_RHS2)) {

			if (statementPRIME())
				derivationStrings.append("<statement> -> <statementPRIME>");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	public boolean statementPRIME() { // <statementPRIME> -> <statementPRIMEPRIME> <statBlock> | ioAction ( variable ) | return ( <expr> )

		valid = skipErrors(union(FIRST_statementPRIME, FOLLOW_statementPRIME));

		if (lookahead.belongsTo(FIRST_statementPRIME_RHS1)) {

			if (statementPRIMEPRIME()
					& statBlock())
				derivationStrings.append("<statementPRIME> -> <statementPRIMEPRIME> <statBlock>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_statementPRIME_RHS2)) {

			if (ioAction()
					& match("OPAREN")
					& variable()
					& match("CPAREN"))
				derivationStrings.append("<statementPRIME> -> ioAction ( variable )");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_statementPRIME_RHS3)) {

			if (match("RETURN")
					& match("OPAREN")
					& expr()
					& match("CPAREN"))
				derivationStrings.append("<statementPRIME> -> return ( <expr> )");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	public boolean statementPRIMEPRIME() { // <statementPRIMEPRIME> -> if ( <expr> ) then <statBlock> else | for ( type id = expr ; arithExpr relOp arithExpr ; assignStat )

		valid = skipErrors(union(FIRST_statementPRIMEPRIME, FOLLOW_statementPRIMEPRIME));

		if (lookahead.belongsTo(FIRST_statementPRIMEPRIME_RHS1)) {

			if (assignStat())
				derivationStrings.append("<statement> -> <assignStat>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_statementPRIMEPRIME_RHS2)) {

			if (statementPRIME())
				derivationStrings.append("<statement> -> <statementPRIME>");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	public boolean type() { // <type> -> <simpleType> | id

		valid = skipErrors(union(FIRST_type, FOLLOW_type));

		if (lookahead.belongsTo(FIRST_type_RHS1)) {

			boolean c1 = match("REAL");
			
			if (c1)
				derivationStrings.append("<type> -> real");
			
			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_type_RHS2)) {

			boolean c1 = match("INTEGER");
			
			if (c1)
				derivationStrings.append("<type> -> integer");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_type_RHS3)) {

			boolean c1 = match("ID");
			
			if (c1)
				derivationStrings.append("<type> -> id");

			else
				valid = false;

		}
		
		else
			valid = false;

		return valid;

	}
	

	
	public boolean statBlock() { // <statBlock> -> { <statementList> } | <statement> | EPSILON
		
		valid = skipErrors(union(FIRST_statBlock, FOLLOW_statBlock));

		if (lookahead.belongsTo(FIRST_statBlock_RHS1)) {

			boolean c1 = statement();
			
			if (c1)
				derivationStrings.append("<statBlock> -> <statement>");

			else
				valid = false;

		}
		
		if (lookahead.belongsTo(FIRST_statBlock_RHS2)) {
			
			boolean c1 = match("OBRACE"), c2 = statementList(), c3 = match("CBRACE");
			
			if (c1 && c2 && c3)
				derivationStrings.append("<statBlock> -> { <statementList> }");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FOLLOW_statBlock))
			derivationStrings.append("<statBlock> -> EPSILON");

		else
			valid = false;

		return valid;
		
	}
	
	public boolean statementList() { // <statementList> -> <statement> <statementList> | EPSILON
	
		valid = skipErrors(union(FIRST_statementList, FOLLOW_statementList));

		if (lookahead.belongsTo(FIRST_statementList)) {

				boolean c1 = statement(), c2 = statementList();
				
			if (c1 && c2)
				derivationStrings.append("<statementList> -> <statement> <statementList>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_statementList))
			derivationStrings.append("<statementList> -> EPSILON");

		else
			valid = false;

		return valid;
		
	}
	
	public boolean expr() { // <expr> -> <arithExpr> <exprPRIME>
		
		valid = skipErrors(union(FIRST_expr, FOLLOW_expr));

		if (lookahead.belongsTo(FIRST_expr)) {

			boolean c1 = arithExpr(), c2 = exprPRIME();
			
			if (c1 && c2)
				derivationStrings.append("<expr> -> <arithExpr> <exprPRIME>");

			else
				valid = false;

		}
		
		else
			valid = false;

		return valid;
		
	}
	
	public boolean exprPRIME() { // <exprPRIME> -> <relOp> <arithExpr> | EPSILON
	
		valid = skipErrors(union(FIRST_exprPRIME, FOLLOW_exprPRIME));

		if (lookahead.belongsTo(FIRST_exprPRIME)) {

			boolean c1 = relOp(), c2 = arithExpr();
			
			if (c1 && c2)
				derivationStrings.append("<exprPRIME> -> <relOp> <arithExpr>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_exprPRIME))
			derivationStrings.append("<exprPRIME> -> EPSILON");

		else
			valid = false;

		return valid;
		
	}
	
	public boolean arithExpr() { // <arithExpr> -> <term> <arithExprPRIME>
		
		valid = skipErrors(union(FIRST_arithExpr, FOLLOW_arithExpr));

		if (lookahead.belongsTo(FIRST_arithExpr)) {

				boolean c1 = term(), c2 = arithExprPRIME();
				
			if (c1 && c2)
				derivationStrings.append("<arithExpr> -> <term> <arithExprPRIME>");

			else
				valid = false;

		}
		
		else
			valid = false;

		return valid;
		
	}

	public boolean arithExprPRIME() { // <arithExprPRIME> -> <addOp> <term> <arithExprPRIME> | EPSILON
		
		valid = skipErrors(union(FIRST_arithExprPRIME, FOLLOW_arithExprPRIME));

		if (lookahead.belongsTo(FIRST_arithExprPRIME)) {
			
			boolean c1 = addOp(), c2 = term(), c3 = arithExprPRIME();
			
			if (c1 && c2 && c3)
				derivationStrings.append("<arithExprPRIME> -> <addOp> <term> <arithExprPRIME>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_arithExprPRIME))
			derivationStrings.append("<arithExprPRIME> -> EPSILON");

		else
			valid = false;

		return valid;
		
	}

	public boolean sign() { // <sign> -> + | -
		
		valid = skipErrors(union(FIRST_sign, FOLLOW_sign));

		if (lookahead.belongsTo(FIRST_sign_RHS1)) {

			boolean c1 = match("PLUS");
			
			if (c1)
				derivationStrings.append("<sign> -> +");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_sign_RHS2)) {
			
			boolean c1 = match("MINUS");
			
			if (c1)
				derivationStrings.append("<sign> -> -");

			else
				valid = false;
		
		}
		
		else
			valid = false;
		
		return valid;
		
	}
	
	public boolean term() { // <term> -> <factor> <termPRIME>
		
		valid = skipErrors(union(FIRST_term, FOLLOW_term));

		if (lookahead.belongsTo(FIRST_term)) {

			boolean c1 = factor(), c2 = termPRIME();
			
			if (c1 && c2)
				derivationStrings.append("<term> -> <factor> <termPRIME>");

			else
				valid = false;

		}
		
		else
			valid = false;

		return valid;
		
	}
	
	public boolean termPRIME() { // <termPRIME> -> <multOp> <factor> <termPRIME> | EPSILON
		
		valid = skipErrors(union(FIRST_termPRIME, FOLLOW_termPRIME));

		if (lookahead.belongsTo(FIRST_termPRIME)) {

			boolean c1 = multOp(), c2 = factor(), c3 = termPRIME();
			
			if (c1 && c2 && c3)
				derivationStrings.append("<termPRIME> -> <multOp> <factor> <termPRIME>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_termPRIME))
			derivationStrings.append("<termPRIME> -> EPSILON");

		else
			valid = false;

		return valid;
		
	}

	public boolean factor() { // <factor> -> <sign> <factor> | not <factor> | num | id <idnestList> <factorPrime>
		
		valid = skipErrors(union(FIRST_factor, FOLLOW_factor));

		if (lookahead.belongsTo(FIRST_factor_RHS1)) {

			boolean c1 = match("OPAREN"), c2 = expr(), c3 = match("CPAREN");
		
			if (c1 && c2 && c3)
				derivationStrings.append("<factor> -> ( <expr> )");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_factor_RHS2)) {
			
			boolean c1 = match("ID");
			
			boolean c2 = indiceList();
			
			boolean c3 = idnestList();
			
			boolean c4 = factorPRIME();
			
			if (c1 && c2 && c3 && c4)
				derivationStrings.append("<factor> -> id <indiceList> <idnestList> <factorPRIME>");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_factor_RHS3)) {

			boolean c1 = match("NUM");
			
			if (c1)
				derivationStrings.append("<factor> -> num");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_factor_RHS4)) {
			
			boolean c1 = match("NOT"), c2 = factor();
			
			if (c1 && c2)
				derivationStrings.append("<factor> -> not <factor>");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_factor_RHS5)) {

			boolean c1 = sign(), c2 = factor();
			
			if (c1 && c2)
				derivationStrings.append("<factor> -> <sign> <factor>");

			else
				valid = false;

		}
		
		else
			valid = false;

		return valid;
		
	}
	
	public boolean factorPRIME() { // <factorPRIME> -> ( <aParams> ) | EPSILON
		
		valid = skipErrors(union(FIRST_factorPRIME, FOLLOW_factorPRIME));

		if (lookahead.belongsTo(FIRST_factorPRIME)) {
			
			boolean c1 = match("OPAREN"), c2 = aParams(), c3 = match("CPAREN");
			
			if (c1 && c2 && c3)
				derivationStrings.append("<factorPRIME> -> ( <aParams> )");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_factorPRIME))
			derivationStrings.append("<factorPRIME> -> EPSILON");

		
		else
			valid = false;

		return valid;
		
	}
	
	public boolean idnestList() { // <idnestList> -> . id <indiceList> <idnestList>
		
		valid = skipErrors(union(FIRST_idnestList, FOLLOW_idnestList));

		if (lookahead.belongsTo(FIRST_idnestList)) {

			boolean c1 = match("DOT");
			
			boolean c2 = match("ID");

			boolean c3 = indiceList();
			
			boolean c4 = idnestList();
			
			if (c1 && c2 && c3 && c4)
				derivationStrings.append("<idnestList> -> . id <indiceList> <idnestList>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_idnestList))
			derivationStrings.append("<idnestList> -> EPSILON");

		else
			valid = false;

		return valid;
		
	}
	
	//Same as idnestList but handles calls differently
	public boolean idnestListPRIME() { // <idnestList> -> . id <indiceList> <idnestList>
		
		valid = skipErrors(union(FIRST_idnestList, FOLLOW_idnestList));

		if (lookahead.belongsTo(FIRST_idnestList)) {

			boolean c1 = match("DOT");
			
			boolean c2 = match("ID");
			
			boolean c3 = indiceList();

			boolean c4 = idnestListPRIME();
			
			if (c1 && c2 && c3 && c4)
				derivationStrings.append("<idnestListPRIME> -> . id <indiceList> <idnestListPRIME>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_idnestList))
			derivationStrings.append("<idnestList> -> EPSILON");

		else
			valid = false;

		return valid;
		
	}
	
	public boolean variable() { // <variable> -> id <idnestListPRIME>
		
		valid = skipErrors(union(FIRST_variable, FOLLOW_variable));

		if (lookahead.belongsTo(FIRST_variable)) {
			
			boolean c1 = match("ID");
			
			boolean c2 = idnestListPRIME();
			
			if (c1 && c2)
				derivationStrings.append("<variable> -> id <idnestListPRIME>");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;
		
	}
	
	public boolean indiceList() { // <indiceList> -> <indice> <indiceList> | EPSILON
		
		valid = skipErrors(union(FIRST_indiceList, FOLLOW_indiceList));

		if (lookahead.belongsTo(FIRST_indiceList)) {
			
			boolean c1 = indice(), c2 = indiceList();
			
			if (c1 && c2)
				derivationStrings.append("<indiceList> -> <indice> <indiceList>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_indiceList))
			derivationStrings.append("<indiceList> -> EPSILON");

		else
			valid = false;

		return valid;
		
	}
	
	public boolean fParams() { // <fParams> -> <type> id <arraySizeList> <fParamsTailList> | EPSILON
		
		valid = skipErrors(union(FIRST_fParams, FOLLOW_fParams));

		if (lookahead.belongsTo(FIRST_fParams)) {
			
			boolean c1 = type(), c2 = match("ID");
			
			boolean c3 = arraySizeList(), c4 = fParamsTailList();
			
			if (c1 && c2 && c3 && c4)
				derivationStrings.append("<fParams> -> <type> id <arraySizeList> <fParamsTailList>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_fParams))
			derivationStrings.append("<fParams> -> EPSILON");

		else
			valid = false;

		return valid;
		
	}
	
	public boolean fParamsTailList() { // <fParamsTailList> -> <fParamsTail> <fParamsTailList> | EPSILON
		
		valid = skipErrors(union(FIRST_fParamsTailList, FOLLOW_fParamsTailList));

		if (lookahead.belongsTo(FIRST_fParamsTailList)) {

			boolean c1 = fParamsTail(), c2 = fParamsTailList();
			
			if (c1 && c2)
				derivationStrings.append("<fParamsTailList> -> <fParamsTail> <fParamsTailList>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_fParamsTailList))
			derivationStrings.append("<fParamsTailList> -> EPSILON");

		else
			valid = false;

		return valid;
		
	}
	
	public boolean aParams() { // <aParams> -> <expr> <aParamsTailList> | EPSILON
		
		valid = skipErrors(union(FIRST_aParams, FOLLOW_aParams));

		if (lookahead.belongsTo(FIRST_aParams)) {

			boolean c1 = expr();
			
			boolean c2 = aParamsTailList();
			
			if (c1 && c2)
				derivationStrings.append("<aParams> -> <expr> <aParamsTailList>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_aParams))
			derivationStrings.append("<aParams> -> EPSILON");

		else
			valid = false;

		return valid;
		
	}
	
	public boolean aParamsTailList() { // <aParamsTailList> -> <aParamsTail> <aParamsTailList> | EPSILON
		
		valid = skipErrors(union(FIRST_aParamsTailList, FOLLOW_aParamsTailList));

		if (lookahead.belongsTo(FIRST_aParamsTailList)) {

			boolean c1 = aParamsTail();
			
			boolean c2 = aParamsTailList();
			
			if (c1 && c2)
				derivationStrings.append("<aParamsTailList> -> <aParamsTail> <aParamsTailList>");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_aParamsTailList))
			derivationStrings.append("<aParamsTailList> -> EPSILON");

		else
			valid = false;

		return valid;
		
	}
	
	public boolean relOp() { // <relOp> -> < | = | > | <> | <= | == | >=
		
		valid = skipErrors(union(FIRST_relOp, FOLLOW_relOp));

		if (lookahead.belongsTo(FIRST_relOp_RHS1)) {
			
			boolean c1 = match("LESS");
			
			if (c1)
				derivationStrings.append("<relOp> -> < <relOpPRIMEPRIME");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS2)) {

			boolean c1 = match(">");
			
			if (c1)
				derivationStrings.append("<relOp> -> >");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS3)) {

			boolean c1 = match("NEQ");
			
			if (c1)
				derivationStrings.append("<relOp> -> <>");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS4)) {

			boolean c1 = match("LEQ");
			
			if (c1)
				derivationStrings.append("<relOp> -> <=");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS5)) {
			
			boolean c1 = match("EQ");
			
			if (c1)
				derivationStrings.append("<relOp> -> ==");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS6)) {

			boolean c1 = match("GEQ");
			
			if (c1)
				derivationStrings.append("<relOp> -> >=");

			else
				valid = false;

		}
		
		else
			valid = false;

		return valid;
		
	}
	
	public boolean addOp() { // <addOp> -> + | - | or
		
		valid = skipErrors(union(FIRST_addOp, FOLLOW_addOp));

		if (lookahead.belongsTo(FIRST_addOp_RHS1)) {

			boolean c1 = match("OR");
			
			if (c1)
				derivationStrings.append("<addOp> -> or");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_addOp_RHS2)) {

			boolean c1 = match("MINUS");
			
			if (c1)
				derivationStrings.append("<addOp> -> -");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_addOp_RHS3)) {
			
			boolean c1 = match("PLUS");
			
			if (c1)
				derivationStrings.append("<addOp> -> +");

			else
				valid = false;

		}
		
		else
			valid = false;

		return valid;
		
	}
	
	public boolean multOp() { // <multOp> -> * | / | and
		
		valid = skipErrors(union(FIRST_multOp, FOLLOW_multOp));

		if (lookahead.belongsTo(FIRST_multOp_RHS1)) {

			boolean c1 = match("AND");
			
			if (c1)
				derivationStrings.append("<multOp> -> and");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_multOp_RHS2)) {

			boolean c1 = match("FWDSLASH");
			
			if (c1)
				derivationStrings.append("<multOp> -> /");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_multOp_RHS3)) {

			boolean c1 = match("ASTERISK");
			
			if (c1)
				derivationStrings.append("<multOp> -> *");

			else
				valid = false;

		}
		
		else
			valid = false;

		return valid;
		
	}
	
}
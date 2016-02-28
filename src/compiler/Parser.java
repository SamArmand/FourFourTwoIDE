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
	private StringBuilder derivationStrings;
	private StringBuilder errorStrings;

	private Token lookahead;
	
	public Parser(Lexer lexer, StringBuilder errorStrings, StringBuilder derivationStrings) {

		this.lexer = lexer;
		this.derivationStrings = derivationStrings;
		this.errorStrings = errorStrings;

	}

	//main parse function and head of recursive descent
	public boolean parse() {

		nextToken();

		return prog() & match("EOF");

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
			
			nextToken();
			return true;

		}
		
		else {

			errorStrings.append("Syntax error at line ").append(lookahead.getLine()).append(". Expected: ").append(Token.toDescription(expectedToken)).append("\n");
			return false;

		}

	}

	//Skips errors and recovers the parser from valid detection
	private boolean skipErrors(String[] firstUfollow) {

		if (Arrays.asList(firstUfollow).contains(lookahead.getType()))
			return true;
		
		else {
			
			errorStrings.append("Syntax error at line ").append(lookahead.getLine()).append(" Misplaced token: ").append(lookahead.getLexeme()).append("\n");

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
	private boolean prog() { // <prog> -> <classDeclList> program { <funcMemberList> } ; <funcDefList>

		boolean valid = skipErrors(union(FIRST_prog, FOLLOW_prog));

		if (lookahead.belongsTo(FIRST_prog)) {

			if (classDeclList()
					& match("PROGRAM")
					& match("OBRACE")
					& funcMemberList()
					& match("CBRACE")
					& match("SEMICOLON")
					& funcDefList())
				derivationStrings.append("<prog> -> <classDeclList> program { <funcMemberList> } ; <funcDefList>").append("\n");

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
			
			if (match("CLASS")
					& match("ID")
					& match("OBRACE")
					& classMemberDeclList()
					& match("CBRACE")
					& match("SEMICOLON")
					& classDeclList())
				derivationStrings.append("<classDeclList> ->  class id { <classMemberDeclList> } ; <classDeclList>").append("\n");
			
			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_classDeclList))
			derivationStrings.append("<classDeclList> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean classMemberDeclPRIME() { // <classMemberDeclPRIME> -> <arraySizeList> | ( <fParams> ) { <funcMemberList> }

		boolean valid = skipErrors(union(FIRST_classMemberDeclPRIME, FOLLOW_classMemberDeclPRIME));

		if (lookahead.belongsTo(FIRST_classMemberDeclPRIME_RHS1)) {
			
			if (arraySizeList())
				derivationStrings.append("<classMemberDeclPRIME> -> <arraySizeList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_classMemberDeclPRIME_RHS2)) {

			if (match("OPAREN")
					& fParams()
					& match("CPAREN")
					& match("OBRACE")
					& funcMemberList()
					& match("CBRACE"))
				derivationStrings.append("<classMemberDeclPRIME> -> ( <fParams> ) { <funcMemberList> }").append("\n");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean classMemberDeclList() { // <classMemberDeclList> -> <type> id <classMemberDeclPRIME> ; <classMemberDeclList> | EPSILON

		boolean valid = skipErrors(union(FIRST_classMemberDeclList, FOLLOW_classMemberDeclList));

		if (lookahead.belongsTo(FIRST_classMemberDeclList)) {
			
			if (type()
					& match("ID")
					& classMemberDeclPRIME()
					& match("SEMICOLON")
					& classMemberDeclList())
				derivationStrings.append("<classMemberDeclList> -> <type> id <classMemberDeclPRIME> ; <classMemberDeclList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_classMemberDeclList))
			derivationStrings.append("<classMemberDeclList> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean funcDefList() { // <funcDefList> -> <type> id ( <fParams> ) { <funcMemberList> } ; <funcDefList> | EPSILON

		boolean valid = skipErrors(union(FIRST_funcDefList, FOLLOW_funcDefList));

		if (lookahead.belongsTo(FIRST_funcDefList)) {
			
			if (type()
					& match("ID")
					& match("OPAREN")
					& fParams()
					& match("CPAREN")
					& match("OBRACE")
					& funcMemberList()
					& match("CBRACE")
					& match("SEMICOLON")
					& funcDefList())
				derivationStrings.append("<funcDefList> -> <type> id ( <fParams> ) { <funcMemberList> } ; <funcDefList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_funcDefList))
			derivationStrings.append("<funcDefList> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean funcMember() { // <funcMember> -> statementPRIME | id <funcMemberPRIME> | <simpleType> id <arraySizeList>

		boolean valid = skipErrors(union(FIRST_funcMember, FOLLOW_funcMember));

		if (lookahead.belongsTo(FIRST_funcMember_RHS1)) {

			if (statementPRIME())
				derivationStrings.append("<funcMember> -> <statementPRIME>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_funcMember_RHS2)) {
			
			if (match("ID")
					& funcMemberPRIME())
				derivationStrings.append("<funcMember> -> id <funcMemberPRIME>").append("\n");

			else
				valid = false;
			
		}

		else if (lookahead.belongsTo(FIRST_funcMember_RHS3)) {

			if (simpleType()
					& match("ID")
					& arraySizeList())
				derivationStrings.append("<funcMember> -> <simpleType> id <arraySizeList>").append("\n");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean funcMemberPRIME() { // <funcMemberPRIME> -> <indiceList> <funcMemberPRIMEPRIME> = <expr> | id <arraySizeList>

		boolean valid = skipErrors(union(FIRST_funcMemberPRIME, FOLLOW_funcMemberPRIME));

		if (lookahead.belongsTo(FIRST_funcMemberPRIME_RHS1)) {

			if (match("ID")
					& arraySizeList())
				derivationStrings.append("<funcMemberPRIME> -> id <arraySizeList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_funcMemberPRIME_RHS2)) {

			if (indiceList()
					& funcMemberPRIMEPRIME()
					& match("DEF")
					& expr())
				derivationStrings.append("<funcMemberPRIME> -> <indiceList> <funcMemberPRIMEPRIME> = <expr>").append("\n");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean funcMemberPRIMEPRIME() { // <funcMemberPRIMEPRIME> -> . <variable> | EPSILON

		boolean valid = skipErrors(union(FIRST_funcMemberPRIMEPRIME, FOLLOW_funcMemberPRIMEPRIME));

		if (lookahead.belongsTo(FIRST_funcMemberPRIMEPRIME)) {

			if (match("DOT")
					& variable())
				derivationStrings.append("<funcMemberPRIMEPRIME> -> . <variable>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_funcMemberPRIMEPRIME))
			derivationStrings.append("<funcMemberPRIMEPRIME> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean funcMemberList() { // <funcMemberList> -> <funcMember> ; <funcMemberList> | EPSILON

		boolean valid = skipErrors(union(FIRST_funcMemberList, FOLLOW_funcMemberList));
		
		if (lookahead.belongsTo(FIRST_funcMemberList)) {

			if (funcMember()
					& match("SEMICOLON")
					& funcMemberList())
				derivationStrings.append("<funcMemberList> -> <funcMember> ; <funcMemberList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_funcMemberList))
			derivationStrings.append("<funcMemberList> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean arraySizeList() { // <arraySizeList> -> [ integer ] <arraySizeList> | EPSILON

		boolean valid = skipErrors(union(FIRST_arraySizeList, FOLLOW_arraySizeList));

		if (lookahead.belongsTo(FIRST_arraySizeList)) {

			if (match("OBRACKET")
					& match("INTEGER")
					& match("CBRACKET")
					& arraySizeList())
				derivationStrings.append("<arraySizeList> -> [ integer ] <arraySizeList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_arraySizeList))
			derivationStrings.append("<arraySizeList> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean statement() { // <statement> -> <assignStat> | <statementPRIME>

		boolean valid = skipErrors(union(FIRST_statement, FOLLOW_statement));

		int line = lookahead.getLine();

		if (lookahead.belongsTo(FIRST_statement_RHS1)) {

			if (assignStat())
				derivationStrings.append("<statement> -> <assignStat>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_statement_RHS2)) {

			if (statementPRIME())
				derivationStrings.append("<statement> -> <statementPRIME>").append("\n");

			else
				valid = false;

		}

		else
			valid = false;

		if (!valid)
			errorStrings.append("Syntax error at line ").append(line).append("Invalid statement").append("\n");

		return valid;

	}

	private boolean statementPRIME() { // <statementPRIME> ->  if ( <expr> ) then <statBlock> else <statBlock> | for ( <type> id = <expr> ; <arithExpr> <relOp> <arithExpr> ; <assignStat> ) <statBlock> | get ( <variable> ) | put ( <variable> ) | return ( <expr> )

		boolean valid = skipErrors(union(FIRST_statementPRIME, FOLLOW_statementPRIME));

		if (lookahead.belongsTo(FIRST_statementPRIME_RHS1)) {

			if (match("RETURN")
					& match("OPAREN")
					& expr()
					& match("CPAREN"))
				derivationStrings.append("<statementPRIME> -> return ( <expr> )").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_statementPRIME_RHS2)) {

			if (match("PUT")
					& match("OPAREN")
					& variable()
					& match("CPAREN"))
				derivationStrings.append("<statementPRIME> -> put ( <variable> )").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_statementPRIME_RHS3)) {

			if (match("GET")
					& match("OPAREN")
					& variable()
					& match("CPAREN"))
				derivationStrings.append("<statementPRIME> -> get ( <variable> )").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_statementPRIME_RHS4)) {

			if (match("IF")
					& match("OPAREN")
					& expr()
					& match("CPAREN")
					& match("THEN")
					& statBlock()
					& match("ELSE")
					& statBlock())
				derivationStrings.append("<statementPRIME> -> if ( <expr> ) then <statBlock> else <statBlock>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_statementPRIME_RHS5)) {

			if (match("FOR")
					& match("OPAREN")
					& type()
					& match("ID")
					& match("DEF")
					& expr()
					& match("SEMICOLON")
					& arithExpr()
					& relOp()
					& arithExpr()
					& match("SEMICOLON")
					& assignStat()
					& match("CPAREN")
					& statBlock())
				derivationStrings.append("<statementPRIME> -> for ( <type> id = <expr> ; <arithExpr> <relOp> <arithExpr> ; <assignStat> ) <statBlock> ").append("\n");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean statBlock() { // <statBlock> -> { <statementList> } | <statement> ; | EPSILON

		boolean valid = skipErrors(union(FIRST_statBlock, FOLLOW_statBlock));

		if (lookahead.belongsTo(FIRST_statBlock_RHS1)) {

			if (statement()
					& match("SEMICOLON"))
				derivationStrings.append("<statBlock> -> <statement> ;").append("\n");

			else
				valid = false;

		}

		if (lookahead.belongsTo(FIRST_statBlock_RHS2)) {

			if (match("OBRACE")
					& statementList()
					& match("CBRACE"))
				derivationStrings.append("<statBlock> -> { <statementList> }").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_statBlock))
			derivationStrings.append("<statBlock> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean assignStat() { // <assignStat> -> <variable> = <expr>

		boolean valid = skipErrors(union(FIRST_assignStat, FOLLOW_assignStat));

		if (lookahead.belongsTo(FIRST_assignStat)) {

			if (variable()
					& match("DEF")
					& expr())
				derivationStrings.append("<assignStat> -> <variable> = <expr>").append("\n");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean statementList() { // <statementList> -> <statement> ; <statementList> | EPSILON

		boolean valid = skipErrors(union(FIRST_statementList, FOLLOW_statementList));

		if (lookahead.belongsTo(FIRST_statementList)) {

			if (statement()
					& match("SEMICOLON")
					& statementList())
				derivationStrings.append("<statementList> -> <statement> ; <statementList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_statementList))
			derivationStrings.append("<statementList> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean expr() { // <expr> -> <arithExpr> <exprPRIME>

		boolean valid = skipErrors(union(FIRST_expr, FOLLOW_expr));

		int line = lookahead.getLine();

		if (lookahead.belongsTo(FIRST_expr)) {

			if (arithExpr()
					& exprPRIME())
				derivationStrings.append("<expr> -> <arithExpr> <exprPRIME>").append("\n");

			else
				valid = false;

		}

		else
			valid = false;

		if (!valid)
			errorStrings.append("Syntax error at line ").append(line).append("Invalid statement").append("\n");

		return valid;

	}

	private boolean exprPRIME() { // <exprPRIME> -> <relOp> <arithExpr> | EPSILON

		boolean valid = skipErrors(union(FIRST_exprPRIME, FOLLOW_exprPRIME));

		if (lookahead.belongsTo(FIRST_exprPRIME)) {

			if (relOp()
					& arithExpr())
				derivationStrings.append("<exprPRIME> -> <relOp> <arithExpr>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_exprPRIME))
			derivationStrings.append("<exprPRIME> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean arithExpr() { // <arithExpr> -> <term> <arithExprPRIME>

		boolean valid = skipErrors(union(FIRST_arithExpr, FOLLOW_arithExpr));

		if (lookahead.belongsTo(FIRST_arithExpr)) {

			if (term()
					& arithExprPRIME())
				derivationStrings.append("<arithExpr> -> <term> <arithExprPRIME>").append("\n");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean arithExprPRIME() { // <arithExprPRIME> -> <addOp> <arithExpr> | EPSILON

		boolean valid = skipErrors(union(FIRST_arithExprPRIME, FOLLOW_arithExprPRIME));

		if (lookahead.belongsTo(FIRST_arithExprPRIME)) {

			if (addOp()
					& arithExpr())
				derivationStrings.append("<arithExprPRIME> -> <addOp> <arithExpr>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_arithExprPRIME))
			derivationStrings.append("<arithExprPRIME> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean sign() { // <sign> -> + | -

		boolean valid = skipErrors(union(FIRST_sign, FOLLOW_sign));

		if (lookahead.belongsTo(FIRST_sign_RHS1)) {

			if (match("MINUS"))
				derivationStrings.append("<sign> -> -").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_sign_RHS2)) {

			if (match("PLUS"))
				derivationStrings.append("<sign> -> +").append("\n");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean term() { // <term> -> <factor> <termPRIME>

		boolean valid = skipErrors(union(FIRST_term, FOLLOW_term));

		if (lookahead.belongsTo(FIRST_term)) {

			if (factor()
					& termPRIME())
				derivationStrings.append("<term> -> <factor> <termPRIME>").append("\n");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean termPRIME() { // <termPRIME> -> <multOp> <term> | EPSILON

		boolean valid = skipErrors(union(FIRST_termPRIME, FOLLOW_termPRIME));

		if (lookahead.belongsTo(FIRST_termPRIME)) {

			if (multOp()
					& term())
				derivationStrings.append("<termPRIME> -> <multOp> <term>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_termPRIME))
			derivationStrings.append("<termPRIME> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean factor() { // <factor> -> <sign> <factor> | not <factor> | <number> | <variable> <factorPrime> | ( <arithExpr> )

		boolean valid = skipErrors(union(FIRST_factor, FOLLOW_factor));

		if (lookahead.belongsTo(FIRST_factor_RHS1)) {

			if (sign()
					& factor())
				derivationStrings.append("<factor> -> <sign> <factor>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_factor_RHS2)) {

			if (number())
				derivationStrings.append("<factor> -> <number>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_factor_RHS3)) {

			if (variable()
					& factorPRIME())
				derivationStrings.append("<factor> -> <variable> <factorPRIME>").append("\n");

			else
				valid = false;

		}

		if (lookahead.belongsTo(FIRST_factor_RHS4)) {

			if (match("NOT")
					& factor())
				derivationStrings.append("<factor> -> not <factor>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_factor_RHS5)) {

			if (match("OPAREN")
					& arithExpr()
					& match("CPAREN"))
				derivationStrings.append("<factor> -> ( <arithExpr> )").append("\n");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean factorPRIME() { // <factorPRIME> -> ( <aParams> ) | EPSILON

		boolean valid = skipErrors(union(FIRST_factorPRIME, FOLLOW_factorPRIME));

		if (lookahead.belongsTo(FIRST_factorPRIME)) {

			if (match("OPAREN")
					& aParams()
					& match("CPAREN"))
				derivationStrings.append("<factorPRIME> -> ( <aParams> )").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_factorPRIME))
			derivationStrings.append("<factorPRIME> -> EPSILON").append("\n");


		else
			valid = false;

		return valid;

	}

	private boolean variableNest() { // <idnestList> -> . <variable> | EPSILON

		boolean valid = skipErrors(union(FIRST_variableNest, FOLLOW_variableNest));

		if (lookahead.belongsTo(FIRST_variableNest)) {

			if (match("DOT")
					& variable())
				derivationStrings.append("<variableNest> -> . variable").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_variableNest))
			derivationStrings.append("<variableNest> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean variable() { // <variable> -> id <indiceList> <variableNest>

		boolean valid = skipErrors(union(FIRST_variable, FOLLOW_variable));

		if (lookahead.belongsTo(FIRST_variable)) {

			if (match("ID")
					& indiceList()
					& variableNest())
				derivationStrings.append("<variable> -> id <indiceList> <variableNest>").append("\n");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}

	private boolean indiceList() { // <indiceList> -> [ <arithExpr> ] <indiceList> | EPSILON

		boolean valid = skipErrors(union(FIRST_indiceList, FOLLOW_indiceList));

		if (lookahead.belongsTo(FIRST_indiceList)) {

			if (match("OBRACKET")
					& arithExpr()
					& match("CBRACKET")
					& indiceList())
				derivationStrings.append("<indiceList> -> [ <arithExpr> ] <indiceList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_indiceList))
			derivationStrings.append("<indiceList> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean fParams() { // <fParams> -> <type> id <arraySizeList> <fParamsTailList> | EPSILON

		boolean valid = skipErrors(union(FIRST_fParams, FOLLOW_fParams));

		if (lookahead.belongsTo(FIRST_fParams)) {

			if (type()
					& match("ID")
					& arraySizeList()
					& fParamsTailList())
				derivationStrings.append("<fParams> -> <type> id <arraySizeList> <fParamsTailList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_fParams))
			derivationStrings.append("<fParams> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean fParamsTailList() { // <fParamsTailList> -> , <type> id <arraySizeList> <fParamsTailList> | EPSILON

		boolean valid = skipErrors(union(FIRST_fParamsTailList, FOLLOW_fParamsTailList));

		if (lookahead.belongsTo(FIRST_fParamsTailList)) {

			if (match("COMMA")
					& type()
					& match("ID")
					& arraySizeList()
					& fParamsTailList())
				derivationStrings.append("<fParamsTailList> -> , <type> id <arraySizeList> <fParamsTailList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_fParamsTailList))
			derivationStrings.append("<fParamsTailList> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean aParams() { // <aParams> -> <expr> <aParamsTailList> | EPSILON

		boolean valid = skipErrors(union(FIRST_aParams, FOLLOW_aParams));

		if (lookahead.belongsTo(FIRST_aParams)) {

			if (expr()
					& aParamsTailList())
				derivationStrings.append("<aParams> -> <expr> <aParamsTailList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_aParams))
			derivationStrings.append("<aParams> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean aParamsTailList() { // <aParamsTailList> -> , <expr> <aParamsTailList> | EPSILON

		boolean valid = skipErrors(union(FIRST_aParamsTailList, FOLLOW_aParamsTailList));

		if (lookahead.belongsTo(FIRST_aParamsTailList)) {

			if (match("COMMA")
					& expr()
					& aParamsTailList())
				derivationStrings.append("<aParamsTailList> -> , <expr> <aParamsTailList>").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FOLLOW_aParamsTailList))
			derivationStrings.append("<aParamsTailList> -> EPSILON").append("\n");

		else
			valid = false;

		return valid;

	}

	private boolean type() { // <type> -> <simpleType> | id

		boolean valid = skipErrors(union(FIRST_type, FOLLOW_type));

		if (lookahead.belongsTo(FIRST_type_RHS1)) {

			if (simpleType())
				derivationStrings.append("<type> -> <simpleType>").append("\n");
			
			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_type_RHS2)) {
			
			if (match("ID"))
				derivationStrings.append("<type> -> id").append("\n");

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
				derivationStrings.append("<simpleType> -> int").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_simpleType_RHS2)) {

			if (match("FLOAT"))
				derivationStrings.append("<simpleType> -> float").append("\n");

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
				derivationStrings.append("<relOp> -> >=").append("\n");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS2)) {
			
			if (match("GREATER"))
				derivationStrings.append("<relOp> -> >").append("\n");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS3)) {
			
			if (match("EQ"))
				derivationStrings.append("<relOp> -> ==").append("\n");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS4)) {
			
			if (match("NEQ"))
				derivationStrings.append("<relOp> -> <>").append("\n");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS5)) {
			
			if (match("LEQ"))
				derivationStrings.append("<relOp> -> <=").append("\n");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_relOp_RHS6)) {
			
			if (match("LESS"))
				derivationStrings.append("<relOp> -> <").append("\n");

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
				derivationStrings.append("<addOp> -> or").append("\n");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_addOp_RHS2)) {

			if (match("MINUS"))
				derivationStrings.append("<addOp> -> -").append("\n");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_addOp_RHS3)) {
			
			if (match("PLUS"))
				derivationStrings.append("<addOp> -> +").append("\n");

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
				derivationStrings.append("<multOp> -> and").append("\n");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_multOp_RHS2)) {
			
			if (match("FWDSLASH"))
				derivationStrings.append("<multOp> -> /").append("\n");

			else
				valid = false;

		}
		
		else if (lookahead.belongsTo(FIRST_multOp_RHS3)) {
			
			if (match("ASTERISK"))
				derivationStrings.append("<multOp> -> *").append("\n");

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
				derivationStrings.append("<number> -> integer").append("\n");

			else
				valid = false;

		}

		else if (lookahead.belongsTo(FIRST_number_RHS2)) {

			if (match("FRACTION"))
				derivationStrings.append("<number> -> fraction").append("\n");

			else
				valid = false;

		}

		else
			valid = false;

		return valid;

	}
	
}
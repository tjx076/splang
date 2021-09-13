package com.sp.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.sp.lang.ast.OLTuple;
import com.sp.lang.SyntaxHelper.SyntaxShortName;
import com.sp.lang.ast.AST;
import com.sp.lang.ast.AndTest;
import com.sp.lang.ast.ArithExpr;
import com.sp.lang.ast.Array;
import com.sp.lang.ast.AssignExpr;
import com.sp.lang.ast.Atom;
import com.sp.lang.ast.CommaExpr;
import com.sp.lang.ast.CommaExprItem;
import com.sp.lang.ast.Comparison;
import com.sp.lang.ast.DicEntry;
import com.sp.lang.ast.Dictionary;
import com.sp.lang.ast.Element;
import com.sp.lang.ast.Expr;
import com.sp.lang.ast.ExprBody;
import com.sp.lang.ast.ObjDecl;
import com.sp.lang.ast.FuncArg;
import com.sp.lang.ast.FuncCall;
import com.sp.lang.ast.FuncDecl;
import com.sp.lang.ast.NotTest;
import com.sp.lang.ast.OLBoolean;
import com.sp.lang.ast.OLDecimal;
import com.sp.lang.ast.OLIdentifier;
import com.sp.lang.ast.OLIndexIdentifier;
import com.sp.lang.ast.OLInteger;
import com.sp.lang.ast.OLNull;
import com.sp.lang.ast.OLRefIdentifier;
import com.sp.lang.ast.OLString;
import com.sp.lang.ast.OperatorAndRightOperand;
import com.sp.lang.ast.OrTest;
import com.sp.lang.ast.Term;
import com.sp.lang.except.ObjDeclException;

/**
 * 
 * 语法解析器
 * 
 *
 */
public class LangParser extends Parser {
	
	public List<AST> list = new ArrayList<AST>();
	
	private Stack<Integer> stack = new Stack<Integer>();
	
	public LangParser(Lexer input) {
		super(input);
	}
	
	public void parse() {
		while(t.getType() != Lexer.EOF_TOKEN.getType()) {
			
			try {
				if(t.isKeyword() && t.getKeyword()==Keywords.OBJ) {
					
					ObjDecl objDecl = objDecl();
					list.add(objDecl);
					continue;
				}
				
				if(t.isKeyword() && t.getKeyword()==Keywords.FUNC){
					match(Tokens.Func.getType());
					Token funcDeclName = match(Tokens.Identifier.getType());
					Token javaStaticMethodFullyQualifiedName = match(Tokens.String.getType());
					
					list.add(new FuncDecl(funcDeclName, javaStaticMethodFullyQualifiedName));
					continue;
				}
				
				if(t.getType() == Tokens.Identifier.getType()) {
					
					if(speculateFuncCall()) {
						FuncCall funcCall = funcCall();
						list.add(funcCall);
						continue;
					}
					
					if(speculateAssignExpr()) {
						AssignExpr assignExpr = assignExpr();
						list.add(assignExpr);
						continue;
					}
					
					throw new RecognitionException("expecting AssignExpr or FuncCall, found " + t);
				}
			} catch(ObjDeclException e) {
				throw new InvalidException(errorMsg(null, e, SyntaxShortName.ObjDecl), e);
			}
			
			throw new InvalidException("invalid token: " + t + printErrorCode());
		}
	}
	
	private ObjDecl objDecl() {
		try{
			match(Tokens.Obj.getType());
			Token exprDeclName = match(Tokens.Identifier.getType());
			Token exprClassFullyQualifiedName = match(Tokens.String.getType());
			
			return new ObjDecl(exprDeclName, exprClassFullyQualifiedName);
		} catch(NoMatchException e) {
			throw new ObjDeclException("The object declare syntax error, " + e.getMessage(), e);
		}
		
	}
	
	private boolean speculateAssignExpr() {
		boolean success = true;
		mark();
		try{
			match(Tokens.Identifier.getType());
			match(Tokens.AssignOperator.getType());
		} catch(RecognitionException|
				NoMatchException e) {
			success =false;
		}
		release();
		return success;
	}

	private AssignExpr assignExpr() {
		Token assignLeftVal = match(Tokens.Identifier.getType());
		match(Tokens.AssignOperator.getType());
		
		Object assignRightOperand = null;
		
		if(t.getType() == Tokens.String.getType()) {
			Token c = match(Tokens.String.getType());
			assignRightOperand = new OLString(c);
			return new AssignExpr(new OLIdentifier(assignLeftVal), assignRightOperand);
		}
		
		if(t.getType() == Tokens.LeftCurly.getType()) {
			assignRightOperand = dictionary();
			return new AssignExpr(new OLIdentifier(assignLeftVal), assignRightOperand);
		}
		
		if(t.getType() == Tokens.LeftSquare.getType()) {
			assignRightOperand = array();
			return new AssignExpr(new OLIdentifier(assignLeftVal), assignRightOperand);
		}
		
		if(t.getType() == Tokens.LeftParenthesis.getType()
				&& speculateExpr()) {
			assignRightOperand = expr();
			return new AssignExpr(new OLIdentifier(assignLeftVal), assignRightOperand);
		}
		
		if(t.getType() == Tokens.LeftParenthesis.getType()) {
			assignRightOperand = tuple();
			return new AssignExpr(new OLIdentifier(assignLeftVal), assignRightOperand);
		}
		
		if(t.getType() == Tokens.Identifier.getType()
				&& speculateFuncCall()) {
			assignRightOperand = funcCall();
			return new AssignExpr(new OLIdentifier(assignLeftVal), assignRightOperand);
		}
		
		
		throw new RecognitionException("expecting FuncCall | Expr | String "
					+ "| Dictionary | Array | Tuple, found "+t);
	}

	private FuncCall funcCall() {
		FuncCall funcCall = new FuncCall();
		
		Token funcName = match(Tokens.Identifier.getType());
		
		match(Tokens.LeftParenthesis.getType());
		if(t.getType() != Tokens.RightParenthesis.getType()){
			List<FuncArg> argList = funcArgList();
			
			funcCall.setArgList(argList);
		}
		match(Tokens.RightParenthesis.getType());
		
		funcCall.setFuncName(funcName);
		return funcCall;
	}

	private List<FuncArg> funcArgList() {
		List<FuncArg> argList = new ArrayList<FuncArg>();
		
		FuncArg arg = funArg();
		argList.add(arg);
		
		while(t.getType() == Tokens.Comma.getType()){
			match(Tokens.Comma.getType());
			arg = funArg();
			argList.add(arg);
		}
		
		return argList;
	}

	private FuncArg funArg() {
		Object val = null;
		
		if(t.getType() == Tokens.Integer.getType()) {
			Token c = match(Tokens.Integer.getType());
			val = new OLInteger(c);
			return new FuncArg(val);
		}
		
		if(t.getType() == Tokens.Decimal.getType()) {
			Token c = match(Tokens.Decimal.getType());
			val = new OLDecimal(c);
			return new FuncArg(val);
		}
		
		if(t.getType() == Tokens.String.getType()) {
			Token c = match(Tokens.String.getType());
			val = new OLString(c);
			return new FuncArg(val);
		}
		
		if(t.getType() == Tokens.True.getType()) {
			Token c = match(Tokens.True.getType());
			val = new OLBoolean(c);
			return new FuncArg(val);
		}
		
		if(t.getType() == Tokens.False.getType()) {
			Token c = match(Tokens.False.getType());
			val = new OLBoolean(c);
			return new FuncArg(val);
		}
		
		if(t.getType() == Tokens.Null.getType()) {
			Token c = match(Tokens.Null.getType());
			val = new OLNull(c);
			return new FuncArg(val);
		}
		
		if(t.getType() == Tokens.LeftCurly.getType()) {
			val = dictionary();
			return new FuncArg(val);
		}
		
		if(t.getType() == Tokens.LeftSquare.getType()) {
			val = array();
			return new FuncArg(val);
		}
		
		if(t.getType() == Tokens.LeftParenthesis.getType()
				&& speculateExpr()) {
			val = expr();
			return new FuncArg(val);
		}
		
		if(t.getType() == Tokens.LeftParenthesis.getType()) {
			val = tuple();
			return new FuncArg(val);
		}
		
		if(t.getType() == Tokens.Identifier.getType()
				&& speculateFuncCall()) {
			val = funcCall();
			return new FuncArg(val);
		}
		
		if(t.getType() == Tokens.Identifier.getType()) {
			Token c = match(Tokens.Identifier.getType());
			val = new OLIdentifier(c);
			return new FuncArg(val);
		}
		
		
		throw new RecognitionException("expecting FuncCall | ExprDeclName | AssignLeftVal |"
					+ " Expr | Integer | Decimal | String | Boolean | Null |Dictionary | Array | Tuple, found "+t);
		
	}
	
	private Dictionary dictionary() {
		
		List<DicEntry> entries = new ArrayList<DicEntry>();
		match(Tokens.LeftCurly.getType());
		
		DicEntry entry = dicEntry();
		entries.add(entry);
		
		while(t.getType() == Tokens.Comma.getType()) {
			match(Tokens.Comma.getType());
			
			entry = dicEntry();
			entries.add(entry);
		}
		
		match(Tokens.RightCurly.getType());
		
		return new Dictionary(entries);
	}

	private DicEntry dicEntry() {
		
		Token key = match(Tokens.Identifier.getType());
		match(Tokens.Colon.getType());
		
		Element val = element();
		
		return new DicEntry(new OLIdentifier(key), val);
	}

	private Element element() {
		Object val = null;
		
		if(t.getType() == Tokens.Integer.getType()) {
			Token c = match(Tokens.Integer.getType());
			val = new OLInteger(c);
			return new Element(val);
		}
		
		if(t.getType() == Tokens.Decimal.getType()) {
			Token c = match(Tokens.Decimal.getType());
			val = new OLDecimal(c);
			return new Element(val);
		}
		
		if(t.getType() == Tokens.String.getType()) {
			Token c = match(Tokens.String.getType());
			val = new OLString(c);
			return new Element(val);
		}
		
		if(t.getType() == Tokens.True.getType()) {
			Token c = match(Tokens.True.getType());
			val = new OLBoolean(c);
			return new Element(val);
		}
		
		if(t.getType() == Tokens.False.getType()) {
			Token c = match(Tokens.False.getType());
			val = new OLBoolean(c);
			return new Element(val);
		}
		
		if(t.getType() == Tokens.Null.getType()) {
			Token c = match(Tokens.Null.getType());
			val = new OLNull(c);
			return new Element(val);
		}
		
		if(t.getType() == Tokens.LeftCurly.getType()) {
			val = dictionary();
			return new Element(val);
		}
		
		if(t.getType() == Tokens.LeftSquare.getType()) {
			val = array();
			return new Element(val);
		}
		
		if(t.getType() == Tokens.LeftParenthesis.getType()
				&& speculateExpr()) {
			val = expr();
			return new Element(val);
		}
		
		if(t.getType() == Tokens.LeftParenthesis.getType()) {
			val = tuple();
			return new Element(val);
		}
		
		if(t.getType()==Tokens.Identifier.getType()) {
			
			if(speculateFuncCall()){
				FuncCall funcCall = funcCall();
				return new Element(funcCall);
			}
			
		}
		
		throw new RecognitionException("expecting Expr | Integer | Decimal | "
					+ "String | Boolean | Null |Dictionary | Array | Tuple | FuncCall , found "+t);
		
	}
	

	private OLTuple tuple() {
		List<Element> eles = new ArrayList<Element>();
		
		match(Tokens.LeftParenthesis.getType());
		
		Element ele = element();
		eles.add(ele);
		
		while(t.getType() == Tokens.Comma.getType()) {
			match(Tokens.Comma.getType());
			ele = element();
			eles.add(ele);
		}
		
		match(Tokens.RightParenthesis.getType());
		
		return new OLTuple(eles);
	}
	
	private boolean speculateExpr() {
		boolean success = true;
		mark();
		try{
			match(Tokens.LeftParenthesis.getType());
			if(t.getType() == Tokens.Identifier.getType()) {
				match(Tokens.Identifier.getType());
				if(t.getType() != Tokens.Comma.getType() 
						&& t.getType() != Tokens.RightParenthesis.getType()) {
					throw new RecognitionException("not a expr");
				}
			} else {
				match(Tokens.RightParenthesis.getType());
			}
			
		} catch(RecognitionException|
				NoMatchException e) {
			success =false;
		}
		release();
		return success;
	}

	private Expr expr() {
		
		Expr expr = new Expr();
		
		match(Tokens.LeftParenthesis.getType());
		if(t.getType() == Tokens.Identifier.getType()){
			List<OLIdentifier> exprArgList = exprArgList();
			expr.setExprArgList(exprArgList);
		}
		match(Tokens.RightParenthesis.getType());
		
		match(Tokens.ExprOperator.getType());
		
		match(Tokens.LeftCurly.getType());
		
		ExprBody exprBody = exprBody();
		expr.setExprBody(exprBody);
		
		match(Tokens.RightCurly.getType());
		
		return expr;
	}
	
	private ExprBody exprBody() {
		
		Object val = null;
		
		if(speculateCommaExpr()) {
			val = commaExpr();
			return new ExprBody(val);
		}
		
		val = orTest();
		
		return new ExprBody(val);
	}

	private OrTest orTest() {
		
		AndTest left = andTest();
		List<OperatorAndRightOperand<AndTest>> rights = new ArrayList<>();
		
		while(t.getType()==Tokens.OrOperator.getType()){
			
			Token operator = match(Tokens.OrOperator.getType());
			
			AndTest right = andTest();
			
			OperatorAndRightOperand<AndTest> oaro = new OperatorAndRightOperand<AndTest>(operator, right);
			rights.add(oaro);
		}
		
		return new OrTest(left, rights);
	}

	private AndTest andTest() {
		
		NotTest left = notTest();
		List<OperatorAndRightOperand<NotTest>> rights = new ArrayList<>();
		
		while(t.getType() == Tokens.AndOperator.getType()) {
			Token operator = match(Tokens.AndOperator.getType());
			
			NotTest right = notTest();
			
			OperatorAndRightOperand<NotTest> oaro = new OperatorAndRightOperand<NotTest>(operator, right);
			rights.add(oaro);
		}
		
		return new AndTest(left, rights);
	}

	private NotTest notTest() {
		
		if(t.getType()==Tokens.NotOperator.getType()){
			
			Token operator = match(Tokens.NotOperator.getType());
			
			NotTest right = notTest();
			
			OperatorAndRightOperand<NotTest> oaro = new OperatorAndRightOperand<NotTest>(operator, right);
			return new NotTest(oaro);
		}
		
		
		Comparison comparison = comparison();
		
		
		
		return new NotTest(comparison);
	}

	private Comparison comparison() {
		
		ArithExpr left = arithExpr();
		List<OperatorAndRightOperand<ArithExpr>> rights = new ArrayList<>();
		
		while(t.getType()==Tokens.EqualOperator.getType()
				||t.getType()==Tokens.NotEqualOperator.getType()
				||t.getType()==Tokens.GreaterThanOperator.getType()
				||t.getType()==Tokens.GreaterThanEqualOperator.getType()
				||t.getType()==Tokens.LessThanOperator.getType()
				||t.getType()==Tokens.LessThanEqualOperator.getType()) {
			
			Token operator =null;
			if(t.getType()==Tokens.EqualOperator.getType()) {
				operator = match(Tokens.EqualOperator.getType());
			}
			if(t.getType()==Tokens.NotEqualOperator.getType()) {
				operator = match(Tokens.NotEqualOperator.getType());
			}
			if(t.getType()==Tokens.GreaterThanOperator.getType()) {
				operator = match(Tokens.GreaterThanOperator.getType());
			}
			if(t.getType()==Tokens.GreaterThanEqualOperator.getType()) {
				operator = match(Tokens.GreaterThanEqualOperator.getType());
			}
			if(t.getType()==Tokens.LessThanOperator.getType()) {
				operator = match(Tokens.LessThanOperator.getType());
			}
			if(t.getType()==Tokens.LessThanEqualOperator.getType()) {
				operator = match(Tokens.LessThanEqualOperator.getType());
			}
			
			ArithExpr right = arithExpr();
			
			OperatorAndRightOperand<ArithExpr> oaro = new OperatorAndRightOperand<ArithExpr>(operator, right);
			
			rights.add(oaro);
			
		}
		
		
		return new Comparison(left, rights);
	}

	private ArithExpr arithExpr() {

		Term left = term();
		List<OperatorAndRightOperand<Term>> rights = new ArrayList<>();
		
		while(t.getType()==Tokens.AddOperator.getType()
				|| t.getType()==Tokens.SubOperator.getType()) {
			

			Token operator =null;
			if(t.getType()==Tokens.AddOperator.getType()) {
				operator = match(Tokens.AddOperator.getType());
			}
			if(t.getType()==Tokens.SubOperator.getType()) {
				operator = match(Tokens.SubOperator.getType());
			}
			
			Term right = term();
			
			OperatorAndRightOperand<Term> oaro = new OperatorAndRightOperand<Term>(operator, right);
			
			rights.add(oaro);
			
		}

		return new ArithExpr(left, rights);
	}
	
	

	private Term term() {
		
		Atom left = atom();
		List<OperatorAndRightOperand<Atom>> rights = new ArrayList<>();
		
		while(t.getType()==Tokens.MulOperator.getType()
				|| t.getType()==Tokens.DivOperator.getType()
				|| t.getType()==Tokens.ModOperator.getType()) {
			

			Token operator =null;
			if(t.getType()==Tokens.MulOperator.getType()) {
				operator = match(Tokens.MulOperator.getType());
			}
			if(t.getType()==Tokens.DivOperator.getType()) {
				operator = match(Tokens.DivOperator.getType());
			}
			if(t.getType()==Tokens.ModOperator.getType()) {
				operator = match(Tokens.ModOperator.getType());
			}
			
			Atom right = atom();
			
			OperatorAndRightOperand<Atom> oaro = new OperatorAndRightOperand<Atom>(operator, right);
			
			rights.add(oaro);
			
		}

		return new Term(left, rights);
	}

	private Atom atom() {
		Object val = null;
		
		if(t.getType() == Tokens.Integer.getType()) {
			Token c = match(Tokens.Integer.getType());
			val = new OLInteger(c);
			return new Atom(val);
		}
		
		if(t.getType() == Tokens.Decimal.getType()) {
			Token c = match(Tokens.Decimal.getType());
			val = new OLDecimal(c);
			return new Atom(val);
		}
		
		if(t.getType() == Tokens.String.getType()) {
			Token c = match(Tokens.String.getType());
			val = new OLString(c);
			return new Atom(val);
		}
		
		if(t.getType() == Tokens.True.getType()) {
			Token c = match(Tokens.True.getType());
			val = new OLBoolean(c);
			return new Atom(val);
		}
		
		if(t.getType() == Tokens.False.getType()) {
			Token c = match(Tokens.False.getType());
			val = new OLBoolean(c);
			return new Atom(val);
		}
		
		if(t.getType() == Tokens.Identifier.getType()) {
			
			if(speculateFuncCall()){
				FuncCall funcCall = funcCall();
				
				return new Atom(funcCall);
			}
			
			
			Token c = match(Tokens.Identifier.getType());
			val = new OLIdentifier(c);
			return new Atom(val);
		}
		
		if(t.getType() == Tokens.RefIdentifier.getType()) {
			Token c = match(Tokens.RefIdentifier.getType());
			val = new OLRefIdentifier(c);
			return new Atom(val);
		}
		
		if(t.getType() == Tokens.IndexIdentifier.getType()) {
			Token c = match(Tokens.IndexIdentifier.getType());
			val = new OLIndexIdentifier(c);
			return new Atom(val);
		}
		
		if(t.getType() == Tokens.LeftParenthesis.getType()) {
			match(Tokens.LeftParenthesis.getType());
			
			OrTest orTest = orTest();
			
			match(Tokens.RightParenthesis.getType());
			
			return new Atom(orTest);
		}
		
		
		throw new RecognitionException("expecting (OrTest) | RefIdentifier | Identifier |"
				+ " Integer | Decimal | String | Boolean, found "+t);
	}

	private CommaExpr commaExpr() {
		
		List<CommaExprItem> items = new ArrayList<CommaExprItem>();
		
		CommaExprItem item = commaExprItem();
		items.add(item);
		
		while(t.getType()==Tokens.Comma.getType()){
			match(Tokens.Comma.getType());
			item = commaExprItem();
			items.add(item);
		}
		
		return new CommaExpr(items);
	}

	private CommaExprItem commaExprItem() {
		
		if(t.getType()==Tokens.Identifier.getType()){
			Token c = match(Tokens.Identifier.getType());
			
			return new CommaExprItem(new OLIdentifier(c));
		}
		
		if(t.getType()==Tokens.RefIdentifier.getType()){
			Token c = match(Tokens.RefIdentifier.getType());
			
			return new CommaExprItem(new OLRefIdentifier(c));
		}
		
		
		if(t.getType()==Tokens.IndexIdentifier.getType()){
			Token c = match(Tokens.IndexIdentifier.getType());
			
			return new CommaExprItem(new OLIndexIdentifier(c));
		}
		
		throw new RecognitionException("expecting Identifier | RefIdentifier, found "+t);
	}

	private boolean speculateCommaExpr() {
		boolean success = true;
		mark();
		try{
			
			if(t.getType()!=Tokens.Identifier.getType()
					&& t.getType()!=Tokens.RefIdentifier.getType()
					&& t.getType()!=Tokens.IndexIdentifier.getType()){
				throw new RecognitionException("not CommaExpr");
			}
			consume();
			match(Tokens.Comma.getType());
			
		} catch(RecognitionException|
				NoMatchException e) {
			success =false;
		}
		release();
		return success;
	}

	private List<OLIdentifier> exprArgList() {
		List<OLIdentifier> exprArgList = new ArrayList<OLIdentifier>();
		
		Token c = match(Tokens.Identifier.getType());
		exprArgList.add(new OLIdentifier(c));
		
		while(t.getType() == Tokens.Comma.getType()) {
			match(Tokens.Comma.getType());
			
			c = match(Tokens.Identifier.getType());
			exprArgList.add(new OLIdentifier(c));
		}
		
		return exprArgList;
	}

	private Array array() {
		List<Element> eles = new ArrayList<Element>();
		
		match(Tokens.LeftSquare.getType());
		
		Element ele = element();
		eles.add(ele);
		
		while(t.getType() == Tokens.Comma.getType()) {
			match(Tokens.Comma.getType());
			ele = element();
			eles.add(ele);
		}
		
		match(Tokens.RightSquare.getType());
		
		return new Array(eles);
	}

	private boolean speculateFuncCall() {
		boolean success = true;
		mark();
		try{
			match(Tokens.Identifier.getType());
			match(Tokens.LeftParenthesis.getType());
		} catch(RecognitionException|
				NoMatchException e) {
			success =false;
		}
		release();
		return success;
	}
	
	void mark() {
		stack.push(p);
	}
	
	void release() {
		p = stack.pop();
		setTo(p);
	}
	
	public List<AST> getASTList() {
		return list;
	}
	
	public String errorMsg(String headMsg, Exception e, SyntaxShortName syntaxShortName) {
		StringBuffer sb = new StringBuffer();
		if(headMsg!=null) {
			sb.append(headMsg);
			sb.append(", ");
		}
		if(e!=null) {
			sb.append(e.getMessage());
			sb.append(", ");
		}
		
		sb.append(printErrorCode());
		
//		if(syntaxShortName!=null) {
//			String syntax = SyntaxHelper.getSyntax(syntaxShortName);
//			if(StringUtil.isNotEmpty(syntax)) {
//				sb.append(syntax);
//				sb.append("\n");
//			}
//		}
		
		return sb.toString();
	}
}

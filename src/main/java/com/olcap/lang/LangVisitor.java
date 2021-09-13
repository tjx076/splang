package com.olcap.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.olcap.lang.ast.Atom;
import com.olcap.lang.ast.CommaExpr;
import com.olcap.lang.ast.OLTuple;
import com.olcap.lang.ast.OrTest;
import com.olcap.lang.ast.AndTest;
import com.olcap.lang.ast.ArithExpr;
import com.olcap.lang.ast.Array;
import com.olcap.lang.ast.AssignExpr;
import com.olcap.lang.ast.CommaExprItem;
import com.olcap.lang.ast.Comparison;
import com.olcap.lang.ast.DicEntry;
import com.olcap.lang.ast.Dictionary;
import com.olcap.lang.ast.Element;
import com.olcap.lang.ast.Expr;
import com.olcap.lang.ast.ExprBody;
import com.olcap.lang.ast.ObjDecl;
import com.olcap.lang.ast.OperatorAndRightOperand;
import com.olcap.lang.ast.Term;
import com.olcap.lang.ast.FuncArg;
import com.olcap.lang.ast.FuncCall;
import com.olcap.lang.ast.FuncDecl;
import com.olcap.lang.ast.NotTest;
import com.olcap.lang.ast.OLBoolean;
import com.olcap.lang.ast.OLDecimal;
import com.olcap.lang.ast.OLIdentifier;
import com.olcap.lang.ast.OLIndexIdentifier;
import com.olcap.lang.ast.OLInteger;
import com.olcap.lang.ast.OLNull;
import com.olcap.lang.ast.OLRefIdentifier;
import com.olcap.lang.ast.OLString;
import com.olcap.lang.symbol.ExprScope;
import com.olcap.lang.symbol.Scope;
import com.olcap.lang.symbol.Symbol;
import com.olcap.lang.symbol.SymbolType;

/**
 * 
 * 外部访问者
 * 
 * @author tangdou
 *
 */
public class LangVisitor {
	Scope curr ;
	
	public LangVisitor(Scope scope) {
		this.curr = scope;
	}
	
	public void visit(FuncDecl funcDecl) {
		 OLIdentifier funcDeclName = funcDecl.getFuncDeclName();
		 OLString javaStaticMethodFullyQualifiedName = funcDecl.getJavaStaticMethodFullyQualifiedName();
		
		 curr.define(new Symbol(funcDeclName.getVal(),
				 javaStaticMethodFullyQualifiedName.getVal(), SymbolType.FuncDecl));
	}

	public void visit(ObjDecl objDecl) {
		OLIdentifier objDeclName = objDecl.getObjDeclName();
		OLString objClassFullyQualifiedName = objDecl.getObjClassFullyQualifiedName();
		
		curr.define(new Symbol(objDeclName.getVal(),
				objClassFullyQualifiedName.getVal(), SymbolType.ObjDecl));
	}
	
	public void visit(AssignExpr assignExpr) {
		OLIdentifier assignLeftVal = assignExpr.getAssignLeftVal();
		Object assignRightOperand =assignExpr.getAssignRightOperand();
		
		Object rightVal = null;
		
		if(assignRightOperand instanceof OLString) {
			rightVal = visit((OLString)assignRightOperand);
			curr.define(new Symbol(assignLeftVal.getVal(),
					rightVal, SymbolType.AssignLeftVal));
			return;
		}
		
		if(assignRightOperand instanceof FuncCall) {
			rightVal = visit((FuncCall)assignRightOperand);
			curr.define(new Symbol(assignLeftVal.getVal(),
					rightVal, SymbolType.AssignLeftVal));
			return;
		}
		
		if(assignRightOperand instanceof Dictionary) {
			rightVal = visit((Dictionary)assignRightOperand);
			curr.define(new Symbol(assignLeftVal.getVal(),
					rightVal, SymbolType.AssignLeftVal));
			return;	
		}	
		
		if(assignRightOperand instanceof Array) {
			rightVal = visit((Array)assignRightOperand);
			curr.define(new Symbol(assignLeftVal.getVal(),
					rightVal, SymbolType.AssignLeftVal));
			return;	
		}
		
		if(assignRightOperand instanceof OLTuple) {
			rightVal = visit((OLTuple)assignRightOperand);
			curr.define(new Symbol(assignLeftVal.getVal(),
					rightVal, SymbolType.AssignLeftVal));
			return;	
		}
		
		if(assignRightOperand instanceof Expr) {
			rightVal = visit((Expr)assignRightOperand);
			curr.define(new Symbol(assignLeftVal.getVal(),
					rightVal, SymbolType.AssignLeftVal));
			return;
		}

		throw new InterpretException("cannt recognize AssignExpr assignRightOperand " + assignExpr);
	}

	public Object visit(FuncCall funcCall) {
	
		OLIdentifier funcName = funcCall.getFuncName();
		List<FuncArg> argList = funcCall.getArgList();
		
		Symbol funcDeclSymbol = curr.resolve(funcName.getVal());
		if(funcDeclSymbol == null || funcDeclSymbol.getType()!=SymbolType.FuncDecl){
			throw new InterpretException(funcName.getVal() + " is not a function ");
		}
		
		String javaStaticMethodFullyQualifiedName = (String)funcDeclSymbol.getVal();
		
		String[] ss = StringUtil.splitWith(javaStaticMethodFullyQualifiedName, '.');
		String[] classArr = new String[ss.length - 1];
		System.arraycopy(ss, 0, classArr , 0, ss.length-1);
		String clazzName = String.join(".", classArr);
		String methodName = ss[ss.length -1];
		
		
		Object returnVal = null;
		
		try{
			
			if(argList!=null&&!argList.isEmpty()){
				List<Object> argValList = visit(argList);
				
				returnVal =  ReflectUtil.callStaticMethod(clazzName, methodName, argValList.toArray());
				
			} else {
				
				returnVal =  ReflectUtil.callStaticMethod(clazzName, methodName);
			}
			
		}catch(Exception e) {
			throw new InterpretException("FuncCall error", e);
		}
	
		return returnVal;
	}

	private List<Object> visit(List<FuncArg> argList) {
		List<Object> argValList = new ArrayList<Object>();
		
		for(FuncArg arg : argList) {
			Object argVal = null;
			
			if(arg.isFuncCall()) {
				argVal = visit((FuncCall)arg.getVal());
				argValList.add(argVal);
				continue;
			}
			
			if(arg.isIdentifier()) {
				OLIdentifier id = (OLIdentifier)arg.getVal();
				Symbol idSymbol = curr.resolve(id.getVal());
				
				if(idSymbol!=null&& idSymbol.getType()==SymbolType.ObjDecl) {
					String objClassFullyQualifiedName = (String)idSymbol.getVal();
					try {
						argVal = ReflectUtil.newInstance(objClassFullyQualifiedName);
					} catch (Exception e) {
						throw new InterpretException(e);
					}
					argValList.add(argVal);
					continue;
				}
				
				if(idSymbol!=null&& idSymbol.getType()==SymbolType.AssignLeftVal) {
					argVal = idSymbol.getVal();
					argValList.add(argVal);
					continue;
				}
				
				throw new InterpretException("cannt recognize Identifier FuncArg " + arg);
			}
			
			if(arg.isInteger()) {
				argVal = visit((OLInteger)arg.getVal());
				argValList.add(argVal);
				continue;
			}
			
			if(arg.isDecimal()) {
				argVal = visit((OLDecimal)arg.getVal());
				argValList.add(argVal);
				continue;
			}
			
			if(arg.isString()) {
				argVal = visit((OLString)arg.getVal());
				argValList.add(argVal);
				continue;
			}
			
			if(arg.isBoolean()) {
				argVal = visit((OLBoolean)arg.getVal());
				argValList.add(argVal);
				continue;
			}
			
			if(arg.isNull()) {
				argVal = visit((OLNull)arg.getVal());
				argValList.add(argVal);
				continue;
			}
			
			if(arg.isDictionary()) {
				argVal = visit((Dictionary)arg.getVal());
				argValList.add(argVal);
				continue;
			}
			
			if(arg.isArray()) {
				argVal = visit((Array)arg.getVal());
				argValList.add(argVal);
				continue;
			}
			
			if(arg.isTuple()) {
				argVal = visit((OLTuple)arg.getVal());
				argValList.add(argVal);
				continue;
			}
			
			if(arg.isExpr()) {
				argVal = visit((Expr)arg.getVal());
				argValList.add(argVal);
				continue;
			}
			
			throw new InterpretException("cannt recognize FuncArg " + arg);
		}
		
		return argValList;
	}
	
	private Map<String, Object> visit(Dictionary dictionary) {
		
		Map<String, Object> ret = new HashMap<>();
		
		List<DicEntry> entries = dictionary.getEntries();
		for(DicEntry e : entries) {
			OLIdentifier key = e.getKey();
			Element value = e.getVal();
			
			String keyStr = visit(key);
			
			if(ret.get(keyStr)!=null) {
				throw new InterpretException("DicEntry key already exists " + e);
			}
			
			Object elementValue = visit(value);
			ret.put(keyStr, elementValue);
		}
		
		return ret;
	}

	private List<Object> visit(Array val) {
		
		 List<Object> ret = new ArrayList<Object>();
		 
		 for(Element element : val.getEles()){
			 ret.add(visit(element));
		 }
		
		return ret;
	}

	
	
	private Tuple visit(OLTuple val) {
		
		List<Element> eles = val.getEles();
		
		int len = eles.size();
		if(len>10){
			len=10;
		}
		
		List<Object> elementValList = new ArrayList<Object>();
		 
		for(int i=0; i<len; i++){
			 
			 Element element = eles.get(i);
			 
			 elementValList.add(visit(element));
			 
		}
		
		Class<?>[] parameterTypes = new Class<?>[len];
		for(int i=0; i<len; i++){
			parameterTypes[i] = Object.class;
		}
		
		Object[] parameterValues = elementValList.toArray();
		String className = Tuple.class.getName();
		
		try {
			return (Tuple)ReflectUtil.newInstance(className, parameterTypes, parameterValues);
		} catch (Exception e) {
			throw new InterpretException(e);
		}
	}

	private Object visit(Element element) {
		
		if(element.isFuncCall()) {
			return visit((FuncCall)element.getVal());
		}
		
		if(element.isInteger()) {
			return visit((OLInteger)element.getVal());
		}
		
		if(element.isDecimal()) {
			return visit((OLDecimal)element.getVal());
		}
		
		if(element.isString()) {
			return visit((OLString)element.getVal());
		}
		
		if(element.isBoolean()) {
			 return visit((OLBoolean)element.getVal());
		}
		
		if(element.isNull()) {
			 return visit((OLNull)element.getVal());
		}
		
		if(element.isDictionary()) {
			return  visit((Dictionary)element.getVal());
		}
		
		if(element.isArray()) {
			return visit((Array)element.getVal());
		}
		
		if(element.isTuple()) {
			return  visit((OLTuple)element.getVal());
		}
		
		if(element.isExpr()) {
			return visit((Expr)element.getVal());
		}
		
		throw new InterpretException("cannt recognize Element " + element);
	}
	
	
	private Object visit(Expr expr) {
		curr = new ExprScope(curr);	
		try{
			List<OLIdentifier> exprArgList = expr.getExprArgList();
			
			if(exprArgList!=null&&!exprArgList.isEmpty()){
				// delay compute
				
				String className = "com.olcap.lang.Expr"+exprArgList.size();
				Class<?>[] parameterTypes = new Class<?>[2];
				parameterTypes[0] = LangVisitor.class;
				parameterTypes[1] = Expr.class;
								
				try {
					return ReflectUtil.newInstance(className, parameterTypes, this, expr);
				} catch (Exception e) {
					throw new InterpretException(e);
				}
			} else {
				return compute(expr);
			}
			
		}finally {
			curr = curr.getEnclosingScope();
		}
	}
	
	private Object compute(Expr expr) {
		
		ExprBody exprBody = expr.getExprBody();
		
		if(exprBody.isOrTest()) {
			return visit((OrTest)exprBody.getVal());
		}
		
		if(exprBody.isCommaExpr()) {
			return visit((CommaExpr)exprBody.getVal());
		}
		
		throw new InterpretException("cannt recognize Expr " + expr);
	}
	
	private Object visit(OrTest orTest) {
		
		AndTest left = orTest.getLeft();
		List<OperatorAndRightOperand<AndTest>> rights = orTest.getRights();
		
		if(rights.isEmpty()){
			return visit(left);
		}
		
		Boolean b = (boolean)visit(left);
		
		for(OperatorAndRightOperand<AndTest> oaro : rights){
			AndTest right  = oaro.getRightOperand();
			
			b = b || (boolean)visit(right);
		}
				
		return b;
	}

	private Object visit(AndTest andTest) {
		
		NotTest left = andTest.getLeft();
		List<OperatorAndRightOperand<NotTest>> rights = andTest.getRights();
		
		if(rights.isEmpty()){
			return visit(left);
		}
		
		Boolean b = (boolean)visit(left);
		
		for(OperatorAndRightOperand<NotTest> oaro : rights){
			NotTest right  = oaro.getRightOperand();
			
			b = b && (boolean)visit(right);
		}
				
		return b;
	}

	@SuppressWarnings("unchecked")
	private Object visit(NotTest notTest) {
		
		if(notTest.isComparison()) {
			return visit((Comparison)notTest.getVal());
		}
		
		OperatorAndRightOperand<NotTest> oaro = (OperatorAndRightOperand<NotTest>)notTest.getVal();
		NotTest right = oaro.getRightOperand();
		
		return ! (boolean)visit(right);
	}

	private Object visit(Comparison comparison) {
		
		ArithExpr left = comparison.getLeft();
		List<OperatorAndRightOperand<ArithExpr>> rights = comparison.getRights();
		
		if(rights.isEmpty()){
			return visit(left);
		}
		
		ArithExpr right  = rights.get(0).getRightOperand();
		
		Object leftVal = visit(left);
		Object rightVal = visit(right);
		
		if(!(leftVal instanceof String) && !(leftVal instanceof Number))  {
			throw new InterpretException("Only string OR number can do Relation operation(>,>=,<,<=,==,!=) " + leftVal);
		}
		
		if(!(rightVal instanceof String) && !(rightVal instanceof Number))  {
			throw new InterpretException("Only string OR number can do Relation operation(>,>=,<,<=,==,!=) " + rightVal);
		}
		
		
		if((leftVal instanceof String) && (rightVal instanceof String)) {
			int i =( (String)leftVal).compareTo((String)rightVal);
			
			Token operator = rights.get(0).getOperator();
			
			if(operator.getType()==Tokens.EqualOperator.getType()) {
				return i==0;
			}
			
			if(operator.getType()==Tokens.NotEqualOperator.getType()) {
				return i!=0;
			}
			
			if(operator.getType()==Tokens.GreaterThanOperator.getType()) {
				return i>0;
			}
			
			if(operator.getType()==Tokens.GreaterThanEqualOperator.getType()) {
				return i>=0;
			}
			
			if(operator.getType()==Tokens.LessThanOperator.getType()) {
				return i<0;
			}
			if(operator.getType()==Tokens.LessThanEqualOperator.getType()) {
				return i<=0;
			}
			
			
		} else if((leftVal instanceof String) && (rightVal instanceof Number)) {
			throw new InterpretException("String and number cannot do Relation operation(>,>=,<,<=,==,!=) " +leftVal+","+rightVal);
		} else if((leftVal instanceof Number) && (rightVal instanceof String)) {
			throw new InterpretException("String and number cannot do Relation operation(>,>=,<,<=,==,!=) " +leftVal+","+rightVal);
		} else {//all number
			

			Token operator = rights.get(0).getOperator();
			
			if(operator.getType()==Tokens.EqualOperator.getType()) {
				return castDouble(leftVal) == castDouble(rightVal);
			}
			
			if(operator.getType()==Tokens.NotEqualOperator.getType()) {
				return castDouble(leftVal) !=castDouble(rightVal);
			}
			
			if(operator.getType()==Tokens.GreaterThanOperator.getType()) {
				return castDouble(leftVal) > castDouble(rightVal);
			}
			
			if(operator.getType()==Tokens.GreaterThanEqualOperator.getType()) {
				return castDouble(leftVal) >= castDouble(rightVal);
			}
			
			if(operator.getType()==Tokens.LessThanOperator.getType()) {
				return castDouble(leftVal) < castDouble(rightVal);
			}
			if(operator.getType()==Tokens.LessThanEqualOperator.getType()) {
				return castDouble(leftVal) <= castDouble(rightVal);
			}
		}
		
		throw new InterpretException("cannt recognize Comparison " + comparison);
		
	}
	
	private double castDouble(Object val) {
		return ((Number)val).doubleValue();
	}

	private Object visit(ArithExpr arithExpr) {
		Term left = arithExpr.getLeft();
		List<OperatorAndRightOperand<Term>> rights = arithExpr.getRights();
		
		if(rights.isEmpty()){
			return visit(left);
		}
		
		Object leftVal = visit(left);
		boolean containsString = false;
		boolean containsSub = false;
		
		List<Object> rightVals = new ArrayList<>();
		
		for(OperatorAndRightOperand<Term> oaro : rights){
			Term right  = oaro.getRightOperand();
			Token operator = oaro.getOperator();
			
			Object rightVal = visit(right);
			rightVals.add(rightVal);
			
			if(rightVal instanceof String) {
				containsString = true;
			}
			if(operator.getType()==Tokens.SubOperator.getType()) {
				containsSub = true;
			}
		}
			
		
		if(containsString&&containsSub) {
			throw new InterpretException("String cannot do subtraction(-) " +arithExpr);
		}
		
		Object ret = null;
		
		if(containsString) {
			
			String leftValStr = (String)leftVal;
			
			for(int i=0;i<rightVals.size();i++) {
				Object rightVal = rightVals.get(i);
				String rightValStr = (String)rightVal;
				
				leftValStr = leftValStr+rightValStr;
			}
			
			ret = leftValStr;
		} else {
			
			Double leftValNum = castDouble(leftVal);
			
			for(int i=0;i<rightVals.size();i++) {
				Object rightVal = rightVals.get(i);
				Double rightValNum = castDouble(rightVal);
				
				Token operator = rights.get(i).getOperator();
				
				if(operator.getType()==Tokens.SubOperator.getType()) {
					leftValNum -= rightValNum;
				}
				if(operator.getType()==Tokens.AddOperator.getType()) {
					leftValNum += rightValNum;
				}
			}
			
			ret = leftValNum;
		}
		
		return ret;
	}

	private Object visit(Term term) {
		Atom left = term.getLeft();
		List<OperatorAndRightOperand<Atom>> rights = term.getRights();
		
		if(rights.isEmpty()){
			return visit(left);
		}
		
		Double b = castDouble(visit(left));
		
		for(OperatorAndRightOperand<Atom> oaro : rights){
			Atom right  = oaro.getRightOperand();
			Token operator = oaro.getOperator();
			
			Object rightVal = visit(right);
			
			if(operator.getType()==Tokens.MulOperator.getType()) {
				b *= castDouble(rightVal);
			}
			if(operator.getType()==Tokens.DivOperator.getType()) {
				b /= castDouble(rightVal);
			}
			if(operator.getType()==Tokens.ModOperator.getType()) {
				b %= castDouble(rightVal);
			}
			
		}
				
		return b;
	}

	private Object visit(Atom atom) {

		if(atom.isBoolean()) {
			return visit((OLBoolean)atom.getVal());
		}
		
		if(atom.isDecimal()) {
			return visit((OLDecimal)atom.getVal());
		}
		
		if(atom.isFuncCall()) {
			return visit((FuncCall)atom.getVal());
		}
		
		if(atom.isIdentifier()) {
			return resolve((OLIdentifier)atom.getVal());
		}
		
		if(atom.isIndexIdentifier()) {
			return resolve((OLIndexIdentifier)atom.getVal());
		}
		
		if(atom.isOrTest()) {
			return visit((OrTest)atom.getVal());
		}
		
		if(atom.isInteger()) {
			return visit((OLInteger)atom.getVal());
		}
		
		if(atom.isRefIdentifier()) {
			return resolve((OLRefIdentifier)atom.getVal());
		}
		
		if(atom.isString()) {
			return visit((OLString)atom.getVal());
		}
		
		throw new InterpretException("cannt recognize Atom " + atom);
	}

	private Object visit(CommaExpr exprBody) {
		
		List<CommaExprItem> items = exprBody.getItems();
		
		boolean isAllIndexInteger = true;
		for(CommaExprItem item : items) {
			if(!item.isIndexIdentifier() 
					|| !((OLIndexIdentifier)item.getVal()).indexIsInteger()){
				isAllIndexInteger = false;
				break;
			}
		}
		
		Object returnVal = null;
		
		if(isAllIndexInteger){
			
			List<Object> vals = new ArrayList<Object>();
			for(CommaExprItem item : items) {
				Tuple tuple = visit(item);				
				vals.add(tuple.val2());
			}
			
			returnVal = vals;
		} else {
			Map<String, Object> vals = new HashMap<String, Object>();
			for(CommaExprItem item : items) {
				Tuple tuple = visit(item);
				vals.put((String)tuple.val1(), tuple.val2());
			}
			
			returnVal = vals;
		}
		
		return returnVal;
	}

	private Tuple visit(CommaExprItem item) {
		
		if(item.isIdentifier()) {
			String s = visit((OLIdentifier)item.getVal());
			Object o = resolve((OLIdentifier)item.getVal());
			return new Tuple(s, o);
		}
		
		if(item.isIndexIdentifier()) {
			String s = ((OLIndexIdentifier)item.getVal()).getIndex();
			Object o = resolve((OLIndexIdentifier)item.getVal());
			return new Tuple(s, o);
		}
		
		if(item.isRefIdentifier()) {
			String s =( (OLRefIdentifier)item.getVal()).getField();
			Object o = resolve((OLRefIdentifier)item.getVal());
			return new Tuple(s, o);
		}
		
		throw new InterpretException("cannt recognize CommaExprItem " + item);
	}

	@SuppressWarnings("rawtypes")
	private Object resolve(OLRefIdentifier refId) {
		Symbol symbol = curr.resolve(refId.getVar());
		if(symbol==null){
			return null;
		}
		
		Object symbolVal = symbol.getVal();
		if(symbolVal==null){
			return null;
		}
		
		String field = refId.getField();
		
		if(symbolVal instanceof Map) {
			
			return ((Map)symbolVal).get(field);
			
		} else {
			
			try {
				return ReflectUtil.callGetMethod(symbolVal, field);
			} catch (Exception e) {
				throw new InterpretException(e);
			}
			
		}
	}

	@SuppressWarnings("rawtypes")
	private Object resolve(OLIndexIdentifier indexId) {
		Symbol symbol = curr.resolve(indexId.getVar());
		if(symbol==null){
			return null;
		}
		
		Object symbolVal = symbol.getVal();
		if(symbolVal==null){
			return null;
		}
		
		if(indexId.indexIsInteger()) {
			Integer index = Integer.parseInt(indexId.getIndex());
			
			if(symbolVal instanceof List) {
				return ((List)symbolVal).get(index);
			}
			
			throw new InterpretException(symbolVal + " must be list to " + indexId);
			
		} else {
			String index = indexId.getIndex();
			
			if(symbolVal instanceof Map) {
				
				return ((Map)symbolVal).get(index);
				
			} else {
				
				try {
					return ReflectUtil.callGetMethod(symbolVal, index);
				} catch (Exception e) {
					throw new InterpretException(e);
				}
				
			}
			
		}
		
		
	}

	private Object resolve(OLIdentifier id) {
		Symbol s = curr.resolve(id.getVal());
		
		if(s!=null) {
			return s.getVal();
		}
		
		return null;
	}

	public Object visit(Expr expr, Map<String, Object> exprArgVals) {
		curr = new ExprScope(curr);	
		try{
			List<OLIdentifier> exprArgList = expr.getExprArgList();
			
			for(OLIdentifier exprArg : exprArgList) {
				
				String exprArgStr = visit(exprArg);
				Object exprArgVal = exprArgVals.get(exprArgStr);
				
				if(exprArgVal==null){// arg not used
					continue;
				}
				
				curr.define(new Symbol(exprArgStr, exprArgVal, SymbolType.ExprArg));
			}
			
			
			return compute(expr);
			
			
		}finally {
			curr = curr.getEnclosingScope();
		}
	}
	
	private Object visit(OLNull val) {
		return val.getVal();
	}

	private Boolean visit(OLBoolean val) {
		
		return val.getVal();
	}

	private String visit(OLString val) {
		return val.getVal();
	}

	public Double visit(OLDecimal decimal) {
		return decimal.getVal();
	}
	
	public Long visit(OLInteger olInteger) {
		return olInteger.getVal();
	}	
	
	public String visit(OLIdentifier identifier) {
		return identifier.getVal();
	}
}

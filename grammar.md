# SuperPlayerLang(SPL) 语法规范

```

+  : 1个或多个;
*  : 0个、1个或多个;
|  : 或;
?  : 0次或1次;
() : 分组;


WS : ' ' | '\t' | '\n' | '\r' ;
Comment : '//' 除了换行符外的任意无穷多字符 '\n' ;

Number : '0'..'9' ;
Letter : 'a'..'z' | 'A'..'Z' ;

ArithOperator : '+' | '-' | '*' | '/' | '％' ;
RelatOperator : '==' | '!=' | '>' | '<' | '>=' | '<=' ;
LogicOperator : '&&' | '||' | '!' ;
AssignOperator : '=' ;
ExprOperator : '->' ;

Integer : Number+ ;
Decimal : Number+ '.' Number+ ;
String : '"' 除了双引号外的任意无穷多字符 '"' ;
Boolean : 'true' | 'false' ;
Null : 'null' | 'NULL' ;

Identifier : ( Letter | '_' )+ ;
RefIdentifier : Identifier '.' Identifier ;
IndexIdentifier : Identifier '[' (Identifier|Integer) ']' ;

Dictionary : '{' DicEntry (',' DicEntry)* '}' ;
DicEntry : Identifier ':' Element ;
Array : '[' Element (',' Element)* ']' ;
Tuple : '(' Element (',' Element)* ')' ;
Element : Expr | 
            Integer | 
            Decimal | 
            String | 
            Boolean | 
            Null |
            Dictionary | 
            Array | 
            Tuple |
            FuncCall ;

ObjDecl : ('obj'|'OBJ') ObjDeclName ObjClassFullyQualifiedName ;
ObjDeclName : Identifier ;
ObjClassFullyQualifiedName : String ;

FuncDecl : ('func'|'FUNC') FuncDeclName JavaStaticMethodFullyQualifiedName ;
FuncDeclName : Identifier ;
JavaStaticMethodFullyQualifiedName : String ;

FuncCall : FuncName '(' ArgList? ')' ;
FuncName : FuncDeclName | BuildinFuncName ;
ArgList : Arg (',' Arg)* ;
Arg : FuncCall | 
        ObjDeclName | 
        AssignLeftVal | 
        Expr | 
        Integer | 
        Decimal | 
        String | 
        Boolean | 
        Null |
        Dictionary | 
        Array | 
        Tuple ;

AssignExpr : AssignLeftVal '=' AssignRightOperand ;
AssignLeftVal : Identifier ;
AssignRightOperand : FuncCall | 
                        Expr | 
                        String |
                        Dictionary | 
                        Array | 
                        Tuple ;

Expr : '(' ExprArgList? ')' '->' '{' ExprBody '}' ;
ExprArgList : ExprArg (',' ExprArg)* ;
ExprArg : Identifier ;
ExprBody : OrTest | CommaExpr ;

OrTest : AndTest ('||' AndTest)* ;
AndTest : NotTest ('&&' NotTest)* ;
NotTest : '!' NotTest | Comparison ;
Comparison : ArithExpr (('=='|'!='|'>'|'<'|'>='|'<=') ArithExpr)* ;
ArithExpr : Term (('+'|'-') Term)* ;
Term : Atom (('*'|'/'|'%') Atom)* ;
Atom : '(' OrTest ')' | 
        RefIdentifier | 
        Identifier | 
        Integer | 
        Decimal | 
        String | 
        Boolean |
        FuncCall | 
        IndexIdentifier ;

CommaExpr : CommaExprItem (',' CommaExprItem)+ ;
CommaExprItem : Identifier | RefIdentifier | IndexIdentifier ;


```

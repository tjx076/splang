# SuperPlayerLang(SPL)

### 注释

```

支持单行注释，以//开头

```

### 标识符和关键字

##### 基本标识符(Identifier)

```

由字母和下划线组成，不能包含数字，大小写敏感

正确示例：_name, fuc_aa, fucaa_

错误示例：1_anme, nam1e.ee, dee1

```

##### 引用标识符(RefIdentifier)

```

由字母和下划线和点组成，不能包含数字，大小写敏感

只能用在表达式中，详细介绍见表达式

正确示例：_user._name, user.age, fucaa_.bbb

错误示例：1_anme., .nam1e.ee, .dee1

```

##### 下标标识符(IndexIdentifier)

```

由字母和下划线和下标组成，大小写敏感

下标可以是数字和基本标识符

只能用在表达式中，详细介绍见表达式

正确示例：_user[1], user[age], fucaa_[bbb]

错误示例：1_anme.[sss.33], .nam1e.ee[33.33], .dee1[332.]

```

##### 关键字

`obj，func，ture，false，null`

### 数据类型

##### 基本数据类型

|SPL数据类型|解释|取值示例|对应Java数据类型|
|---|----|----|----|----|-----|
|Integer|整数|123|Long|
|Decimal|小数|12.123|Double|
|String|字符串|"12sfdfe"|String|
|Boolean|布尔值|true/false|Boolean|
|Null|空|null|null|

##### 复杂数据类型

###### 字典(Dictionary)

字典：

- key：基本标识符
- value：任意类型(整数、小数、布尔、字符串、null、字典、数组、元组、常量表达式、函数调用)

```

对应Java数据类型Map<String, Object>

```

使用示例：

```

{
	name   : "zhangsan",
	age    : 3,
	bool_val : true,
	null_val : null,
	arr    : [15, 35, 45],
	tuple : ("2", "4", "3"),
	dic : {
		home : "hangzhou",
		addr : "xixi"
	},
	funcCall : anyfunc_doit(
		1,
		true,
		[1,3,4]
	),
	const_expr : () -> { 1+2 }
}

```

###### Array

数组：

- element：任意类型(整数、小数、布尔、字符串、null、字典、数组、元组、常量表达式、函数调用)

```

对应Java数据类型List<Object>

```

使用示例：

```

[
		"zhangsan",
		3,
		true,
		null,
		[15, 35, 45],
		("2", "4", "3"),
		{
			home : "hangzhou",
			addr : "xixi"
		},
		anyfunc_doit(
			1,
			true,
			[1,3,4]
		),
		() -> { 1+2 }
]

```

###### Tuple

元组：

- element：任意类型(整数、小数、布尔、字符串、null、字典、数组、元组、常量表达式、函数调用)

```

对应Java数据类型com.sp.lang.Tuple

```

使用示例：

```

(
		"zhangsan",
		3,
		true,
		null,
		[15, 35, 45],
		("2", "4", "3"),
		{
			home : "hangzhou",
			addr : "xixi"
		},
		anyfunc_doit(
			1,
			true,
			[1,3,4]
		),
		() -> { 1+2 }
)

```

### 对象声明(Object Declare)

###### 语法

```

obj ObjDeclName ObjClassFullyQualifiedName

ObjDeclName : 任意标识符，为Java对象取个别名

ObjClassFullyQualifiedName : Java的类全限定名，该类需提供无参数构造器

示例：

obj userComparator "xxx.UserComparator"

```

###### 使用示例

`说明：` 对于SPL实现不了的超级复杂的计算任务，我们可以使用`表达式`，用Java来实现

比如 Aggregate算子需要传入一个聚合函数，如果这个聚合函数很复杂，就写一个Java的BiFunction实现，然后引入SPL使用即可

然而，实际上，对象声明可以是任意Java对象

```

obj userAgeAggregateBiFunction "xxx.UserAgeAggregateBiFunction"

Aggregate(usersList, 0, userAgeAggregateBiFunction)

```

### 函数声明

###### 语法

```

func FuncDeclName JavaStaticMethodFullyQualifiedName


FuncDeclName : 任意标识符，为Java静态函数起个别名

JavaStaticMethodFullyQualifiedName : Java静态函数全限定名


示例:

func Aggregate "com.sp.lang.BuildinFuncs.Aggregate"

```

###### 使用示例

说明： 支持任意UDF(User define function)，只要它是Java静态函数即可，支持函数重载

内建算子，见附录

```

func Aggregate "com.sp.lang.BuildinFuncs.Aggregate"
obj userAgeAggregateBiFunction "xxx.UserAgeAggregateBiFunction"

Aggregate(usersList, 0, userAgeAggregateBiFunction)

```

### 赋值语句

###### 语法

```

VarName = AssignRightOperand


VarName : 任意标识符，为计算后的值起个别名

AssignRightOperand : 函数调用、常量表达式、变量表达式、字符串、字典、数组、元组

```


示例:

```

// 字典
val_dic = {
		name : "zhangsan",
		age : 3,
		arr : [15, 35, 45],
		tuple : ("2", "4", "3"),
		dic : {
			home : "hangzhou",
			addr : "xixi"
		}
	}

// 数组
val_arr = [
		13,
		"12",
		true,
		[15, 35, 45],
		("2", "4", "3"),
		{
			home : "hangzhou",
			addr : "xixi"
		}
	]

// 元组
val_tuple = (
		12.3,
		"astr",
		null,
		[15, 35, 45],
		("2", "4", "3"),
		{
			home : "hangzhou",
			addr : "xixi"
		}
	)

// 串
val_str = "astring"

// 表达式
var_expr = (u) -> {
				(u + 2) < (1 + u.t - u.name * 1 / 2 % 3) && !(1 <= 3) || 1==3 || 1!=3 || 1>3 && 1>=3
			}

// 函数调用
val_func = udf_getUserName(
		any_func (
			1,
			true
		),
		expr_keySelector,
		var_expr,
		{
			name : "zhangsan",
			age : () -> {(2 + 3) > 2 && 1 < 3},
			tree : [15, 35, 45]
		},
		(
			12.3,
			2.2,
			null
		),
		[
			13,
			12,
			12
		],
		() -> { 1+2 },
		"astring",
		null,
		(u) -> {
				(u + 2) < (1 + u.t) && 1 < 3
		},
		(u) -> {
				(u + 2) < (1 + u.t) && 1 < u[1] && 2 > u[name]
		}
)


```

### 表达式

###### 运算符

```

算数运算符 : + | - | * | / | ％ ;
关系运算符 : == | != | > | < | >= | <= ;
逻辑运算符 : && | || | ! ;


```

###### 语法

```

( ExprArgList ) -> { ExprBody }

ExprArgList : 参数列表由标识符组成，逗号分隔
ExprBody : 逗号表达式 | 逻辑表达式 | 算数表达式 | 关系表达式

--------------------------------------------------------
逻辑表达式/算数表达式/关系表达式:

这三种表达式的运算符，就是上面支持的，它们都是双目运算符
运算符的操作数：
整数、小数、字符串、布尔、函数调用、基本标识符、引用标识符、下标标识符

--------------------------------------------------------
逗号表达式: 由Identifier/RefIdentifier/IndexIdentifier组成

RefIdentifier(引用标识符): 用于获取对象的某个属性值

IndexIdentifier(下标标识符) : 用于获取对象或List的值

Identifier(基本标识符): 用于获取任意值

--------------------------------------------------------
无参数表达式，就是常量表达式，会立即计算
参数变量表达式，会在函数内部调用时才计算，如果函数内部没用调用表达式，则不会计算


```

`注意：`表达式中，可以做函数调用、可以定义变量、可以引用变量的某个属性，这使得SPL非常灵活易用

`注意2：`表达式有单独的作用域，所以表达式的变量可以覆盖全局变量


###### 使用示例

```

// 各种计算
val_expr = (u) -> {
				(u + 2) < (1 + u.age - u.name * 1 / 2 % 3) && !(1 <= 3) || 1==3 || 1!=3 || 1>3 && 1>=3
			}

// 引用表达式，可以获取u 中的属性，只要定义了对应的get方法，返回值是Map<String, Object>
val_comma_expr = (u) -> {
                u.name, u.age, u.home, u.addr
            }

// 这种用法，与上面的表达式返回结果相同，是一个语法糖
val_index_expr = (u) -> {
                u[name], u[age], u[home], u[addr]
            }

// 常量表达式，返回值4.0，因为我们把所有的整数都自动提升为Double进行计算的
val_const_expr = () -> {
				1 + 3
			}

// 下标表达式，返回List<Object>，用于获取list中的元素
val_const_expr = (list) -> {
				 list[2], u[3], u[4], u[5]
			}

// 表达式用在函数调用中
udf_getUserName(
		(u) -> {
				(u + 2) < (1 + u.t) && 1 < 3
		},
		(u) -> {
				(u + 2) < (1 + u.t) && 1 < u[1] && 2 > u[name]
		},
		() -> {
            1 + 3
        }
)

// 表达式中可以调用函数
val_func_expr = (list) -> {
				 udf_getSomeValue() + 3
			}

// 字典、数组、元组中，也可以使用表达式，但只能是常量表达式，即无参数表达式
// 但，表达中可以调用函数
// 字典
val_dic = {
		name : "zhangsan",
		age : () -> { getUserAge(u) }
	}

// 数组
val_arr = [
		1,
        3,
        () -> {
            1 + 3
        }
	]

// 元组
val_tuple = (
		1,
        3,
        () -> {
            udf_getSomeValue() + 3
        }
	)


```


### 函数调用

###### 语法

```

FuncName ( ArgList )

FuncName: 声明的UDF或者内建函数，函数声明语法见上文

ArgList: 函数可以无参数，也可以有无限个参数

函数的参数：函数调用、表达式、整数、小数、字符串、布尔、null、字典、数组、元组，赋值的变量

```

###### 使用示例

```

udf_getUserName(
		anyOtherFunc (
			1,
			true
		),
		expr_keySelector,
		var_expr,
		{
			name : "zhangsan",
			age : () -> {(2 + 3) > 2 && 1 < 3},
			tree : [15, 35, 45]
		},
		(
			12.3,
			2.2,
			null
		),
		[
			13,
			12,
			12
		],
		() -> { 1+2 },
		"astring",
		null,
		(u) -> {
				(u + 2) < (1 + u.t) && 1 < 3
		},
		(u) -> {
				(u + 2) < (1 + u.t) && 1 < u[1] && 2 > u[name]
		}
)


```


### 一些约定

1. 整个计算流程，只会返回最后一个函数调用的结果，其他结果丢弃，如果想返回多个结果，可以用Tuple 包装函数
2. Tuple最多支持10个值
3. 表达式最多支持6个参数，超过6个请使用对象
4. 表达式可以序列化




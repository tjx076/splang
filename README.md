# SuperPlayerLang(SPL)

### 说明

1. 一个所见即所得的脚本语言，可以方便的嵌入到Java应用程序里；
2. 可以使用宿主语言(目前是Java)提供的任何能力，就像是本来就内置的功能一样；
3. 支持面向过程(procedure-oriented)编程和函数式编程(functional programming)； 

### 语法

* [语法规范](./grammar.md) 
* [语法说明](./spl.md) 

### 内建算子

##### 算数-算子:

1. Add
2. Sub
3. Mul
4. Div
5. Mod

##### 关系-算子:

1. Equal
2. NotEqual
3. GreaterThan
4. GreaterThanEqual
5. LessThan
6. LessThanEqual

##### 逻辑-算子:

1. And
2. Not
3. Or

##### 计算-算子:

- Aggregate 
- All 
- Any 
- Append 
- Average 
- Cast 
- Concat 
- Contains 
- Count 
- Distinct 
- ElementAt 
- ElementAtOrDefault 
- Empty 
- Except 
- First 
- FirstOrDefault 
- GroupBy 
- LeftJoin 
- RightJoin 
- InnerJoin 
- CartesianProduct 
- GroupLeftJoin 
- GroupRightJoin 
- GroupInnerJoin 
- GroupCartesianProduct 
- Intersect 
- Last 
- LastOrDefault 
- LongCount 
- Max 
- Min 
- OrderBy 
- OrderByDescending 
- Prepend 
- Range 
- Repeat 
- Reverse 
- Select 
- SelectMany 
- SequenceEqual 
- Skip 
- SkipWhile 
- Sum 
- Take 
- TakeWhile 
- ThenBy 
- ThenByDescending 
- ToArray 
- ToMap 
- ToSet 
- ToList 
- Union 
- Where 
- Zip 

##### 数据源-算子:

1. FromArray
2. FromList
3. FromSet
4. FromMap
5. FromEnumerable 
6. FromIterable 
7. FromIterator

### 使用示例1

```

// 外部函数声明
func MultiValue "example.MultiValue"
func AnyUDFCall "AnyUDFCall"

// 外部对象声明
obj anyObjRef "AnyJavaObject"

// 支持单行注释
// 基本数据类型：整数、小数、boolean、null、字符串
// 复杂数据类型：字典、数组、元组，元素可以是任意类型
// 支持 常量表达式 和 参数变量表达式
// 支持 任意UDF定义
// 支持 引入任意外部Java对象

// 数组，数组元素可以是任意类型
val_arr = [
	"2",
	1,
	true,
	null
]

// 字典，字典元素可以是任意类型
val_dic = {
	name: "sili",
	age : 12,
	love : "game"
}

// 字符串
val_str = "I am a string"

// 常量表达式
val_const_expr = () -> {
	500 + 1000
}

// 参数变量表达式
val_var_expr = (a, b) -> {
	a + b 
}

// 元组，元组元素可以是任意类型
val_tuple = (
		12, 
		13.4,
		true,
		"astr",
		null,
		[15, 35, 45],
		("2", "4", "3"),
		{
			addr : "xixi",
			name : "zhangsan",
			age : 20
		},
		()->{ 
			( AnyUDFCall(val_var_expr) + 3 ) *4 > 4/2 && 2<3 || 1==2
		},
		AnyUDFCall(val_var_expr)
)

// 函数调用，这个多值函数返回一个Tuple
MultiValue(
		[
			"2",
			1,
			true,
			null
		], 
		val_dic,
		()->{ 
			AnyUDFCall(val_var_expr) + 3 *4 > 4/2 && 2<3 || 1==2
		}, 
		val_str, 
		{
			name: "sili",
			age : 12,
			love : "game"
		},
		AnyUDFCall(val_var_expr),
		() -> {
			500 + 1000
		},
		(1,2,3),
		val_const_expr,
		val_tuple
)



```

### 使用示例2

```

// 任何Java的静态函数，都可以成为SPL的算子
// 假设我在Java里面已经定义了一个FromMysql的算子

func FromMysql "xxx.FromMysql"


// 简单计算
Count(
    FromMysql("select * from user_info;")
)


val_users = FromMysql("select * from user_info;")
AverageInt(
    val_users,
    (u) -> { u.age }
)

val_users = FromMysql("select * from user_info;")
ToList(
    Skip(val_users, 1)
)

val_users = FromMysql("select * from user_info;")
Reverse (
    ThenBy (
        OrderBy(
            val_users,
            (u) -> { u.age }
        ),
        (u) -> { u.name }
    )
)

// 复杂计算

val_users = FromMysql("select * from user_info;")
val_dis = Distinct(val_users)

val_where = Where(val_dis, (u)-> { u.age > 20 })
val_groupBy = GroupBy(
                ThenByDescending(
                    ThenBy(
                        OrderBy(
                            Skip(val_where, 1),
                            (u)-> { u.name }
                        ),
                        (u)-> { u.age }
                    ),
                    (u)-> { u.home }
                ),
                (u)-> { u.name },
                (u)-> { u }
            )

````

# 下一步工作



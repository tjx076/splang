# SuperPlayerLang(SPL)

### 说明

1. SPL是一个轻量微小的脚本语言，可以很方便的嵌入到应用程序里，从而为应用程序提供灵活的扩展和定制功能；
2. SPL提供了非常易于使用的扩展接口和机制：SPL可以使用宿主语言(目前是Java)提供的任何能力，就像是本来就内置的功能一样；
3. 支持面向过程(procedure-oriented)编程和函数式编程(functional programming)； 

### 语法

* [语法规范](./grammar.md) 
* [详细语法说明](./spl.md) 

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

- ​Aggregate 
- ​All 
- ​Any 
- ​Append 
- ​Average 
- ​Cast 
- ​Concat 
- ​Contains 
- ​Count 
- ​Distinct 
- ​Element​At 
- ​Element​At​Or​Default 
- ​Empty 
- ​Except 
- ​First 
- ​First​Or​Default 
- ​Group​By 
- ​​LeftJoin 
- ​​RightJoin 
- ​​InnerJoin 
- ​​CartesianProduct 
- ​​GroupLeftJoin 
- ​​GroupRightJoin 
- ​​GroupInnerJoin 
- ​​GroupCartesianProduct 
- ​Intersect 
- ​Last 
- ​Last​Or​Default 
- ​Long​Count 
- ​Max 
- ​Min 
- ​Order​By 
- ​Order​By​Descending 
- ​Prepend 
- ​Range 
- ​Repeat 
- ​Reverse 
- ​Select 
- ​Select​Many 
- ​Sequence​Equal 
- ​Skip 
- ​Skip​While 
- ​Sum 
- ​Take 
- ​Take​While 
- ​Then​By 
- ​Then​By​Descending 
- ​To​Array 
- ​To​Map 
- ​To​Set 
- ​To​List 
- ​Union 
- ​Where 
- ​Zip 

##### 数据源-算子:

1. FromRpc
	
2. FromPgSql
3. FromMysql
4. FromHbase
	
5. FromArray
6. FromList
7. FromSet
8. FromMap
9. FromEnumerable
10. FromIterable
11. FromIterator


### 使用示例1

```

// 外部函数声明
func MultiValue "com.taobao.lbs.olcap.test.InterpreterTest.MultiValue"
func AnyUDFCall "com.taobao.lbs.olcap.test.InterpreterTest.AnyUDFCall"

// 外部对象声明
obj anyObjRef "com.taobao.lbs.olcap.test.AnyJavaObject"

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

// 因为我这里都用的内建算子，所以不需要做函数声明
// 这里的例子 与 下面JavaSDK例子 是对应的


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

obj goThroughConsumer "com.taobao.lbs.businessmap.GoThroughFuncitionConsumer"
obj aggregateConsumer "com.taobao.lbs.businessmap.AggregateFuncitionConsumer"

val_users = FromMysql("select * from user_info;")
val_dis = Distinct(val_users)
val_goth = GoThrough(val_dis, goThroughConsumer)

val_where = Where(val_goth, (u)-> { u.age > 20 })
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

Aggregate(val_groupBy, 1, aggregateConsumer)

// 多数据源复杂计算，也是这样算子的组合，只是用到了多个数据源算子，这里不多做介绍

````


# Java SDK

### 单数据源简单计算

```

// count
DataSource.FromMysql("select * from user_info;", User.class)
.count();

// distinct
DataSource.FromMysql("select * from user_info;", User.class)
.distinct();

// avg
DataSource.FromMysql("select * from user_info;", User.class)
.averageInt(u -> u.getAge());

// sum
DataSource.FromMysql("select * from user_info;", User.class)
.sumInt(u -> u.getAge());

// skip and toList
DataSource.FromMysql("select * from user_info;", User.class)
.skip(1)
.toList();

// orderBy, thenBy and reverse
DataSource.FromMysql("select * from user_info;", User.class)
.orderBy(u -> u.getAge())
.thenBy(u -> u.getName())
.reverse();

```

### 单数据源复杂计算

```

DataSource.FromMysql("select * from user_info;", User.class)
.distinct()
.goThrough(u -> {
    // do something
}).where(u -> {
    // test whether is true
    return true;
}).skip(1)
.orderBy(u -> {
    return u.getName();
})
.thenBy(u ->  {
    return u.getAge();
})
.thenByDescending(u -> {
    return u.getHome();
})
.groupBy(u -> u.getName(), u -> u)
.aggregate(1, (accu, e) -> {
    int sumAge = accu;
    for(User u : e.getValue()){
        sumAge += u.getAge();
    }
    return sumAge;
});

```

### 多数据源简单计算

```

// mysql and hbase
DataSource.FromMysql("select * from user_info;", User.class)
.intersect(
        DataSource.FromHbase("select * from user_data_table_in_mysql_another;", User.class)
)
.toSet();


// mysql and pgsql
DataSource.FromMysql("select * from user_info;", User.class)
.intersect(
        DataSource.FromPgsql("select * from user_data_table_in_gpsql;", User.class)
)
.toSet();


// mysql and rpc
UserService userService = null;
DataSource.FromMysql("select * from user_info;", User.class)
.except(
        DataSource.FromRpc(() -> {
            
            List<User> users = userService.getUsersFromRemoteService();
            
            return DataSource.FromList(users);
        })
        
)
.toSet();

```

### 多数据源复杂计算

```

DataSource.FromMysql("select * from user_info;", User.class)
.intersect(
        DataSource.FromMysql("select * from user_data_table_in_mysql_another;", User.class)
).except(
        DataSource.FromRpc(() -> {
            
            List<User> users = userService.getUsersFromRemoteService();
            
            return DataSource.FromList(users);
        })
        
)
.range(0, 100)
.reverse()
.union(
        DataSource.FromHbase("select * from user_data_table_in_mysql_another;", User.class)
)
.where(u -> u.getAge()==30)
.<Car, Long, UserWithCar>leftJoin(
        u -> u.getCarId(), 
        DataSource.FromPgsql("select * from car_table_in_hbase;", Car.class), 
        c -> c.getId(),
        (u , c) -> {
            
            UserWithCar userWithCar = new UserWithCar();
            userWithCar.c = c;
            userWithCar.u = u;
            
            return userWithCar;
        }
)
.toSet();

```

# 下一步工作

1. 目前OlapLang在解析和解释过程中，错误提示信息不够友好，需要提供语义级别的错误提示，方便定位语法错误
2. 提供一个OlapLang语法检查、高亮和格式化的工具，实现代码风格的统一，利于代码编写，也利用后期维护
3. 开发数据可视化Web系统

# 业务

比如：定义一个`数据可视化场景`分为四个步骤: 定义计算过程，定义输入，定义输出，定义执行方式

1. 使用 JavaSDK 或 OlapLang 定义计算过程
2. 定义输入
3. 定义输出(数据表-mysql/hbase/odps/pgsql，图表-柱状图/折线图/饼状图/雷达图，图片-后端渲染热力图 等等)
4. 定义执行方式(实时，定时)


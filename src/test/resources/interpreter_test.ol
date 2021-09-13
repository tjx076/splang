// 外部函数声明
func MultiValue "com.olcap.test.InterpreterTest.MultiValue"
func AnyUDFCall "com.olcap.test.InterpreterTest.AnyUDFCall"

// 外部对象声明
obj anyObjRef "com.olcap.test.AnyJavaObject"

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

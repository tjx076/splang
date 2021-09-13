// 外部函数声明
func udf_getUserName "com.lbs.user.UserInfo.getUserName"

// 外部表达式声明
obj expr_keySelector "com.lbs.user.KeySelectorFunc"

// 字典
val_dic = {
		name : "zhangsan",
		age : 3,
		arr : [15, 35, 45],
		tuple : ("2", "4", "3"),
		dic : {
			home : "hangzhou",
			addr : "xixi"
		},
		funcCall : func_any_doit(
			1,
			true,
			[1,3,4]
		)
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
		},
		func_any_doit(
			1,
			true,
			[1,3,4]
		)
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
		},
		func_any_doit(
			1,
			true,
			[1,3,4]
		)
	)
	
// 串
val_str = "!@#$%^&*()  {}  [] :;'<>?,.2212dddfq"

// 表达式
var_expr = (u) -> {
				(u + 2) < (1 + u.t - u.name * 1 / 2 % 3) && !(1 <= 3) || 1==3 || 1!=3 || 1>3 && 1>=3
			}

// 函数调用
udf_getUserName(
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
			() -> {(2 + 3) > 2 && 1 < 3}, 
			null
		), 
		[
			13, 
			() -> {(2 + 3) > 2 && 1 < 3}, 
			12
		], 
		() -> { 1+2 },
		"astring",
		null,
		() -> {
				(u + 2) < (1 + u.t) && 1 < 3
		},
		(u,a,b,c) -> {
				u,u.name,u.id,u.age
		},
		any_func_two(),
		(u,a,b,c) -> {
				u
		},
		(u,a,b,c) -> {
				u.a
		},
		(u,a,b,c) -> {
				func_any_doit(
					1,
					true,
					[1,3,4]
				) + 1 > 3 &&  1< u[1] && 2 > u[name]
		}
)



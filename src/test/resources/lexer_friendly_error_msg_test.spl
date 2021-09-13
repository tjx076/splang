// 外部函数声明
func udf_getUserName "com.lbs.user.UserInfo.getUserName"

// 外部表达式声明
obj obj_keySelector "com.lbs.user.KeySelectorFunc"

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
				true[11] sdf.null &| dje?12 31 231
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



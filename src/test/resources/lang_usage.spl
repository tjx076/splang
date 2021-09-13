
// 因为我这里都用的内建算子，所以不需要做函数声明
// 这里的例子 与 下面JavaSDK例子 是对应的


// 简单计算
Count(
    FromTDDL("select * from user_info;")
)


val_users = FromTDDL("select * from user_info;")
AverageInt(
    val_users,
    (u) -> { u.age }
)

val_users = FromTDDL("select * from user_info;")
ToList(
    Skip(val_users, 1)
)

val_users = FromTDDL("select * from user_info;")
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

obj goThroughConsumer "com.lbs.businessmap.GoThroughFuncitionConsumer"
obj aggregateConsumer "com.lbs.businessmap.AggregateFuncitionConsumer"

val_users = FromTDDL("select * from user_info;")
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

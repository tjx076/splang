package com.olcap.lang;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.function.Consumer;
import java.util.Map.Entry;


import com.olcap.Enumerable2;
import com.olcap.Enumerable;


public class BuildinFuncs {
	
	public static Object Value(Object val) {
		return val;
	}
	
	public static <TSource, TAccumulate, TResult> TResult Aggregate(Enumerable<TSource> source , TAccumulate seed,
			Expr2<TAccumulate, TSource, TAccumulate> func, Expr1<TAccumulate, TResult> resultTransformer) {
		return Enumerable2.Aggregate(source, seed, (x, y)->func.apply(x, y), x->resultTransformer.apply(x));
	}

	public static <TSource, TAccumulate> TAccumulate Aggregate(Enumerable<TSource> source ,TAccumulate seed, Expr2<TAccumulate, TSource, TAccumulate> func) {
		return Enumerable2.Aggregate(source, seed, (x,y)->func.apply(x, y));
	}


	public static <TSource> Boolean All(Enumerable<TSource> source , Expr1<TSource, Boolean> expr1) {
		return Enumerable2.All(source, x->expr1.apply(x));
	}

	public static <TSource> Boolean Any(Enumerable<TSource> source, Expr1<TSource, Boolean> expr1) {
		return Enumerable2.Any(source, x->expr1.apply(x));
	}

	public static <TSource> Double AverageInt(Enumerable<TSource> source ,Expr1<TSource, Integer> selector) {
		return Enumerable2.AverageInt(source.select(x->selector.apply(x)));
	}

	public static <TSource> Double AverageOptionalInt(Enumerable<TSource> source, Expr1<TSource, OptionalInt> selector) {
		return Enumerable2.AverageOptionalInt(source.select(x->selector.apply(x)));
	}

	public static <TSource> Double AverageLong(Enumerable<TSource> source, Expr1<TSource, Long> selector) {
		return Enumerable2.AverageLong(source.select(x->selector.apply(x)));
	}

	public static <TSource> Double AverageOptionalLong(Enumerable<TSource> source,Expr1<TSource, OptionalLong> selector) {
		return Enumerable2.AverageOptionalLong(source.select(x->selector.apply(x)));
	}

	public static <TSource> Double AverageDouble(Enumerable<TSource> source,Expr1<TSource, Double> selector) {
		return Enumerable2.AverageDouble(source.select(x->selector.apply(x)));
	}

	public static <TSource> Double AverageOptionalDouble(Enumerable<TSource> source, Expr1<TSource, OptionalDouble> selector) {
		return Enumerable2.AverageOptionalDouble(source.select(x->selector.apply(x)));
	}

	public static <TSource, TResult> Enumerable<TResult> Select(Enumerable<TSource> source, Expr1<TSource, TResult> selector) {
		return Enumerable2.Select(source, x->selector.apply(x));
	}

	public static <TSource, TResult> Enumerable<TResult> Select(Enumerable<TSource> source, Expr2<TSource, Integer, TResult> selector) {
		return Enumerable2.Select(source, (x,y)->selector.apply(x, y));
	}

	public static <TSource, TResult> Enumerable<TResult> Map(Enumerable<TSource> source, Expr1<TSource, TResult> mapper) {
		return Enumerable2.Map(source, x->mapper.apply(x));
	}

	public static <TSource, TResult> Enumerable<TResult> Map(Enumerable<TSource> source, Expr2<TSource, Integer, TResult> mapper) {
		return Enumerable2.Map(source, (x,y)->mapper.apply(x, y));
	}

	public static <TSource> Enumerable<TSource> Concat(Enumerable<TSource> source, Enumerable<TSource> other) {
		return Enumerable2.Concat(source, other);
	}

	public static <TSource> List<TSource> ToList(Enumerable<TSource> source) {
		return Enumerable2.ToList(source);
	}

	public static <TSource> Object[] ToArray(Enumerable<TSource> source) {
		return Enumerable2.ToArray(source);
	}

	public static <TSource> Enumerable<TSource> GoThrough(Enumerable<TSource> source, Consumer<TSource> consumer) {
		return Enumerable2.GoThrough(source, consumer);
	}

	public static <TSource> Boolean Contains(Enumerable<TSource> source,TSource value, Expr2<TSource, TSource, Integer> comparer) {
		return Enumerable2.Contains(source, value, (x,y)->comparer.apply(x, y));
	}

	public static<TSource> int Count(Enumerable<TSource> source) {
		return Enumerable2.Count(source);
	}

	public static <TSource>int Count(Enumerable<TSource> source,Expr1<TSource, Boolean> expr1) {
		return Enumerable2.Count(source, x->expr1.apply(x));
	}

	public static <TSource>long LongCount(Enumerable<TSource> source) {
		return Enumerable2.LongCount(source);
	}

	public static <TSource> long LongCount(Enumerable<TSource> source, Expr1<TSource, Boolean> expr1) {
		return Enumerable2.LongCount(source, x->expr1.apply(x));
	}

	public static <TSource> Enumerable<TSource> Distinct(Enumerable<TSource> source) {
		return Enumerable2.Distinct(source,null);
	}

	public static <TSource>Enumerable<TSource> Distinct(Enumerable<TSource> source,Expr2<TSource,TSource,Integer> comparer) {
		return Enumerable2.Distinct(source, (x,y)->comparer.apply(x, y));
	}

	public static <TSource>TSource ElementAt(Enumerable<TSource> source,int index) {
		return Enumerable2.ElementAt(source, index);
	}

	public static <TSource> TSource ElementAtOrDefault(Enumerable<TSource> source, int index, TSource defaultVal) {
		return Enumerable2.ElementAtOrDefault(source, index, defaultVal);
	}

	public static <TSource> Enumerable<TSource> Except(Enumerable<TSource> source, Enumerable<TSource> other) {
		return Enumerable2.Except(source, other);
	}

	public static <TSource> Enumerable<TSource> Except(Enumerable<TSource> source, Enumerable<TSource> other, Expr2<TSource,TSource,Integer> comparer) {
		return Enumerable2.Except(source, other, (x,y)->comparer.apply(x, y));
	}

	public static <TSource> TSource First(Enumerable<TSource> source) {
		return Enumerable2.First(source);
	}

	public static <TSource> TSource First(Enumerable<TSource> source, Expr1<TSource, Boolean> expr1) {
		return Enumerable2.First(source, x->expr1.apply(x));
	}

	public static <TSource> TSource FirstOrDefault(Enumerable<TSource> source,TSource defaultVal) {
		return Enumerable2.FirstOrDefault(source, defaultVal);
	}

	public static <TSource>TSource FirstOrDefault(Enumerable<TSource> source,Expr1<TSource, Boolean> expr1, TSource defaultVal) {
		return Enumerable2.FirstOrDefault(source, x->expr1.apply(x), defaultVal);
	}

	public static <TSource,TKey> Enumerable<Entry<TKey, List<TSource>>> GroupBy(Enumerable<TSource> source,Expr1<TSource, TKey> keySelector) {
		return Enumerable2.GroupBy(source, x->keySelector.apply(x));
	}

	public static <TSource, TKey> Enumerable<Entry<TKey, List<TSource>>> GroupBy(Enumerable<TSource> source,
			Expr1<TSource, TKey> keySelector,
			Expr2<TKey,TKey,Integer> comparer) {
		return Enumerable2.GroupBy(source,
				x->keySelector.apply(x), 
				(Comparator<TKey>)((x,y)->comparer.apply(x, y))
				);
	}

	public static <TSource, TKey, TElement> Enumerable<Entry<TKey, List<TElement>>> GroupBy(Enumerable<TSource> source, Expr1<TSource, TKey> keySelector,
			Expr1<TSource, TElement> elementSelector) {
		return Enumerable2.GroupBy(source,  x->keySelector.apply(x),  x->elementSelector.apply(x));
	}

	public static <TSource, TKey, TElement> Enumerable<Entry<TKey, List<TElement>>> GroupBy(Enumerable<TSource> source, Expr1<TSource, TKey> keySelector,
			Expr1<TSource, TElement> elementSelector, Expr2<TKey,TKey,Integer> comparer) {
		return Enumerable2.GroupBy(source, 
				x->keySelector.apply(x), 
				x->elementSelector.apply(x), 
				(Comparator<TKey>)((x,y)->comparer.apply(x, y)));
	}

	public static <TSource, TKey, TResult> Enumerable<TResult> GroupBy(Enumerable<TSource> source, Expr1<TSource, TKey> keySelector,
			Expr2<TKey, Enumerable<TSource>, TResult> resultSelector, Expr2<TKey,TKey,Integer> comparer) {
		return Enumerable2.GroupBy(source, 
				x->keySelector.apply(x), 
				(x,y)->resultSelector.apply(x, y), 
				(x,y)->comparer.apply(x, y));
	}

	public static <TSource, TKey, TElement, TResult> Enumerable<TResult> GroupBy(Enumerable<TSource> source, Expr1<TSource, TKey> keySelector,
			Expr1<TSource, TElement> elementSelector, Expr2<TKey, Enumerable<TElement>, TResult> resultSelector,
			Expr2<TKey,TKey,Integer> comparer) {
		return Enumerable2.GroupBy(source, 
				x->keySelector.apply(x), 
				x->elementSelector.apply(x), 
				(x,y)->resultSelector.apply(x, y), 
				(x,y)->comparer.apply(x, y));
	}

	public static <TSource>Enumerable<TSource> Intersect(Enumerable<TSource> source ,Enumerable<TSource> other) {
		return Enumerable2.Intersect(source, other);
	}

	public static <TSource>Enumerable<TSource> Intersect(Enumerable<TSource> source, Enumerable<TSource> other, Expr2<TSource,TSource,Integer> comparer) {
		return Enumerable2.Intersect(source, other, (x,y)->comparer.apply(x, y));
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> LeftJoin(Enumerable<TSource> source, Expr1<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Expr1<TTarget, TKey> targetKeySelector,
			Expr2<TSource, TTarget, TResult> resultSelector) {
		return Enumerable2.LeftJoin(source, x->sourceKeySelector.apply(x), 
				target, x->targetKeySelector.apply(x), (x,y)->resultSelector.apply(x, y), null);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> LeftJoin(Enumerable<TSource> source, Expr1<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Expr1<TTarget, TKey> targetKeySelector,
			Expr2<TSource, TTarget, TResult> resultSelector, Expr2<TKey,TKey,Integer> comparer) {
		return Enumerable2.LeftJoin(source, x->sourceKeySelector.apply(x), 
				target, x->targetKeySelector.apply(x), (x,y)->resultSelector.apply(x, y), 
				(x,y)->comparer.apply(x,y));
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> GroupLeftJoin(Enumerable<TSource> source, Expr1<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Expr1<TTarget, TKey> targetKeySelector,
			Expr2<TSource, Enumerable<TTarget>, TResult> resultSelector) {
		return Enumerable2.GroupLeftJoin(source, x->sourceKeySelector.apply(x), target, 
				x->targetKeySelector.apply(x), (x,y)->resultSelector.apply(x, y), null);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> GroupLeftJoin(Enumerable<TSource> source, Expr1<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Expr1<TTarget, TKey> targetKeySelector,
			Expr2<TSource, Enumerable<TTarget>, TResult> resultSelector, Expr2<TKey,TKey,Integer> comparer) {
		return 
				Enumerable2.GroupLeftJoin(source, x->sourceKeySelector.apply(x), target, 
						x->targetKeySelector.apply(x), (x,y)->resultSelector.apply(x, y), 
						(x,y)->comparer.apply(x,y));
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> RightJoin(Enumerable<TSource> source, Expr1<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Expr1<TTarget, TKey> targetKeySelector,
			Expr2<TSource, TTarget, TResult> resultSelector) {
		return Enumerable2.RightJoin(source, x->sourceKeySelector.apply(x), target, x->targetKeySelector.apply(x), (x,y)->resultSelector.apply(x, y), null);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> RightJoin(Enumerable<TSource> source, Expr1<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Expr1<TTarget, TKey> targetKeySelector,
			Expr2<TSource, TTarget, TResult> resultSelector, Expr2<TKey,TKey,Integer> comparer) {
		return Enumerable2.RightJoin(source, 
				x->sourceKeySelector.apply(x), 
				target, 
				x->targetKeySelector.apply(x),
				(x,y)->resultSelector.apply(x, y), 
				(x,y)->comparer.apply(x, y));
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> GroupRightJoin(Enumerable<TSource> source, Expr1<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Expr1<TTarget, TKey> targetKeySelector,
			Expr2<Enumerable<TSource>, TTarget, TResult> resultSelector) {
		return Enumerable2.GroupRightJoin(source,
				x->sourceKeySelector.apply(x), 
				target, x->targetKeySelector.apply(x), 
				(x,y)->resultSelector.apply(x, y), null);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> GroupRightJoin(Enumerable<TSource> source, Expr1<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Expr1<TTarget, TKey> targetKeySelector,
			Expr2<Enumerable<TSource>, TTarget, TResult> resultSelector, Expr2<TKey,TKey,Integer> comparer) {
		return Enumerable2.GroupRightJoin(source, 
				x->sourceKeySelector.apply(x), 
				target, 
				x->targetKeySelector.apply(x),
				(x,y)->resultSelector.apply(x, y), 
				(x,y)->comparer.apply(x, y));
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> InnerJoin(Enumerable<TSource> source, Expr1<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Expr1<TTarget, TKey> targetKeySelector,
			Expr2<TSource, TTarget, TResult> resultSelector) {
		return Enumerable2.InnerJoin(source, 
				x->sourceKeySelector.apply(x), 
				target, 
				x->targetKeySelector.apply(x),
				(x,y)->resultSelector.apply(x, y), 
				null);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> InnerJoin(Enumerable<TSource> source, Expr1<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Expr1<TTarget, TKey> targetKeySelector,
			Expr2<TSource, TTarget, TResult> resultSelector, Expr2<TKey,TKey,Integer> comparer) {
		return Enumerable2.InnerJoin(source, 
				x->sourceKeySelector.apply(x), 
				target, 
				x->targetKeySelector.apply(x),
				(x,y)->resultSelector.apply(x, y), 
				(x,y)->comparer.apply(x, y));
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> GroupInnerJoin(Enumerable<TSource> source, Expr1<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Expr1<TTarget, TKey> targetKeySelector,
			Expr2<Enumerable<TSource>, Enumerable<TTarget>, TResult> resultSelector) {
		return Enumerable2.GroupInnerJoin(source, 
				x->sourceKeySelector.apply(x), 
				target, 
				x->targetKeySelector.apply(x),
				(x,y)->resultSelector.apply(x, y), 
				null);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> GroupInnerJoin(Enumerable<TSource> source, Expr1<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Expr1<TTarget, TKey> targetKeySelector,
			Expr2<Enumerable<TSource>, Enumerable<TTarget>, TResult> resultSelector, Expr2<TKey,TKey,Integer> comparer) {
		return Enumerable2.GroupInnerJoin(source, 
				x->sourceKeySelector.apply(x), 
				target, 
				x->targetKeySelector.apply(x),
				(x,y)->resultSelector.apply(x, y), 
				(x,y)->comparer.apply(x, y));
	}

	/**
	 * 笛卡尔积
	 * 
	 * @param sourceKeySelector
	 * @param target
	 * @param targetKeySelector
	 * @param resultSelector
	 * @return
	 */
	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> CartesianProduct(Enumerable<TSource> source, Expr1<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Expr1<TTarget, TKey> targetKeySelector,
			Expr2<TSource, TTarget, TResult> resultSelector) {
		return Enumerable2.CartesianProduct(source, 
				x->sourceKeySelector.apply(x), 
				target, 
				x->targetKeySelector.apply(x),
				(x,y)->resultSelector.apply(x, y), 
				null);
	}

	/**
	 * 笛卡尔积
	 * 
	 * @param sourceKeySelector
	 * @param target
	 * @param targetKeySelector
	 * @param resultSelector
	 * @param comparer
	 * @return
	 */
	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> CartesianProduct(Enumerable<TSource> source, Expr1<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Expr1<TTarget, TKey> targetKeySelector,
			Expr2<TSource, TTarget, TResult> resultSelector, Expr2<TKey,TKey,Integer> comparer) {
		return Enumerable2.CartesianProduct(source, 
				x->sourceKeySelector.apply(x), 
				target, 
				x->targetKeySelector.apply(x),
				(x,y)->resultSelector.apply(x, y), 
				(x,y)->comparer.apply(x, y));
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> GroupCartesianProduct(Enumerable<TSource> source,
			Expr1<TSource, TKey> sourceKeySelector, Enumerable<TTarget> target,
			Expr1<TTarget, TKey> targetKeySelector,
			Expr2<Enumerable<TSource>, Enumerable<TTarget>, TResult> resultSelector) {
		return Enumerable2.GroupCartesianProduct(source, 
				x->sourceKeySelector.apply(x), 
				target, 
				x->targetKeySelector.apply(x),
				(x,y)->resultSelector.apply(x, y), 
				null);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> GroupCartesianProduct(Enumerable<TSource> source,
			Expr1<TSource, TKey> sourceKeySelector, Enumerable<TTarget> target,
			Expr1<TTarget, TKey> targetKeySelector,
			Expr2<Enumerable<TSource>, Enumerable<TTarget>, TResult> resultSelector, Expr2<TKey,TKey,Integer> comparer) {
		return Enumerable2.GroupCartesianProduct(source, 
				x->sourceKeySelector.apply(x), 
				target, 
				x->targetKeySelector.apply(x),
				(x,y)->resultSelector.apply(x, y), 
				(x,y)->comparer.apply(x, y));
	}

	public static <TSource> TSource Last(Enumerable<TSource> source) {
		return Enumerable2.Last(source);
	}

	public static <TSource> TSource Last(Enumerable<TSource> source, Expr1<TSource, Boolean> expr1) {
		return Enumerable2.Last(source, x->expr1.apply(x));
	}

	public static <TSource> TSource LastOrDefault(Enumerable<TSource> source, TSource defaultVal) {
		return Enumerable2.LastOrDefault(source, defaultVal);
	}

	public static <TSource> TSource LastOrDefault(Enumerable<TSource> source, Expr1<TSource, Boolean> expr1, TSource defaultVal) {
		return Enumerable2.LastOrDefault(source,  x->expr1.apply(x), defaultVal);
	}

	public static <TSource>int MaxInt(Enumerable<TSource> source, Expr1<TSource, Integer> selector) {
		return Enumerable2.MaxInt(source.select( x->selector.apply(x)));
	}

	public static <TSource>Integer MaxOptionalInt(Enumerable<TSource> source, Expr1<TSource, OptionalInt> selector) {
		return Enumerable2.MaxOptionalInt(source.select( x->selector.apply(x)));
	}

	public static <TSource> long MaxLong(Enumerable<TSource> source, Expr1<TSource, Long> selector) {
		return Enumerable2.MaxLong(source.select( x->selector.apply(x)));
	}

	public static <TSource> Long MaxOptionalLong(Enumerable<TSource> source, Expr1<TSource, OptionalLong> selector) {
		return Enumerable2.MaxOptionalLong(source.select( x->selector.apply(x)));
	}

	public static <TSource>double MaxDouble(Enumerable<TSource> source, Expr1<TSource, Double> selector) {
		return Enumerable2.MaxDouble(source.select( x->selector.apply(x)));
	}

	public static <TSource>Double MaxOptionalDouble(Enumerable<TSource> source, Expr1<TSource, OptionalDouble> selector) {
		return Enumerable2.MaxOptionalDouble(source.select( x->selector.apply(x)));
	}

	public static <TSource> int MinInt(Enumerable<TSource> source, Expr1<TSource, Integer> selector) {
		return Enumerable2.MinInt(source.select( x->selector.apply(x)));
	}

	public static <TSource> Integer MinOptionalInt(Enumerable<TSource> source, Expr1<TSource, OptionalInt> selector) {
		return Enumerable2.MinOptionalInt(source.select( x->selector.apply(x)));
	}

	public static <TSource> long MinLong(Enumerable<TSource> source, Expr1<TSource, Long> selector) {
		return Enumerable2.MinLong(source.select( x->selector.apply(x)));
	}

	public static <TSource> Long MinOptionalLong(Enumerable<TSource> source, Expr1<TSource, OptionalLong> selector) {
		return Enumerable2.MinOptionalLong(source.select( x->selector.apply(x)));
	}

	public static <TSource> double MinDouble(Enumerable<TSource> source, Expr1<TSource, Double> selector) {
		return Enumerable2.MinDouble(source.select( x->selector.apply(x)));
	}

	public static <TSource> Double MinOptionalDouble(Enumerable<TSource> source, Expr1<TSource, OptionalDouble> selector) {
		return Enumerable2.MinOptionalDouble(source.select( x->selector.apply(x)));
	}

	public static <TSource,TKey extends Comparable<TKey>> Enumerable<TSource> OrderBy(Enumerable<TSource> source, Expr1<TSource, TKey> keySelector) {
		return Enumerable2.OrderBy(source, x->keySelector.apply(x));
	}

	public static <TSource, TKey> Enumerable<TSource> OrderBy(Enumerable<TSource> source, Expr1<TSource, TKey> keySelector, Expr2<TKey,TKey,Integer> comparer) {
		return Enumerable2.OrderBy(source, x->keySelector.apply(x), (x,y)->comparer.apply(x, y));
	}

	public static <TSource,TKey extends Comparable<TKey>> Enumerable<TSource> OrderByDescending(Enumerable<TSource> source, Expr1<TSource, TKey> keySelector) {
		return Enumerable2.OrderByDescending(source, x->keySelector.apply(x));
	}

	public static <TSource, TKey> Enumerable<TSource> OrderByDescending(Enumerable<TSource> source, Expr1<TSource, TKey> keySelector,
			Expr2<TKey,TKey,Integer> comparer) {
		return Enumerable2.OrderByDescending(source, x->keySelector.apply(x), (x,y)->comparer.apply(x, y));
	}

	public static <TSource>Enumerable<TSource> Append(Enumerable<TSource> source, TSource element) {
		return Enumerable2.Append(source, element);
	}

	public static <TSource> Enumerable<TSource> Prepend(Enumerable<TSource> source, TSource element) {
		return Enumerable2.Prepend(source, element);
	}

	public static <TSource> Enumerable<TSource> Range(Enumerable<TSource> source, int start, int count) {
		return Enumerable2.Range(source, start, count);

	}

	public static <TSource> Enumerable<TSource> Reverse(Enumerable<TSource> source) {
		return Enumerable2.Reverse(source);
	}

	public static <TSource, TResult> Enumerable<TResult> SelectMany(Enumerable<TSource> source, Expr1<TSource, Enumerable<TResult>> selector) {
		return Enumerable2.SelectMany(source, x->selector.apply(x));
	}

	public static <TSource, TResult> Enumerable<TResult> SelectMany(Enumerable<TSource> source, Expr2<TSource, Integer, Enumerable<TResult>> selector) {
		return Enumerable2.SelectMany(source, (x,y)->selector.apply(x, y));
	}

	public static <TSource, TCollection, TResult> Enumerable<TResult> SelectMany(Enumerable<TSource> source,
			Expr2<TSource, Integer, Enumerable<TCollection>> collectionSelector,
			Expr2<TSource, TCollection, TResult> resultSelector) {
		return Enumerable2.SelectMany(source, (x,y)->collectionSelector.apply(x,y), 
				(x,y)->resultSelector.apply(x,y));
	}

	public static <TSource, TCollection, TResult> Enumerable<TResult> SelectMany(Enumerable<TSource> source,
			Expr1<TSource, Enumerable<TCollection>> collectionSelector,
			Expr2<TSource, TCollection, TResult> resultSelector) {
		return Enumerable2.SelectMany(source, (x)->collectionSelector.apply(x), 
				(x,y)->resultSelector.apply(x,y));
	}

	public static <TSource ,TKey extends Comparable<TKey>> Enumerable<TSource> ThenBy(Enumerable<TSource> source, Expr1<TSource, TKey> keySelector) {
		return Enumerable2.ThenBy(source, x->keySelector.apply(x));
	}

	public static <TSource, TKey> Enumerable<TSource> ThenBy(Enumerable<TSource> source, Expr1<TSource, TKey> keySelector, Expr2<TKey,TKey,Integer> comparer) {
		return Enumerable2.ThenBy(source, x->keySelector.apply(x),(x,y)->comparer.apply(x,y));
	}

	public static <TSource, TKey extends Comparable<TKey>> Enumerable<TSource> ThenByDescending(Enumerable<TSource> source, Expr1<TSource, TKey> keySelector) {
		
		return Enumerable2.ThenByDescending(source, x->keySelector.apply(x));
		
	}

	public static <TSource, TKey> Enumerable<TSource> ThenByDescending(Enumerable<TSource> source, Expr1<TSource, TKey> keySelector,
			Expr2<TKey,TKey,Integer> comparer) {
		return Enumerable2.ThenByDescending(source, x->keySelector.apply(x),(x,y)->comparer.apply(x,y));
	}

	public static <TSource> Set<TSource> ToSet(Enumerable<TSource> source, Expr2<TSource,TSource,Integer> comparer) {
		return Enumerable2.ToSet(source, (x,y)->comparer.apply(x, y));
	}

	public static <TSource> Set<TSource> ToSet(Enumerable<TSource> source) {
		return Enumerable2.ToSet(source,null);
	}

	public static <TSource> Enumerable<TSource> Union(Enumerable<TSource> source, Enumerable<TSource> other, Expr2<TSource,TSource,Integer> comparer) {
		return Enumerable2.Union(source, other, (x,y)->comparer.apply(x, y));
	}

	public static <TSource> Enumerable<TSource> Union(Enumerable<TSource> source, Enumerable<TSource> other) {
		return Enumerable2.Union(source, other, null);
	}

	public static <TSource> Enumerable<TSource> Where(Enumerable<TSource> source, Expr1<TSource, Boolean> expr1) {
		return Enumerable2.Where(source, x-> expr1.apply(x));
	}

	public static <TSource> Enumerable<TSource> Where(Enumerable<TSource> source, Expr2<TSource, Integer, Boolean> Expr1) {
		return Enumerable2.Where(source, (x,y)->Expr1.apply(x, y));
	}

	public static <TSource, TTarget, TResult> Enumerable<TResult> Zip(Enumerable<TSource> source, Enumerable<TTarget> target,
			Expr2<TSource, TTarget, TResult> resultSelector) {
		return Enumerable2.Zip(source, target, (x,y)->resultSelector.apply(x, y));
	}

	public static <TSource, TKey> Map<TKey, TSource> ToMap(Enumerable<TSource> source, Expr1<TSource, TKey> keySelector) {
		return Enumerable2.ToMap(source, x->keySelector.apply(x), 
				null, null);
	}

	public static <TSource, TKey> Map<TKey, TSource> ToMap(Enumerable<TSource> source, Expr1<TSource, TKey> keySelector, Expr2<TKey,TKey,Integer> comparer) {
		return Enumerable2.ToMap(source, x->keySelector.apply(x), 
				null, (x,y)->comparer.apply(x, y));
	}

	public static <TSource, TKey, TElement> Map<TKey, TElement> ToMap(Enumerable<TSource> source, Expr1<TSource, TKey> keySelector,
			Expr1<TSource, TElement> elementSelector) {
		return Enumerable2.ToMap(source, x->keySelector.apply(x), 
				x->elementSelector.apply(x), null);
	}

	public static <TSource, TKey, TElement> Map<TKey, TElement> ToMap(Enumerable<TSource> source, Expr1<TSource, TKey> keySelector,
			Expr1<TSource, TElement> elementSelector, Expr2<TKey,TKey,Integer> comparer) {
		return Enumerable2.ToMap(source, x->keySelector.apply(x), 
				x->elementSelector.apply(x), (x,y)->comparer.apply(x, y));
	}

	public static <TSource> int SumInt(Enumerable<TSource> source, Expr1<TSource, Integer> selector) {
		return Enumerable2.SumInt(source.select(x->selector.apply(x)));
	}

	public static <TSource> Integer SumOptionalInt(Enumerable<TSource> source, Expr1<TSource, OptionalInt> selector) {
		return Enumerable2.SumOptionalInt(source.select(x->selector.apply(x)));
	}

	public static <TSource> long SumLong(Enumerable<TSource> source, Expr1<TSource, Long> selector) {
		return Enumerable2.SumLong(source.select(x->selector.apply(x)));
	}

	public static <TSource> Long SumOptionalLong(Enumerable<TSource> source, Expr1<TSource, OptionalLong> selector) {
		return Enumerable2.SumOptionalLong(source.select(x->selector.apply(x)));
	}

	public static <TSource> double SumDouble(Enumerable<TSource> source, Expr1<TSource, Double> selector) {
		return Enumerable2.SumDouble(source.select(x->selector.apply(x)));
	}

	public static <TSource> Double SumOptionalDouble(Enumerable<TSource> source, Expr1<TSource, OptionalDouble> selector) {
		return Enumerable2.SumOptionalDouble(source.select(x->selector.apply(x)));
	}


	public static <TSource> Enumerable<TSource> Skip(Enumerable<TSource> source, int count) {
		return Enumerable2.Skip(source, count);
	}

	public static <TSource> Enumerable<TSource> SkipWhile(Enumerable<TSource> source, Expr1<TSource, Boolean> expr1) {
		return Enumerable2.SkipWhile(source, x->expr1.apply(x));
	}

	public static <TSource> Enumerable<TSource> SkipWhile(Enumerable<TSource> source, Expr2<TSource, Integer, Boolean> expr1) {
		return Enumerable2.SkipWhile(source, (x,y)->expr1.apply(x,y));
	}

	public static <TSource> Enumerable<TSource> Take(Enumerable<TSource> source, int count) {
		return Enumerable2.Take(source, count);
	}

	public static <TSource> Enumerable<TSource> TakeWhile(Enumerable<TSource> source, Expr1<TSource, Boolean> expr1) {
		return Enumerable2.TakeWhile(source, x->expr1.apply(x));
	}

	public static <TSource> Enumerable<TSource> TakeWhile(Enumerable<TSource> source, Expr2<TSource, Integer, Boolean> expr1) {
		return Enumerable2.TakeWhile(source, (x,y)->expr1.apply(x,y));
	}

	public static <TResult> Enumerable<TResult> Repeat(TResult element, int count) {
		return Enumerable2.Repeat(element, count);
	}

	public static Enumerable<Integer> RangeInt(int start, int count) {
		return Enumerable2.RangeInt(start, count);
	}

	/**
	 * 平均数
	 * 
	 * @param source
	 *            源
	 * @return
	 */
	public static Double AverageInt(Enumerable<Integer> source) {
		return Enumerable2.AverageInt(source);
	}

	public static Double AverageOptionalInt(Enumerable<OptionalInt> source) {
		return Enumerable2.AverageOptionalInt(source);
	}

	public static Double AverageLong(Enumerable<Long> source) {
		return Enumerable2.AverageLong(source);
	}

	public static Double AverageOptionalLong(Enumerable<OptionalLong> source) {
		return Enumerable2.AverageOptionalLong(source);
	}

	public static double AverageDouble(Enumerable<Double> source) {
		return Enumerable2.AverageDouble(source);
	}

	public static Double AverageOptionalDouble(Enumerable<OptionalDouble> source) {
		return Enumerable2.AverageOptionalDouble(source);
	}

	public static int MaxInt(Enumerable<Integer> source) {
		return Enumerable2.MaxInt(source);
	}

	public static Integer MaxOptionalInt(Enumerable<OptionalInt> source) {
		return Enumerable2.MaxOptionalInt(source);
	}

	public static long MaxLong(Enumerable<Long> source) {
		return Enumerable2.MaxLong(source);
	}

	public static Long MaxOptionalLong(Enumerable<OptionalLong> source) {
		return Enumerable2.MaxOptionalLong(source);
	}

	public static double MaxDouble(Enumerable<Double> source) {
		return Enumerable2.MaxDouble(source);
	}

	public static Double MaxOptionalDouble(Enumerable<OptionalDouble> source) {
		return Enumerable2.MaxOptionalDouble(source);
	}

	public static int MinInt(Enumerable<Integer> source) {
		return Enumerable2.MinInt(source);
	}

	public static Integer MinOptionalInt(Enumerable<OptionalInt> source) {
		return Enumerable2.MinOptionalInt(source);
	}

	public static long MinLong(Enumerable<Long> source) {
		return Enumerable2.MinLong(source);
	}

	public static Long MinOptionalLong(Enumerable<OptionalLong> source) {
		return Enumerable2.MinOptionalLong(source);
	}

	public static double MinDouble(Enumerable<Double> source) {
		return Enumerable2.MinDouble(source);
	}

	public static Double MinOptionalDouble(Enumerable<OptionalDouble> source) {
		return Enumerable2.MinOptionalDouble(source);
	}
	
	public static int SumInt(Enumerable<Integer> source) {
		return Enumerable2.SumInt(source);
	}

	public static Integer SumOptionalInt(Enumerable<OptionalInt> source) {
		return Enumerable2.SumOptionalInt(source);
	}

	public static long SumLong(Enumerable<Long> source) {
		return Enumerable2.SumLong(source);
	}

	public static Long SumOptionalLong(Enumerable<OptionalLong> source) {
		return Enumerable2.SumOptionalLong(source);
	}

	public static double SumDouble(Enumerable<Double> source) {
		return Enumerable2.SumDouble(source);
	}

	public static Double SumOptionalDouble(Enumerable<OptionalDouble> source) {
		return Enumerable2.SumOptionalDouble(source);
	}

	public static <TSource extends Comparable<TSource>> Boolean SequenceEqual(Enumerable<TSource> first,
			Enumerable<TSource> second) {
		return Enumerable2.SequenceEqual(first, second, null);
	}

	public static <TSource extends Comparable<TSource>> Boolean SequenceEqual(Enumerable<TSource> first,
			Enumerable<TSource> second, Expr2<TSource,TSource,Integer> comparer) {
		return Enumerable2.SequenceEqual(first, second, (x,y)->comparer.apply(x, y));
	}
}

package com.olcap;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


public interface Enumerable<TSource> extends Iterable<TSource> {
	/**
	 * 聚合
	 * 
	 * @param seed
	 *            初识累加值
	 * @param func
	 *            累加函数
	 * @param resultTransformer
	 *            transform the final accumulator value into the result value
	 * @return
	 */
	default <TAccumulate, TResult> TResult aggregate(TAccumulate seed,
			BiFunction<TAccumulate, TSource, TAccumulate> func, Function<TAccumulate, TResult> resultTransformer) {
		return Enumerable2.Aggregate(this, seed, func, resultTransformer);
	}

	default <TAccumulate> TAccumulate aggregate(TAccumulate seed, BiFunction<TAccumulate, TSource, TAccumulate> func) {
		return Enumerable2.Aggregate(this, seed, func);
	}

	/**
	 * 是否所有元素都满足谓语
	 * 
	 * @param predicate
	 *            谓语条件
	 * @return
	 */
	default Boolean all(Predicate<TSource> predicate) {

		return Enumerable2.All(this, predicate);
	}

	/**
	 * 是否存在任一个元素满足谓语
	 * 
	 * @param predicate
	 *            谓语条件
	 * @return
	 */
	default Boolean any(Predicate<TSource> predicate) {
		return Enumerable2.Any(this, predicate);
	}

	/**
	 * 平均数
	 * 
	 * @param selector
	 *            选择器
	 * @return
	 */
	default Double averageInt(Function<TSource, Integer> selector) {
		return Enumerable2.AverageInt(this.select(selector));
	}

	default Double averageOptionalInt(Function<TSource, OptionalInt> selector) {
		return Enumerable2.AverageOptionalInt(this.select(selector));
	}

	default Double averageLong(Function<TSource, Long> selector) {
		return Enumerable2.AverageLong(this.select(selector));
	}

	default Double averageOptionalLong(Function<TSource, OptionalLong> selector) {
		return Enumerable2.AverageOptionalLong(this.select(selector));
	}

	default Double averageDouble(Function<TSource, Double> selector) {
		return Enumerable2.AverageDouble(this.select(selector));
	}

	default Double averageOptionalDouble(Function<TSource, OptionalDouble> selector) {
		return Enumerable2.AverageOptionalDouble(this.select(selector));
	}

	default <TResult> Enumerable<TResult> cast() {
		return Enumerable2.Cast(this);
	}

	default <TResult> Enumerable<TResult> select(Function<TSource, TResult> selector) {
		return Enumerable2.Select(this, selector);
	}

	default <TResult> Enumerable<TResult> select(BiFunction<TSource, Integer, TResult> selector) {
		return Enumerable2.Select(this, selector);
	}

	default <TResult> Enumerable<TResult> map(Function<TSource, TResult> mapper) {
		return Enumerable2.Map(this, mapper);
	}

	default <TResult> Enumerable<TResult> map(BiFunction<TSource, Integer, TResult> mapper) {
		return Enumerable2.Map(this, mapper);
	}

	default Enumerable<TSource> concat(Enumerable<TSource> other) {
		return Enumerable2.Concat(this, other);
	}

	default List<TSource> toList() {
		return Enumerable2.ToList(this);
	}

	default Object[] toArray() {
		return Enumerable2.ToArray(this);
	}

	default Enumerable<TSource> goThrough(Consumer<TSource> consumer) {
		return Enumerable2.GoThrough(this, consumer);
	}

	default Boolean contains(TSource value, Comparator<TSource> comparer) {
		return Enumerable2.Contains(this, value, comparer);
	}

	default int count() {
		return Enumerable2.Count(this);
	}

	default int count(Predicate<TSource> predicate) {
		return Enumerable2.Count(this, predicate);
	}

	default long longCount() {
		return Enumerable2.LongCount(this);
	}

	default long longCount(Predicate<TSource> predicate) {
		return Enumerable2.LongCount(this, predicate);
	}

	default Enumerable<TSource> distinct() {
		return Enumerable2.Distinct(this);
	}

	default Enumerable<TSource> distinct(Comparator<TSource> comparer) {
		return Enumerable2.Distinct(this, comparer);
	}

	default TSource elementAt(int index) {
		return Enumerable2.ElementAt(this, index);
	}

	default TSource elementAtOrDefault(int index, TSource defaultVal) {
		return Enumerable2.ElementAtOrDefault(this, index, defaultVal);
	}

	default Enumerable<TSource> except(Enumerable<TSource> other) {
		return Enumerable2.Except(this, other);
	}

	default Enumerable<TSource> except(Enumerable<TSource> other, Comparator<TSource> comparer) {
		return Enumerable2.Except(this, other, comparer);
	}

	default TSource first() {
		return Enumerable2.First(this);
	}

	default TSource first(Predicate<TSource> predicate) {
		return Enumerable2.First(this, predicate);
	}

	default TSource firstOrDefault(TSource defaultVal) {
		return Enumerable2.FirstOrDefault(this, defaultVal);
	}

	default TSource firstOrDefault(Predicate<TSource> predicate, TSource defaultVal) {
		return Enumerable2.FirstOrDefault(this, predicate, defaultVal);
	}

	default <TKey> Enumerable<Entry<TKey, List<TSource>>> groupBy(Function<TSource, TKey> keySelector) {
		return groupBy(keySelector, (Function<TSource, TSource>) null, (Comparator<TKey>) null);
	}

	default <TKey> Enumerable<Entry<TKey, List<TSource>>> groupBy(Function<TSource, TKey> keySelector,
			Comparator<TKey> comparer) {
		return groupBy(keySelector, (Function<TSource, TSource>) null, comparer);
	}

	default <TKey, TElement> Enumerable<Entry<TKey, List<TElement>>> groupBy(Function<TSource, TKey> keySelector,
			Function<TSource, TElement> elementSelector) {
		return groupBy(keySelector, elementSelector, (Comparator<TKey>) null);
	}

	default <TKey, TElement> Enumerable<Entry<TKey, List<TElement>>> groupBy(Function<TSource, TKey> keySelector,
			Function<TSource, TElement> elementSelector, Comparator<TKey> comparer) {
		return Enumerable2.GroupBy(this, keySelector, elementSelector, comparer);
	}

	default <TKey, TResult> Enumerable<TResult> groupBy(Function<TSource, TKey> keySelector,
			BiFunction<TKey, Enumerable<TSource>, TResult> resultSelector) {
		return groupBy(keySelector, (Function<TSource, TSource>) null, resultSelector, (Comparator<TKey>) null);
	}

	default <TKey, TElement, TResult> Enumerable<TResult> groupBy(Function<TSource, TKey> keySelector,
			Function<TSource, TElement> elementSelector,
			BiFunction<TKey, Enumerable<TElement>, TResult> resultSelector) {
		return groupBy(keySelector, elementSelector, resultSelector, (Comparator<TKey>) null);
	}

	default <TKey, TResult> Enumerable<TResult> groupBy(Function<TSource, TKey> keySelector,
			BiFunction<TKey, Enumerable<TSource>, TResult> resultSelector, Comparator<TKey> comparer) {
		return groupBy(keySelector, (Function<TSource, TSource>) null, resultSelector, comparer);
	}

	default <TKey, TElement, TResult> Enumerable<TResult> groupBy(Function<TSource, TKey> keySelector,
			Function<TSource, TElement> elementSelector, BiFunction<TKey, Enumerable<TElement>, TResult> resultSelector,
			Comparator<TKey> comparer) {
		return Enumerable2.GroupBy(this, keySelector, elementSelector, resultSelector, comparer);
	}

	default Enumerable<TSource> intersect(Enumerable<TSource> other) {
		return Enumerable2.Intersect(this, other);
	}

	default Enumerable<TSource> intersect(Enumerable<TSource> other, Comparator<TSource> comparer) {
		return Enumerable2.Intersect(this, other, comparer);
	}

	default <TTarget, TKey, TResult> Enumerable<TResult> leftJoin(Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, TTarget, TResult> resultSelector) {
		return leftJoin(sourceKeySelector, target, targetKeySelector, resultSelector, null);
	}

	default <TTarget, TKey, TResult> Enumerable<TResult> leftJoin(Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, TTarget, TResult> resultSelector, Comparator<TKey> comparer) {
		return Enumerable2.LeftJoin(this, sourceKeySelector, target, targetKeySelector, resultSelector, comparer);
	}

	default <TTarget, TKey, TResult> Enumerable<TResult> groupLeftJoin(Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, Enumerable<TTarget>, TResult> resultSelector) {
		return groupLeftJoin(sourceKeySelector, target, targetKeySelector, resultSelector, null);
	}

	default <TTarget, TKey, TResult> Enumerable<TResult> groupLeftJoin(Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, Enumerable<TTarget>, TResult> resultSelector, Comparator<TKey> comparer) {
		
		return Enumerable2.GroupLeftJoin(this, sourceKeySelector, target, targetKeySelector, resultSelector, comparer);
	}

	default <TTarget, TKey, TResult> Enumerable<TResult> rightJoin(Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, TTarget, TResult> resultSelector) {
		return rightJoin(sourceKeySelector, target, targetKeySelector, resultSelector, null);
	}

	default <TTarget, TKey, TResult> Enumerable<TResult> rightJoin(Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, TTarget, TResult> resultSelector, Comparator<TKey> comparer) {
		return Enumerable2.RightJoin(this, sourceKeySelector, target, targetKeySelector, resultSelector, comparer);
	}

	default <TTarget, TKey, TResult> Enumerable<TResult> groupRightJoin(Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<Enumerable<TSource>, TTarget, TResult> resultSelector) {
		return groupRightJoin(sourceKeySelector, target, targetKeySelector, resultSelector, null);
	}

	default <TTarget, TKey, TResult> Enumerable<TResult> groupRightJoin(Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<Enumerable<TSource>, TTarget, TResult> resultSelector, Comparator<TKey> comparer) {
		return Enumerable2.GroupRightJoin(this, sourceKeySelector, target, targetKeySelector, resultSelector, comparer);
	}

	default <TTarget, TKey, TResult> Enumerable<TResult> innerJoin(Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, TTarget, TResult> resultSelector) {
		return innerJoin(sourceKeySelector, target, targetKeySelector, resultSelector, null);
	}

	default <TTarget, TKey, TResult> Enumerable<TResult> innerJoin(Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, TTarget, TResult> resultSelector, Comparator<TKey> comparer) {
		return Enumerable2.InnerJoin(this, sourceKeySelector, target, targetKeySelector, resultSelector, comparer);
	}

	default <TTarget, TKey, TResult> Enumerable<TResult> groupInnerJoin(Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<Enumerable<TSource>, Enumerable<TTarget>, TResult> resultSelector) {
		return groupInnerJoin(sourceKeySelector, target, targetKeySelector, resultSelector, null);
	}

	default <TTarget, TKey, TResult> Enumerable<TResult> groupInnerJoin(Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<Enumerable<TSource>, Enumerable<TTarget>, TResult> resultSelector, Comparator<TKey> comparer) {
		return Enumerable2.GroupInnerJoin(this, sourceKeySelector, target, targetKeySelector, resultSelector, comparer);
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
	default <TTarget, TKey, TResult> Enumerable<TResult> cartesianProduct(Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, TTarget, TResult> resultSelector) {
		return cartesianProduct(sourceKeySelector, target, targetKeySelector, resultSelector, null);
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
	default <TTarget, TKey, TResult> Enumerable<TResult> cartesianProduct(Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, TTarget, TResult> resultSelector, Comparator<TKey> comparer) {
		return Enumerable2.CartesianProduct(this, sourceKeySelector, target, targetKeySelector, resultSelector, comparer);
	}

	default <TTarget, TKey, TResult> Enumerable<TResult> groupCartesianProduct(
			Function<TSource, TKey> sourceKeySelector, Enumerable<TTarget> target,
			Function<TTarget, TKey> targetKeySelector,
			BiFunction<Enumerable<TSource>, Enumerable<TTarget>, TResult> resultSelector) {
		return groupCartesianProduct(sourceKeySelector, target, targetKeySelector, resultSelector, null);
	}

	default <TTarget, TKey, TResult> Enumerable<TResult> groupCartesianProduct(
			Function<TSource, TKey> sourceKeySelector, Enumerable<TTarget> target,
			Function<TTarget, TKey> targetKeySelector,
			BiFunction<Enumerable<TSource>, Enumerable<TTarget>, TResult> resultSelector, Comparator<TKey> comparer) {
		return Enumerable2.GroupCartesianProduct(this, sourceKeySelector, target, targetKeySelector, resultSelector, comparer);
	}

	default TSource last() {
		return Enumerable2.Last(this);
	}

	default TSource last(Predicate<TSource> predicate) {
		return Enumerable2.Last(this, predicate);
	}

	default TSource lastOrDefault(TSource defaultVal) {
		return Enumerable2.LastOrDefault(this, defaultVal);
	}

	default TSource lastOrDefault(Predicate<TSource> predicate, TSource defaultVal) {
		return Enumerable2.LastOrDefault(this, predicate, defaultVal);
	}

	default int maxInt(Function<TSource, Integer> selector) {
		return Enumerable2.MaxInt(this.select(selector));
	}

	default Integer maxOptionalInt(Function<TSource, OptionalInt> selector) {
		return Enumerable2.MaxOptionalInt(this.select(selector));
	}

	default long maxLong(Function<TSource, Long> selector) {
		return Enumerable2.MaxLong(this.select(selector));
	}

	default Long maxOptionalLong(Function<TSource, OptionalLong> selector) {
		return Enumerable2.MaxOptionalLong(this.select(selector));
	}

	default double maxDouble(Function<TSource, Double> selector) {
		return Enumerable2.MaxDouble(this.select(selector));
	}

	default Double maxOptionalDouble(Function<TSource, OptionalDouble> selector) {
		return Enumerable2.MaxOptionalDouble(this.select(selector));
	}

	default int minInt(Function<TSource, Integer> selector) {
		return Enumerable2.MinInt(this.select(selector));
	}

	default Integer minOptionalInt(Function<TSource, OptionalInt> selector) {
		return Enumerable2.MinOptionalInt(this.select(selector));
	}

	default long minLong(Function<TSource, Long> selector) {
		return Enumerable2.MinLong(this.select(selector));
	}

	default Long minOptionalLong(Function<TSource, OptionalLong> selector) {
		return Enumerable2.MinOptionalLong(this.select(selector));
	}

	default double minDouble(Function<TSource, Double> selector) {
		return Enumerable2.MinDouble(this.select(selector));
	}

	default Double minOptionalDouble(Function<TSource, OptionalDouble> selector) {
		return Enumerable2.MinOptionalDouble(this.select(selector));
	}

	default <TKey extends Comparable<TKey>> Enumerable<TSource> orderBy(Function<TSource, TKey> keySelector) {
		return Enumerable2.OrderBy(this, keySelector);
	}

	default <TKey> Enumerable<TSource> orderBy(Function<TSource, TKey> keySelector, Comparator<TKey> comparer) {
		return Enumerable2.OrderBy(this, keySelector, comparer);
	}

	default <TKey extends Comparable<TKey>> Enumerable<TSource> orderByDescending(Function<TSource, TKey> keySelector) {
		return Enumerable2.OrderByDescending(this, keySelector);
	}

	default <TKey> Enumerable<TSource> orderByDescending(Function<TSource, TKey> keySelector,
			Comparator<TKey> comparer) {
		return Enumerable2.OrderByDescending(this, keySelector, comparer);
	}

	default Enumerable<TSource> append(TSource element) {
		return Enumerable2.Append(this, element);
	}

	default Enumerable<TSource> prepend(TSource element) {
		return Enumerable2.Prepend(this, element);
	}

	default Enumerable<TSource> range(int start, int count) {
		return Enumerable2.Range(this, start, count);

	}

	default Enumerable<TSource> reverse() {
		return Enumerable2.Reverse(this);
	}

	default <TResult> Enumerable<TResult> selectMany(Function<TSource, Enumerable<TResult>> selector) {
		return Enumerable2.SelectMany(this, selector);
	}

	default <TResult> Enumerable<TResult> selectMany(BiFunction<TSource, Integer, Enumerable<TResult>> selector) {
		return Enumerable2.SelectMany(this, selector);
	}

	default <TCollection, TResult> Enumerable<TResult> selectMany(
			BiFunction<TSource, Integer, Enumerable<TCollection>> collectionSelector,
			BiFunction<TSource, TCollection, TResult> resultSelector) {
		return Enumerable2.SelectMany(this, collectionSelector, resultSelector);
	}

	default <TCollection, TResult> Enumerable<TResult> selectMany(
			Function<TSource, Enumerable<TCollection>> collectionSelector,
			BiFunction<TSource, TCollection, TResult> resultSelector) {
		return Enumerable2.SelectMany(this, collectionSelector, resultSelector);
	}

	default <TKey extends Comparable<TKey>> Enumerable<TSource> thenBy(Function<TSource, TKey> keySelector) {
		return Enumerable2.ThenBy(this, keySelector);
	}

	default <TKey> Enumerable<TSource> thenBy(Function<TSource, TKey> keySelector, Comparator<TKey> comparer) {
		return Enumerable2.ThenBy(this, keySelector, comparer);
	}

	default <TKey extends Comparable<TKey>> Enumerable<TSource> thenByDescending(Function<TSource, TKey> keySelector) {
		return Enumerable2.ThenByDescending(this, keySelector);
	}

	default <TKey> Enumerable<TSource> thenByDescending(Function<TSource, TKey> keySelector,
			Comparator<TKey> comparer) {
		return Enumerable2.ThenByDescending(this, keySelector, comparer);
	}

	default Set<TSource> toSet(Comparator<TSource> comparer) {
		return Enumerable2.ToSet(this, comparer);
	}

	default Set<TSource> toSet() {
		return Enumerable2.ToSet(this, null);
	}

	default Enumerable<TSource> union(Enumerable<TSource> other, Comparator<TSource> comparer) {
		return Enumerable2.Union(this, other, comparer);
	}

	default Enumerable<TSource> union(Enumerable<TSource> other) {
		return Enumerable2.Union(this, other, null);
	}

	default Enumerable<TSource> where(Predicate<TSource> predicate) {
		return Enumerable2.Where(this, predicate);
	}

	default Enumerable<TSource> where(BiFunction<TSource, Integer, Boolean> predicate) {
		return Enumerable2.Where(this, predicate);
	}

	default <TTarget, TResult> Enumerable<TResult> zip(Enumerable<TTarget> target,
			BiFunction<TSource, TTarget, TResult> resultSelector) {
		return Enumerable2.Zip(this, target, resultSelector);
	}

	default <TKey> Map<TKey, TSource> toMap(Function<TSource, TKey> keySelector) {
		return Enumerable2.ToMap(this,keySelector, null, null);
	}

	default <TKey> Map<TKey, TSource> toMap(Function<TSource, TKey> keySelector, Comparator<TKey> comparer) {
		return Enumerable2.ToMap(this, keySelector, null, comparer);
	}

	default <TKey, TElement> Map<TKey, TElement> toMap(Function<TSource, TKey> keySelector,
			Function<TSource, TElement> elementSelector) {
		return Enumerable2.ToMap(this, keySelector, elementSelector, null);
	}

	default <TKey, TElement> Map<TKey, TElement> toMap(Function<TSource, TKey> keySelector,
			Function<TSource, TElement> elementSelector, Comparator<TKey> comparer) {
		return Enumerable2.ToMap(this, keySelector, elementSelector, comparer);
	}

	default int sumInt(Function<TSource, Integer> selector) {
		return Enumerable2.SumInt(this.select(selector));
	}

	default Integer sumOptionalInt(Function<TSource, OptionalInt> selector) {
		return Enumerable2.SumOptionalInt(this.select(selector));
	}

	default long sumLong(Function<TSource, Long> selector) {
		return Enumerable2.SumLong(this.select(selector));
	}

	default Long sumOptionalLong(Function<TSource, OptionalLong> selector) {
		return Enumerable2.SumOptionalLong(this.select(selector));
	}

	default double sumDouble(Function<TSource, Double> selector) {
		return Enumerable2.SumDouble(this.select(selector));
	}

	default Double sumOptionalDouble(Function<TSource, OptionalDouble> selector) {
		return Enumerable2.SumOptionalDouble(this.select(selector));
	}


	default Enumerable<TSource> skip(int count) {
		return Enumerable2.Skip(this, count);
	}

	default Enumerable<TSource> skipWhile(Predicate<TSource> predicate) {
		return Enumerable2.SkipWhile(this, predicate);
	}

	default Enumerable<TSource> skipWhile(BiFunction<TSource, Integer, Boolean> predicate) {
		return Enumerable2.SkipWhile(this, predicate);
	}

	default Enumerable<TSource> take(int count) {
		return Enumerable2.Take(this, count);
	}

	default Enumerable<TSource> takeWhile(Predicate<TSource> predicate) {
		return Enumerable2.TakeWhile(this, predicate);
	}

	default Enumerable<TSource> takeWhile(BiFunction<TSource, Integer, Boolean> predicate) {
		return Enumerable2.TakeWhile(this, predicate);
	}

}

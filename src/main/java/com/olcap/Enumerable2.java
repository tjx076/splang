package com.olcap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


public class Enumerable2 {
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
	public static <TSource, TAccumulate, TResult> TResult Aggregate(Enumerable<TSource> source , TAccumulate seed,
			BiFunction<TAccumulate, TSource, TAccumulate> func, Function<TAccumulate, TResult> resultTransformer) {
		if (func == null)
			throw new NullPointerException("func cannot be null");
		if (resultTransformer == null)
			throw new NullPointerException("resultTransformer cannot be null");
		TAccumulate result = seed;
		for (TSource element : source)
			result = func.apply(result, element);
		return resultTransformer.apply(result);
	}

	public static <TSource, TAccumulate> TAccumulate Aggregate(Enumerable<TSource> source ,TAccumulate seed, BiFunction<TAccumulate, TSource, TAccumulate> func) {
		if (func == null)
			throw new NullPointerException("func cannot be null");
		TAccumulate result = seed;
		for (TSource element : source)
			result = func.apply(result, element);
		return result;
	}

	/**
	 * 是否所有元素都满足谓语
	 * 
	 * @param predicate
	 *            谓语条件
	 * @return
	 */
	public static <TSource> Boolean All(Enumerable<TSource> source , Predicate<TSource> predicate) {
		if (predicate == null)
			throw new NullPointerException("predicate cannot be null");
		for (TSource element : source) {
			if (!predicate.test(element))
				return false;
		}
		return true;
	}

	/**
	 * 是否存在任一个元素满足谓语
	 * 
	 * @param predicate
	 *            谓语条件
	 * @return
	 */
	public static <TSource> Boolean Any(Enumerable<TSource> source, Predicate<TSource> predicate) {
		if (predicate == null)
			throw new NullPointerException("predicate cannot be null");
		for (TSource element : source) {
			if (predicate.test(element))
				return true;
		}
		return false;
	}

	/**
	 * 平均数
	 * 
	 * @param selector
	 *            选择器
	 * @return
	 */
	public static <TSource> Double AverageInt(Enumerable<TSource> source ,Function<TSource, Integer> selector) {
		return Enumerable2.AverageInt(source.select(selector));
	}

	public static <TSource> Double AverageOptionalInt(Enumerable<TSource> source, Function<TSource, OptionalInt> selector) {
		return Enumerable2.AverageOptionalInt(source.select(selector));
	}

	public static <TSource> Double AverageLong(Enumerable<TSource> source, Function<TSource, Long> selector) {
		return Enumerable2.AverageLong(source.select(selector));
	}

	public static <TSource> Double AverageOptionalLong(Enumerable<TSource> source,Function<TSource, OptionalLong> selector) {
		return Enumerable2.AverageOptionalLong(source.select(selector));
	}

	public static <TSource> Double AverageDouble(Enumerable<TSource> source,Function<TSource, Double> selector) {
		return Enumerable2.AverageDouble(source.select(selector));
	}

	public static <TSource> Double AverageOptionalDouble(Enumerable<TSource> source, Function<TSource, OptionalDouble> selector) {
		return Enumerable2.AverageOptionalDouble(source.select(selector));
	}

	@SuppressWarnings("unchecked")
	public static <TSource, TResult> Enumerable<TResult> Cast(Enumerable<TSource> source) {
		return Select(source, x -> (TResult) x);
	}

	public static <TSource, TResult> Enumerable<TResult> Select(Enumerable<TSource> source, Function<TSource, TResult> selector) {
		if (selector == null)
			throw new NullPointerException("selector cannot be null");

		List<TResult> list = new ArrayList<TResult>();

		for (TSource v : source) {
			list.add(selector.apply(v));
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource, TResult> Enumerable<TResult> Select(Enumerable<TSource> source, BiFunction<TSource, Integer, TResult> selector) {
		if (selector == null)
			throw new NullPointerException("selector cannot be null");

		List<TResult> list = new ArrayList<TResult>();
		int index = -1;
		for (TSource element : source) {
			index++;

			list.add(selector.apply(element, index));
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource, TResult> Enumerable<TResult> Map(Enumerable<TSource> source, Function<TSource, TResult> mapper) {
		if (mapper == null)
			throw new NullPointerException("mapper cannot be null");

		List<TResult> list = new ArrayList<TResult>();

		for (TSource v : source) {
			list.add(mapper.apply(v));
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource, TResult> Enumerable<TResult> Map(Enumerable<TSource> source, BiFunction<TSource, Integer, TResult> mapper) {
		if (mapper == null)
			throw new NullPointerException("mapper cannot be null");

		List<TResult> list = new ArrayList<TResult>();
		int index = -1;
		for (TSource element : source) {
			index++;

			list.add(mapper.apply(element, index));
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource> Enumerable<TSource> Concat(Enumerable<TSource> source, Enumerable<TSource> other) {
		if (other == null)
			throw new NullPointerException("other cannot be null");

		List<TSource> total = new ArrayList<TSource>();
		total.addAll(source.toList());
		total.addAll(other.toList());

		return new ListEnumerable<>(total);
	}

	public static <TSource> List<TSource> ToList(Enumerable<TSource> source) {
		List<TSource> result = new ArrayList<TSource>();

		for (TSource v : source) {
			result.add(v);
		}

		return result;
	}

	public static <TSource> Object[] ToArray(Enumerable<TSource> source) {
		return ToList(source).toArray();
	}

	public static <TSource> Enumerable<TSource> GoThrough(Enumerable<TSource> source, Consumer<TSource> consumer) {
		if (consumer == null)
			throw new NullPointerException("consumer cannot be null");

		for (TSource v : source) {
			consumer.accept(v);
		}

		return source;
	}

	public static <TSource> Boolean Contains(Enumerable<TSource> source,TSource value, Comparator<TSource> comparer) {
		for (TSource element : source)
			if (comparer.compare(element, value) == 0)
				return true;
		
		return false;
	}

	@SuppressWarnings("unused")
	public static<TSource> int Count(Enumerable<TSource> source) {
		int count = 0;
		for (TSource v : source) {
			count++;
		}
		return count;
	}

	public static <TSource>int Count(Enumerable<TSource> source,Predicate<TSource> predicate) {
		if (predicate == null)
			throw new NullPointerException("predicate cannot be null");
		int count = 0;
		for (TSource element : source) {
			if (predicate.test(element))
				count++;

		}
		return count;
	}

	@SuppressWarnings("unused")
	public static <TSource>long LongCount(Enumerable<TSource> source) {
		long count = 0;
		for (TSource v : source) {
			count++;
		}
		return count;
	}

	public static <TSource> long LongCount(Enumerable<TSource> source, Predicate<TSource> predicate) {
		if (predicate == null)
			throw new NullPointerException("predicate cannot be null");
		long count = 0;
		for (TSource element : source) {
			if (predicate.test(element))
				count++;

		}
		return count;
	}

	public static <TSource> Enumerable<TSource> Distinct(Enumerable<TSource> source) {
		return Distinct(source,null);
	}

	public static <TSource>Enumerable<TSource> Distinct(Enumerable<TSource> source,Comparator<TSource> comparer) {
		Set<TSource> set = new TreeSet<TSource>(comparer);
		for (TSource element : source)
			set.add(element);

		return new SetEnumerable<>(set);
	}

	public static <TSource>TSource ElementAt(Enumerable<TSource> source,int index) {
		if (index < 0)
			throw new IndexOutOfBoundsException("index:" + index);

		Iterator<TSource> ite = source.iterator();
		while (true) {
			if (!ite.hasNext())
				throw new IndexOutOfBoundsException("index:" + index);
			if (index == 0)
				return ite.next();
			index--;
		}
	}

	public static <TSource> TSource ElementAtOrDefault(Enumerable<TSource> source, int index, TSource defaultVal) {
		if (index < 0)
			throw new IndexOutOfBoundsException("index:" + index);

		Iterator<TSource> ite = source.iterator();
		while (true) {
			if (!ite.hasNext())
				break;
			if (index == 0)
				return ite.next();
			index--;
		}

		return defaultVal;
	}

	public static <TSource> Enumerable<TSource> Except(Enumerable<TSource> source, Enumerable<TSource> other) {
		if (other == null)
			throw new NullPointerException("other cannot be null");
		return Except(other, null);
	}

	public static <TSource> Enumerable<TSource> Except(Enumerable<TSource> source, Enumerable<TSource> other, Comparator<TSource> comparer) {
		if (other == null)
			throw new NullPointerException("other cannot be null");

		Set<TSource> set = new TreeSet<TSource>(comparer);
		for (TSource element : other)
			set.add(element);

		List<TSource> list = new ArrayList<TSource>();
		for (TSource element : source)
			if (set.add(element))
				list.add(element);

		return new ListEnumerable<>(list);
	}

	public static <TSource> TSource First(Enumerable<TSource> source) {
		Iterator<TSource> ite = source.iterator();

		if (ite.hasNext())
			return ite.next();
		throw new NoElementsException();
	}

	public static <TSource> TSource First(Enumerable<TSource> source, Predicate<TSource> predicate) {
		if (predicate == null)
			throw new NullPointerException("predicate cannot be null");
		for (TSource element : source) {
			if (predicate.test(element))
				return element;
		}
		throw new NoMatchException();
	}

	public static <TSource> TSource FirstOrDefault(Enumerable<TSource> source,TSource defaultVal) {
		Iterator<TSource> ite = source.iterator();

		if (ite.hasNext())
			return ite.next();

		return defaultVal;
	}

	public static <TSource>TSource FirstOrDefault(Enumerable<TSource> source,Predicate<TSource> predicate, TSource defaultVal) {
		if (predicate == null)
			throw new NullPointerException("predicate cannot be null");
		for (TSource element : source) {
			if (predicate.test(element))
				return element;
		}
		return defaultVal;
	}

	public static <TSource,TKey> Enumerable<Entry<TKey, List<TSource>>> GroupBy(Enumerable<TSource> source,Function<TSource, TKey> keySelector) {
		return GroupBy(source,keySelector, (Function<TSource, TSource>) null, (Comparator<TKey>) null);
	}

	public static <TSource, TKey> Enumerable<Entry<TKey, List<TSource>>> GroupBy(Enumerable<TSource> source,Function<TSource, TKey> keySelector,
			Comparator<TKey> comparer) {
		return GroupBy(source,keySelector, (Function<TSource, TSource>) null, comparer);
	}

	public static <TSource, TKey, TElement> Enumerable<Entry<TKey, List<TElement>>> GroupBy(Enumerable<TSource> source, Function<TSource, TKey> keySelector,
			Function<TSource, TElement> elementSelector) {
		return GroupBy(source,keySelector, elementSelector, (Comparator<TKey>) null);
	}

	@SuppressWarnings("unchecked")
	public static <TSource, TKey, TElement> Enumerable<Entry<TKey, List<TElement>>> GroupBy(Enumerable<TSource> source, Function<TSource, TKey> keySelector,
			Function<TSource, TElement> elementSelector, Comparator<TKey> comparer) {
		if (keySelector == null)
			throw new NullPointerException("keySelector cannot be null");
		// if (elementSelector == null) throw new
		// NullPointerException("elementSelector cannot be null");
		// if (comparer == null) throw new NullPointerException("comparer cannot
		// be null");

		Map<TKey, List<TElement>> map = new TreeMap<TKey, List<TElement>>(comparer);

		for (TSource v : source) {
			TKey key = keySelector.apply(v);
			TElement element = elementSelector != null ? elementSelector.apply(v) : (TElement) v;

			List<TElement> list = map.get(key);
			if (list == null) {
				list = new ArrayList<TElement>();
				map.put(key, list);
			}

			list.add(element);
		}

		return new MapEnumerable<TKey, List<TElement>>(map);
	}

	public static <TSource, TKey, TResult> Enumerable<TResult> GroupBy(Enumerable<TSource> source, Function<TSource, TKey> keySelector,
			BiFunction<TKey, Enumerable<TSource>, TResult> resultSelector) {
		return GroupBy(source,keySelector, (Function<TSource, TSource>) null, resultSelector, (Comparator<TKey>) null);
	}

	public static <TSource, TKey, TElement, TResult> Enumerable<TResult> GroupBy(Enumerable<TSource> source, Function<TSource, TKey> keySelector,
			Function<TSource, TElement> elementSelector,
			BiFunction<TKey, Enumerable<TElement>, TResult> resultSelector) {
		return GroupBy(source, keySelector, elementSelector, resultSelector, (Comparator<TKey>) null);
	}

	public static <TSource, TKey, TResult> Enumerable<TResult> GroupBy(Enumerable<TSource> source, Function<TSource, TKey> keySelector,
			BiFunction<TKey, Enumerable<TSource>, TResult> resultSelector, Comparator<TKey> comparer) {
		return GroupBy(source, keySelector, (Function<TSource, TSource>) null, resultSelector, comparer);
	}

	@SuppressWarnings("unchecked")
	public static <TSource, TKey, TElement, TResult> Enumerable<TResult> GroupBy(Enumerable<TSource> source, Function<TSource, TKey> keySelector,
			Function<TSource, TElement> elementSelector, BiFunction<TKey, Enumerable<TElement>, TResult> resultSelector,
			Comparator<TKey> comparer) {
		if (keySelector == null)
			throw new NullPointerException("keySelector cannot be null");
		if (resultSelector == null)
			throw new NullPointerException("resultSelector cannot be null");

		Map<TKey, List<TElement>> map = new TreeMap<TKey, List<TElement>>(comparer);

		for (TSource v : source) {
			TKey key = keySelector.apply(v);
			TElement element = elementSelector != null ? elementSelector.apply(v) : (TElement) v;

			List<TElement> list = map.get(key);
			if (list == null) {
				list = new ArrayList<TElement>();
				map.put(key, list);
			}

			list.add(element);
		}

		List<TResult> list = new ArrayList<TResult>();
		for (Map.Entry<TKey, List<TElement>> e : map.entrySet()) {
			list.add(resultSelector.apply(e.getKey(), new ListEnumerable<TElement>(e.getValue())));
		}

		return new ListEnumerable<TResult>(list);
	}

	public static <TSource>Enumerable<TSource> Intersect(Enumerable<TSource> source ,Enumerable<TSource> other) {
		if (other == null)
			throw new NullPointerException("other cannot be null");
		return Intersect(source, other, null);
	}

	public static <TSource>Enumerable<TSource> Intersect(Enumerable<TSource> source, Enumerable<TSource> other, Comparator<TSource> comparer) {
		if (other == null)
			throw new NullPointerException("other cannot be null");

		Set<TSource> set = new TreeSet<TSource>(comparer);
		for (TSource element : other)
			set.add(element);

		List<TSource> list = new ArrayList<TSource>();
		for (TSource element : source)
			if (set.remove(element))
				list.add(element);

		return new ListEnumerable<TSource>(list);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> LeftJoin(Enumerable<TSource> source, Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, TTarget, TResult> resultSelector) {
		return LeftJoin(source, sourceKeySelector, target, targetKeySelector, resultSelector, null);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> LeftJoin(Enumerable<TSource> source, Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, TTarget, TResult> resultSelector, Comparator<TKey> comparer) {
		if (target == null)
			throw new NullPointerException("target cannot be null");
		if (sourceKeySelector == null)
			throw new NullPointerException("sourceKeySelector cannot be null");
		if (targetKeySelector == null)
			throw new NullPointerException("targetKeySelector cannot be null");
		if (resultSelector == null)
			throw new NullPointerException("resultSelector cannot be null");

		Map<TKey, List<TTarget>> map = new TreeMap<TKey, List<TTarget>>(comparer);
		for (TTarget dest : target) {
			TKey key = targetKeySelector.apply(dest);

			List<TTarget> list = map.get(key);
			if (list == null) {
				list = new ArrayList<TTarget>();
				map.put(key, list);
			}

			list.add(dest);
		}

		List<TResult> list = new ArrayList<TResult>();

		for (TSource src : source) {
			TKey key = sourceKeySelector.apply(src);

			List<TTarget> destList = map.get(key);
			if (destList != null) {

				for (TTarget dest : destList) {
					list.add(resultSelector.apply(src, dest));
				}

			} else {
				list.add(resultSelector.apply(src, null));
			}
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> GroupLeftJoin(Enumerable<TSource> source, Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, Enumerable<TTarget>, TResult> resultSelector) {
		return GroupLeftJoin(source, sourceKeySelector, target, targetKeySelector, resultSelector, null);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> GroupLeftJoin(Enumerable<TSource> source, Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, Enumerable<TTarget>, TResult> resultSelector, Comparator<TKey> comparer) {
		if (target == null)
			throw new NullPointerException("target cannot be null");
		if (sourceKeySelector == null)
			throw new NullPointerException("sourceKeySelector cannot be null");
		if (targetKeySelector == null)
			throw new NullPointerException("targetKeySelector cannot be null");
		if (resultSelector == null)
			throw new NullPointerException("resultSelector cannot be null");

		Map<TKey, List<TTarget>> map = new TreeMap<TKey, List<TTarget>>(comparer);
		for (TTarget dest : target) {
			TKey key = targetKeySelector.apply(dest);

			List<TTarget> list = map.get(key);
			if (list == null) {
				list = new ArrayList<TTarget>();
				map.put(key, list);
			}

			list.add(dest);
		}

		List<TResult> list = new ArrayList<TResult>();

		for (TSource src : source) {
			TKey key = sourceKeySelector.apply(src);

			List<TTarget> destList = map.get(key);

			list.add(resultSelector.apply(src, new ListEnumerable<>(destList)));
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> RightJoin(Enumerable<TSource> source, Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, TTarget, TResult> resultSelector) {
		return RightJoin(source, sourceKeySelector, target, targetKeySelector, resultSelector, null);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> RightJoin(Enumerable<TSource> source, Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, TTarget, TResult> resultSelector, Comparator<TKey> comparer) {
		if (target == null)
			throw new NullPointerException("target cannot be null");
		if (sourceKeySelector == null)
			throw new NullPointerException("sourceKeySelector cannot be null");
		if (targetKeySelector == null)
			throw new NullPointerException("targetKeySelector cannot be null");
		if (resultSelector == null)
			throw new NullPointerException("resultSelector cannot be null");

		Map<TKey, List<TSource>> map = new TreeMap<TKey, List<TSource>>(comparer);
		for (TSource src : source) {
			TKey key = sourceKeySelector.apply(src);

			List<TSource> list = map.get(key);
			if (list == null) {
				list = new ArrayList<TSource>();
				map.put(key, list);
			}

			list.add(src);
		}

		List<TResult> list = new ArrayList<TResult>();

		for (TTarget dest : target) {
			TKey key = targetKeySelector.apply(dest);

			List<TSource> srcList = map.get(key);
			if (srcList != null) {

				for (TSource src : srcList) {
					list.add(resultSelector.apply(src, dest));
				}

			} else {
				list.add(resultSelector.apply(null, dest));
			}
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> GroupRightJoin(Enumerable<TSource> source, Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<Enumerable<TSource>, TTarget, TResult> resultSelector) {
		return GroupRightJoin(source, sourceKeySelector, target, targetKeySelector, resultSelector, null);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> GroupRightJoin(Enumerable<TSource> source, Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<Enumerable<TSource>, TTarget, TResult> resultSelector, Comparator<TKey> comparer) {
		if (target == null)
			throw new NullPointerException("target cannot be null");
		if (sourceKeySelector == null)
			throw new NullPointerException("sourceKeySelector cannot be null");
		if (targetKeySelector == null)
			throw new NullPointerException("targetKeySelector cannot be null");
		if (resultSelector == null)
			throw new NullPointerException("resultSelector cannot be null");

		Map<TKey, List<TSource>> map = new TreeMap<TKey, List<TSource>>(comparer);
		for (TSource src : source) {
			TKey key = sourceKeySelector.apply(src);

			List<TSource> list = map.get(key);
			if (list == null) {
				list = new ArrayList<TSource>();
				map.put(key, list);
			}

			list.add(src);
		}

		List<TResult> list = new ArrayList<TResult>();

		for (TTarget dest : target) {
			TKey key = targetKeySelector.apply(dest);

			List<TSource> srcList = map.get(key);

			list.add(resultSelector.apply(new ListEnumerable<>(srcList), dest));
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> InnerJoin(Enumerable<TSource> source, Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, TTarget, TResult> resultSelector) {
		return InnerJoin(source, sourceKeySelector, target, targetKeySelector, resultSelector, null);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> InnerJoin(Enumerable<TSource> source, Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, TTarget, TResult> resultSelector, Comparator<TKey> comparer) {
		if (target == null)
			throw new NullPointerException("target cannot be null");
		if (sourceKeySelector == null)
			throw new NullPointerException("sourceKeySelector cannot be null");
		if (targetKeySelector == null)
			throw new NullPointerException("targetKeySelector cannot be null");
		if (resultSelector == null)
			throw new NullPointerException("resultSelector cannot be null");

		Map<TKey, List<TTarget>> destMap = new TreeMap<TKey, List<TTarget>>(comparer);
		for (TTarget dest : target) {
			TKey key = targetKeySelector.apply(dest);

			List<TTarget> list = destMap.get(key);
			if (list == null) {
				list = new ArrayList<TTarget>();
				destMap.put(key, list);
			}

			list.add(dest);
		}

		Map<TKey, List<TSource>> srcMap = new TreeMap<TKey, List<TSource>>(comparer);
		for (TSource src : source) {
			TKey key = sourceKeySelector.apply(src);

			List<TSource> list = srcMap.get(key);
			if (list == null) {
				list = new ArrayList<TSource>();
				srcMap.put(key, list);
			}

			list.add(src);
		}

		Set<TKey> set = new TreeSet<TKey>(comparer);
		set.addAll(srcMap.keySet());
		set.addAll(destMap.keySet());

		List<TResult> list = new ArrayList<TResult>();

		for (TKey key : set) {
			List<TSource> srcList = srcMap.get(key);
			List<TTarget> destList = destMap.get(key);

			if (srcList != null && destList != null) {
				for (TSource src : srcList) {
					for (TTarget dest : destList) {
						list.add(resultSelector.apply(src, dest));
					}
				}
			}
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> GroupInnerJoin(Enumerable<TSource> source, Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<Enumerable<TSource>, Enumerable<TTarget>, TResult> resultSelector) {
		return GroupInnerJoin(source, sourceKeySelector, target, targetKeySelector, resultSelector, null);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> GroupInnerJoin(Enumerable<TSource> source, Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<Enumerable<TSource>, Enumerable<TTarget>, TResult> resultSelector, Comparator<TKey> comparer) {
		if (target == null)
			throw new NullPointerException("target cannot be null");
		if (sourceKeySelector == null)
			throw new NullPointerException("sourceKeySelector cannot be null");
		if (targetKeySelector == null)
			throw new NullPointerException("targetKeySelector cannot be null");
		if (resultSelector == null)
			throw new NullPointerException("resultSelector cannot be null");

		Map<TKey, List<TTarget>> destMap = new TreeMap<TKey, List<TTarget>>(comparer);
		for (TTarget dest : target) {
			TKey key = targetKeySelector.apply(dest);

			List<TTarget> list = destMap.get(key);
			if (list == null) {
				list = new ArrayList<TTarget>();
				destMap.put(key, list);
			}

			list.add(dest);
		}

		Map<TKey, List<TSource>> srcMap = new TreeMap<TKey, List<TSource>>(comparer);
		for (TSource src : source) {
			TKey key = sourceKeySelector.apply(src);

			List<TSource> list = srcMap.get(key);
			if (list == null) {
				list = new ArrayList<TSource>();
				srcMap.put(key, list);
			}

			list.add(src);
		}

		Set<TKey> set = new TreeSet<TKey>(comparer);
		set.addAll(srcMap.keySet());
		set.addAll(destMap.keySet());

		List<TResult> list = new ArrayList<TResult>();

		for (TKey key : set) {
			List<TSource> srcList = srcMap.get(key);
			List<TTarget> destList = destMap.get(key);

			if (srcList != null && destList != null) {
				list.add(resultSelector.apply(new ListEnumerable<>(srcList), new ListEnumerable<>(destList)));
			}
		}

		return new ListEnumerable<>(list);
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
	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> CartesianProduct(Enumerable<TSource> source, Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, TTarget, TResult> resultSelector) {
		return CartesianProduct(source, sourceKeySelector, target, targetKeySelector, resultSelector, null);
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
	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> CartesianProduct(Enumerable<TSource> source, Function<TSource, TKey> sourceKeySelector,
			Enumerable<TTarget> target, Function<TTarget, TKey> targetKeySelector,
			BiFunction<TSource, TTarget, TResult> resultSelector, Comparator<TKey> comparer) {
		if (target == null)
			throw new NullPointerException("target cannot be null");
		if (sourceKeySelector == null)
			throw new NullPointerException("sourceKeySelector cannot be null");
		if (targetKeySelector == null)
			throw new NullPointerException("targetKeySelector cannot be null");
		if (resultSelector == null)
			throw new NullPointerException("resultSelector cannot be null");

		Map<TKey, List<TTarget>> destMap = new TreeMap<TKey, List<TTarget>>(comparer);
		for (TTarget dest : target) {
			TKey key = targetKeySelector.apply(dest);

			List<TTarget> list = destMap.get(key);
			if (list == null) {
				list = new ArrayList<TTarget>();
				destMap.put(key, list);
			}

			list.add(dest);
		}

		Map<TKey, List<TSource>> srcMap = new TreeMap<TKey, List<TSource>>(comparer);
		for (TSource src : source) {
			TKey key = sourceKeySelector.apply(src);

			List<TSource> list = srcMap.get(key);
			if (list == null) {
				list = new ArrayList<TSource>();
				srcMap.put(key, list);
			}

			list.add(src);
		}

		Set<TKey> set = new TreeSet<TKey>(comparer);
		set.addAll(srcMap.keySet());
		set.addAll(destMap.keySet());

		List<TResult> list = new ArrayList<TResult>();

		for (TKey key : set) {
			List<TSource> srcList = srcMap.get(key);
			List<TTarget> destList = destMap.get(key);

			if (srcList != null && destList != null) {
				for (TSource src : srcList) {
					for (TTarget dest : destList) {
						list.add(resultSelector.apply(src, dest));
					}
				}
			}
			if (srcList != null && destList == null) {
				for (TSource src : srcList) {
					list.add(resultSelector.apply(src, null));
				}
			}
			if (srcList == null && destList != null) {
				for (TTarget dest : destList) {
					list.add(resultSelector.apply(null, dest));
				}
			}
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> GroupCartesianProduct(Enumerable<TSource> source,
			Function<TSource, TKey> sourceKeySelector, Enumerable<TTarget> target,
			Function<TTarget, TKey> targetKeySelector,
			BiFunction<Enumerable<TSource>, Enumerable<TTarget>, TResult> resultSelector) {
		return GroupCartesianProduct(source, sourceKeySelector, target, targetKeySelector, resultSelector, null);
	}

	public static <TSource, TTarget, TKey, TResult> Enumerable<TResult> GroupCartesianProduct(Enumerable<TSource> source,
			Function<TSource, TKey> sourceKeySelector, Enumerable<TTarget> target,
			Function<TTarget, TKey> targetKeySelector,
			BiFunction<Enumerable<TSource>, Enumerable<TTarget>, TResult> resultSelector, Comparator<TKey> comparer) {
		if (target == null)
			throw new NullPointerException("target cannot be null");
		if (sourceKeySelector == null)
			throw new NullPointerException("sourceKeySelector cannot be null");
		if (targetKeySelector == null)
			throw new NullPointerException("targetKeySelector cannot be null");
		if (resultSelector == null)
			throw new NullPointerException("resultSelector cannot be null");

		Map<TKey, List<TTarget>> destMap = new TreeMap<TKey, List<TTarget>>(comparer);
		for (TTarget dest : target) {
			TKey key = targetKeySelector.apply(dest);

			List<TTarget> list = destMap.get(key);
			if (list == null) {
				list = new ArrayList<TTarget>();
				destMap.put(key, list);
			}

			list.add(dest);
		}

		Map<TKey, List<TSource>> srcMap = new TreeMap<TKey, List<TSource>>(comparer);
		for (TSource src : source) {
			TKey key = sourceKeySelector.apply(src);

			List<TSource> list = srcMap.get(key);
			if (list == null) {
				list = new ArrayList<TSource>();
				srcMap.put(key, list);
			}

			list.add(src);
		}

		Set<TKey> set = new TreeSet<TKey>(comparer);
		set.addAll(srcMap.keySet());
		set.addAll(destMap.keySet());

		List<TResult> list = new ArrayList<TResult>();

		for (TKey key : set) {
			List<TSource> srcList = srcMap.get(key);
			List<TTarget> destList = destMap.get(key);

			list.add(resultSelector.apply(new ListEnumerable<>(srcList), new ListEnumerable<>(destList)));
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource> TSource Last(Enumerable<TSource> source) {
		List<TSource> list = source.toList();

		if (list.isEmpty()) {
			throw new NoMatchException();
		}

		return list.get(list.size() - 1);
	}

	public static <TSource> TSource Last(Enumerable<TSource> source, Predicate<TSource> predicate) {
		if (predicate == null)
			throw new NullPointerException("predicate cannot be null");

		TSource result = null;
		Boolean found = false;
		for (TSource element : source) {
			if (predicate.test(element)) {
				result = element;
				found = true;
			}
		}
		if (found)
			return result;

		throw new NoMatchException();
	}

	public static <TSource> TSource LastOrDefault(Enumerable<TSource> source, TSource defaultVal) {
		List<TSource> list = source.toList();

		if (list.isEmpty()) {
			return defaultVal;
		}

		return list.get(list.size() - 1);
	}

	public static <TSource> TSource LastOrDefault(Enumerable<TSource> source, Predicate<TSource> predicate, TSource defaultVal) {
		if (predicate == null)
			throw new NullPointerException("predicate cannot be null");

		TSource result = defaultVal;
		for (TSource element : source) {
			if (predicate.test(element)) {
				result = element;
			}
		}
		return result;
	}

	public static <TSource>int MaxInt(Enumerable<TSource> source, Function<TSource, Integer> selector) {
		return Enumerable2.MaxInt(source.select(selector));
	}

	public static <TSource>Integer MaxOptionalInt(Enumerable<TSource> source, Function<TSource, OptionalInt> selector) {
		return Enumerable2.MaxOptionalInt(source.select(selector));
	}

	public static <TSource> long MaxLong(Enumerable<TSource> source, Function<TSource, Long> selector) {
		return Enumerable2.MaxLong(source.select(selector));
	}

	public static <TSource> Long MaxOptionalLong(Enumerable<TSource> source, Function<TSource, OptionalLong> selector) {
		return Enumerable2.MaxOptionalLong(source.select(selector));
	}

	public static <TSource>double MaxDouble(Enumerable<TSource> source, Function<TSource, Double> selector) {
		return Enumerable2.MaxDouble(source.select(selector));
	}

	public static <TSource>Double MaxOptionalDouble(Enumerable<TSource> source, Function<TSource, OptionalDouble> selector) {
		return Enumerable2.MaxOptionalDouble(source.select(selector));
	}

	public static <TSource> int MinInt(Enumerable<TSource> source, Function<TSource, Integer> selector) {
		return Enumerable2.MinInt(source.select(selector));
	}

	public static <TSource> Integer MinOptionalInt(Enumerable<TSource> source, Function<TSource, OptionalInt> selector) {
		return Enumerable2.MinOptionalInt(source.select(selector));
	}

	public static <TSource> long MinLong(Enumerable<TSource> source, Function<TSource, Long> selector) {
		return Enumerable2.MinLong(source.select(selector));
	}

	public static <TSource> Long MinOptionalLong(Enumerable<TSource> source, Function<TSource, OptionalLong> selector) {
		return Enumerable2.MinOptionalLong(source.select(selector));
	}

	public static <TSource> double MinDouble(Enumerable<TSource> source, Function<TSource, Double> selector) {
		return Enumerable2.MinDouble(source.select(selector));
	}

	public static <TSource> Double MinOptionalDouble(Enumerable<TSource> source, Function<TSource, OptionalDouble> selector) {
		return Enumerable2.MinOptionalDouble(source.select(selector));
	}

	public static <TSource,TKey extends Comparable<TKey>> Enumerable<TSource> OrderBy(Enumerable<TSource> source, Function<TSource, TKey> keySelector) {
		if (keySelector == null)
			throw new NullPointerException("keySelector cannot be null");

		List<TSource> list = source.toList();

		Collections.sort(list, new Comparator<TSource>() {
			@Override
			public int compare(TSource o1, TSource o2) {
				TKey key1 = keySelector.apply(o1);
				TKey key2 = keySelector.apply(o2);

				return key1.compareTo(key2);
			}
		});

		return new ListEnumerable<>(list);
	}

	public static <TSource, TKey> Enumerable<TSource> OrderBy(Enumerable<TSource> source, Function<TSource, TKey> keySelector, Comparator<TKey> comparer) {
		if (keySelector == null)
			throw new NullPointerException("keySelector cannot be null");
		if (comparer == null)
			throw new NullPointerException("comparer cannot be null");

		List<TSource> list = source.toList();

		Collections.sort(list, new Comparator<TSource>() {
			@Override
			public int compare(TSource o1, TSource o2) {
				TKey key1 = keySelector.apply(o1);
				TKey key2 = keySelector.apply(o2);

				return comparer.compare(key1, key2);
			}
		});

		return new ListEnumerable<>(list);
	}

	public static <TSource,TKey extends Comparable<TKey>> Enumerable<TSource> OrderByDescending(Enumerable<TSource> source, Function<TSource, TKey> keySelector) {
		if (keySelector == null)
			throw new NullPointerException("keySelector cannot be null");

		List<TSource> list = source.toList();

		Collections.sort(list, new Comparator<TSource>() {
			@Override
			public int compare(TSource o1, TSource o2) {
				TKey key1 = keySelector.apply(o1);
				TKey key2 = keySelector.apply(o2);

				return key2.compareTo(key1);
			}
		});

		return new ListEnumerable<>(list);
	}

	public static <TSource, TKey> Enumerable<TSource> OrderByDescending(Enumerable<TSource> source, Function<TSource, TKey> keySelector,
			Comparator<TKey> comparer) {
		if (keySelector == null)
			throw new NullPointerException("keySelector cannot be null");
		if (comparer == null)
			throw new NullPointerException("comparer cannot be null");

		List<TSource> list = source.toList();

		Collections.sort(list, new Comparator<TSource>() {
			@Override
			public int compare(TSource o1, TSource o2) {
				TKey key1 = keySelector.apply(o1);
				TKey key2 = keySelector.apply(o2);

				return comparer.compare(key2, key1);
			}
		});

		return new ListEnumerable<>(list);
	}

	public static <TSource>Enumerable<TSource> Append(Enumerable<TSource> source, TSource element) {
		List<TSource> list = source.toList();
		list.add(element);

		return new ListEnumerable<>(list);
	}

	public static <TSource> Enumerable<TSource> Prepend(Enumerable<TSource> source, TSource element) {
		List<TSource> list = new ArrayList<TSource>();
		list.add(element);
		list.addAll(source.toList());

		return new ListEnumerable<>(list);
	}

	public static <TSource> Enumerable<TSource> Range(Enumerable<TSource> source, int start, int count) {
		long max = ((long) start) + count - 1;
		if (count < 0 || max > Integer.MAX_VALUE)
			throw new OutOfRangeException("start=" + start + ",count=" + count);

		List<TSource> list = source.toList();
		
		List<TSource> list1 = new ArrayList<TSource>();
		Collections.copy(list1, list.subList(start, start + count));

		return new ListEnumerable<>(list1);

	}

	public static <TSource> Enumerable<TSource> Reverse(Enumerable<TSource> source) {
		List<TSource> list = source.toList();
		Collections.reverse(list);

		return new ListEnumerable<>(list);
	}

	public static <TSource, TResult> Enumerable<TResult> SelectMany(Enumerable<TSource> source, Function<TSource, Enumerable<TResult>> selector) {
		if (selector == null)
			throw new NullPointerException("selector cannot be null");

		List<TResult> list = new ArrayList<TResult>();
		for (TSource element : source) {
			Enumerable<TResult> et = selector.apply(element);

			for (TResult t : et) {
				list.add(t);
			}
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource, TResult> Enumerable<TResult> SelectMany(Enumerable<TSource> source, BiFunction<TSource, Integer, Enumerable<TResult>> selector) {
		if (selector == null)
			throw new NullPointerException("selector cannot be null");

		List<TResult> list = new ArrayList<TResult>();
		int index = -1;
		for (TSource element : source) {
			index++;

			Enumerable<TResult> et = selector.apply(element, index);

			for (TResult t : et) {
				list.add(t);
			}
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource, TCollection, TResult> Enumerable<TResult> SelectMany(Enumerable<TSource> source,
			BiFunction<TSource, Integer, Enumerable<TCollection>> collectionSelector,
			BiFunction<TSource, TCollection, TResult> resultSelector) {
		if (collectionSelector == null)
			throw new NullPointerException("selector cannot be null");
		if (resultSelector == null)
			throw new NullPointerException("selector cannot be null");

		List<TResult> list = new ArrayList<TResult>();
		int index = -1;
		for (TSource element : source) {
			index++;
			Enumerable<TCollection> et = collectionSelector.apply(element, index);

			for (TCollection subElement : et) {

				TResult t = resultSelector.apply(element, subElement);

				list.add(t);
			}
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource, TCollection, TResult> Enumerable<TResult> SelectMany(Enumerable<TSource> source,
			Function<TSource, Enumerable<TCollection>> collectionSelector,
			BiFunction<TSource, TCollection, TResult> resultSelector) {
		if (collectionSelector == null)
			throw new NullPointerException("selector cannot be null");
		if (resultSelector == null)
			throw new NullPointerException("selector cannot be null");

		List<TResult> list = new ArrayList<TResult>();
		for (TSource element : source) {
			Enumerable<TCollection> et = collectionSelector.apply(element);

			for (TCollection subElement : et) {

				TResult t = resultSelector.apply(element, subElement);

				list.add(t);
			}
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource ,TKey extends Comparable<TKey>> Enumerable<TSource> ThenBy(Enumerable<TSource> source, Function<TSource, TKey> keySelector) {
		if (keySelector == null)
			throw new NullPointerException("keySelector cannot be null");

		List<TSource> list = source.toList();

		Collections.sort(list, new Comparator<TSource>() {
			@Override
			public int compare(TSource o1, TSource o2) {
				TKey key1 = keySelector.apply(o1);
				TKey key2 = keySelector.apply(o2);

				return key1.compareTo(key2);
			}
		});

		return new ListEnumerable<>(list);
	}

	public static <TSource, TKey> Enumerable<TSource> ThenBy(Enumerable<TSource> source, Function<TSource, TKey> keySelector, Comparator<TKey> comparer) {
		if (keySelector == null)
			throw new NullPointerException("keySelector cannot be null");
		if (comparer == null)
			throw new NullPointerException("comparer cannot be null");

		List<TSource> list = source.toList();

		Collections.sort(list, new Comparator<TSource>() {
			@Override
			public int compare(TSource o1, TSource o2) {
				TKey key1 = keySelector.apply(o1);
				TKey key2 = keySelector.apply(o2);

				return comparer.compare(key1, key2);
			}
		});

		return new ListEnumerable<>(list);
	}

	public static <TSource, TKey extends Comparable<TKey>> Enumerable<TSource> ThenByDescending(Enumerable<TSource> source, Function<TSource, TKey> keySelector) {
		if (keySelector == null)
			throw new NullPointerException("keySelector cannot be null");

		List<TSource> list = source.toList();

		Collections.sort(list, new Comparator<TSource>() {
			@Override
			public int compare(TSource o1, TSource o2) {
				TKey key1 = keySelector.apply(o1);
				TKey key2 = keySelector.apply(o2);

				return key2.compareTo(key1);
			}
		});

		return new ListEnumerable<>(list);
	}

	public static <TSource, TKey> Enumerable<TSource> ThenByDescending(Enumerable<TSource> source, Function<TSource, TKey> keySelector,
			Comparator<TKey> comparer) {
		if (keySelector == null)
			throw new NullPointerException("keySelector cannot be null");
		if (comparer == null)
			throw new NullPointerException("comparer cannot be null");

		List<TSource> list = source.toList();

		Collections.sort(list, new Comparator<TSource>() {
			@Override
			public int compare(TSource o1, TSource o2) {
				TKey key1 = keySelector.apply(o1);
				TKey key2 = keySelector.apply(o2);

				return comparer.compare(key2, key1);
			}
		});

		return new ListEnumerable<>(list);
	}

	public static <TSource> Set<TSource> ToSet(Enumerable<TSource> source, Comparator<TSource> comparer) {
		Set<TSource> set = new TreeSet<TSource>(comparer);

		for (TSource s : source) {
			set.add(s);
		}

		return set;
	}

	public static <TSource> Set<TSource> ToSet(Enumerable<TSource> source) {
		return ToSet(source,null);
	}

	public static <TSource> Enumerable<TSource> Union(Enumerable<TSource> source, Enumerable<TSource> other, Comparator<TSource> comparer) {
		if (other == null)
			throw new NullPointerException("other cannot be null");
		Set<TSource> set = new TreeSet<TSource>(comparer);

		for (TSource e : source)
			set.add(e);

		for (TSource e : other)
			set.add(e);

		return new SetEnumerable<>(set);
	}

	public static <TSource> Enumerable<TSource> Union(Enumerable<TSource> source, Enumerable<TSource> other) {
		return Union(source, other, null);
	}

	public static <TSource> Enumerable<TSource> Where(Enumerable<TSource> source, Predicate<TSource> predicate) {
		if (predicate == null)
			throw new NullPointerException("predicate cannot be null");
		List<TSource> list = new ArrayList<TSource>();

		for (TSource element : source) {
			if (predicate.test(element))
				list.add(element);
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource> Enumerable<TSource> Where(Enumerable<TSource> source, BiFunction<TSource, Integer, Boolean> predicate) {
		if (predicate == null)
			throw new NullPointerException("predicate cannot be null");
		List<TSource> list = new ArrayList<TSource>();

		int index = -1;
		for (TSource element : source) {
			index++;
			if (predicate.apply(element, index))
				list.add(element);
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource, TTarget, TResult> Enumerable<TResult> Zip(Enumerable<TSource> source, Enumerable<TTarget> target,
			BiFunction<TSource, TTarget, TResult> resultSelector) {
		if (target == null)
			throw new NullPointerException("target cannot be null");
		if (resultSelector == null)
			throw new NullPointerException("resultSelector cannot be null");
		List<TResult> list = new ArrayList<TResult>();

		Iterator<TSource> e1 = source.iterator();
		Iterator<TTarget> e2 = target.iterator();

		while (e1.hasNext() && e2.hasNext())
			list.add(resultSelector.apply(e1.next(), e2.next()));

		return new ListEnumerable<>(list);
	}

	public static <TSource, TKey> Map<TKey, TSource> ToMap(Enumerable<TSource> source, Function<TSource, TKey> keySelector) {
		return ToMap(source, keySelector, null, null);
	}

	public static <TSource, TKey> Map<TKey, TSource> ToMap(Enumerable<TSource> source, Function<TSource, TKey> keySelector, Comparator<TKey> comparer) {
		return ToMap(source, keySelector, null, comparer);
	}

	public static <TSource, TKey, TElement> Map<TKey, TElement> ToMap(Enumerable<TSource> source, Function<TSource, TKey> keySelector,
			Function<TSource, TElement> elementSelector) {
		return ToMap(source, keySelector, elementSelector, null);
	}

	@SuppressWarnings("unchecked")
	public static <TSource, TKey, TElement> Map<TKey, TElement> ToMap(Enumerable<TSource> source, Function<TSource, TKey> keySelector,
			Function<TSource, TElement> elementSelector, Comparator<TKey> comparer) {
		if (keySelector == null)
			throw new NullPointerException("keySelector cannot be null");

		Map<TKey, TElement> d = new TreeMap<TKey, TElement>(comparer);
		for (TSource element : source) {
			d.put(keySelector.apply(element),
					elementSelector == null ? (TElement) element : elementSelector.apply(element));
		}

		return d;
	}

	public static <TSource> int SumInt(Enumerable<TSource> source, Function<TSource, Integer> selector) {
		return Enumerable2.SumInt(source.select(selector));
	}

	public static <TSource> Integer SumOptionalInt(Enumerable<TSource> source, Function<TSource, OptionalInt> selector) {
		return Enumerable2.SumOptionalInt(source.select(selector));
	}

	public static <TSource> long SumLong(Enumerable<TSource> source, Function<TSource, Long> selector) {
		return Enumerable2.SumLong(source.select(selector));
	}

	public static <TSource> Long SumOptionalLong(Enumerable<TSource> source, Function<TSource, OptionalLong> selector) {
		return Enumerable2.SumOptionalLong(source.select(selector));
	}

	public static <TSource> double SumDouble(Enumerable<TSource> source, Function<TSource, Double> selector) {
		return Enumerable2.SumDouble(source.select(selector));
	}

	public static <TSource> Double SumOptionalDouble(Enumerable<TSource> source, Function<TSource, OptionalDouble> selector) {
		return Enumerable2.SumOptionalDouble(source.select(selector));
	}


	public static <TSource> Enumerable<TSource> Skip(Enumerable<TSource> source, int count) {
		List<TSource> list = new ArrayList<TSource>();

		Iterator<TSource> e = source.iterator();
		while (count > 0 && e.hasNext())
			count--;

		if (count <= 0) {
			while (e.hasNext())
				list.add(e.next());
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource> Enumerable<TSource> SkipWhile(Enumerable<TSource> source, Predicate<TSource> predicate) {
		if (predicate == null)
			throw new NullPointerException("predicate cannot be null");
		List<TSource> list = new ArrayList<TSource>();

		Boolean yielding = false;
		for (TSource element : source) {
			if (!yielding && !predicate.test(element))
				yielding = true;

			if (yielding)
				list.add(element);
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource> Enumerable<TSource> SkipWhile(Enumerable<TSource> source, BiFunction<TSource, Integer, Boolean> predicate) {
		if (predicate == null)
			throw new NullPointerException("predicate cannot be null");
		List<TSource> list = new ArrayList<TSource>();

		int index = -1;
		Boolean yielding = false;
		for (TSource element : source) {
			index++;
			if (!yielding && !predicate.apply(element, index))
				yielding = true;
			if (yielding)
				list.add(element);
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource> Enumerable<TSource> Take(Enumerable<TSource> source, int count) {
		List<TSource> list = new ArrayList<TSource>();
		if (count > 0) {
			for (TSource element : source) {
				list.add(element);
				if (--count == 0)
					break;
			}
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource> Enumerable<TSource> TakeWhile(Enumerable<TSource> source, Predicate<TSource> predicate) {
		if (predicate == null)
			throw new NullPointerException("predicate cannot be null");
		List<TSource> list = new ArrayList<TSource>();

		for (TSource element : source) {
			if (!predicate.test(element))
				break;
			list.add(element);
		}

		return new ListEnumerable<>(list);
	}

	public static <TSource> Enumerable<TSource> TakeWhile(Enumerable<TSource> source, BiFunction<TSource, Integer, Boolean> predicate) {
		if (predicate == null)
			throw new NullPointerException("predicate cannot be null");
		List<TSource> list = new ArrayList<TSource>();

		int index = -1;
		for (TSource element : source) {
			index++;
			if (!predicate.apply(element, index))
				break;
			list.add(element);
		}

		return new ListEnumerable<>(list);
	}

	public static <TResult> Enumerable<TResult> Repeat(TResult element, int count) {
		if (count < 0)
			throw new OutOfRangeException("count=" + count);

		List<TResult> list = new ArrayList<TResult>();

		for (int i = 0; i < count; i++)
			list.add(element);

		return new ListEnumerable<>(list);
	}

	public static Enumerable<Integer> RangeInt(int start, int count) {
		long max = ((long) start) + count - 1;
		if (count < 0 || max > Integer.MAX_VALUE)
			throw new OutOfRangeException("start=" + start + ",count=" + count);

		List<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i < count; i++)
			list.add(start + i);

		return new ListEnumerable<>(list);
	}

	public static <TResult> Enumerable<TResult> Empty() {
		return new EmptyEnumerable<TResult>();
	}

	/**
	 * 平均数
	 * 
	 * @param source
	 *            源
	 * @return
	 */
	public static Double AverageInt(Enumerable<Integer> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");
		long sum = 0;
		long count = 0;

		for (int v : source) {
			sum += v;
			count++;
		}

		if (count > 0)
			return (double) sum / count;
		throw new NoElementsException();
	}

	public static Double AverageOptionalInt(Enumerable<OptionalInt> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");
		long sum = 0;
		long count = 0;

		for (OptionalInt v : source) {
			if (v.isPresent()) {
				sum += v.getAsInt();
				count++;
			}
		}

		if (count > 0)
			return (double) sum / count;
		return null;
	}

	public static Double AverageLong(Enumerable<Long> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");
		long sum = 0;
		long count = 0;

		for (long v : source) {
			sum += v;
			count++;
		}

		if (count > 0)
			return (double) sum / count;
		throw new NoElementsException();
	}

	public static Double AverageOptionalLong(Enumerable<OptionalLong> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");
		long sum = 0;
		long count = 0;

		for (OptionalLong v : source) {
			if (v.isPresent()) {
				sum += v.getAsLong();
				count++;
			}
		}

		if (count > 0)
			return (double) sum / count;
		return null;
	}

	public static double AverageDouble(Enumerable<Double> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");
		double sum = 0;
		long count = 0;

		for (Double v : source) {
			sum += v;
			count++;
		}

		if (count > 0)
			return sum / count;
		throw new NoElementsException();
	}

	public static Double AverageOptionalDouble(Enumerable<OptionalDouble> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");
		double sum = 0;
		long count = 0;

		for (OptionalDouble v : source) {
			if (v.isPresent()) {
				sum += v.getAsDouble();
				count++;
			}
		}

		if (count > 0)
			return sum / count;
		return null;
	}

	public static int MaxInt(Enumerable<Integer> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");

		int value = 0;
		Boolean hasValue = false;
		for (int x : source) {
			if (hasValue) {
				if (x > value)
					value = x;
			} else {
				value = x;
				hasValue = true;
			}
		}
		if (hasValue)
			return value;
		throw new NoElementsException();
	}

	public static Integer MaxOptionalInt(Enumerable<OptionalInt> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");

		Integer value = null;
		for (OptionalInt x : source) {
			if (x == null)
				continue;
			if (x.isPresent()) {
				if (value == null || x.getAsInt() > value)
					value = x.getAsInt();
			}
		}

		return value;
	}

	public static long MaxLong(Enumerable<Long> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");

		long value = 0;
		Boolean hasValue = false;
		for (long x : source) {
			if (hasValue) {
				if (x > value)
					value = x;
			} else {
				value = x;
				hasValue = true;
			}
		}
		if (hasValue)
			return value;
		throw new NoElementsException();
	}

	public static Long MaxOptionalLong(Enumerable<OptionalLong> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");

		Long value = null;
		for (OptionalLong x : source) {
			if (x == null)
				continue;
			if (x.isPresent()) {
				if (value == null || x.getAsLong() > value)
					value = x.getAsLong();
			}

		}
		return value;
	}

	public static double MaxDouble(Enumerable<Double> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");
		double value = 0;
		Boolean hasValue = false;
		for (double x : source) {
			if (hasValue) {
				if (x > value || Double.isNaN(value))
					value = x;
			} else {
				value = x;
				hasValue = true;
			}
		}
		if (hasValue)
			return value;
		throw new NoElementsException();
	}

	public static Double MaxOptionalDouble(Enumerable<OptionalDouble> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");

		Double value = null;
		for (OptionalDouble x : source) {
			if (x == null)
				continue;
			if (x.isPresent()) {
				if (value == null || x.getAsDouble() > value || Double.isNaN(value))
					value = x.getAsDouble();
			}

		}
		return value;
	}

	public static int MinInt(Enumerable<Integer> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");

		int value = 0;
		Boolean hasValue = false;
		for (int x : source) {
			if (hasValue) {
				if (x < value)
					value = x;
			} else {
				value = x;
				hasValue = true;
			}
		}
		if (hasValue)
			return value;
		throw new NoElementsException();
	}

	public static Integer MinOptionalInt(Enumerable<OptionalInt> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");

		Integer value = null;
		for (OptionalInt x : source) {
			if (x == null)
				continue;
			if (x.isPresent()) {
				if (value == null || x.getAsInt() < value)
					value = x.getAsInt();
			}

		}
		return value;
	}

	public static long MinLong(Enumerable<Long> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");

		long value = 0;
		Boolean hasValue = false;
		for (long x : source) {
			if (hasValue) {
				if (x < value)
					value = x;
			} else {
				value = x;
				hasValue = true;
			}
		}
		if (hasValue)
			return value;
		throw new NoElementsException();
	}

	public static Long MinOptionalLong(Enumerable<OptionalLong> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");

		Long value = null;
		for (OptionalLong x : source) {
			if (x == null)
				continue;
			if (x.isPresent()) {
				if (value == null || x.getAsLong() < value)
					value = x.getAsLong();
			}
		}
		return value;
	}

	public static double MinDouble(Enumerable<Double> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");
		double value = 0;
		Boolean hasValue = false;
		for (double x : source) {
			if (hasValue) {
				if (x < value || Double.isNaN(x))
					value = x;
			} else {
				value = x;
				hasValue = true;
			}
		}
		if (hasValue)
			return value;
		throw new NoElementsException();
	}

	public static Double MinOptionalDouble(Enumerable<OptionalDouble> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");
		Double value = null;
		for (OptionalDouble x : source) {
			if (x == null)
				continue;
			if (x.isPresent()) {
				if (value == null || x.getAsDouble() < value || Double.isNaN(x.getAsDouble()))
					value = x.getAsDouble();
			}

		}
		return value;
	}

	public static <TSource> Function<TSource, Boolean> CombinePredicates(Predicate<TSource> predicate1,
			Predicate<TSource> predicate2) {
		return x -> predicate1.test(x) && predicate2.test(x);
	}

	public static <TSource, TMiddle, TResult> Function<TSource, TResult> CombineSelectors(
			Function<TSource, TMiddle> selector1, Function<TMiddle, TResult> selector2) {
		return x -> selector2.apply(selector1.apply(x));
	}
	
	public static int SumInt(Enumerable<Integer> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");

		int sum = 0;

		for (int v : source)
			sum += v;

		return sum;
	}

	public static Integer SumOptionalInt(Enumerable<OptionalInt> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");

		int sum = 0;

		for (OptionalInt v : source) {
			if (v != null)
				sum += v.orElse(0);
		}

		return sum;
	}

	public static long SumLong(Enumerable<Long> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");

		long sum = 0;

		for (long v : source)
			sum += v;

		return sum;
	}

	public static Long SumOptionalLong(Enumerable<OptionalLong> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");

		long sum = 0;

		for (OptionalLong v : source) {
			if (v != null)
				sum += v.orElse(0L);
		}

		return sum;
	}

	public static double SumDouble(Enumerable<Double> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");

		double sum = 0;
		for (double v : source)
			sum += v;
		return sum;
	}

	public static Double SumOptionalDouble(Enumerable<OptionalDouble> source) {
		if (source == null)
			throw new NullPointerException("source cannot be null");

		double sum = 0;
		for (OptionalDouble v : source) {
			if (v != null)
				sum += v.orElse(0D);
		}
		return sum;
	}

	public static <TSource extends Comparable<TSource>> Boolean SequenceEqual(Enumerable<TSource> first,
			Enumerable<TSource> second) {
		return SequenceEqual(first, second, null);
	}

	public static <TSource extends Comparable<TSource>> Boolean SequenceEqual(Enumerable<TSource> first,
			Enumerable<TSource> second, Comparator<TSource> comparer) {
		if (first == null)
			throw new NullPointerException("first cannot be null");
		if (second == null)
			throw new NullPointerException("second cannot be null");

		if (comparer == null)
			comparer = Comparator.nullsFirst(new EqualityComparer<TSource>());

		Iterator<TSource> e1 = first.iterator();
		Iterator<TSource> e2 = second.iterator();

		while (e1.hasNext()) {
			if (!(e2.hasNext() && comparer.compare(e1.next(), e2.next()) == 0))
				return false;
		}
		if (e2.hasNext())
			return false;

		return true;
	}
}

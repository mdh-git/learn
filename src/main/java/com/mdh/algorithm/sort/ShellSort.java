package com.mdh.algorithm.sort;

/**
 * 希尔排序，也被称为递减增量排序，是简单插入排序的一种改进版本。
 *
 * 在插入排序中，如果待排序列中的某个元素，距离有序数列中待插入位置非常远，就需要比较很多次才可以到达插入位置，这是因为待插入元素局部非常无序，
 * 比如说[2, 3, 4, 5, 6, 7, 8, 1, ...]，我们要插入 1，就必须将 1 和前面的 2-8 每个值都比较一下，就是因为 1
 * 附近非常无序，想象一下，如果待插入元素附近比较有序，那么在进行插入排序的时候就只需要比较非常少的几次就可以插入到正确位置。
 *
 * 希尔排序就是先把整个序列排得相对比较有序，再进行插入排序的时候，需要比较的次数就会变得很少。
 * 插入排序的增量(间隔)为 1，希尔排序相当于将这个间隔从最大为数组长度的一半一直降到1，这一点在程序中体现的很清楚。
 * 当间隔很大时，比较的次数也会很少，在进行了几次大间隔的插入排序后，序列已经部分有序，这样再进行小间隔的插入排序也自然会比较很少的次数。
 * 希尔排序就是将处在相同间隔的元素提取出来单独进行插入排序，然后逐步将间隔减小到 1 的过程。
 *
 * @Author: madonghao
 * @Date: 2021/5/21 10:07 上午
 */
public class ShellSort {}

# HashSet
~~~
在多线程环境中,HashSet也是线程不安全的

java.util.ConcurrentModificationException

HashSet 底层数据结构是HashMap
/**
* Constructs a new, empty set; the backing <tt>HashMap</tt> instance has
* default initial capacity (16) and load factor (0.75).
*/
public HashSet() {
    map = new HashMap<>();
}

private static final Object PRESENT = new Object();
public boolean add(E e) {
    return map.put(e, PRESENT)==null;
}
~~~

## CopyOnWriteArraySet
~~~
底层使用的是CopyOnWriteArrayList

    private final CopyOnWriteArrayList<E> al;

    /**
     * Creates an empty set.
     */
    public CopyOnWriteArraySet() {
        al = new CopyOnWriteArrayList<E>();
    }
~~~
package com.mdh.algorithm.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * @author madonghao
 * @create 2020-11-09 15:01
 **/
public class LRUCacheDemoTwo {

    /**
     * map负责查找，构建一个虚拟的双向链表，它里面安装的就是一个个Node节点，作为数据载体。
     */


    /**
     * 1.构造一个node节点作为数据载体
     */
    class Node<K, V>
    {
        K key;
        V value;
        Node<K,V> prev;
        Node<K,V> next;

        public Node(){
            this.prev = this.next = null;
        }

        public Node(K key, V value)
        {
            this.key = key;
            this.value = value;
            this.prev = this.next = null;
        }

    }

    /**
     * 2 构建一个虚拟的双向链表,,里面安放的就是我们的Node
     */
    class DoubleLinkedList<K, V>
    {
        Node<K, V> head;
        Node<K, V> tail;

        public DoubleLinkedList(){
            head = new Node<>();
            tail = new Node<>();
            head.next = tail;
            tail.prev = head;
        }

        /**
         * 3. 添加到头
         */
        public void addHead(Node<K,V> node)
        {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        /**
         * 4.删除节点
         */
        public void removeNode(Node<K, V> node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            node.prev = null;
            node.next = null;
        }

        /**
         * 5.获得最后一个节点
         * @return Node
         */
        public Node getLast() {
            return tail.prev;
        }

        /**
         * 6.获得第一个节点
         * @return Node
         */
        public Node getFast() {
            return head.next;
        }

        public Node getHead() {
            return head;
        }

    }

    private int cacheSize;
    Map<Integer,Node<Integer,Integer>> map;
    DoubleLinkedList<Integer,Integer> doubleLinkedList;

    public LRUCacheDemoTwo(int cacheSize)
    {
        //坑位
        this.cacheSize = cacheSize;
        //查找
        map = new HashMap<>();
        doubleLinkedList = new DoubleLinkedList<>();
    }

    public int get(int key){
        if (!map.containsKey(key)){
            return -1;
        }

        Node<Integer, Integer> node = map.get(key);
        doubleLinkedList.removeNode(node);
        doubleLinkedList.addHead(node);

        return node.value;
    }

    public void put(int key, int value)
    {
        //update
        if (map.containsKey(key)){
            Node<Integer, Integer> node = map.get(key);
            node.value = value;
            map.put(key, node);

            doubleLinkedList.removeNode(node);
            doubleLinkedList.addHead(node);
        }else {
            //坑位满了
            if (map.size() == cacheSize)
            {
                Node<Integer,Integer> lastNode = doubleLinkedList.getLast();
                map.remove(lastNode.key);
                doubleLinkedList.removeNode(lastNode);
            }

            //新增一个
            Node<Integer, Integer> newNode = new Node<>(key, value);
            map.put(key,newNode);
            doubleLinkedList.addHead(newNode);

        }
    }



    public static void main(String[] args) {

        LRUCacheDemoTwo lruCacheDemo = new LRUCacheDemoTwo(3);

        lruCacheDemo.put(1,1);
        lruCacheDemo.put(2,2);
        lruCacheDemo.put(3,3);
        System.out.println(lruCacheDemo.map.keySet());
        System.out.println(lruCacheDemo.doubleLinkedList.getLast().key);
        System.out.println(lruCacheDemo.doubleLinkedList.getFast().key);
        System.out.println(lruCacheDemo.doubleLinkedList.getHead().key);
        System.out.println("---1---");

        lruCacheDemo.put(4,1);
        System.out.println(lruCacheDemo.map.keySet());
        System.out.println(lruCacheDemo.doubleLinkedList.getLast().key);
        System.out.println(lruCacheDemo.doubleLinkedList.getFast().key);
        System.out.println("---2---");

        lruCacheDemo.put(3,1);
        System.out.println(lruCacheDemo.map.keySet());
        System.out.println(lruCacheDemo.doubleLinkedList.getLast().key);
        System.out.println(lruCacheDemo.doubleLinkedList.getFast().key);
        System.out.println("---3---");

        lruCacheDemo.put(3,1);
        System.out.println(lruCacheDemo.map.keySet());
        System.out.println(lruCacheDemo.doubleLinkedList.getLast().key);
        System.out.println(lruCacheDemo.doubleLinkedList.getFast().key);
        System.out.println("---4---");

        lruCacheDemo.put(3,1);
        System.out.println(lruCacheDemo.map.keySet());
        System.out.println(lruCacheDemo.doubleLinkedList.getLast().key);
        System.out.println(lruCacheDemo.doubleLinkedList.getFast().key);
        System.out.println("---5---");

        lruCacheDemo.put(5,1);
        System.out.println(lruCacheDemo.map.keySet());
        System.out.println(lruCacheDemo.doubleLinkedList.getLast().key);
        System.out.println(lruCacheDemo.doubleLinkedList.getFast().key);
        System.out.println("---6---");

        lruCacheDemo.put(6,2);
        System.out.println(lruCacheDemo.map.keySet());
        System.out.println(lruCacheDemo.doubleLinkedList.getLast().key);
        System.out.println(lruCacheDemo.doubleLinkedList.getFast().key);
    }

}

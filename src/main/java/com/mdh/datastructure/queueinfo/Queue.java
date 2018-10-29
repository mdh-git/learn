package com.mdh.datastructure.queueinfo;

/**
 *
 * 循环队列（也成为缓冲环）
 * 在存储上是线形的，但是在逻辑上它是一个首尾衔接的环形
 * @author madonghao
 * @date 2018/10/29
 */
public class Queue {

    private int[] queArray;
    private int maxSize;
    /** 存储队头元素的下标 */
    private int front;
    /** 存储队尾元素的下标 */
    private int rear;
    /** 队列长度 */
    private int length;

    /**
     * 构造方法，初始化队列
     * @param maxSize 列的最大个数
     */
    public Queue(int maxSize) {
        this.maxSize = maxSize;
        queArray = new int[maxSize];
        front = 0;
        rear = -1;
        length = 0;
    }

    /**
     * 插入操作
     * @param elem 待插入的元素
     */
    public void insert(int elem) throws Exception {
        if(isFull()){
            throw new Exception("队列已满，不能进行插入操作！");
        }
        //如果队尾指针已到达数组的末端，插入到数组的第一个位置
        if(rear == maxSize -1) {
            rear = -1;
        }
        queArray[++rear] = elem;
        length++;
    }

    /**
     * 移除
     * @return
     */
    public int remove() throws Exception {
        if(isEmpty()){
            throw new Exception("队列为空，不能进行移除操作！");
        }
        int elem = queArray[front++];
        if(front == maxSize){
            front = 0;
        }
        length--;
        return elem;
    }

    /**
     * 获取队列长度
     * @return
     */
    public int size() {
        return length;
    }

    /**
     * 判断队列是否未空
     * @return
     */
    public boolean isEmpty() {
        return (length == 0);
    }

    /**
     * 判断队列是否存满
     * @return
     */
    public boolean isFull() {
        return (length == maxSize);
    }

    public static void main(String[] args) throws Exception {

        Queue queue = new Queue(10);
        queue.insert(11);
        queue.insert(22);
        queue.insert(33);
        queue.insert(44);
        queue.insert(55);
        queue.insert(66);
        System.out.println("移除:" + queue.remove());
        queue.insert(77);
        queue.insert(88);
        queue.insert(99);
        System.out.println("移除:" + queue.remove());
        queue.insert(111);
        queue.insert(222);
        System.out.println("移除:" + queue.remove());
        queue.insert(333);
        queue.insert(444);
        System.out.println("移除:" + queue.remove());
        queue.insert(555);
        System.out.println("移除:" + queue.remove());
        System.out.println("长度:" + queue.size());
        System.out.println("队列头:" + queue.front + ",值:" + queue.queArray[queue.front]);
        System.out.println("队列尾:" + queue.rear + ",值:" + queue.queArray[queue.rear]);
    }

}

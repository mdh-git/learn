package com.mdh.datastructure.queueinfo;

/**
 * 优先级队列
 * 像普通队列一样，优先级队列有一个队头和一个队尾，并且也是从队头移除数据，从队尾插入数据，
 * 不同的是，在优先级队列中，数据项按关键字的值排序，数据项插入的时候会按照顺序插入到合适的位置
 * 除了可以快速访问优先级最高的数据项，优先级队列还应该可以实现相当快的插入，
 * 因此，优先级队列通常使用一种称为堆的数据结构来实现。
 * @author madonghao
 * @date 2018/10/29
 */
public class PriorityQueue {

    private int [] queArray;
    private int maxSize;
    /** 队列长度 */
    private int length;
    /** 基准点 */
    private int referencePoint;

    /**
     * 构造方法，初始化队列
     * @param maxSize 队列的最大长度
     * @param referencePoint 基准点
     */
    public PriorityQueue(int maxSize, int referencePoint){
        this.maxSize = maxSize;
        this.referencePoint = referencePoint;
        queArray = new int [maxSize];
        length = 0;
    }

    /**
     * 插入
     * @param elem
     * @throws Exception
     */
    public void insert(int elem) throws Exception {
        if(isFull()){
            throw new Exception("队列已满，不能进行插入操作！");
        }

        //如果队列为空，插入到该数组的第一个位置
        if(length == 0){
            queArray[length++] = elem;
        } else {
            int i;
            for(i = length; i > 0; i--){
                //待插入元素的距离
                int dis = Math.abs(elem - referencePoint);
                //当前元素的距离
                int curDis = Math.abs(queArray[i - 1] - referencePoint);

                //将比插入元素优先级高的元素后移一位
                if(dis >= curDis){
                    queArray[i] = queArray[i - 1];
                } else {
                    break;
                }
            }
            queArray[i] = elem;
            length++;
        }
    }

    /**
     * 移除
     * @return
     * @throws Exception
     */
    public int remove() throws Exception {
        if(isEmpty()){
            throw new Exception("队列为空，不能进行移除操作！");
        }
        int elem = queArray[--length];
        return elem;
    }

    /**
     * 查看队头元素
     * @return
     * @throws Exception
     */
    public int peek() throws Exception {
        if (isEmpty()){
            throw new Exception("队列内没有元素！");
        }
        return queArray[length - 1];
    }

    /**
     * 返回队列长度
     * @return
     */
    public int size(){
        return length;
    }

    /**
     * 判空
     * @return
     */
    public boolean isEmpty(){
        return (length == 0);
    }

    /**
     * 判满
     * @return
     */
    public boolean isFull(){
        return (length == maxSize);
    }

    public static void main(String[] args) throws Exception {
        PriorityQueue priorityQueue = new PriorityQueue(10,15);
        System.out.println(priorityQueue.isEmpty());
        priorityQueue.insert(18);
        priorityQueue.insert(16);
        priorityQueue.insert(17);
        priorityQueue.insert(15);
        priorityQueue.insert(19);
        priorityQueue.insert(20);
        priorityQueue.insert(21);
        priorityQueue.insert(22);
        System.out.println("长度:" + priorityQueue.size());
        System.out.println("删除:" + priorityQueue.remove());
        System.out.println(priorityQueue.isFull());
        priorityQueue.insert(23);
        priorityQueue.insert(24);
        priorityQueue.insert(25);
        System.out.println(priorityQueue.peek());
    }
}

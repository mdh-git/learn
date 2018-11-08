package com.mdh.datastructure.linkinfo;

/**
 * 单链表
 * 链表的封装类
 * @author madonghao
 * @date 2018/10/29
 */
public class LinkList {

    /** 指向链表中的第一个链结点 */
    private Link first;

    public LinkList() {
        first = null;
    }

    /**
     * 插入到链表的前端
     * @param link
     */
    public void inserFirst(Link link){
        link.next = first;
        first = link;
    }

    public Link deleteFirst() throws Exception {
        if(isEmpty()){
            throw new Exception("链表为空！不能进行删除操作");
        }
        Link temp = first;
        first = first.next;
        return temp;
    }

    /**
     * 打印出所有的链表元素
     */
    public void displayList(){
        Link cur = first;
        while (cur != null){
            //循环打印每个链结点
            cur.displayLink();
            cur = cur.next;
        }
    }

    /**
     * 删除属性为指定值的链结点
     * @param key
     * @return
     */
    public Link delete(int key){
        Link link = null;
        Link cur = first;
        Link next = first.next;
        Link previous = null;
        while (cur != null){
            if(cur.age == key){
                //找到了要删除的链结点
                link = cur;

                //如果当前链结点的前驱为null，证明当其为链表的第一个链结点，删除该链结点后需要对first属性重新赋值
                if(previous == null){
                    this.first = next;
                } else {
                    //删除操作，即将前驱的next指针指向当前链结点的next，链表中将去当前链结点这一环
                    previous.next = next;
                }
                break;
            } else if (cur.next == null){
                //当前链结点不是目标且下一个链结点为null，证明没有要删除的链结点
                break;
            }

            //当前链结点不是要删除的目标，则向后继续寻找
            next = next.next;
            cur = cur.next;
            previous = cur;
        }
        return link;
    }

    /**
     * 查找属性为指定值的链结点
     * @param key
     * @return
     */
    public Link find(int key){
        Link link = null;
        Link cur = first;
        Link next = null;
        Link previous = null;
        while (cur != null){
            if(cur.age == key){
                link = cur;
                break;
            } else if (cur.next == null) {
                //当前链结点不是要找的目标且下一个链结点为null，则证明没有找到目标
                break;
            }
            //当前链结点不是要找的目标且存在下一个链结点，则向后继续寻找
            next = next.next;
            cur = cur.next;
            previous = cur;
        }
        return link;
    }

    /**
     * 判空
     * @return
     */
    public boolean isEmpty(){
        return (first == null);
    }

    public static void main(String[] args) throws Exception {

        LinkList linkList = new LinkList();
        Link linkOne = new Link(1,"一");
        Link linkTwo = new Link(2,"二");
        Link linkThree = new Link(3,"三");
        Link linkFour = new Link(4,"亖");
        linkList.inserFirst(linkOne);
        linkList.inserFirst(linkThree);
        linkList.inserFirst(linkFour);
        linkList.inserFirst(linkTwo);
        linkList.displayList();
        System.out.println("1--------------------------------");
        linkList.find(4);
        System.out.println("2--------------------------------");
        linkList.deleteFirst();
        linkList.displayList();
        System.out.println("3--------------------------------");
        linkList.delete(3);
        linkList.displayList();
        System.out.println("4--------------------------------");
    }
}

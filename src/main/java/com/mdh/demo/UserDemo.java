package com.mdh.demo;

import java.util.*;

/**
 * Created by madonghao on 2018/9/12.
 */
public class UserDemo {
    public static void main(String[] args) {

        User user4 = new User();
        user4.setName("小明");
        user4.setAge(24);
        user4.setSex(1);

        User user1 = new User();
        user1.setName("小明");
        user1.setAge(25);
        user1.setSex(3);

        User user2 = new User();
        user2.setName("小明");
        user2.setAge(26);
        user2.setSex(2);

        User user3 = new User();
        user3.setName("小刚");
        user3.setAge(26);
        user3.setSex(1);

        List<User> userList = new ArrayList<>();
        userList.add(user4);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        userList.sort(Comparator.comparing(User::getName).thenComparing(User::getAge));
        userList.forEach(x -> System.out.println(x));
        //userId = (userId == null) ? "" : userId;
        //String name = (user2.getTitle() == null) ? user2.getName() : user1.getTitle();

        //List<User> result = userList.stream().filter(u -> u.getName().equals("小明") ).collect(Collectors.toList());
        //result.forEach(x -> System.out.println(x));

        //System.out.println("************");
        /*List<User> distinctList = userList.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getName()))),
                        ArrayList::new));
        distinctList.forEach(x -> System.out.println(x));*/
        SET = new HashSet<String>();
        SET.add("ASC");
        SET.add("DESC");
        for(int i = 0 ; i < STR_CHSR.length() ; i ++){
            SET.add(Character.toString(STR_CHSR.charAt(i)));
        }

        Iterator<String> iter = SET.iterator();
        while (iter.hasNext()){
            System.out.println(iter.next());
        }
    }
    public static final String STR_CHSR = " \t\r\n\f";
    private static Set<String> SET = null;
}

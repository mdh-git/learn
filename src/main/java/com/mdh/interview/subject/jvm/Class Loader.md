# ClassLoader
## 介绍
~~~
ClassLoader类的介绍可以总结出这个类的作用就是根据一个指定的类的全限定名,找到对应的Class字节码文件,
然后加载它转化成一个java.lang.Class类的一个实例.

类加载器的划分:
　　　大部分java程序会使用以下3中系统提供的类加载器:

启动类加载器(Bootstrap ClassLoader):
        (负责加载 JVM 运行时核心类,加载System.getProperty("sun.boot.class.path")所指定的路径或jar)
　　　　这个类加载器负责将\lib目录下的类库加载到虚拟机内存中,用来加载java的核心库,此类加载器并不继承于java.lang.ClassLoader,
       不能被java程序直接调用,代码是使用C++编写的.是虚拟机自身的一部分.

扩展类加载器(Extendsion ClassLoader):
       这个类加载器负责加载\lib\ext目录下的类库,用来加载java的扩展库,开发者可以直接使用这个类加载器.
       (负责加载 JVM 扩展类，比如 swing 系列、内置的 js 引擎、xml 解析器 等等，这些库名通常以 javax 开头，它们的 jar 包位于 JAVAHOME/lib/rt.jar文件中.
        加载System.getProperty("java.ext.dirs")所指定的路径或jar。
        在使用Java运行程序时，也可以指定其搜索路径，例如：java -Djava.ext.dirs=d:\projects\testproj\classes HelloWorld。)

应用程序类加载器(Application ClassLoader):
       这个类加载器负责加载用户类路径(CLASSPATH)下的类库,一般我们编写的java类都是由这个类加载器加载,这个类加载器是CLassLoader中的getSystemClassLoader()方法的返回值,
       所以也称为系统类加载器.一般情况下这就是系统默认的类加载器.

　　除此之外,我们还可以加入自己定义的类加载器,以满足特殊的需求,需要继承java.lang.ClassLoader类.


         类加载器之间的层次关系图:

            引导类加载器(启动类加载器(Bootstrap ClassLoader))
                            |
                            |
            扩展类加载器(Extendsion ClassLoader)
                            |
                            |
            系统类加载器(应用程序类加载器(Application ClassLoader))
                |                                |
                |                                |
    开发人员编写的类加载器A                


ClassLoader loader = TestClassLoader.class.getClassLoader();

loader.toString()                 sun.misc.Launcher$AppClassLoader@18b4aac2

loader.getParent().toString()     sun.misc.Launcher$ExtClassLoader@2f4d3709

loader.getParent().getParent()    null

第一行打印的是应用程序类加载器(默认加载器),
第二行打印的是其父类加载器,扩展类加载器,
按照我们的想法第三行应该打印启动类加载器的,这里却返回的null,原因是getParent(),返回时null的话,就默认使用启动类加载器作为父加载器.
~~~

## 类加载器的双亲委派模型:
~~~
private final ClassLoader parent; 
protected Class<?> loadClass(String name, boolean resolve)
        throws ClassNotFoundException
    {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();
                try {
                    if (parent != null) {
                        c = parent.loadClass(name, false);
                    } else {
                        c = findBootstrapClassOrNull(name);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }
                //父亲都加载不到了,那就只能往下找,看下子类是否重写了findClass,这也是双亲委派机制的破坏
                if (c == null) {
                    // If still not found, then invoke findClass in order
                    // to find the class.
                    long t1 = System.nanoTime();
                    c = findClass(name);

                    // this is the defining class loader; record the stats
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }


双亲委派模型是一种组织类加载器之间关系的一种规范,他的工作原理是:
如果一个类加载器收到了类加载的请求,它不会自己去尝试加载这个类,而是把这个请求委派给父类加载器去完成,这样层层递进,
最终所有的加载请求都被传到最顶层的启动类加载器中,只有当父类加载器无法完成这个加载请求(它的搜索范围内没有找到所需的类)时,才会交给子类加载器去尝试加载.

这样的好处是:java类随着它的类加载器一起具备了带有优先级的层次关系.
这是十分必要的,比如java.langObject,它存放在\jre\lib\rt.jar中,它是所有java类的父类,因此无论哪个类加载都要加载这个类,最终所有的加载请求都汇总到顶层的启动类加载器中,
因此Object类会由启动类加载器来加载,所以加载的都是同一个类,如果不使用双亲委派模型,由各个类加载器自行去加载的话,系统中就会出现不止一个Object类,应用程序就会全乱了.

~~~

## 关于双亲委派机制的破坏(jdbc、tomcat、spring)
~~~
也就是不是一直向上的,顶级父类都加载不到,那就只能向下了,即先想上,再向下

ClassLoader 里面有三个重要的方法 loadClass()、findClass() 和 defineClass()。

loadClass() 方法是加载目标类的入口，它首先会查找当前 ClassLoader 以及它的双亲里面是否已经加载了目标类，
如果没有找到就会让双亲尝试加载，如果双亲都加载不了，就会调用 findClass() 让自定义加载器自己来加载目标类。

ClassLoader 的 findClass() 方法是需要子类来覆盖的，不同的加载器将使用不同的逻辑来获取目标类的字节码。
拿到这个字节码之后再调用 defineClass() 方法将字节码转换成 Class 对象。

不要轻易覆盖 loadClass 方法。否则可能会导致自定义加载器无法加载内置的核心类库。
在使用自定义加载器时，要明确好它的父加载器是谁，将父加载器通过子类的构造器传入。
如果父类加载器是 null，那就表示父加载器是「根加载器」。

~~~

## Class.forName()()与ClassLoader.loadClass():
~~~
Class.forname():是一个静态方法,最常用的是Class.forname(String className);
根据传入的类的全限定名返回一个Class对象.该方法在将Class文件加载到内存的同时,会执行类的初始化.

Class.forName(className)方法，其实调用的方法是Class.forName(className,true,classloader);
注意看第2个boolean参数，它表示的意思，在loadClass后必须初始化。
在执行过此方法后，目标对象的 static块代码已经被执行，static参数也已经被初始化。

如: Class.forName("com.wang.HelloWorld");

-------------------------------------------------------------------------------------
ClassLoader.loadClass():这是一个实例方法,需要一个ClassLoader对象来调用该方法,
该方法将Class文件加载到内存时,并不会执行类的初始化,直到这个类第一次使用时才进行初始化.该方法因为需要得到一个ClassLoader对象,所以可以根据需要指定使用哪个类加载器.

ClassLoader.loadClass(className)方法，其实他调用的方法是ClassLoader.loadClass(className,false);
还是注意看第2个 boolean参数，该参数表示目标对象被装载后不进行链接，这就意味这不会去执行该类静态块中间的内容

如:ClassLoader cl=.......;
   cl.loadClass("com.wang.HelloWorld");

~~~

## 关于jvm类的加载
~~~
在Java中，类装载器把一个类装入Java虚拟机中，要经过三个步骤来完成：
装载、链接和初始化，
其中链接又可以分成校验、准备和解析三步，
除了解析外，其它步骤是严格按照顺序完成的，各个步骤的主要工作如下：

    装载：查找和导入类或接口的二进制数据； //byte[]

　　链接：执行下面的校验、准备和解析步骤，其中解析步骤是可以选择的；//包含了虚方法表的初始化

　　校验：检查导入类或接口的二进制数据的正确性； //魔数 babycafe?

　　准备：给类的静态变量分配并初始化存储空间； //分配内存空间

　　解析：将符号引用转成直接引用； 

　　初始化：激活类的静态变量的初始化Java代码和静态Java代码块。
//对应的为<clinit>()方法,该方法在多线环境中如果有多个线程同时去初始化一个类,那么久只有一个线程去执行。
//这也是我们写单例时,为什么可以使用静态内部类了,保证了内部重排序对外部线程时不可见的,具体可以见对DCL的分析
~~~

## ContextClassLoader
~~~
https://blog.csdn.net/qq_22912803/article/details/78065847
~~~
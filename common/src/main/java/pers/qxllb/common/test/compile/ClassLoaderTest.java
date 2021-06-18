package pers.qxllb.common.test.compile;

/**
 * TODO
 *
 * @author liangb
 * @version 1.0
 * @date 2021/6/11 18:24
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException {

        /**
         * 1.Class•forName有重载方法可以指定是否需要初始化，而默认的方法初始化设置为true这会初始化类执行链接和初始化操作
         * 2.ClassLoader是有类加载器的loadClass方法加载，传入的是false只会执行链接操作，而不会执行初始化操作
         */
        Class.forName("pers.qxllb.common.test.compile.ClassLoaderTest");

        //获取系统类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);//sun.misc.Launcher$AppClassLoader@18b4aac2

        //获取其上层：扩展类加载器
        ClassLoader extClassLoader = systemClassLoader.getParent();
        System.out.println(extClassLoader);//sun.misc.Launcher$ExtClassLoader@60e53b93

        //获取其上层：获取不到引导类加载器
        ClassLoader bootStrapLoader = extClassLoader.getParent();
        System.out.println(bootStrapLoader);//null


        //我们自己定义的类是由什么类加载器加载的:使用系统类加载器
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println(classLoader);//sun.misc.Launcher$AppClassLoader@18b4aac2


        //看看String是由什么类加载器加载的：使用引导类加载器
        ClassLoader classLoaderString = String.class.getClassLoader();
        System.out.println(classLoaderString);//null
    }
}

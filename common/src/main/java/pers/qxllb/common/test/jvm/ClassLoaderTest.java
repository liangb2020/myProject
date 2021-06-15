package pers.qxllb.common.test.jvm;

/**
 * TODO
 *
 * @author liangb
 * @version 1.0
 * @date 2021/6/11 18:24
 */
public class ClassLoaderTest {
    public static void main(String[] args) {

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

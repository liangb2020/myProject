package pers.qxllb.common.test.inner;

/**
 * TODO
 *
 * @author liangb
 * @version 1.0
 * @date 2021/6/22 12:47
 */
public class OutMethod {

    private String name;

    public void sayHello(){
        int a=0;
        class In{
            int j=a;
            public void showName(){
                int i=j;
            }

        }

    }

}

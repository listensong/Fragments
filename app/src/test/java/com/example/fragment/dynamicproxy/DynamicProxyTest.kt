package com.example.fragment.dynamicproxy

import org.junit.Test
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class DynamicProxyTest {

    @Test
    fun testIt() {
        val dynamicProxy = DynamicProxyObject()
        val realOperator = RealOperator()
        val operatorProxy = dynamicProxy.newProxyInstance(realOperator) as IOperation
        println("=>" + operatorProxy.start())
    }

    class RealOperator : IOperation {
        override fun start(): Int {
            println("==>>RealOperator")
            return 10086
        }
    }

    class DynamicProxyObject : InvocationHandler {
        private var realObject: Any? = null

        fun newProxyInstance(realObject: Any): Any? {
            this.realObject = realObject
            println("==>>开始生成代理类")
            // Proxy.newProxyInstance（）
            // 作用：根据指定的类装载器、一组接口 & 调用处理器 生成动态代理类实例，并最终返回
            // 参数说明：
            // 参数1：指定产生代理对象的类加载器，需要将其指定为和目标对象同一个类加载器
            // 参数2：interfaces表示委托类的接口，生成代理类时需要实现这些接口
            // 即要给目标对象提供一组什么接口。若提供了一组接口给它，
            // 那么该代理对象就默认实现了该接口，这样就能调用这组接口中的方法
            // 参数3：InvocationHandler实现类对象，负责连接代理类和委托类的中间类
            return Proxy.newProxyInstance(
                realObject.javaClass.classLoader,
                realObject.javaClass.interfaces,
                this
            )
        }

        /**
         * 可以在这里增加一些自己需要的逻辑
         * @param proxy  也就是当前的代理对象Object(不是被代理对象)
         * @param method 当前执行的方法
         * @param args   当前执行方法的参数
         */
        override fun invoke(
            proxy: Any?,
            method: Method?,
            args: Array<out Any>?
        ): Any? {
            println("==>>开始调用真实对象")
            // 这里的args:Array? , 与不定长参数是不兼容的。
            // 需要在args前加上伸展操作符（*），表示将已有的数组内容传递给函数
            return method?.invoke(realObject, *(args ?: emptyArray()))
        }
    }
}

interface IOperation {
    fun start(): Int
}
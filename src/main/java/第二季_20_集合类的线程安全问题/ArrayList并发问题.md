> 视频P20到P23

# ArrayList类的java.util.ConcurrentModificationException
## 原因
多个线程同时修改list的内容
## 解决方法
- 使用Vector类
- 使用Collections.synchronizedList()方法，参数为一个List对象
- 使用CopyOnWriteArrayList类

# CopyOnWriteArrayList类的add()方法源码
```
public boolean add(E e) {
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
        Object[] elements = getArray();
        int len = elements.length;
        Object[] newElements = Arrays.copyOf(elements, len + 1);
        newElements[len] = e;
        setArray(newElements);
        return true;
    } finally {
        lock.unlock();
    }
}
```

# HashSet类也是线程不安全的
## 解决方法
- 使用Vector类
- 使用Collections.synchronizedSet()方法，参数为一个Set对象
- 使用CopyOnWriteArraySet类
> CopyOnWriteArraySet类的底层使用了CopyOnWriteArrayList类

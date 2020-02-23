> 视频P17到P19

# ABA问题的产生
![ABA问题.png](http://ww1.sinaimg.cn/large/005IGVTXly1gbp16d19t3j31hc0u04lc.jpg)
如果只管头尾的值是否一致，那就可以忽略ABA问题，不然的话就是有问题的  

# 如何解决ABA问题
原子类已经不够用了，使用AtomicReference<V>类+版本号  
AtomicStampedReference
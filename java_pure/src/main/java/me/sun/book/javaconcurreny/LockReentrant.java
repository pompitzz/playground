package me.sun.book.javaconcurreny;

public class LockReentrant {
}

class Parent {
    public synchronized void doSomeThing() {
    }
}



class Child extends Parent {
    @Override
    public synchronized void doSomeThing() {
        super.doSomeThing(); // 재진입성을 가지기 때문에 부모의 동기화 메서드를 호출하여도 데드락에 걸리지 않는다.
    }
}

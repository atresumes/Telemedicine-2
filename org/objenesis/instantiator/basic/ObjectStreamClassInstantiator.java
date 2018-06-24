package org.objenesis.instantiator.basic;

import java.io.ObjectStreamClass;
import java.lang.reflect.Method;
import org.objenesis.ObjenesisException;
import org.objenesis.instantiator.ObjectInstantiator;

public class ObjectStreamClassInstantiator<T> implements ObjectInstantiator<T> {
    private static Method newInstanceMethod;
    private final ObjectStreamClass objStreamClass;

    private static void initialize() {
        if (newInstanceMethod == null) {
            try {
                newInstanceMethod = ObjectStreamClass.class.getDeclaredMethod("newInstance", new Class[0]);
                newInstanceMethod.setAccessible(true);
            } catch (Throwable e) {
                throw new ObjenesisException(e);
            } catch (Throwable e2) {
                throw new ObjenesisException(e2);
            }
        }
    }

    public ObjectStreamClassInstantiator(Class<T> type) {
        initialize();
        this.objStreamClass = ObjectStreamClass.lookup(type);
    }

    public T newInstance() {
        try {
            return newInstanceMethod.invoke(this.objStreamClass, new Object[0]);
        } catch (Throwable e) {
            throw new ObjenesisException(e);
        }
    }
}

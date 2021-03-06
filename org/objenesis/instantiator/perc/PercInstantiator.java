package org.objenesis.instantiator.perc;

import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import org.objenesis.ObjenesisException;
import org.objenesis.instantiator.ObjectInstantiator;

public class PercInstantiator<T> implements ObjectInstantiator<T> {
    private final Method newInstanceMethod;
    private final Object[] typeArgs = new Object[]{null, Boolean.FALSE};

    public PercInstantiator(Class<T> type) {
        this.typeArgs[0] = type;
        try {
            this.newInstanceMethod = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Boolean.TYPE});
            this.newInstanceMethod.setAccessible(true);
        } catch (Throwable e) {
            throw new ObjenesisException(e);
        } catch (Throwable e2) {
            throw new ObjenesisException(e2);
        }
    }

    public T newInstance() {
        try {
            return this.newInstanceMethod.invoke(null, this.typeArgs);
        } catch (Throwable e) {
            throw new ObjenesisException(e);
        }
    }
}

package org.objenesis.instantiator.android;

import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import org.objenesis.ObjenesisException;
import org.objenesis.instantiator.ObjectInstantiator;

public class Android10Instantiator<T> implements ObjectInstantiator<T> {
    private final Method newStaticMethod = getNewStaticMethod();
    private final Class<T> type;

    public Android10Instantiator(Class<T> type) {
        this.type = type;
    }

    public T newInstance() {
        try {
            return this.type.cast(this.newStaticMethod.invoke(null, new Object[]{this.type, Object.class}));
        } catch (Throwable e) {
            throw new ObjenesisException(e);
        }
    }

    private static Method getNewStaticMethod() {
        try {
            Method newStaticMethod = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Class.class});
            newStaticMethod.setAccessible(true);
            return newStaticMethod;
        } catch (Throwable e) {
            throw new ObjenesisException(e);
        } catch (Throwable e2) {
            throw new ObjenesisException(e2);
        }
    }
}

package util;

import java.io.IOException;

@FunctionalInterface
public interface Supplier<T> {
     T get() throws IOException;
}

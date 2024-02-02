package java.generics.examples;

import java.util.List;

// fuente original: WildcardError.java
public class ComodinError {

    void foo(List<?> i) {
        i.set(0, i.get(0));
    }
}

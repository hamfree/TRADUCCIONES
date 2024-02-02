package java.IandI.examples.defaultmethods;

import java.util.*;
import java.util.stream.*;
import java.lang.*;
import java.util.function.*;

public interface SortFunction extends Function<Comparator<Carta>, Baraja> {

    public Baraja apply(Comparator<Carta> cartaComparator);
}

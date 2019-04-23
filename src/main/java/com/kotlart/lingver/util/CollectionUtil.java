package com.kotlart.lingver.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CollectionUtil {
    private final static Random RANDOM = new Random();

    public static void removeRandomlyFromList(List<?> list, int numberOfElementsToDelete) {
        for (int i = 0; i < numberOfElementsToDelete; i++) {
            list.remove(RANDOM.nextInt(list.size()));
        }
    }

    public static <T> List<T> createListFromCollectionWithoutElements(Collection<T> collection, Collection<T> elementsToRemove) {
        List<T> list = new ArrayList<>(collection);
        list.removeAll(elementsToRemove);
        return list;
    }

    public static <T> boolean hasDuplicate(Iterable<T> all) {
        Set<T> set = new HashSet<>();
        // Set#add returns false if the set does not change, which
        // indicates that a duplicate element has been added.
        for (T each : all) {
            if (!set.add(each)) {
                return true;
            }
        }
        return false;
    }

}

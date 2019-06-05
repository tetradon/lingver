package com.kotlart.lingver.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

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
}

package DataStructures;

import util.Pair;

public class Map<T, V> {
    private final ArrayList<Pair<T, V>> map;

    public Map() {
        map = new ArrayList<>();
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public void clear() {
        map.clear();
    }

    public V remove(T key) {
        for (int i = 0; i < size(); i++) {
            if (map.get(i).getFst().equals(key)) {
                V toReturn = map.get(i).getSnd();
                map.remove(i);
                return toReturn;
            }
        }

        return null;
    }

    public V put(T key, V value) {
        map.add(new Pair<>(key, value));

        return value;
    }

    public V get(T key) {
        for (Pair<T, V> entry : map) {
            if (entry.getFst() == key) {
                return entry.getSnd();
            }
        }

        return null;
    }

    public boolean containsKey(T key) {
        for (Pair<T, V> entry : map) {
            if (entry.getFst() == key) {
                return true;
            }
        }

        return false;
    }

    public boolean containsValue(V value) {
        for (Pair<T, V> entry : map) {
            if (entry.getSnd() == value) {
                return true;
            }
        }

        return false;
    }

    public Collection<Pair<T, V>> entrySet() {
        return new ArrayList<>(map);
    }

    public Collection<T> keys() {
        ArrayList<T> keys = new ArrayList<>();

        for (Pair<T, V> entry : map) {
            keys.add(entry.getFst());
        }

        return keys;
    }

    public Collection<V> values() {
        ArrayList<V> values = new ArrayList<>();

        for (Pair<T, V> entry : map) {
            values.add(entry.getSnd());
        }

        return values;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}

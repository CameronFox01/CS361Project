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

    public void clear() {
        map.clear();
    }

    public V put(T key, V value) {
        map.add(new Pair<>(key, value));

        return value;
    }

    public V get(T key) {
        for (Pair<T, V> entry : map) {
            if (entry.fst == key) {
                return entry.snd;
            }
        }

        return null;
    }

    public boolean containsKey(T key) {
        for (Pair<T, V> entry : map) {
            if (entry.fst == key) {
                return true;
            }
        }

        return false;
    }

    public boolean containsValue(V value) {
        for (Pair<T, V> entry : map) {
            if (entry.snd == value) {
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
            keys.add(entry.fst);
        }

        return keys;
    }

    public Collection<V> values() {
        ArrayList<V> values = new ArrayList<>();

        for (Pair<T, V> entry : map) {
            values.add(entry.snd);
        }

        return values;
    }
}

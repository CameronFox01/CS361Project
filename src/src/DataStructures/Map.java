package DataStructures;

import util.Pair;

public class Map<K, V> {
    private final ArrayList<Pair<K, V>> map;

    public Map() {
        map = new ArrayList<>();
    }

    public Map(Map<K, V> other) {
        map = new ArrayList<>(other.entrySet());
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

    public V remove(K key) {
        for (int i = 0; i < size(); i++) {
            if (map.get(i).getFst().equals(key)) {
                V toReturn = map.get(i).getSnd();
                map.remove(i);
                return toReturn;
            }
        }

        return null;
    }

    public boolean remove(K key, V value) {
        V val = get(key);

        if (val == null) return false;

        if (val == value) {
            remove(key);
            return true;
        } else {
            return false;
        }
    }

    public V put(K key, V value) {
        if (containsKey(key)) {
            for (Pair<K, V> entry : map) {
                if (entry.getFst().equals(key)) {
                    entry.setSnd(value);
                    break;
                }
            }
        } else {
            map.add(new Pair<>(key, value));
        }

        return value;
    }

    public boolean replace(K key, V value) {
        if (containsKey(key)) {
            put(key, value);
            return true;
        } else {
            return false;
        }
    }

    public boolean replace(K key, V oldValue, V newValue) {
        V v = get(key);

        if (v == null) return false;

        if (v == oldValue) {
            replace(key, newValue);
            return true;
        } else {
            return false;
        }
    }

    public V get(K key) {
        for (Pair<K, V> entry : map) {
            if (entry.getFst() == key) {
                return entry.getSnd();
            }
        }

        return null;
    }

    public boolean containsKey(K key) {
        for (Pair<K, V> entry : map) {
            if (entry.getFst() == key) {
                return true;
            }
        }

        return false;
    }

    public boolean containsValue(V value) {
        for (Pair<K, V> entry : map) {
            if (entry.getSnd() == value) {
                return true;
            }
        }

        return false;
    }

    public List<Pair<K, V>> entrySet() {
        return new ArrayList<>(map);
    }

    public List<K> keys() {
        ArrayList<K> keys = new ArrayList<>();

        for (Pair<K, V> entry : map) {
            keys.add(entry.getFst());
        }

        return keys;
    }

    public List<V> values() {
        ArrayList<V> values = new ArrayList<>();

        for (Pair<K, V> entry : map) {
            values.add(entry.getSnd());
        }

        return values;
    }

    @Override
    public String toString() {
        if (size() == 0) {
            return "{}";
        } else if (size() == 1) {
            return "{" + map.get(0).getFst() + "=" + map.get(0).getSnd() + "}";
        } else {
            StringBuilder builder = new StringBuilder("{\n");

            for (int i = 0; i < size() - 1; i++) {
                builder.append(map.get(i).getFst()).append("=").append(map.get(i).getSnd()).append(", \n");
            }

            builder.append(map.getLast().getFst()).append("=").append(map.getLast().getSnd()).append("\n}");

            return builder.toString();
        }
    }
}

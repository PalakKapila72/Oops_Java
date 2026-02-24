import java.util.HashMap;

public class AssetStore {

    HashMap<String, Asset> assets = new HashMap<>();

    public void addAsset(Asset a) {
        assets.put(a.assetId, a);
    }

    public Asset findAsset(String assetId)
            throws NullPointerException {

        Asset a = assets.get(assetId);

        if (a == null) {
            throw new NullPointerException("Asset not found: " + assetId);
        }

        return a;
    }

    public void markBorrowed(Asset a)
            throws IllegalStateException {

        if (!a.available) {
            throw new IllegalStateException("Asset already borrowed");
        }

        a.available = false;
    }
}

import java.time.LocalDate;

public class CheckoutService {

    AssetStore store;
    java.util.HashMap<String, Student> students;

    public CheckoutService(AssetStore store,
                           java.util.HashMap<String, Student> students) {
        this.store = store;
        this.students = students;
    }

    public String checkout(CheckoutRequest req)
            throws IllegalArgumentException,
                   IllegalStateException,
                   SecurityException,
                   NullPointerException {

        // 1. Validate request fields
        ValidationUtil.validateUid(req.uid);
        ValidationUtil.validateAssetId(req.assetId);
        ValidationUtil.validateHours(req.hoursRequested);

        // 2. Fetch student and asset
        Student student = students.get(req.uid);
        if (student == null) {
            throw new NullPointerException("Student not found: " + req.uid);
        }

        Asset asset = store.findAsset(req.assetId);

        // 3. Apply student and asset policies
        student.validatePolicy();
        asset.validatePolicy(req.uid);

        // 4. Realistic constraints

        int hours = req.hoursRequested;

        if (hours == 6) {
            System.out.println("Note: Max duration selected. Return strictly on time.");
        }

        if (asset.assetName.contains("Cable") && hours > 3) {
            hours = 3;
            System.out.println("Policy applied: Cables max 3 hours. Updated to 3.");
        }

        // 5. Mark borrowed
        store.markBorrowed(asset);
        student.currentBorrowCount++;

        // 6. Generate receipt
        return "TXN-" + LocalDate.now().toString().replace("-", "")
                + "-" + asset.assetId + "-" + student.uid;
    }
}

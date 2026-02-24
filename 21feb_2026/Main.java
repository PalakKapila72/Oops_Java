import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        // Create students
        Student s1 = new Student("KRG20281", "Rahul", 0, 0);
        Student s2 = new Student("ABC12345", "Aman", 100, 0); // fine
        Student s3 = new Student("XYZ56789", "Riya", 0, 2);  // limit reached

        HashMap<String, Student> studentMap = new HashMap<>();
        studentMap.put(s1.uid, s1);
        studentMap.put(s2.uid, s2);
        studentMap.put(s3.uid, s3);

        // Create assets
        Asset a1 = new Asset("LAB-101", "HDMI Cable", true, 1);
        Asset a2 = new Asset("LAB-102", "Projector", true, 3);
        Asset a3 = new Asset("LAB-103", "Router", false, 2);

        AssetStore store = new AssetStore();
        store.addAsset(a1);
        store.addAsset(a2);
        store.addAsset(a3);

        CheckoutService service = new CheckoutService(store, studentMap);

        // Requests
        CheckoutRequest r1 = new CheckoutRequest("KRG20281", "LAB-101", 5); // success
        CheckoutRequest r2 = new CheckoutRequest("KRG20281", "LAB-999", 2); // invalid asset
        CheckoutRequest r3 = new CheckoutRequest("ABC12345", "LAB-102", 2); // fine issue

        CheckoutRequest[] requests = {r1, r2, r3};

        for (CheckoutRequest req : requests) {

            try {
                String receipt = service.checkout(req);
                System.out.println("SUCCESS: " + receipt);

            } catch (IllegalArgumentException e) {
                AuditLogger.logError(e);

            } catch (NullPointerException e) {
                AuditLogger.logError(e);

            } catch (SecurityException e) {
                AuditLogger.logError(e);

            } catch (IllegalStateException e) {
                AuditLogger.logError(e);

            } finally {
                AuditLogger.log("Attempt finished for UID="
                        + req.uid + ", asset=" + req.assetId);
            }
        }
    }
}

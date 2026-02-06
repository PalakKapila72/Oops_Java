abstract class AbstractAssessmentFlow {

    public final void executeAssessment() {
        validate();
        prepare();
        evaluate();
        publishResult();
    }

    private void validate() {
        System.out.println("Validation: Student identity + eligibility verified.");
    }

    protected void prepare() {
        System.out.println("Preparation: Answer scripts loaded for evaluation.");
    }

    protected abstract void evaluate();

    protected void publishResult() {
        System.out.println("Result Published: Marks updated in university portal.\n");
    }
}

interface AutoAssessment {

    default void evaluate() {
        System.out.println("AutoAssessment: AI-based automatic grading executed.");
    }
}

interface ManualAssessment {

    default void evaluate() {
        System.out.println("ManualAssessment: Teacher-based manual grading executed.");
    }
}

public class UnifiedAssessmentExecutor
        extends AbstractAssessmentFlow
        implements AutoAssessment, ManualAssessment {

    private final boolean isProctored;

    public UnifiedAssessmentExecutor(boolean isProctored) {
        this.isProctored = isProctored;
    }

    @Override
    protected void evaluate() {

        System.out.println("UnifiedAssessmentExecutor: Selecting evaluation mode...");

        if (isProctored) {
            ManualAssessment.super.evaluate();
        } else {
            AutoAssessment.super.evaluate();
        }
    }

    public static void main(String[] args) {

        System.out.println("===== Non-Proctored Exam (Auto Evaluation) =====");
        UnifiedAssessmentExecutor auto =
                new UnifiedAssessmentExecutor(false);
        auto.executeAssessment();

        System.out.println("===== Proctored Exam (Manual Evaluation) =====");
        UnifiedAssessmentExecutor manual =
                new UnifiedAssessmentExecutor(true);
        manual.executeAssessment();
    }
}

package third;

import binaryOperations.IntersectionOperator;
import binaryOperations.Operator;
import binaryOperations.SubtractionOperator;
import binaryOperations.UnionOperator;

public class BinaryOperations {
    private final Operator UNION = new UnionOperator();
    private final Operator SUBTRACT = new SubtractionOperator();
    private final Operator INTERSECT = new IntersectionOperator();
    private IModel model1;
    private IModel model2;

    public BinaryOperations(IModel model1, IModel model2) {
        this.model1 = model1;
        this.model2 = model2;
    }

    public BinaryOperations() {
    }

    public IModel intersect() {
        if (nullOperation()) return null;
        return INTERSECT.operate(model1,model2);
    }



    public IModel union() {
        if (nullOperation()) return null;
        return UNION.operate(model1,model2);
    }


    public IModel subtract() {
        if (nullOperation()) return null;
        return SUBTRACT.operate(model1,model2);
    }

    public boolean nullOperation(){
        return model1 == null || model2 == null;
    }

    public IModel getModel1() {
        return model1;
    }

    public void setModel1(IModel model1) {
        this.model1 = model1;
    }

    public IModel getModel2() {
        return model2;
    }

    public void setModel2(IModel model2) {
        this.model2 = model2;
    }

    public void clear() {
        model1 = null;
        model2 = null;
    }
}

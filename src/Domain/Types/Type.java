package Domain.Types;

import Domain.Clonable;
import Domain.Values.Value;

public interface Type extends Clonable<Type> {
    Value getDefault();
}

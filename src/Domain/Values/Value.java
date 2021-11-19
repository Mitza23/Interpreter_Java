package Domain.Values;

import Domain.Clonable;
import Domain.Types.Type;

public interface Value extends Clonable<Value> {
    Type getType();
}
